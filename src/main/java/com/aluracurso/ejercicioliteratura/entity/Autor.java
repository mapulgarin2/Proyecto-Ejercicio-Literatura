package com.aluracurso.ejercicioliteratura.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    //@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Libro> libros;

    public Autor() {}

    public Autor(String nombre, Integer anioNacimiento, Integer anioFallecimiento) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioFallecimiento = anioFallecimiento;
    }

    // Getters y setters

    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getAnioNacimiento() { return anioNacimiento; }

    public void setAnioNacimiento(Integer anioNacimiento) { this.anioNacimiento = anioNacimiento; }

    public Integer getAnioFallecimiento() { return anioFallecimiento; }

    public void setAnioFallecimiento(Integer anioFallecimiento) { this.anioFallecimiento = anioFallecimiento; }

    public List<Libro> getLibros() { return libros; }

    public void setLibros(List<Libro> libros) { this.libros = libros; }

//    @Override
@Override
/*public String toString() {
    String librosDelAutor = (libros == null || libros.isEmpty())
            ? "Ninguno"
            : libros.stream()
            .map(Libro::getTitulo)
            .collect(Collectors.joining(", "));

    return "👤 Autor: " + nombre +
            "\n📅 Nacimiento: " + (anioNacimiento != null ? anioNacimiento : "Desconocido") +
            "\n💀 Fallecimiento: " + (anioFallecimiento != null ? anioFallecimiento : "Desconocido") +
            "\n📚 Libros: " + librosDelAutor +
            "\n------------------------------";
}*/

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("👤 Autor: ").append(nombre)
                .append("\n📅 Nacimiento: ").append(anioNacimiento != null ? anioNacimiento : "Desconocido")
                .append("\n💀 Fallecimiento: ").append(anioFallecimiento != null ? anioFallecimiento : "Desconocido")
                .append("\n📚 Libros: ").append(libros != null && !libros.isEmpty()
                        ? libros.stream().map(Libro::getTitulo).reduce((a, b) -> a + ", " + b).orElse("")
                        : "Ninguno")
                .append("\n------------------------------");
        return sb.toString();
    }

}
