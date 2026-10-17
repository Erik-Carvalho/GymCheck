package com.gymcheck.gymcheck.service;

import com.gymcheck.gymcheck.model.Refeicao;
import com.gymcheck.gymcheck.model.Usuario;
import com.gymcheck.gymcheck.dto.ResumoNutricionalDTO;
import com.gymcheck.gymcheck.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

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
        return refeicaoRepository.findByUsuarioIdOrderByDataDescHorarioAsc(usuario.getId());
    }

    public List<Refeicao> buscarRefeicoesDeHoje() {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        return refeicaoRepository.findByUsuarioIdAndDataOrderByHorarioAsc(usuario.getId(), LocalDate.now());
    }

    public ResumoNutricionalDTO resumir(List<Refeicao> refeicoes) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        double calorias = somar(refeicoes, Refeicao::getCalorias);
        double proteinas = somar(refeicoes, Refeicao::getProteinasGramas);
        double carboidratos = somar(refeicoes, Refeicao::getCarboidratosGramas);
        double gorduras = somar(refeicoes, Refeicao::getGordurasGramas);
        boolean temDados = refeicoes.stream().anyMatch(refeicao -> refeicao.getCalorias() != null
                || refeicao.getProteinasGramas() != null || refeicao.getCarboidratosGramas() != null
                || refeicao.getGordurasGramas() != null);

        return new ResumoNutricionalDTO(
                calorias, proteinas, carboidratos, gorduras,
                usuario.getMetaCaloriasDia(), usuario.getMetaProteinasDia(),
                usuario.getMetaCarboidratosDia(), usuario.getMetaGordurasDia(),
                progresso(calorias, usuario.getMetaCaloriasDia()),
                progresso(proteinas, usuario.getMetaProteinasDia()),
                progresso(carboidratos, usuario.getMetaCarboidratosDia()),
                progresso(gorduras, usuario.getMetaGordurasDia()), temDados);
    }

    private double somar(List<Refeicao> refeicoes, Function<Refeicao, Double> valor) {
        return refeicoes.stream().mapToDouble(refeicao -> {
            Double quantidade = valor.apply(refeicao);
            return quantidade != null && Double.isFinite(quantidade) ? quantidade : 0;
        }).sum();
    }

    private int progresso(double total, Integer meta) {
        if (meta == null || meta <= 0) return 0;
        return (int) Math.min(100, Math.round(total * 100 / meta));
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