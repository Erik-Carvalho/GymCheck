package com.gymcheck.gymcheck.controller;

import com.gymcheck.gymcheck.model.Refeicao;
import com.gymcheck.gymcheck.service.RefeicaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dieta")
public class RefeicaoController {

    private final RefeicaoService refeicaoService;

    public RefeicaoController(RefeicaoService refeicaoService) {
        this.refeicaoService = refeicaoService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("refeicoes", refeicaoService.listarTodas());
        model.addAttribute("refeicao", new Refeicao());
        return "dieta/index";
    }

    @PostMapping
    public String salvar(@ModelAttribute("refeicao") Refeicao refeicao, RedirectAttributes redirectAttributes) {
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