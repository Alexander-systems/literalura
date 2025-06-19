package com.literalura.service;

import com.literalura.model.Autor;
import com.literalura.model.GutendexResponse;
import com.literalura.model.Libro;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    private final String BASE_URL = "https://gutendex.com/books/";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        String urlInicial = BASE_URL + "?search=" + titulo.replace(" ", "%20");
        System.out.println("Buscando en Gutendex: " + urlInicial);

        GutendexResponse response = null;
        try {
            response = restTemplate.getForObject(urlInicial, GutendexResponse.class);
        } catch (Exception e) {
            System.err.println("Error al conectar con la API de Gutendex: " + e.getMessage());
            return Collections.emptyList();
        }

        // Si la búsqueda inicial no devuelve resultados, intenta con la última palabra
        if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
            System.out.println("No se encontraron libros con la frase exacta. Intentando con la última palabra clave...");
            String[] palabras = titulo.split(" ");
            if (palabras.length > 0) {
                String ultimaPalabra = palabras[palabras.length - 1]; // Toma la última palabra
                String urlUltimaPalabra = BASE_URL + "?search=" + ultimaPalabra.replace(" ", "%20");
                System.out.println("Reintentando búsqueda con: " + urlUltimaPalabra);
                try {
                    response = restTemplate.getForObject(urlUltimaPalabra, GutendexResponse.class);
                } catch (Exception e) {
                    System.err.println("Error al conectar con la API de Gutendex en reintento: " + e.getMessage());
                    return Collections.emptyList();
                }
            }
        }

        // --- El resto de tu lógica para procesar los libros (sin cambios) ---
        List<Libro> librosProcesados = new ArrayList<>();

        if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
            for (Libro libroApi : response.getResults()) {
                List<Libro> libroYaExiste = libroRepository.findByTituloContainingIgnoreCase(libroApi.getTitulo());

                if (libroYaExiste.isEmpty()) {
                    System.out.println("--- Procesando libro de API: " + libroApi.getTitulo() + " ---");

                    Autor autorObtenidoDeApi = libroApi.getAutor();

                    Autor autorAsociadoAlLibro = null;
                    if (autorObtenidoDeApi != null && autorObtenidoDeApi.getNombre() != null && !autorObtenidoDeApi.getNombre().isEmpty()) {
                        Optional<Autor> autorExistente = autorRepository.findByNombre(autorObtenidoDeApi.getNombre());

                        if (autorExistente.isPresent()) {
                            autorAsociadoAlLibro = autorExistente.get();
                            System.out.println("Autor '" + autorAsociadoAlLibro.getNombre() + "' ya existe. Usando autor existente.");
                        } else {
                            autorAsociadoAlLibro = autorRepository.save(autorObtenidoDeApi);
                            System.out.println("Autor '" + autorAsociadoAlLibro.getNombre() + "' es nuevo. Se guardó antes de asociar al libro.");
                        }
                    } else {
                        System.out.println("DEBUG: LibroService - No se encontró autor o el nombre del autor es nulo para este libro de la API.");
                    }

                    libroApi.setAutor(autorAsociadoAlLibro);
                    libroApi.setId(null);

                    System.out.println("DEBUG: LibroService - Antes de guardar: Libro: " + libroApi.getTitulo() +
                            ", Idioma: " + libroApi.getIdioma() +
                            ", Autor: " + (libroApi.getAutor() != null ? libroApi.getAutor().getNombre() : "NULL_AT_SAVE"));

                    Libro savedLibro = libroRepository.save(libroApi);
                    System.out.println("Libro guardado: " + savedLibro.getTitulo());
                    System.out.println("DEBUG: LibroService - Estado del libro guardado (via toString): " + savedLibro);
                    librosProcesados.add(savedLibro);
                } else {
                    System.out.println("--- Procesando libro de API: " + libroApi.getTitulo() + " ---");
                    System.out.println("Libro '" + libroApi.getTitulo() + "' ya existe en la base de datos.");
                }
            }
            return librosProcesados;
        } else {
            System.out.println("No se encontraron libros para el título: " + titulo + " en la API de Gutendex.");
            return Collections.emptyList();
        }
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public long contarLibrosPorIdioma(String idioma) {
        return libroRepository.countByIdioma(idioma);
    }

    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }
}
