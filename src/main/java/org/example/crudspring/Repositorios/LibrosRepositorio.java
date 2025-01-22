package org.example.crudspring.Repositorios;

import org.example.crudspring.DAOS.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrosRepositorio extends JpaRepository<Libro, String> {
}
