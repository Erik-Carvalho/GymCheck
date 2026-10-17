package com.gymcheck.gymcheck.service;

import com.gymcheck.gymcheck.model.MedidaCorporal;
import com.gymcheck.gymcheck.model.Usuario;
import com.gymcheck.gymcheck.repository.MedidaCorporalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedidaCorporalService {

    private final MedidaCorporalRepository medidaCorporalRepository;
    private final UsuarioService usuarioService;

    public MedidaCorporalService(MedidaCorporalRepository medidaCorporalRepository, UsuarioService usuarioService) {
        this.medidaCorporalRepository = medidaCorporalRepository;
        this.usuarioService = usuarioService;
    }

    public List<MedidaCorporal> listarTodas() {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        return medidaCorporalRepository.findByUsuarioIdOrderByDataDesc(usuario.getId());
    }

    public List<MedidaCorporal> buscarHistoricoCompleto() {
        return listarTodas();
    }

    public Optional<MedidaCorporal> buscarUltimaMedida() {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        return medidaCorporalRepository.findFirstByUsuarioIdOrderByDataDesc(usuario.getId());
    }

    public Optional<Double> buscarVariacaoPeso() {
        List<MedidaCorporal> medidasComPeso = listarTodas().stream()
                .filter(medida -> medida.getPesoKg() != null)
                .limit(2)
                .toList();
        if (medidasComPeso.size() < 2) return Optional.empty();
        return Optional.of(medidasComPeso.get(0).getPesoKg() - medidasComPeso.get(1).getPesoKg());
    }

    public MedidaCorporal salvar(MedidaCorporal medida) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        medida.setUsuario(usuario);
        return medidaCorporalRepository.save(medida);
    }

    public void deletar(Long id) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        medidaCorporalRepository.findByIdAndUsuarioId(id, usuario.getId()).ifPresent(medidaCorporalRepository::delete);
    }
}