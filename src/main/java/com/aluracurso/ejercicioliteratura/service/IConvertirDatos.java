package com.aluracurso.ejercicioliteratura.service;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
