//package br.com.aulaspring.carRent.service;
//
//import br.com.aulaspring.carRent.model.Aluguel;
//import br.com.aulaspring.carRent.repository.AluguelRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AluguelService {
//    private final AluguelRepository repository;
//
//    public AluguelService(AluguelRepository repository) {
//        this.repository = repository;
//    }
//
//    public AluguelDTO criar(AluguelDTO dto) {
//        Aluguel a = new Aluguel();
//        a.setUsuario(dto.usuario);
//        a.setCarro(dto.carro);
//        a.setDataInicio(dto.dataInicio);
//        a.setDataFim(dto.dataFim);
//        return toDTO(repository.save(a));
//    }
//
//    public List<AluguelDTO> listar() {
//        return repository.findAll().stream().map(this::toDTO).toList();
//    }
//
//    private AluguelDTO toDTO(Aluguel a) {
//        return new AluguelDTO(a.getId(), a.getUsuario())
//    }
//}
