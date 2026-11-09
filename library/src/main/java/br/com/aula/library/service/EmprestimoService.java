package br.com.aula.library.service;

import br.com.aula.library.dto.EmprestimoDTO;
import br.com.aula.library.model.Emprestimo;
import br.com.aula.library.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoService {
    private final EmprestimoRepository repository;

    public EmprestimoService(EmprestimoRepository repository) {
        this.repository = repository;
    }

    public EmprestimoDTO criar(EmprestimoDTO dto ) {
        Emprestimo emp = new Emprestimo();
        emp.setId(dto.id());
        emp.setLeitor(dto.leitor());
        emp.setLivro(dto.livro());
        emp.setDataEmprestimo(dto.dataEmprestimo());
        emp.setDataDevolucao(dto.dataDevolucao());
        return toDTO(repository.save(emp));
    }

    public List<EmprestimoDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    private EmprestimoDTO toDTO(Emprestimo emp) {
        return new EmprestimoDTO(
                emp.getId(),
                emp.getLeitor(),
                emp.getLivro(),
                emp.getDataEmprestimo(),
                emp.getDataDevolucao()
        );
    }
}
