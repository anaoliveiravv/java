package br.com.aulaspring.carRent.repository; //dominio

import br.com.aulaspring.carRent.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
