public class Main {
    public static void main(String[] args) {
        Carro carro1 = new Carro(); //criando objeto ja que o metodo criado nao tem static
        Carro carro2 = new Carro();

        carro1.ano = 2026;
        carro1.cor = "Amarelo";
        carro1.modelo = "Onix";
        carro1.marca = "Chevrolet";

        carro2.ano = 2022;
        carro2.cor = "Preto";
        carro2.modelo = "Civic";
        carro2.marca = "Honda";

        System.out.printf("O seu carro é um %s %d, da marca %s, de cor %s\n", carro2.modelo, carro2.ano,carro2.marca, carro2.cor);


    }
}
