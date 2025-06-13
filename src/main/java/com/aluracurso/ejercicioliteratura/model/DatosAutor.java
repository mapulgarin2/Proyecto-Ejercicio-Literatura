package com.aluracurso.ejercicioliteratura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(@JsonAlias("name") String nombreAutor,
                         @JsonAlias("birth_year") String nacimientoAutor,
                         @JsonAlias("death_year") String muerteAutor){}
