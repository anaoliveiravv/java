    class Produto {
    String nome;
    double preco;
    int quantidade;

        public Produto(String nome, double preco, int quantidade) {
            this.nome = nome;
            this.preco = preco;
            this.quantidade = quantidade;
        }

        double calcularTotal() {
            return this.preco * this.quantidade;
        }

        void mostrarDados() {
            System.out.println("Produto: " + this.nome);
            System.out.println("Preço: " + this.preco);
            System.out.println("Quantidade: " + this.quantidade);
        }
}
