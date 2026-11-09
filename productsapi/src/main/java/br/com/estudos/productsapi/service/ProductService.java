package br.com.estudos.productsapi.service; //localiza o pacote em que a Java class está inserida, ou seja, localiza o pacote Service

import br.com.estudos.productsapi.model.Product; //traz a classe product
import br.com.estudos.productsapi.repository.ProductRepository; //traz a classe ProductRepository
import org.springframework.stereotype.Service; //traz a classe Service

import java.util.List; //importa a lista
@Service //diz ao spring que essa classe (ProductService) é um Service, ou seja, a parte que cuida da logica do sistema e cria automaticamente um objeto dessa classe que pode ser chamado posteriormente.
public class ProductService {
    private final ProductRepository repository;// atributo da classe ProductService; final = a variavel repository nao pode apontar para um novo objeto
    public ProductService(ProductRepository repository) {
        this.repository = repository; //??
    }

    public Product create(Product product) {
        if (product.getPrice() <= 0) {
            throw new RuntimeException("Preço inválido"); //serve para lançar um erro de propósito quando alguma coisa deu errado, throw = lançar um erro, new Run.. = cria um obj de erro
        }

        return repository.save(product);
    }
    public List<Product> findAll() { //vai retornar uma lista do tipo Product e o nome do metodo é findAll. se é do tipo Product entao quer dizer que cada item na lista precisa ser um objeto dessa classe Product.
        return repository.findAll();
    }
}
