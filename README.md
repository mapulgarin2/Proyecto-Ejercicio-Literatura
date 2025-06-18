# LiterAlura – Catálogo de Libros

Bienvenido al proyecto **LiterAlura**, un catálogo de libros desarrollado en consola que permite buscar libros mediante una API externa y gestionar la información en una base de datos PostgreSQL usando Java y Spring Boot.

---

## 🚀 Tecnologías Utilizadas

- Java 17  
- Spring Boot 3.2.4  
- Spring Data JPA  
- PostgreSQL  
- Maven  
- API Gutendex ([https://gutendex.com](https://gutendex.com))

---

## 💡 Descripción del Proyecto

La aplicación permite a una persona:

- Buscar libros por título usando la API Gutendex  
- Almacenar información de libros y autores en una base de datos PostgreSQL  
- Listar libros registrados  
- Listar autores registrados  
- Consultar autores vivos en un año determinado  
- Listar libros por idioma  

Todo esto desde una interfaz de consola.

---

## 📋 Requisitos

### Obligatorios

1. **Buscar libro por título**
   - Ingresar un título 
   - Consultar la API Gutendex
   - Registrar el libro (si no está duplicado) junto a su autor en la base de datos

2. **Listar libros registrados**  
3. **Listar autores registrados**  
4. **Listar autores vivos en determinado año**  
5. **Listar libros por idioma**

### Validaciones

- Evitar registrar el mismo libro dos veces  
- Informar si el libro no fue encontrado en la API

### Opcionales y Recomendadas

- Mostrar los datos del libro (título, autor, idioma, descargas) formateados  
- Asociar múltiples libros a un autor  
- Cargar detalles como fecha de nacimiento y fallecimiento del autor  
- Mostrar todos los libros de un autor en una sola línea

---

## 📂 Estructura del Proyecto

### Entidades

- **Autor**  
  - Atributos: `id`, `nombre`, `anioNacimiento`, `anioFallecimiento`  
  - Relación: `@OneToMany` con `Libro`  
- **Libro**  
  - Atributos: `id`, `titulo`, `idioma`, `descargas`  
  - Relación: `@ManyToOne` con `Autor`

### Repositorios

- `LibroRepository`  
- `AutorRepository`  
  - Incluye consulta personalizada para listar autores vivos

### Servicios

- `LibroService`  
  - Lógica para guardar y consultar libros  
- `AutorService`  
  - Lógica para guardar, consultar autores y filtrar por año de vida
 
### Modelo

- `DatosGenerales`  
  - Representa la respuesta general de la API Gutendex. Contiene la lista de libros obtenidos en una búsqueda.

- `DatosLibro`  
  - Representa los datos individuales de un libro devueltos por la API: título, idioma, descargas y autores.

- `DatosAutor`  
  - Representa los datos del autor que vienen en la API: nombre, año de nacimiento y año de fallecimiento.

### Servicio

- `ConsumoApiGutendex`  
  - Clase encargada de consumir la API de Gutendex y obtener los datos en formato JSON.

- `ConvertirDatos`  
  - Implementación de la interfaz `IConvertirDatos`, que convierte el JSON obtenido de la API en objetos Java utilizando la librería Jackson.

- `IConvertirDatos`  
  - Interfaz que define el contrato para la conversión de datos JSON a objetos Java, permitiendo flexibilidad y reutilización.


### Principal

- Clase `Principal`  
  - Contiene el menú de consola y coordina las acciones entre el usuario, los servicios y los repositorios

---

## 🔍 API Externa

**Gutendex API**: [https://gutendex.com](https://gutendex.com)

- Proyecto basado en Gutenberg  
- Devuelve información sobre libros: título, autor, idioma, descargas, etc.

---

## 📁 Estructura del Proyecto en Spring Initializr

- Tipo de proyecto: Maven  
- Lenguaje: Java  
- Versiones recomendadas:
  - Spring Boot: 3.2.4 (sin snapshot o M3)  
  - Java: 17  
- Dependencias necesarias:
  - Spring Data JPA  
  - PostgreSQL Driver  

---

## ⚖️ Licencia

Este proyecto fue desarrollado como parte del programa Oracle Next Education (ONE) con fines educativos.

---

## 📚 Recursos Adicionales

- Documentación API Gutendex: [https://gutendex.com/](https://gutendex.com/)  
- Comunidad de soporte en Discord  
- Tablero Trello del challenge con tarjetas y tareas

---

## 🧑‍💻 Autores

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/mapulgarin2">
        <img src="https://avatars.githubusercontent.com/u/111947748?v=4" width="100px;" alt="Mauricio Pulgarin"/><br />
        <sub><b>Mauricio Pulgarin</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://www.aluracursos.com/">
        <img src="https://www.aluracursos.com/assets/img/home/alura-logo.1730889068.svg" width="100px;" alt="Alura Latam"/><br />
        <sub><b>Alura Latam</b></sub>
      </a>
    </td>
  </tr>
</table>

---

## 🙏 Agradecimientos

- A mi familia por apoyarme en cada reto que me propongo.  
- A mí por mi esfuerzo y dedicación.  
- A los instructores de Alura Latam por su excelente formación.  
- A la comunidad de Java por sus valiosos recursos.  
- A los contribuidores de open source que hacen posible estas herramientas.

---

✨ ¡Mucho éxito programando tu proyecto LiterAlura! ✨
