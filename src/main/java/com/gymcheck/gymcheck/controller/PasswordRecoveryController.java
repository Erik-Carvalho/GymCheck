package com.gymcheck.gymcheck.controller;

import com.gymcheck.gymcheck.service.PasswordRecoveryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PasswordRecoveryController {
    private final PasswordRecoveryService passwordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @GetMapping("/recuperar-senha")
    public String solicitar() {
        return "recuperar-senha";
    }

    @PostMapping("/recuperar-senha")
    public String enviarLink(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        passwordRecoveryService.solicitarRecuperacao(email);
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Se o e-mail estiver cadastrado, você receberá um link para redefinir a senha.");
        return "redirect:/recuperar-senha";
    }

    @GetMapping("/redefinir-senha")
    public String redefinir(@RequestParam(value = "token", required = false) String token, Model model) {
        model.addAttribute("token", token);
        model.addAttribute("tokenValido", passwordRecoveryService.tokenValido(token));
        return "redefinir-senha";
    }

    @PostMapping("/redefinir-senha")
    public String salvarNovaSenha(@RequestParam("token") String token,
                                  @RequestParam("novaSenha") String novaSenha,
                                  @RequestParam("confirmarSenha") String confirmarSenha,
                                  Model model) {
        boolean tokenValido = passwordRecoveryService.tokenValido(token);
        String mensagemErro = null;

        if (!tokenValido) {
            mensagemErro = "Este link é inválido ou expirou. Solicite uma nova recuperação.";
        } else if (novaSenha == null || novaSenha.length() < 6) {
            mensagemErro = "A senha deve ter no mínimo 6 caracteres.";
        } else if (!novaSenha.equals(confirmarSenha)) {
            mensagemErro = "As senhas não conferem.";
        } else if (!passwordRecoveryService.redefinirSenha(token, novaSenha)) {
            tokenValido = false;
            mensagemErro = "Este link é inválido ou expirou. Solicite uma nova recuperação.";
        }

        if (mensagemErro != null) {
            model.addAttribute("token", token);
            model.addAttribute("tokenValido", tokenValido);
            model.addAttribute("mensagemErro", mensagemErro);
            return "redefinir-senha";
        }

        return "redirect:/login?senhaAtualizada";
    }
}