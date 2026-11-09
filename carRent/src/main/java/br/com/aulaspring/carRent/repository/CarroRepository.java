package br.com.aulaspring.carRent.repository;

import br.com.aulaspring.carRent.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface CarroRepository extends JpaRepository<Carro, Long> { //parte que conversa com o banco de dados, ponte entre o java e o banco
        //quero um repository que trabalhe com a entidade Carro cujo id é Long
        //Daí o Spring já me oferece metodos prontos como findall, save, findbyid, deletebyId
        //lembrando: repository sabe salvar ENTIDADES, não DTOs. Por isso que o Service converte em Entidade para salvar no banco de dados
}
