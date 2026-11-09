import java.util.Arrays;

public class Participante {
    private final String nome;
    private final String email;
    private String[] desejos = {"", "", ""};

    public Participante(String nome, String email) {
        this.nome = nome.trim();
        this.email = email.trim();
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String[] getDesejos() { return Arrays.copyOf(desejos, desejos.length); }

    public void setDesejos(String[] desejos) {
        this.desejos = Arrays.copyOf(desejos, 3);
        for (int i = 0; i < this.desejos.length; i++) {
            if (this.desejos[i] == null) this.desejos[i] = "";
            this.desejos[i] = this.desejos[i].trim();
        }
    }
}
