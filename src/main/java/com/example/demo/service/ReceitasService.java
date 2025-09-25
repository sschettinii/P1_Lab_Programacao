package com.example.demo.service;

import com.example.demo.model.Receitas;
import com.example.demo.repository.ReceitasRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReceitasService {
    private final ReceitasRepository receitasRepository;

    public ReceitasService(ReceitasRepository receitasRepository) { this.receitasRepository = receitasRepository; }

    public List<Receitas> getAll() {
        return receitasRepository.findAll();
    }
    public Receitas getById(Long id) {
        return receitasRepository.findById(id).orElse(null);
    }
    public Receitas create(Receitas receita) {
        return receitasRepository.save(receita);
    }
    public void remove(Long id) { return receitasRepository.deleteById(id); }
    public Receitas update(Long id, Receitas receita) {
        if (receitasRepository.existById(id)) {
            receita.setId(id);
            return receitasRepository.save(receita);
        }
        return null;
    }
    public boolean isReceptValid(Receitas receita) {
        return receita != null && receita.getNome() != null && !receita.getNome().isBlank() &&
                receita.get() != null && !receita.getEmail().isBlank() &&
                receita.getTelefone() != null && !receita.getTelefone().isBlank();
    }
}