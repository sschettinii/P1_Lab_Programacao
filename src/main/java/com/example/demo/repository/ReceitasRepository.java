package com.example.demo.repository;

import com.example.demo.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitasRepository
        extends JpaRepository<Receitas, Long> {
}