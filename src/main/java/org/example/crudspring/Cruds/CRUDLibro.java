package org.example.crudspring.Cruds;


import jakarta.validation.Valid;
import org.example.crudspring.DAOS.Libro;
import org.example.crudspring.Repositorios.LibrosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class CRUDLibro {
    LibrosRepositorio librosRepositorio;

    public CRUDLibro() {}

    @Autowired
    public CRUDLibro(LibrosRepositorio libroRepositorio) {this.librosRepositorio = libroRepositorio;}

    //GET --> SELECT *
    @GetMapping
    public ResponseEntity<List<Libro>> getLibro(){
        List<Libro> lista = this.librosRepositorio.findAll();
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }

    //GET BY ISBN --> SELECT BY ISBN
    @GetMapping("/{isbn}")
    @Cacheable
    public ResponseEntity<Libro> getLibroJson(@PathVariable String isbn){
        Libro l = this.librosRepositorio.findById(isbn).get();
        return ResponseEntity.ok(l);
    }

    //POST --> INSERT
    @PostMapping("/libro")
    public ResponseEntity<Libro> addLibro(@Valid @RequestBody Libro libro){
        System.out.println("Entra aqui");
        Libro libroPersistido = this.librosRepositorio.save(libro);
        return ResponseEntity.ok().body(libroPersistido);
    }

    //POST con Form normal, se trabajará con JSONs normalmente...
    @PostMapping(value = "/libroForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Libro> addLibroForm(@RequestParam String isbn,
                                              @RequestParam String titulo,
                                              @RequestParam String autor){
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        this.librosRepositorio.save(libro);
        return ResponseEntity.created(null).body(libro);
    }

    //POST con Form normal y fichero, se trabajará con JSONs normalmente...
    @PostMapping(value = "/libroFormFichero", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Libro> addLibroFormFichero(@RequestParam String isbn,
                                                     @RequestParam String titulo,
                                                     @RequestParam String autor,
                                                     @RequestParam MultipartFile imagen){
        //Datos básicos del libro
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);

        //guardado en la bbdd del libro
        this.librosRepositorio.save(libro);

        //devolución del objeto en formato json para el cliente
        return ResponseEntity.created(null).body(libro);
    }

    //PUT --> UPDATE
    @PutMapping("/{isbn}")
    public ResponseEntity<Libro> updateLibro(@RequestBody Libro libro, @PathVariable String isbn){
        Libro libroPersistido = librosRepositorio.save(libro);
        return ResponseEntity.ok().body(libroPersistido);
    }

    //DELETE
    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteLibro(@PathVariable String isbn){
        librosRepositorio.deleteById(isbn);
        String mensaje = "libro con isbn: "+isbn+" borrado";
        return ResponseEntity.ok().body(mensaje);
    }


}
