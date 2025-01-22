package org.example.crudspring.Repositorios;

import org.example.crudspring.DAOS.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamosRepositorio extends JpaRepository<Prestamo, String> {
}
