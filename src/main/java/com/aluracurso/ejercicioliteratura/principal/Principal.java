package com.aluracurso.ejercicioliteratura.principal;

import com.aluracurso.ejercicioliteratura.entity.Autor;
import com.aluracurso.ejercicioliteratura.entity.Libro;
import com.aluracurso.ejercicioliteratura.model.DatosAutor;
import com.aluracurso.ejercicioliteratura.model.DatosGenerales;
import com.aluracurso.ejercicioliteratura.model.DatosLibro;
import com.aluracurso.ejercicioliteratura.repository.AutorRepository;
import com.aluracurso.ejercicioliteratura.repository.LibroRepository;
import com.aluracurso.ejercicioliteratura.service.ConsumoApiGutendex;
import com.aluracurso.ejercicioliteratura.service.ConvertirDatos;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private final Scanner teclado = new Scanner(System.in);
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoApiGutendex consumoApi = new ConsumoApiGutendex();
    private final ConvertirDatos conversor = new ConvertirDatos(); // ← aquí está el cambio

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    \n📚 MENÚ PRINCIPAL 📚
                    1 - Buscar libro por título
                    2 - Mostrar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un año
                    5 - Listar libros por idioma
                    0 - Salir
                    ---------------------------""");

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Opción inválida. Intenta de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> mostrarLibrosGuardados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosPorAnio();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("👋 Cerrando aplicación.");
                default -> System.out.println("⚠️ Opción no válida.");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("🔍 Ingrese el título del libro en inglés: ");
        String tituloBuscado = teclado.nextLine().trim();

        try {
            String tituloCodificado = URLEncoder.encode(tituloBuscado, StandardCharsets.UTF_8);
            String url = URL_BASE + "?search=" + tituloCodificado;
            String json = consumoApi.obtenerdatosApiGutendex(url);
            DatosGenerales datos = conversor.obtenerDatos(json, DatosGenerales.class);

            if (datos.resultados().isEmpty()) {
                System.out.println("❌ No se encontró ningún libro con ese título.");
                return;
            }

            DatosLibro datosLibro = datos.resultados().get(0);
            if (libroRepository.existsByTitulo(datosLibro.titulo())) {
                System.out.println("⚠️ El libro ya está registrado en la base de datos.");
                return;
            }

            DatosAutor datosAutor = datosLibro.autor().isEmpty()
                    ? new DatosAutor("Desconocido", null, null)
                    : datosLibro.autor().get(0);

            // Buscar autor por nombre
            Autor autor = autorRepository.findByNombre(datosAutor.nombreAutor())
                    .orElseGet(() -> {
                        Autor nuevo = new Autor(
                                datosAutor.nombreAutor(),
                                datosAutor.anioNacimiento(),
                                datosAutor.anioFallecimiento()
                        );
                        return autorRepository.save(nuevo); // Guardar autor antes del libro
                    });

            // Crear el libro con el autor persistido
            Libro libro = new Libro(
                    datosLibro.titulo(),
                    datosLibro.idioma().isEmpty() ? "Desconocido" : datosLibro.idioma().get(0),
                    datosLibro.descargas(),
                    autor
            );

            libroRepository.save(libro);
            System.out.println("✅ Libro guardado correctamente.");
            mostrarDetalleLibro(libro);

        } catch (Exception e) {
            System.out.println("❌ Error al buscar el libro: " + e.getMessage());
        }
    }


    private void mostrarLibrosGuardados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("📭 No hay libros registrados aún.");
        } else {
            libros.forEach(this::mostrarDetalleLibro);
        }
    }

    private void mostrarDetalleLibro(Libro libro) {
        System.out.println("-------📖 LIBRO -------");
        System.out.printf("Título: %s\n", libro.getTitulo());
        System.out.printf("Autor: %s\n", libro.getAutor().getNombre());
        System.out.printf("Idioma: %s\n", libro.getIdioma());
        System.out.printf("Cantidad de descargas: %.0f\n", libro.getDescargas());
        System.out.println("-----------------------");
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.encontrarTodosConLibros(); // Debe usar @EntityGraph o @Query con JOIN FETCH

        if (autores.isEmpty()) {
            System.out.println("📭 No hay autores registrados.");
        } else {
            System.out.println("🖋️ Autores registrados:");
            autores.forEach(autor -> {
                System.out.println("👤 Autor: " + autor.getNombre());
                System.out.println("📅 Nacimiento: " + (autor.getAnioNacimiento() != null ? autor.getAnioNacimiento() : "Desconocido"));
                System.out.println("💀 Fallecimiento: " + (autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "Desconocido"));
                System.out.print("📚 Libros: ");
                var titulos = autor.getLibros().stream()
                        .map(Libro::getTitulo)
                        .toList();
                System.out.println(String.join(", ", titulos));
                System.out.println("------------------------------");
            });
        }
    }

    private void listarAutoresVivosPorAnio() {
        System.out.print("Ingrese el año: ");
        try {
            int anio = Integer.parseInt(teclado.nextLine());
            List<Autor> autores = autorRepository.encontrarAutoresVivosEnAnio(anio);

            if (autores.isEmpty()) {
                System.out.println("📭 No se encontraron autores vivos en ese año.");
            } else {
                autores.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Año inválido.");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                            \n🌐 Ingrese el idioma para buscar los libros:
                            en - Inglés
                            es - Español
                            fr - Francés
                            pt - Portugués
                            ---------------------------""");
        String idioma = teclado.nextLine().trim();

        List<Libro> libros = libroRepository.buscarPorIdiomaIgnoreCase(idioma);


        if (libros.isEmpty()) {
            System.out.println("📭 No hay libros en ese idioma.");
        } else {
            libros.forEach(this::mostrarDetalleLibro);
        }
    }
}
