public class Personagem {
    public String nome;
    public int vida;
    public int defesa;
    private int stamina = 100;

    public int getStamina() {
        return stamina;
    }
    public String getNome() {
        return nome;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
    public void correr() {
        stamina = stamina - 10;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
