package br.com.estudos.productsapi.model; //endereco da classe Product
import jakarta.persistence.Entity; //permite usar @Entity
import jakarta.persistence.GeneratedValue; //permite usar @generatedvalue
import jakarta.persistence.GenerationType; //permite usar @generationtype
import jakarta.persistence.Id; //permite usar @id

@Entity //fala que a classe representa uma entidade de um banco de dados
public class Product {
    @Id //esse atributo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) //o id vai ser gerado automaticamente, o próprio banco vau gerar o valor automaticamente
    //ambas essas anotacoes ficam em cima do atributo correspondente, do atributo que elas configuram, por isso sabem a qual atributo estão se referindo
    private Long id; //atributo id (tipo SERIAL, Long representa o serial)

    private String name; //atributo nome do produto
    private double price; //atributo preço

    public Product() {
    //o construtor vazio permite criar o objeto primeiro; depois o JPA consegue preenchê-lo com os dados vindos do banco, obrigatorio para cara JPA.
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { //caso eu queira alterar o nome depois
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}