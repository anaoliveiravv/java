package br.com.estudos.productsapi.repository;

//esses import servem para dizer ao java onde estão as CLASSES que eu quero usar nesse arquivo
import br.com.estudos.productsapi.model.Product; //classe Product
import org.springframework.data.jpa.repository.JpaRepository; //classe/interface JpaRepository, traz a ferramenta da biblioteca Spring
//JpaRepository = ferramenta que me concede métodos prontos

public interface ProductRepository extends JpaRepository<Product, Long> { //extends = vai herdar coisas de outra estrutura (nesse caso de JpaRepository q é uma interface pronta do Spring e já vem com métodos prontos(save, findall, findbyid e deletebyid))
    // Jparep..<Product, Long> = product é a classe que o repository vai controlar e long é o tipo do id do Product//
    // save()
    // findAll()
    // findById()
    // deleteById()
}
//cria a tabela no banco