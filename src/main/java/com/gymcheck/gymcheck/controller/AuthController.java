package com.gymcheck.gymcheck.controller;

import com.gymcheck.gymcheck.model.Usuario;
import com.gymcheck.gymcheck.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.regex.Pattern;

@Controller
public class AuthController {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String salvarUsuario(Usuario usuario, RedirectAttributes redirectAttributes) {
        String email = usuario.getEmail();
        String senha = usuario.getSenha();

        if (email == null || email.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "O e-mail é obrigatório!");
            return "redirect:/cadastro";
        }

        String emailNormalizado = email.trim().toLowerCase();

        if (!EMAIL_PATTERN.matcher(emailNormalizado).matches()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Por favor, informe um formato de e-mail válido (ex: seuemail@dominio.com).");
            return "redirect:/cadastro";
        }

        if (senha == null || senha.trim().length() < 6) {
            redirectAttributes.addFlashAttribute("mensagemErro", "A senha deve ter no mínimo 6 caracteres.");
            return "redirect:/cadastro";
        }

        if (usuarioRepository.existsByEmailIgnoreCase(emailNormalizado)) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Este e-mail já está cadastrado!");
            return "redirect:/cadastro";
        }

        usuario.setEmail(emailNormalizado);
        usuario.setSenha(passwordEncoder.encode(senha.trim()));
        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Cadastro realizado com sucesso! Faça seu login.");
        return "redirect:/login?cadastrado";
    }
}