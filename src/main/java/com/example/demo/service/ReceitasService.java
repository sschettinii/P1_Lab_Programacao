package com.example.demo.service;

import com.example.demo.model.Receitas;
import com.example.demo.repository.ReceitasRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReceitasService {
    private final ReceitasRepository receitasRepository;

    public ReceitasService(ReceitasRepository receitasRepository) { this.receitasRepository = receitasRepository; }

    public List<Receitas> getAll() {
        return receitasRepository.findAll();
    }
    public Receitas create(Receitas receita) {
        return receitasRepository.save(receita);
    }
    public void remove(Long id) { receitasRepository.deleteById(id); }
}