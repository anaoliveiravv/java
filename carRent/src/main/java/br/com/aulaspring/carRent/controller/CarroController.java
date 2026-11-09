package br.com.aulaspring.carRent.controller;

import br.com.aulaspring.carRent.dto.CarroDTO;
import br.com.aulaspring.carRent.service.CarroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Diz que é um Controller
@RequestMapping("/carros") //A classe recebe requisicões relacionadas ao endereço /carros
public class CarroController {

        private final CarroService service; //O controller precisa chamar o Service

        public CarroController(CarroService service) {
            this.service = service;// Liga o Service ao atributo service dp Controller
        }

        @PostMapping //Se chegar uma requisição POST execute isso:
        @ResponseStatus(HttpStatus.CREATED)//Diz o Status HTTP quando aquele metodo funcionar, ou seja, quando o carro é salvo após a criacao dele, o Spring responde com a mensagem> Status: 201 Created
        public CarroDTO criar(@Valid @RequestBody CarroDTO dto) {//RequestBody diz de onde os dados (json) da requisicao estao vindo, no caso, do corpo da Requisição. é o @RequestBody que diz ao Spring para pegar o corpo da requisição e montar o objeto dto do tipo CarroDTO. Requetsbody é usado quando se quer enviar algo para o corpo da requisicao como é com o metodo POST.
            //Valid manda o spring validar as annotations do DTO, a validacao é executada
            return service.criar(dto); //Controller passa request dto pro Service transformar em entidade
        }

        @GetMapping //Quando a requisição for GET, execute:
        public List<CarroDTO> listar() {
            return service.listar();
        }
    }
