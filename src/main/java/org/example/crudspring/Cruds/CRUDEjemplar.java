package org.example.crudspring.Cruds;

import jakarta.validation.Valid;
import org.example.crudspring.DAOS.Ejemplar;
import org.example.crudspring.Repositorios.EjemplaresRepositorio;
import org.example.crudspring.DAOS.Libro;
import org.example.crudspring.Repositorios.LibrosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ejemplares")
public class CRUDEjemplar {

    EjemplaresRepositorio ejemplaresRepositorio;
    LibrosRepositorio librosRepositorio;
    public CRUDEjemplar() {}

    @Autowired
    public CRUDEjemplar(EjemplaresRepositorio repositorio, LibrosRepositorio repositorio2) {
        this.ejemplaresRepositorio = repositorio;
        this.librosRepositorio = repositorio2;
    }


    //SELECT *
    @GetMapping
    public ResponseEntity<List<Ejemplar>> getEjemplares() {
        List<Ejemplar> lista = this.ejemplaresRepositorio.findAll();
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }

    //SELECT BY ISBN
    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Ejemplar> getEjemplarJson(@PathVariable String id){
        Ejemplar e = this.ejemplaresRepositorio.findById(id).get();
        return ResponseEntity.ok(e);
    }

    //INSERT
    @PostMapping("/ejemplar")
    public ResponseEntity<Ejemplar> addEjemplar(@Valid @RequestBody Ejemplar ejemplar){
        Ejemplar ejemplarPersistido = this.ejemplaresRepositorio.save(ejemplar);
        return ResponseEntity.ok().body(ejemplarPersistido);
    }

    // POST con Form normal
    @PostMapping(value = "/ejemplarForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ejemplar> addEjemplarForm(
            @RequestParam Integer id,
            @RequestParam String estado,
            @RequestParam String isbn
    ) {
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setId(id);
        ejemplar.setEstado(estado);

        Libro libro = librosRepositorio.findById(isbn).orElse(null);

        ejemplar.setIsbn(libro);

        Ejemplar ejemplarPersistido = this.ejemplaresRepositorio.save(ejemplar);
        return ResponseEntity.created(null).body(ejemplarPersistido);
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Ejemplar> updateEjemplar(@RequestBody Ejemplar ejemplar, @PathVariable String id){
        Ejemplar ejemplarPersistido = ejemplaresRepositorio.save(ejemplar);
        return ResponseEntity.ok().body(ejemplarPersistido);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEjemplar(@PathVariable String id){
        ejemplaresRepositorio.deleteById(id);
        String mensaje = "ejemplar con id: "+id+" borrado";
        return ResponseEntity.ok().body(mensaje);
    }

}
