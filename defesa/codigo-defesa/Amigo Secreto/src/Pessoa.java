
import java.util.ArrayList;

public class Pessoa {

    String nome;
    ArrayList<String> presentes = new ArrayList<>();

    public Pessoa(String nome) {
        this.nome = nome;
    }

    void adicionarPresente(String presente) {
        if (presentes.size() < 3) {
            presentes.add(presente);
        }
    }

    void mostrarPresentes() {
        System.out.println("Sugestões de " + nome + ":");

        for (String presente : presentes) {
            System.out.println("- " + presente);
        }
    }
}
