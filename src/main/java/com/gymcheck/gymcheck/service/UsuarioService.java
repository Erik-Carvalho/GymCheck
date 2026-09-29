package com.gymcheck.gymcheck.service;

import com.gymcheck.gymcheck.model.Usuario;
import com.gymcheck.gymcheck.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario getUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new IllegalStateException("Nenhum usuário autenticado encontrado no contexto de segurança.");
        }
        String email = auth.getName();
        String emailTratado = (email != null) ? email.trim().toLowerCase() : "";
        return usuarioRepository.findByEmailIgnoreCase(emailTratado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return usuarioRepository.findByEmailIgnoreCase(email.trim().toLowerCase());
    }

    public Usuario atualizarPerfil(Usuario dadosAtualizados) {
        Usuario usuario = getUsuarioAutenticado();
        if (dadosAtualizados.getNome() != null) {
            usuario.setNome(dadosAtualizados.getNome().trim());
        }
        usuario.setDataNascimento(dadosAtualizados.getDataNascimento());
        usuario.setSexo(dadosAtualizados.getSexo());
        usuario.setAltura(dadosAtualizados.getAltura());
        return usuarioRepository.save(usuario);
    }
}
