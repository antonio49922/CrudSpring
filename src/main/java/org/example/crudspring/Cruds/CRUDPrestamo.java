package org.example.crudspring.Cruds;

import jakarta.validation.Valid;
import org.example.crudspring.DAOS.Prestamo;
import org.example.crudspring.Repositorios.PrestamosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class CRUDPrestamo {
    PrestamosRepositorio prestamosRepositorio;
    public CRUDPrestamo() {}

    @Autowired
    public CRUDPrestamo(PrestamosRepositorio repositorio) {this.prestamosRepositorio = repositorio;}


    //SELECT ALL
    @GetMapping
    public ResponseEntity<List<Prestamo>> getPrestamos() {
        List<Prestamo> lista = prestamosRepositorio.findAll();
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }

    //SELECT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getPrestamoById(@PathVariable Integer id) {
        Prestamo p= this.prestamosRepositorio.findById(String.valueOf(id)).get();
        return ResponseEntity.ok(p);
    }

    //INSERT
    @PostMapping("/prestamo")
    public ResponseEntity<Prestamo> addPrestamo(@Valid @RequestBody Prestamo prestamo) {
        Prestamo prestamoPersistido = this.prestamosRepositorio.save(prestamo);
        return ResponseEntity.ok().body(prestamoPersistido);
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> updatePrestamo(@Valid @RequestBody Prestamo prestamo, @PathVariable Integer id) {
        Prestamo prestamOPersistido= prestamosRepositorio.save(prestamo);
        return ResponseEntity.ok().body(prestamOPersistido);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrestamo(@PathVariable Integer id) {
        prestamosRepositorio.deleteById(String.valueOf(id));
        return ResponseEntity.ok().body("Prestamo eliminado");
    }

}
