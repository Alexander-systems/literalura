package com.literalura.repository;


import com.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.nacimiento <= :anio AND (a.fallecimiento IS NULL OR a.fallecimiento >= :anio)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);

    // Nuevo método para encontrar un autor por su nombre (retorna un Optional)
    Optional<Autor> findByNombre(String nombre);
}
