package com.literalura.controller;

import com.literalura.model.Libro;
import com.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroApiService; // Esta es la instancia inyectada

    @GetMapping("/buscar")
    public List<Libro> buscarLibro(@RequestParam String titulo) {
        // Usa la instancia inyectada para llamar al método de instancia
        return libroApiService.buscarLibrosPorTitulo(titulo);
    }
}