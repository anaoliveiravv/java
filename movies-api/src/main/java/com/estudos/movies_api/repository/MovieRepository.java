package com.estudos.movies_api.repository;

import com.estudos.movies_api.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> { //faz a ligacao com o banco
}
