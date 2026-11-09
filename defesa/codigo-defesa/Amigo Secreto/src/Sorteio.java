
import java.util.ArrayList;
import java.util.Collections;

public class Sorteio {

    ArrayList<Pessoa> participantes = new ArrayList<>();
    ArrayList<Pessoa> resultados = new ArrayList<>();

    boolean sorteado = false;

    void cadastrar(Pessoa pessoa) {

        if (sorteado) {
            System.out.println("O sorteio já foi realizado!");
            return;
        }

        for (Pessoa p : participantes) {
            if (p.nome.equalsIgnoreCase(pessoa.nome)) {
                System.out.println("Essa pessoa já está cadastrada!");
                return;
            }
        }

        participantes.add(pessoa);
        System.out.println("Participante cadastrado!");
    }

    void listar() {

        if (participantes.isEmpty()) {
            System.out.println("Nenhum participante cadastrado!");
            return;
        }

        System.out.println("\n=== PARTICIPANTES ===");

        for (Pessoa pessoa : participantes) {
            System.out.println("- " + pessoa.nome);
        }
    }

    void realizarSorteio() {

        if (sorteado) {
            System.out.println("O sorteio já foi realizado!");
            return;
        }

        if (participantes.size() < 2) {
            System.out.println("Cadastre pelo menos 2 pessoas!");
            return;
        }

        resultados = new ArrayList<>(participantes);

        Collections.shuffle(resultados);

        ArrayList<Pessoa> ordem = new ArrayList<>(resultados);

        resultados.clear();

        for (int i = 0; i < ordem.size(); i++) {
            int proximo = (i + 1) % ordem.size();

            Pessoa pessoa = participantes.get(i);

            int posicao = ordem.indexOf(pessoa);

            Pessoa amigo = ordem.get(
                    (posicao + 1) % ordem.size()
            );

            resultados.add(amigo);
        }

        sorteado = true;
        System.out.println("Sorteio realizado com sucesso!");
    }

    void descobrir(String nome) {

        if (!sorteado) {
            System.out.println("Realize o sorteio primeiro!");
            return;
        }

        for (int i = 0; i < participantes.size(); i++) {

            Pessoa pessoa = participantes.get(i);

            if (pessoa.nome.equalsIgnoreCase(nome)) {

                Pessoa amigo = resultados.get(i);

                System.out.println("\nVocê tirou: " + amigo.nome);
                amigo.mostrarPresentes();
                return;
            }
        }

        System.out.println("Participante não encontrado!");
    }
}
