package com.gymcheck.gymcheck.controller;

import com.gymcheck.gymcheck.dto.UsuarioProfileDTO;
import com.gymcheck.gymcheck.model.Usuario;
import com.gymcheck.gymcheck.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        model.addAttribute("perfilForm", toProfileDTO(usuario));
        return "perfil";
    }

    @PostMapping
    public String salvarPerfil(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid @ModelAttribute("perfilForm") UsuarioProfileDTO form,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("mensagemErro", bindingResult.getFieldError().getDefaultMessage());
            return "perfil";
        }

        usuario.setNome(form.getNome() != null ? form.getNome().trim() : null);
        usuario.setDataNascimento(form.getDataNascimento());
        usuario.setSexo(form.getSexo());
        usuario.setAltura(form.getAltura());
        usuarioRepository.save(usuario);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Perfil atualizado com sucesso!");
        return "redirect:/perfil";
    }

    private UsuarioProfileDTO toProfileDTO(Usuario usuario) {
        UsuarioProfileDTO form = new UsuarioProfileDTO();
        form.setNome(usuario.getNome());
        form.setDataNascimento(usuario.getDataNascimento());
        form.setSexo(usuario.getSexo());
        form.setAltura(usuario.getAltura());
        return form;
    }
}