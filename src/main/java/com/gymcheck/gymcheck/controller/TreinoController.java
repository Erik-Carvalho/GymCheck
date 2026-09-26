package com.gymcheck.gymcheck.controller;

import com.gymcheck.gymcheck.model.DiaSemana;
import com.gymcheck.gymcheck.model.ItemTreino;
import com.gymcheck.gymcheck.model.RotinaTreino;
import com.gymcheck.gymcheck.service.TreinoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/treinos")
public class TreinoController {

    private final TreinoService treinoService;

    public TreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @GetMapping
    public String listarRotinas(Model model) {
        model.addAttribute("rotinas", treinoService.listarTodasRotinas());
        model.addAttribute("novaRotina", new RotinaTreino());
        model.addAttribute("todosDiasSemana", DiaSemana.values());
        return "treinos/index";
    }

    @PostMapping("/nova")
    public String criarRotina(@ModelAttribute RotinaTreino rotina, RedirectAttributes redirectAttributes) {
        if (rotina.getNome() == null || rotina.getNome().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "O nome do treino é obrigatório!");
            return "redirect:/treinos";
        }
        rotina.setNome(rotina.getNome().trim());
        if (rotina.getDescricao() != null) {
            rotina.setDescricao(rotina.getDescricao().trim());
        }
        treinoService.salvarRotina(rotina);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Rotina criada com sucesso!");
        return "redirect:/treinos";
    }

    @GetMapping("/rotinas/{id}")
    public String detalhesRotina(@PathVariable Long id, Model model) {
        try {
            RotinaTreino rotina = treinoService.buscarRotinaPorId(id);
            model.addAttribute("rotina", rotina);
            model.addAttribute("novoItem", new ItemTreino());
            model.addAttribute("todosDiasSemana", DiaSemana.values());
            return "treinos/detalhes";
        } catch (RuntimeException e) {
            return "redirect:/treinos";
        }
    }

    @PostMapping("/rotinas/{id}/atualizar-dias")
    public String atualizarDias(@PathVariable Long id,
                                @ModelAttribute RotinaTreino dados,
                                RedirectAttributes redirectAttributes) {
        try {
            RotinaTreino rotina = treinoService.buscarRotinaPorId(id);
            rotina.setDiasSemana(dados.getDiasSemana());
            treinoService.salvarRotina(rotina);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Dias da semana atualizados com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao atualizar dias do treino.");
        }
        return "redirect:/treinos/rotinas/" + id;
    }

    @PostMapping("/rotinas/{id}/adicionar-exercicio")
    public String adicionarExercicio(@PathVariable Long id,
                                     @ModelAttribute ItemTreino itemTreino,
                                     RedirectAttributes redirectAttributes) {
        if (itemTreino.getNomeExercicio() == null || itemTreino.getNomeExercicio().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "O nome do exercício é obrigatório!");
            return "redirect:/treinos/rotinas/" + id;
        }

        if (itemTreino.getSeries() != null && itemTreino.getSeries() <= 0) {
            redirectAttributes.addFlashAttribute("mensagemErro", "A quantidade de séries deve ser maior que 0.");
            return "redirect:/treinos/rotinas/" + id;
        }

        if (itemTreino.getRepeticoes() != null && itemTreino.getRepeticoes() <= 0) {
            redirectAttributes.addFlashAttribute("mensagemErro", "A quantidade de repetições deve ser maior que 0.");
            return "redirect:/treinos/rotinas/" + id;
        }

        if (itemTreino.getPeso() != null && itemTreino.getPeso() < 0) {
            redirectAttributes.addFlashAttribute("mensagemErro", "O peso (carga) não pode ser negativo.");
            return "redirect:/treinos/rotinas/" + id;
        }

        itemTreino.setNomeExercicio(itemTreino.getNomeExercicio().trim());

        try {
            treinoService.adicionarExercicioNaRotina(id, itemTreino);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Exercício adicionado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao adicionar exercício.");
        }
        return "redirect:/treinos/rotinas/" + id;
    }

    @PostMapping("/rotinas/{rotinaId}/remover-item/{itemId}")
    public String removerExercicio(@PathVariable Long rotinaId,
                                   @PathVariable Long itemId,
                                   RedirectAttributes redirectAttributes) {
        treinoService.removerItemDoTreino(itemId);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Exercício removido!");
        return "redirect:/treinos/rotinas/" + rotinaId;
    }

    @PostMapping("/rotinas/{id}/deletar")
    public String deletarRotina(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        treinoService.deletarRotina(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Rotina excluída!");
        return "redirect:/treinos";
    }

    @PostMapping("/rotinas/{id}/concluir")
    public String concluirTreino(@PathVariable Long id,
                                 @RequestParam(value = "observacoesDia", required = false) String observacoesDia,
                                 RedirectAttributes redirectAttributes) {
        try {
            treinoService.concluirTreino(id, observacoesDia);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Parabéns! Treino concluído com sucesso e registrado no seu histórico!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não foi possível concluir o treino: " + e.getMessage());
        }
        return "redirect:/";
    }
}
