package org.example.crudspring.Repositorios;

import org.example.crudspring.DAOS.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepositorio extends JpaRepository<Usuario, String> {
}
