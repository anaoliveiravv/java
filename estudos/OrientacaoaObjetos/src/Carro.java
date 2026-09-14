public class Carro {
    int ano;
    String modelo;
    String cor;
    String marca;
    boolean ligado;
    boolean andando;

    //sem o static, o metodo pertence ao objeto (instância de uma classe)
    void andar() {
        //ao add o static aq, o metodo vai pertencer diretamente à classe e nao será necessario criar um obj depois. poderia so chamar a classe com seu metodo direto.
        if (ligado) {
            andando = true;
            System.out.println("Carro andando");
        } else {
            andando = false;
            System.out.println("O carro não pode andar, pois está desligado");
        }
    }

    void ligar() {
        if (!ligado) {
            ligado = true;
            System.out.println("Carro ligado");
        } else {
            System.out.println("O carro já está ligado");
        }
    }

    void parar() {
        if (!andando) {
            System.out.println("O carro já está parado");
        } else {
            andando = false;
            System.out.println("O carro parou");
        }
    }

    void desligar() {
        if (!ligado) {
            System.out.println("O carro já está desligado");
        } else if (ligado && !andando) {
            System.out.println("Carro desligado");
            ligado = false;
            andando = false;
        }
        if (andando) {
            System.out.println("Pare o carro antes de desligar");
        }
    }

    void buzinar() {
        if (ligado) {
            System.out.println("Bi biiiiiiii");
        } else {
            System.out.println("O carro não pode buzinar, pois está desligado");
        }
    }
}

