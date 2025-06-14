package com.aluracurso.ejercicioliteratura.principal;

import com.aluracurso.ejercicioliteratura.model.DatosAutor;
import com.aluracurso.ejercicioliteratura.model.DatosGenerales;
import com.aluracurso.ejercicioliteratura.model.DatosLibro;
import com.aluracurso.ejercicioliteratura.model.Libro;
import com.aluracurso.ejercicioliteratura.repository.LibroRepository;
import com.aluracurso.ejercicioliteratura.service.ConsumoApiGutendex;
import com.aluracurso.ejercicioliteratura.service.ConvertirDatos;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoApiGutendex consumoApiGutendex = new ConsumoApiGutendex();
    //    private List<Libro> libros;
    private ConvertirDatos convertirDatos = new ConvertirDatos();

    private LibroRepository repository;

    public Principal(LibroRepository repository) {
        this.repository = repository;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1- Buscar libro por titulo
                    2- Mostrar libros buscados
                    0- salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }

    }
    private DatosLibro getDatosLibro(String nombreLibro) {

        String url = URL_BASE + "?search=" + nombreLibro.replace(" ", "%20");
        var json = consumoApiGutendex.obtenerdatosApiGutendex(url);
        DatosGenerales datos = convertirDatos.obtenerDatos(json, DatosGenerales.class);
        if (datos.resultados().isEmpty()) {
            return null;
        }
        return datos.resultados().stream()
                .filter(l -> l.titulo().equalsIgnoreCase(nombreLibro))
                .findFirst()
                .orElse(null);
    }

    public void mostrarInformacionLibro(DatosLibro libroEncontrado) {
        System.out.println("---------LIBRO---------");
        System.out.println("Titulo: "+libroEncontrado.titulo());
        String autor = libroEncontrado.autor().isEmpty()
                ? "Autor desconocido"
                : libroEncontrado.autor().stream()
                .map(DatosAutor::nombreAutor)
                .collect(Collectors.joining(", "));

        System.out.println("Autor: " + autor);

        String idioma = libroEncontrado.idioma().isEmpty()
                ? "Idioma no encontrado"
                : libroEncontrado.idioma().stream()
                .findFirst()
                .orElse("Idioma no encontrado");

        System.out.println("Idioma: " + idioma);
        System.out.println("Total de descargas: "+libroEncontrado.descargas());
        System.out.println("-----------------------");

    }



    private void buscarLibro() {
        System.out.println("Ingrese el titulo del libro");
        String tituloLibro = scanner.nextLine();

        if (repository.findByTituloIgnoreCase(tituloLibro).isPresent()) {
            System.out.println("EL libro ya se encuentra en la base de datos");
            return;
        }

        DatosLibro libroEncontrado = getDatosLibro(tituloLibro);
        if (libroEncontrado == null) {
            System.out.println("Libro no encontrado");
            return;
        }
        mostrarInformacionLibro(libroEncontrado);
        guardarLibroBD(libroEncontrado);
    }


    private void guardarLibroBD(DatosLibro libroEncontrado) {
        String titulo = libroEncontrado.titulo();

        if (repository.findByTituloIgnoreCase(titulo).isPresent()) {
            System.out.println("El libro ya este en la base de datos");
        }
        String autor = libroEncontrado.autor().stream()
                .map(DatosAutor::nombreAutor)
                .collect(Collectors.joining(", "));

        String idioma = libroEncontrado.idioma().isEmpty()
                ? "Idioma no encontrado"
                : libroEncontrado.idioma().get(0);
        Libro libro = new Libro(
                libroEncontrado.titulo(),
                autor,
                idioma,
                libroEncontrado.descargas()
        );
        repository.save(libro);

    }

}

