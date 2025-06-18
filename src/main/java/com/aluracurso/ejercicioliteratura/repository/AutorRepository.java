package com.aluracurso.ejercicioliteratura.repository;

import com.aluracurso.ejercicioliteratura.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    /*@Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :anio AND (a.anioFallecimiento IS NULL OR a.anioFallecimiento >= :anio)")
    List<Autor> encontrarAutoresVivosEnAnio(@Param("anio") Integer anio);

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> encontrarTodosConLibros();*/

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a JOIN FETCH a.libros")
    List<Autor> encontrarTodosConLibros();

    /*@Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> encontrarAutoresVivosEnAnio(int anio);*/


    @Query("""
    SELECT a FROM Autor a
    LEFT JOIN FETCH a.libros
    WHERE a.anioNacimiento <= :anio
    AND (a.anioFallecimiento IS NULL OR a.anioFallecimiento >= :anio)
""")
    List<Autor> encontrarAutoresVivosEnAnio(@Param("anio") int anio);


    /*@Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :anio AND (a.anioFallecimiento IS NULL OR a.anioFallecimiento >= :anio)")
    List<Autor> encontrarAutoresVivosEnAnio(Integer anio);*/
}
