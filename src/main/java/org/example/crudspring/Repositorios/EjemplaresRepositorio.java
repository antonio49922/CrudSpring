package org.example.crudspring.Repositorios;

import org.example.crudspring.DAOS.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EjemplaresRepositorio extends JpaRepository<Ejemplar, String> {
}
