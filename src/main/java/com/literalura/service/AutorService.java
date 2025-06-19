package com.literalura.service;

import com.literalura.model.Autor;
import com.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> obtenerAutoresVivosEnAnio(Integer anio) {
        return autorRepository.findAutoresVivosEnAnio(anio);
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public Autor guardarAutor(Autor autor) {
        return autorRepository.save(autor);
    }
}
