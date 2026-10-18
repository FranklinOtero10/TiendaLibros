package com.example.tienda_libros.repositorio;

import com.example.tienda_libros.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepositorio  extends JpaRepository<Libro, Integer> {
}
