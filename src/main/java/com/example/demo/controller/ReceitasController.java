package com.example.demo.controller;

import com.example.demo.model.Receitas;
import com.example.demo.service.ReceitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("receitas")
public class ReceitasController {
    private final ReceitasService service;

    @Autowired
    public ReceitasController(ReceitasService service) { this.service = service; }

    @GetMapping
    public List<Receitas> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Receitas receita = service.getById(id);
        if (receita != null) {
            return ResponseEntity.ok(receita);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Receitas> create(@RequestBody Receitas receita) {
        try {
            Receitas novaReceita = service.create(receita);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaReceita); // 201 CREATED
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        try {
            service.remove(id);
            return ResponseEntity.noContent().build(); // 204 NO CONTENT
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.notFound().build(); // 404 NOT FOUND
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(@PathVariable Long id, @RequestBody Receitas receita) {
        if (!service.isValidRecept(receita)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 BAD REQUEST
        }
        try {
            Receitas updatedRecept = service.update(id, receita);
            return ResponseEntity.ok(updatedRecept); // 200 OK
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.notFound().build(); // 404 NOT FOUND
        }
    }
}

