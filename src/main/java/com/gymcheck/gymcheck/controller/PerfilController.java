package com.gymcheck.gymcheck.controller;

import com.gymcheck.gymcheck.model.Usuario;
import com.gymcheck.gymcheck.repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    private final UsuarioRepository usuarioRepository;

    public PerfilController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String exibirPerfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping
    public String salvarPerfil(@AuthenticationPrincipal UserDetails userDetails,
                               @ModelAttribute("usuario") Usuario form,
                               RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(form.getNome());
        usuario.setDataNascimento(form.getDataNascimento());
        usuario.setSexo(form.getSexo());
        usuario.setAltura(form.getAltura());
        usuarioRepository.save(usuario);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Perfil atualizado com sucesso!");
        return "redirect:/perfil";
    }
}