package br.com.aulaspring.carRent.service;

import br.com.aulaspring.carRent.dto.CarroDTO;
import br.com.aulaspring.carRent.model.Carro;
import br.com.aulaspring.carRent.repository.CarroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
//PARTE DA LÓGICA, CONTEM REGRAS DE NEGÓCIO
@Service
public class CarroService {
    private final CarroRepository repository; //service precisa falar com o banco, por isso precisa do CarroRepository

    public CarroService(CarroRepository repository) {
        this.repository = repository; //o construtor faz o Spring entregar o Repository ao Service
    }

    public CarroDTO criar(CarroDTO dto) {//receber um obj de CarroDTO (dto) e devolver um novo obj do tipo CarroDTO (c DTO) ;
        Carro c = new Carro(); //cria uma entity vazia, objeto da entidade Carro
        c.setMarca(dto.marca()); //os sets vao atribuir os dados de CarroDTO dentro da entity
        c.setModelo(dto.modelo());
        c.setAno(dto.ano());
        c.setPlaca(dto.placa());
        c.setDisponivel(dto.disponivel());
        return toDTO(repository.save(c)); //manda essa entity c para o repository e salva no banco, um novo id é gerado e esse c é transformado novamente em DTO (o dto nao é salvo no banco, a ENTIDADE é, dps que ela é salva, vira dto dnv)
    }

    public List<CarroDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList(); //devolve uma lista de dto de carro (de dto pq nao quero devolver a minha entity pro front e sim devolver a DTO dela)
        //findAll()= buscar todos ps carros do banco
        //.stream()= percorrer item por item
        //.map(...)= pega cada item e transforma em outra coisa, nesse caso em DTO, usa o metodo toDTO
        //.tolist()= transforma tudo em lista
    }

    private CarroDTO toDTO(Carro c) { //metodo toDTO, serve para converter em DTO
        return new CarroDTO(
                c.getId(),
                c.getMarca(),
                c.getModelo(),
                c.getAno(),
                c.getPlaca(),
                c.isDisponivel());
    }
}
//ou seja, o service está fazendo a lógica por trás dos metodos list e create para devolver ao controller
