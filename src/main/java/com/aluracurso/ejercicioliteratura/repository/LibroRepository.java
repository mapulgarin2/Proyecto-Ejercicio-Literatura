package com.aluracurso.ejercicioliteratura.repository;

import com.aluracurso.ejercicioliteratura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository  extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloIgnoreCase(String titulo);
}
