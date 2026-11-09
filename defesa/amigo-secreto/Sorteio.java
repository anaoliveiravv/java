import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Sorteio {
    private static final String ARQUIVO = "amigo-secreto.properties";

    private final List<Participante> participantes = new ArrayList<>();
    private final Map<String, Participante> resultados = new HashMap<>();
    private final Set<String> presentesComprados = new HashSet<>();
    private BigDecimal valorMinimo = BigDecimal.ZERO;
    private BigDecimal valorMaximo = new BigDecimal("100");
    private LocalDate dataTroca;

    public static Sorteio carregar() throws IOException {
        Properties dados = new Properties();
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new Sorteio();
        try (InputStream entrada = new FileInputStream(arquivo)) { dados.load(entrada); }
        Sorteio sorteio = new Sorteio();
        sorteio.valorMinimo = new BigDecimal(dados.getProperty("valor.minimo", "0"));
        sorteio.valorMaximo = new BigDecimal(dados.getProperty("valor.maximo", "100"));
        String data = dados.getProperty("data.troca", "");
        if (!data.isEmpty()) sorteio.dataTroca = LocalDate.parse(data);
        int quantidade = Integer.parseInt(dados.getProperty("participantes.quantidade", "0"));
        for (int i = 0; i < quantidade; i++) {
            Participante participante = new Participante(decodificar(dados.getProperty("participante." + i + ".nome", "")),
                    decodificar(dados.getProperty("participante." + i + ".email", "")));
            participante.setDesejos(new String[]{
                    decodificar(dados.getProperty("participante." + i + ".desejo.0", "")),
                    decodificar(dados.getProperty("participante." + i + ".desejo.1", "")),
                    decodificar(dados.getProperty("participante." + i + ".desejo.2", ""))});
            sorteio.participantes.add(participante);
        }
        for (String chave : dados.stringPropertyNames()) {
            if (chave.startsWith("resultado.")) {
                String email = decodificar(chave.substring("resultado.".length()));
                String emailAmigo = decodificar(dados.getProperty(chave));
                sorteio.participantes.stream().filter(p -> chave(p.getEmail()).equals(emailAmigo))
                        .findFirst().ifPresent(p -> sorteio.resultados.put(email, p));
            } else if (chave.startsWith("comprado.")) {
                sorteio.presentesComprados.add(decodificar(chave.substring("comprado.".length())));
            }
        }
        return sorteio;
    }

    public void salvar() throws IOException {
        Properties dados = new Properties();
        dados.setProperty("valor.minimo", valorMinimo.toPlainString());
        dados.setProperty("valor.maximo", valorMaximo.toPlainString());
        dados.setProperty("data.troca", dataTroca == null ? "" : dataTroca.toString());
        dados.setProperty("participantes.quantidade", String.valueOf(participantes.size()));
        for (int i = 0; i < participantes.size(); i++) {
            Participante p = participantes.get(i);
            dados.setProperty("participante." + i + ".nome", codificar(p.getNome()));
            dados.setProperty("participante." + i + ".email", codificar(p.getEmail()));
            String[] desejos = p.getDesejos();
            for (int j = 0; j < desejos.length; j++) dados.setProperty("participante." + i + ".desejo." + j, codificar(desejos[j]));
        }
        resultados.forEach((email, amigo) -> dados.setProperty("resultado." + codificar(email), codificar(chave(amigo.getEmail()))));
        presentesComprados.forEach(email -> dados.setProperty("comprado." + codificar(email), "true"));
        try (OutputStream saida = new FileOutputStream(ARQUIVO)) { dados.store(saida, "Dados locais do amigo secreto"); }
    }

    public void adicionarParticipante(Participante participante) {
        if (!resultados.isEmpty()) throw new IllegalStateException("O sorteio já foi realizado.");
        if (participante.getNome().isBlank() || participante.getEmail().isBlank())
            throw new IllegalArgumentException("Preencha nome e e-mail.");
        if (participantes.stream().anyMatch(p -> chave(p.getEmail()).equals(chave(participante.getEmail()))))
            throw new IllegalArgumentException("Esse e-mail já está cadastrado.");
        participantes.add(participante);
    }

    public void realizarSorteio() {
        if (participantes.size() < 2) throw new IllegalStateException("Cadastre pelo menos 2 participantes.");
        if (!resultados.isEmpty()) throw new IllegalStateException("O sorteio já foi realizado.");
        List<Participante> embaralhados = new ArrayList<>(participantes);
        Collections.shuffle(embaralhados);
        for (int i = 0; i < embaralhados.size(); i++)
            resultados.put(chave(embaralhados.get(i).getEmail()), embaralhados.get((i + 1) % embaralhados.size()));
    }

    public Participante consultarResultado(String email) { return resultados.get(chave(email)); }
    public List<Participante> getParticipantes() { return Collections.unmodifiableList(participantes); }
    public boolean foiRealizado() { return !resultados.isEmpty(); }
    public BigDecimal getValorMinimo() { return valorMinimo; }
    public BigDecimal getValorMaximo() { return valorMaximo; }
    public LocalDate getDataTroca() { return dataTroca; }

    public void configurar(BigDecimal minimo, BigDecimal maximo, LocalDate data) {
        if (minimo.signum() < 0 || maximo.compareTo(minimo) < 0)
            throw new IllegalArgumentException("Confira os valores mínimo e máximo.");
        valorMinimo = minimo;
        valorMaximo = maximo;
        dataTroca = data;
    }

    public void atualizarDesejos(String email, String[] desejos) {
        Participante participante = participantes.stream()
                .filter(p -> chave(p.getEmail()).equals(chave(email))).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Participante não encontrado."));
        participante.setDesejos(desejos);
    }

    public boolean presenteComprado(String email) { return presentesComprados.contains(chave(email)); }
    public void confirmarPresente(String email, boolean comprado) {
        if (comprado) presentesComprados.add(chave(email));
        else presentesComprados.remove(chave(email));
    }

    private static String chave(String email) { return email.trim().toLowerCase(Locale.ROOT); }
    private static String codificar(String valor) { return Base64.getUrlEncoder().withoutPadding().encodeToString(valor.getBytes(StandardCharsets.UTF_8)); }
    private static String decodificar(String valor) { return new String(Base64.getUrlDecoder().decode(valor), StandardCharsets.UTF_8); }
}
