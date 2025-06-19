package com.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String idioma; // Este campo almacenará el idioma (una sola cadena)

    private Integer descargas;

    @ManyToOne(cascade = CascadeType.MERGE) // <-- Cambia PERSIST a MERGE
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // --- CAMPOS CLAVE PARA LA DESERIALIZACIÓN DE LA API ---
    @Transient // Indica a JPA que este campo NO debe ser persistido en la DB
    @JsonAlias("authors") // Indica a Jackson que este campo mapea al array "authors" del JSON
    private List<Autor> autoresApi;

    @Transient // Indica a JPA que este campo NO debe ser persistido en la DB
    @JsonAlias("languages") // Indica a Jackson que este campo mapea al array "languages" del JSON
    private List<String> idiomasApi;
    // --------------------------------------------------------


    public Libro() {}

    // Getters y setters


    public List<Autor> getAutoresApi() {
        return autoresApi;
    }

    public List<String> getIdiomasApi() {
        return idiomasApi;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @JsonAlias("title") // Mapea el campo 'title' del JSON al 'titulo'
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    // Getter para 'idioma' (una sola cadena para la DB)
    public String getIdioma() { return idioma; }
    // Setter para 'idioma' (una sola cadena para la DB)
    public void setIdioma(String idioma) { this.idioma = idioma; }

    @JsonAlias("download_count") // Mapea el campo 'download_count' del JSON al 'descargas'
    public Integer getDescargas() { return descargas; }
    public void setDescargas(Integer descargas) { this.descargas = descargas; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    // --- SETTERS PERSONALIZADOS PARA PROCESAR LOS ARRAYS DE LA API ---
    // Jackson llamará a este método cuando encuentre el campo "authors" en el JSON
    public void setAutoresApi(List<Autor> autoresApi) {
        System.out.println("DEBUG: Libro - setAutoresApi llamado con: " + autoresApi); // ESTE DEBUG DEBE APARECER
        if (autoresApi != null && !autoresApi.isEmpty()) {
            this.autor = autoresApi.get(0); // Toma el primer autor de la lista
            System.out.println("DEBUG: Libro - Estableciendo autor a: " + (this.autor != null ? this.autor.getNombre() : "null (después de get(0))")); // ESTE DEBUG DEBE APARECER
        } else {
            this.autor = null; // Si no hay autores, el autor será null
            System.out.println("DEBUG: Libro - autoresApi es null o vacío. Autor establecido a null.");
        }
        this.autoresApi = autoresApi; // Mantenemos la lista temporal si la necesitas para algo más, aunque no se guarda en DB
    }

    // Jackson llamará a este método cuando encuentre el campo "languages" en el JSON
    public void setIdiomasApi(List<String> idiomasApi) {
        System.out.println("DEBUG: Libro - setIdiomasApi llamado con: " + idiomasApi); // ESTE DEBUG DEBE APARECER
        if (idiomasApi != null && !idiomasApi.isEmpty()) {
            this.idioma = idiomasApi.get(0); // Toma el primer idioma de la lista
            System.out.println("DEBUG: Libro - Estableciendo idioma a: " + this.idioma); // ESTE DEBUG DEBE APARECER
        } else {
            this.idioma = null; // Si no hay idiomas, el idioma será null
            System.out.println("DEBUG: Libro - idiomasApi es null o vacío. Idioma establecido a null.");
        }
        this.idiomasApi = idiomasApi; // Mantenemos la lista temporal
    }
    // ------------------------------------------------------------------


    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", descargas=" + descargas +
                ", autor=" + (autor != null ? autor.getNombre() : "N/A") + // Mostrar nombre del autor si existe
                '}';
    }
}