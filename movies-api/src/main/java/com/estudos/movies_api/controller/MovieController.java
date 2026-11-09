package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //pra dizer que essa classe vai ser o controller, vai receber os pedidos/requisicoes da API
@RequestMapping("/movies") //o endereço principal desse controller começa com /movies, tudo relacionado a filmes vai começar com /movies. /movies se torna um prefixo para todas as rotas, entao por exemplo se tem um metodo "alo" e eu quero fazer uma requisicao nesse endereco ficaria assim: GET /movies/alo

public class MovieController {
    @GetMapping //quando chegar uma requisição GET nesse endereço (GET /movies), execute esse metodo:
    public String listmovies() {
        return "Movie List";
    }

    @GetMapping //quando chegar uma requisição GET nesse endereço (GET /movies/featured), execute esse metodo:
    public String featuredMovies() {
        return "Featured Movies";
    }
}
