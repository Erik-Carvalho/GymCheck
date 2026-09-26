package com.gymcheck.gymcheck.controller;

import com.gymcheck.gymcheck.model.DiaSemana;
import com.gymcheck.gymcheck.model.RotinaTreino;
import com.gymcheck.gymcheck.model.Usuario;
import com.gymcheck.gymcheck.service.MedidaCorporalService;
import com.gymcheck.gymcheck.service.RefeicaoService;
import com.gymcheck.gymcheck.service.TreinoService;
import com.gymcheck.gymcheck.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DashboardController {
    private final TreinoService treinoService;
    private final RefeicaoService refeicaoService;
    private final MedidaCorporalService medidaCorporalService;
    private final UsuarioService usuarioService;

    public DashboardController(TreinoService treinoService,
                               RefeicaoService refeicaoService,
                               MedidaCorporalService medidaCorporalService,
                               UsuarioService usuarioService) {
        this.treinoService = treinoService;
        this.refeicaoService = refeicaoService;
        this.medidaCorporalService = medidaCorporalService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String index(Model model) {
        try {
            Usuario usuario = usuarioService.getUsuarioAutenticado();
            model.addAttribute("usuarioLogado", usuario);
        } catch (Exception ignored) {}

        DiaSemana diaAtual = DiaSemana.fromDayOfWeek(LocalDate.now().getDayOfWeek());
        List<RotinaTreino> treinosHoje = treinoService.buscarTreinosDeHoje();

        model.addAttribute("diaSemanaAtual", diaAtual != null ? diaAtual.getDescricao() : "");
        model.addAttribute("treinosHoje", treinosHoje);
        model.addAttribute("ultimosTreinos", treinoService.listarHistoricoTreinos());
        model.addAttribute("rotinas", treinoService.listarTodasRotinas());
        model.addAttribute("refeicoesHoje", refeicaoService.buscarRefeicoesDeHoje());
        model.addAttribute("ultimaMedida", medidaCorporalService.buscarUltimaMedida().orElse(null));
        return "dashboard";
    }
}
