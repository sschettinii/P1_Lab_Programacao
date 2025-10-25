package com.example.demo.controller;

import com.example.demo.model.Receitas;
import com.example.demo.service.ReceitasService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("receitas")
public class ReceitasController {
    private final ReceitasService service;

    @Autowired
    public ReceitasController(ReceitasService service) { this.service = service; }


    @GetMapping
    public String listarReceitas(Model model, @RequestParam(value = "termo", required = false) String termoBusca, HttpSession session) {
        String termoEfetivo;
        if (termoBusca != null) {
            session.setAttribute("termoBuscaSalvo", termoBusca);
            termoEfetivo = termoBusca;
        } else {
            termoEfetivo = (String) session.getAttribute("termoBuscaSalvo");
        }

        List<Receitas> receitas = service.search(termoEfetivo);

        model.addAttribute("receitas", receitas);
        model.addAttribute("receita", new Receitas());
        model.addAttribute("termoBusca", termoEfetivo);
        return "receitas/lista-receitas";
    }

    @GetMapping("/limpar-busca")
    public String limparBusca(HttpSession session) {
        session.removeAttribute("termoBuscaSalvo");

        return "redirect:/receitas";
    }

    @PostMapping("/salvar")
    public String salvarReceita(@Valid @ModelAttribute Receitas receita, BindingResult bindingResult, Model model, HttpSession session) {
        String termoEfetivo = (String) session.getAttribute("termoBuscaSalvo");
        if (bindingResult.hasErrors()) {
            List<Receitas> listaDeReceitas = service.getAll();
            model.addAttribute("receitas", listaDeReceitas);
            model.addAttribute("showModal", true);
            model.addAttribute("receita", receita);
            model.addAttribute("termoBusca", termoEfetivo);
            return "receitas/lista-receitas";
        }

        service.create(receita);
        return "redirect:/receitas";
    }

    @PostMapping("/deletar/{id}")
    public String deletarReceita(@PathVariable Long id) {
        service.remove(id);
        return "redirect:/receitas";
    }

    @PostMapping("/api/salvar")
    @ResponseBody
    public ResponseEntity<?> salvarReceitaAjax(@Valid @ModelAttribute Receitas receita, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            fieldError -> fieldError.getField(),
                            DefaultMessageSourceResolvable::getDefaultMessage
                    ));

            return ResponseEntity.badRequest().body(errors);
        }
        Receitas receitaSalva = service.create(receita);

        return ResponseEntity.ok(receitaSalva);
    }
}

