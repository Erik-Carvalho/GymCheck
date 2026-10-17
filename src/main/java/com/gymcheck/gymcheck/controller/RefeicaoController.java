package com.gymcheck.gymcheck.controller;

import com.gymcheck.gymcheck.model.Refeicao;
import com.gymcheck.gymcheck.service.RefeicaoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Clock;
import java.time.LocalDate;

@Controller
@RequestMapping("/dieta")
public class RefeicaoController {

    private final RefeicaoService refeicaoService;
    private final Clock clock;

    public RefeicaoController(RefeicaoService refeicaoService, Clock clock) {
        this.refeicaoService = refeicaoService;
        this.clock = clock;
    }

    @GetMapping
    public String index(Model model) {
        var refeicoes = refeicaoService.listarTodas();
        var refeicoesHoje = refeicaoService.buscarRefeicoesDeHoje();
        model.addAttribute("refeicoes", refeicoes);
        model.addAttribute("refeicoesHoje", refeicoesHoje);
        model.addAttribute("nutricaoHoje", refeicaoService.resumir(refeicoesHoje));
        Refeicao refeicao = new Refeicao();
        refeicao.setData(LocalDate.now(clock));
        model.addAttribute("refeicao", refeicao);
        return "dieta/index";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("refeicao") Refeicao refeicao,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Confira os dados nutricionais informados.");
            return "redirect:/dieta";
        }
        if (refeicao.getNomeRefeicao() == null || refeicao.getNomeRefeicao().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "O nome da refeição é obrigatório (ex: Café da Manhã, Almoço)!");
            return "redirect:/dieta";
        }

        refeicao.setNomeRefeicao(refeicao.getNomeRefeicao().trim());
        if (refeicao.getDescricaoAlimentos() != null) {
            refeicao.setDescricaoAlimentos(refeicao.getDescricaoAlimentos().trim());
        }

        try {
            refeicaoService.salvar(refeicao);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Refeição adicionada ao seu plano!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao salvar refeição.");
        }
        return "redirect:/dieta";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        refeicaoService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Refeição removida!");
        return "redirect:/dieta";
    }
}