# LiterAlura: Catálogo de Libros y Autores

LiterAlura es una aplicación de consola y API RESTful desarrollada con Spring Boot que permite buscar, registrar y consultar libros y autores utilizando la API de Gutendex.

## 🚀 Funcionalidades

### **Funcionalidades de Consola:**
1.  **Buscar libro por título:** Permite buscar libros en la API de Gutendex e importar sus datos (título, autor, idioma, descargas) a la base de datos local.
2.  **Listar todos los libros:** Muestra todos los libros guardados en la base de datos local.
3.  **Listar todos los autores:** Muestra todos los autores registrados en la base de datos local.
4.  **Listar autores vivos en un año:** Permite consultar autores que estaban vivos en un determinado año.
5.  **Mostrar cantidad de libros por idioma:** Muestra el número de libros guardados, filtrados por idioma.

### **Funcionalidades API RESTful:**
(Basado en `LibroController.java`)
* `GET /api/libros/buscar?titulo={titulo}`: Busca libros por título en la API de Gutendex y los persiste localmente. Retorna una lista de libros.

## 🛠️ Tecnologías Utilizadas

* **Java 17**
* **Spring Boot 3.x:** Framework para desarrollo de aplicaciones Java.
    * `spring-boot-starter-data-jpa`: Para persistencia de datos con JPA y Hibernate.
    * `spring-boot-starter-web`: Para crear aplicaciones web y RESTful.
    * `spring-boot-starter-test`: Para pruebas unitarias e integración.
* **PostgreSQL:** Base de datos relacional.
* **Hibernate:** Implementación de JPA.
* **Jackson:** Para mapeo de JSON a objetos Java (`jackson-databind`).
* **API de Gutendex:** Fuente externa de datos de libros (https://gutendex.com/).


