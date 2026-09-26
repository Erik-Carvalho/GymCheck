package com.gymcheck.gymcheck.service;

import com.gymcheck.gymcheck.model.Refeicao;
import com.gymcheck.gymcheck.model.Usuario;
import com.gymcheck.gymcheck.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefeicaoService {

    private final RefeicaoRepository refeicaoRepository;
    private final UsuarioService usuarioService;

    public RefeicaoService(RefeicaoRepository refeicaoRepository, UsuarioService usuarioService) {
        this.refeicaoRepository = refeicaoRepository;
        this.usuarioService = usuarioService;
    }

    public List<Refeicao> listarTodas() {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        return refeicaoRepository.findByUsuarioIdOrderByHorarioAsc(usuario.getId());
    }

    public List<Refeicao> buscarRefeicoesDeHoje() {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        return refeicaoRepository.findByUsuarioIdOrderByHorarioAsc(usuario.getId());
    }

    public Refeicao salvar(Refeicao refeicao) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        refeicao.setUsuario(usuario);
        return refeicaoRepository.save(refeicao);
    }

    public void deletar(Long id) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        refeicaoRepository.findByIdAndUsuarioId(id, usuario.getId()).ifPresent(refeicaoRepository::delete);
    }
}