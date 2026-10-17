package com.gymcheck.gymcheck.controller;

import com.gymcheck.gymcheck.model.MedidaCorporal;
import com.gymcheck.gymcheck.service.MedidaCorporalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/medidas")
public class MedidaCorporalController {

    private final MedidaCorporalService medidaCorporalService;

    public MedidaCorporalController(MedidaCorporalService medidaCorporalService) {
        this.medidaCorporalService = medidaCorporalService;
    }

    @GetMapping
    public String index(Model model) {
        List<MedidaCorporal> medidas = medidaCorporalService.listarTodas();
        List<MedidaCorporal> cronologicas = medidas.stream()
            .filter(medida -> medida.getData() != null)
            .sorted(Comparator.comparing(MedidaCorporal::getData))
            .toList();
        DateTimeFormatter formatoGrafico = DateTimeFormatter.ofPattern("dd/MM");

        model.addAttribute("medidas", medidas);
        model.addAttribute("graficoDatas", cronologicas.stream()
            .map(medida -> medida.getData().format(formatoGrafico)).toList());
        model.addAttribute("graficoPeso", cronologicas.stream().map(MedidaCorporal::getPesoKg).toList());
        model.addAttribute("graficoCintura", cronologicas.stream().map(MedidaCorporal::getCinturaCm).toList());
        model.addAttribute("graficoBraco", cronologicas.stream().map(MedidaCorporal::getBracoCm).toList());
        model.addAttribute("medida", new MedidaCorporal());
        return "medidas/index";
    }

    @PostMapping
    public String salvar(@ModelAttribute("medida") MedidaCorporal medida, RedirectAttributes redirectAttributes) {
        if (medida.getPesoKg() != null && (medida.getPesoKg() <= 0 || medida.getPesoKg() > 500)) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Informe um peso válido (entre 1kg e 500kg).");
            return "redirect:/medidas";
        }

        if (medida.getCinturaCm() != null && (medida.getCinturaCm() <= 0 || medida.getCinturaCm() > 300)) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Informe uma medida de cintura válida (entre 1cm e 300cm).");
            return "redirect:/medidas";
        }

        if (medida.getBracoCm() != null && (medida.getBracoCm() <= 0 || medida.getBracoCm() > 150)) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Informe uma medida de braço válida (entre 1cm e 150cm).");
            return "redirect:/medidas";
        }

        try {
            medidaCorporalService.salvar(medida);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Medida registrada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao registrar medida.");
        }
        return "redirect:/medidas";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        medidaCorporalService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Medida excluída!");
        return "redirect:/medidas";
    }
}