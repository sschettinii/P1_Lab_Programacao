package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public String autenticar(@RequestParam String email, @RequestParam String senha, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.autenticar(email, senha);

        if (usuario != null) {
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/receitas";
        } else {
            redirectAttributes.addFlashAttribute("error", "E-mail ou senha inválidos!");
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "Você saiu do sistema.");
        return "redirect:/";
    }
}
