package br.com.estudos.productsapi.controller;

import br.com.estudos.productsapi.model.Product;
import br.com.estudos.productsapi.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController //a classe vai receber requisicoes(pedidos) da internet
@RequestMapping("/products")//os enderecos dessa classe comecam com /products
public class ProductController {

    public ProductController(ProductService service) {
    }

    @PostMapping //se chegar uma requisicao do tipo POST (para criar algo) execute o metodo abaixo
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @GetMapping
    public List<Product> findAll() {
        return service.findAll();
    }
}

