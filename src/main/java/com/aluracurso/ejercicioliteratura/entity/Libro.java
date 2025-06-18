package com.aluracurso.ejercicioliteratura.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="Libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Double descargas;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {
    }

    public Libro(String titulo, String idioma, Double descargas, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
        this.autor = autor;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return String.format(
                "-------LIBRO-------\n" +
                        "Libro= %s\n" +
                        "Autor= %s\n" +
                        "Idioma= %s\n" +
                        "Cantidad de descargas= %.0f\n" +
                        "-------------------",
                titulo != null ? titulo : "Desconocido",
                autor != null ? autor.getNombre() : "Desconocido",
                idioma != null ? idioma : "Desconocido",
                descargas != null ? descargas : 0.0
        );
    }
}