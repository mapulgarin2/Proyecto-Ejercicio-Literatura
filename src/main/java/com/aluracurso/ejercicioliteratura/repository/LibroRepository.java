package com.aluracurso.ejercicioliteratura.repository;

import com.aluracurso.ejercicioliteratura.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository  extends JpaRepository<Libro,Long> {
    /*Optional<Libro> findByTituloIgnoreCase(String titulo);

    boolean existsByTitulo(String titulo);

    List<Libro> findByIdioma(String idioma);

    List<Libro> findByIdiomaIgnoreCase(String idioma);*/
    boolean existsByTitulo(String titulo);

    @Query("SELECT l FROM Libro l WHERE UPPER(l.idioma) = UPPER(:idioma)")
    List<Libro> buscarPorIdiomaIgnoreCase(@Param("idioma") String idioma);

    List<Libro> findByIdioma(String idioma);

}
