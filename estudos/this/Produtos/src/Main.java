public class Main {
    public static void main(String[] args) {
        Produto produto1 = new Produto("Teclado", 150, 2);
        Produto produto2 = new Produto("Mouse", 80, 3);

        produto1.mostrarDados();
        System.out.println(produto1.calcularTotal());
    }
}