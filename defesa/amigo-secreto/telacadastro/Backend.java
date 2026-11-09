import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public class Backend {
    private static final int PORTA = 8080;
    private static final Sorteio SORTEIO = carregarSorteio();

    public static void main(String[] args) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress("127.0.0.1", PORTA), 0);
        servidor.createContext("/api/participantes", Backend::participantes);
        servidor.createContext("/api/resultado", Backend::resultado);
        servidor.createContext("/api/presente", Backend::presente);
        servidor.start();
        System.out.println("Back-end Java disponível em http://localhost:" + PORTA);
    }

    private static Sorteio carregarSorteio() {
        try {
            Sorteio sorteio = Sorteio.carregar();
            sorteio.configurar(BigDecimal.ZERO, new BigDecimal("300"), LocalDate.of(2026, 12, 25));
            return sorteio;
        } catch (IOException | RuntimeException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static void participantes(HttpExchange troca) throws IOException {
        cors(troca);
        if (troca.getRequestMethod().equals("OPTIONS")) { troca.sendResponseHeaders(204, -1); return; }
        try {
            if (troca.getRequestMethod().equals("GET")) {
                synchronized (SORTEIO) {
                    StringBuilder json = new StringBuilder("[");
                    for (Participante p : SORTEIO.getParticipantes()) {
                        if (json.length() > 1) json.append(',');
                        json.append("{\"nome\":").append(citar(p.getNome())).append('}');
                    }
                    responder(troca, 200, json.append(']').toString());
                }
            } else if (troca.getRequestMethod().equals("POST")) {
                Map<String, String> form = lerFormulario(troca);
                Participante pessoa = new Participante(obrigatorio(form, "nome"), obrigatorio(form, "email"));
                validarEmail(pessoa.getEmail());
                pessoa.setDesejos(new String[]{form.getOrDefault("desejo1", ""), form.getOrDefault("desejo2", ""), form.getOrDefault("desejo3", "")});
                synchronized (SORTEIO) {
                    SORTEIO.adicionarParticipante(pessoa);
                    SORTEIO.salvar();
                }
                responder(troca, 201, "{\"ok\":true}");
            } else responder(troca, 405, erro("Método não permitido."));
        } catch (IllegalArgumentException | IllegalStateException e) {
            responder(troca, 400, erro(e.getMessage()));
        } catch (IOException e) {
            responder(troca, 500, erro("Não foi possível salvar os dados."));
        }
    }

    private static void resultado(HttpExchange troca) throws IOException {
        cors(troca);
        if (troca.getRequestMethod().equals("OPTIONS")) { troca.sendResponseHeaders(204, -1); return; }
        if (!troca.getRequestMethod().equals("POST")) { responder(troca, 405, erro("Método não permitido.")); return; }
        try {
            Map<String, String> form = lerFormulario(troca);
            String nome = obrigatorio(form, "nome"), email = obrigatorio(form, "email");
            validarEmail(email);
            synchronized (SORTEIO) {
                Participante pessoa = SORTEIO.getParticipantes().stream()
                        .filter(p -> igual(p.getNome(), nome) && igual(p.getEmail(), email)).findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Confira o nome e o e-mail cadastrados."));
                if (!SORTEIO.foiRealizado()) SORTEIO.realizarSorteio();
                SORTEIO.salvar();
                Participante amigo = SORTEIO.consultarResultado(pessoa.getEmail());
                StringBuilder json = new StringBuilder("{\"amigo\":").append(citar(amigo.getNome())).append(",\"desejos\":[");
                String[] desejos = amigo.getDesejos();
                for (int i = 0; i < desejos.length; i++) {
                    if (i > 0) json.append(',');
                    json.append(citar(desejos[i]));
                }
                json.append("],\"comprado\":").append(SORTEIO.presenteComprado(pessoa.getEmail())).append('}');
                responder(troca, 200, json.toString());
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            responder(troca, 400, erro(e.getMessage()));
        } catch (IOException e) {
            responder(troca, 500, erro("Não foi possível salvar o sorteio."));
        }
    }

    private static void presente(HttpExchange troca) throws IOException {
        cors(troca);
        if (troca.getRequestMethod().equals("OPTIONS")) { troca.sendResponseHeaders(204, -1); return; }
        if (!troca.getRequestMethod().equals("POST")) { responder(troca, 405, erro("Método não permitido.")); return; }
        try {
            Map<String, String> form = lerFormulario(troca);
            String email = obrigatorio(form, "email");
            synchronized (SORTEIO) {
                if (SORTEIO.consultarResultado(email) == null) throw new IllegalArgumentException("Resultado não encontrado.");
                SORTEIO.confirmarPresente(email, Boolean.parseBoolean(form.getOrDefault("comprado", "false")));
                SORTEIO.salvar();
            }
            responder(troca, 200, "{\"ok\":true}");
        } catch (IllegalArgumentException e) {
            responder(troca, 400, erro(e.getMessage()));
        } catch (IOException e) {
            responder(troca, 500, erro("Não foi possível salvar a confirmação."));
        }
    }

    private static Map<String, String> lerFormulario(HttpExchange troca) throws IOException {
        byte[] corpo = troca.getRequestBody().readNBytes(8193);
        if (corpo.length > 8192) throw new IllegalArgumentException("Formulário muito grande.");
        Map<String, String> dados = new HashMap<>();
        for (String par : new String(corpo, StandardCharsets.UTF_8).split("&")) {
            if (par.isEmpty()) continue;
            String[] partes = par.split("=", 2);
            String chave = URLDecoder.decode(partes[0], StandardCharsets.UTF_8);
            String valor = partes.length == 2 ? URLDecoder.decode(partes[1], StandardCharsets.UTF_8) : "";
            dados.put(chave, valor.trim());
        }
        return dados;
    }

    private static String obrigatorio(Map<String, String> form, String campo) {
        String valor = form.getOrDefault(campo, "").trim();
        if (valor.isEmpty()) throw new IllegalArgumentException("Preencha nome e e-mail.");
        return valor;
    }

    private static boolean igual(String a, String b) { return a.trim().equalsIgnoreCase(b.trim()); }
    private static void validarEmail(String email) {
        if (email.length() > 320 || !email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))
            throw new IllegalArgumentException("Informe um e-mail válido.");
    }

    private static void cors(HttpExchange troca) {
        troca.getResponseHeaders().set("Access-Control-Allow-Origin", "http://localhost:5173");
        troca.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        troca.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }

    private static String erro(String mensagem) { return "{\"erro\":" + citar(mensagem == null ? "Requisição inválida." : mensagem) + "}"; }

    private static String citar(String valor) {
        StringBuilder json = new StringBuilder("\"");
        for (char c : valor.toCharArray()) {
            switch (c) {
                case '"' -> json.append("\\\"");
                case '\\' -> json.append("\\\\");
                case '\n' -> json.append("\\n");
                case '\r' -> json.append("\\r");
                case '\t' -> json.append("\\t");
                default -> { if (c < 32) json.append(String.format("\\u%04x", (int)c)); else json.append(c); }
            }
        }
        return json.append('"').toString();
    }

    private static void responder(HttpExchange troca, int status, String corpo) throws IOException {
        byte[] bytes = corpo.getBytes(StandardCharsets.UTF_8);
        troca.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        troca.sendResponseHeaders(status, bytes.length);
        try (var saida = troca.getResponseBody()) { saida.write(bytes); }
    }
}
