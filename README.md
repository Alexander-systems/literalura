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

## ⚙️ Configuración del Proyecto

### **Requisitos Previos**

* Java Development Kit (JDK) 17 o superior.
* Maven 3.6.x o superior.
* PostgreSQL instalado y configurado.

### **Pasos para Ejecutar**

1.  **Clonar el repositorio:**
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd literalura
    ```

2.  **Configurar la Base de Datos:**
    * Crea una base de datos PostgreSQL llamada `literalura` (o el nombre que prefieras).
    * Actualiza el archivo `src/main/resources/application.properties` con las credenciales de tu base de datos:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura # Reemplaza literalura si usas otro nombre
    spring.datasource.username=tu_usuario_postgres
    spring.datasource.password=tu_contraseña_postgres
    # ... otras configuraciones existentes ...
    ```

3.  **Construir el proyecto:**
    ```bash
    mvn clean install
    ```

4.  **Ejecutar la aplicación:**
    ```bash
    mvn spring-boot:run
    ```

    La aplicación se iniciará y el menú de la consola se mostrará en tu terminal.
