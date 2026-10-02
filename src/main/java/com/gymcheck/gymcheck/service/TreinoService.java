package com.gymcheck.gymcheck.service;

import com.gymcheck.gymcheck.model.*;
import com.gymcheck.gymcheck.repository.*;
import com.gymcheck.gymcheck.dto.ItemTreinoDTO;
import com.gymcheck.gymcheck.dto.ReordenacaoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TreinoService {

    private final RotinaTreinoRepository rotinaRepository;
    private final ItemTreinoRepository itemTreinoRepository;
    private final RegistroTreinoRepository registroTreinoRepository;
    private final ExercicioRepository exercicioRepository;
    private final SerieRepository serieRepository;
    private final UsuarioService usuarioService;

    public TreinoService(RotinaTreinoRepository rotinaRepository,
                         ItemTreinoRepository itemTreinoRepository,
                         RegistroTreinoRepository registroTreinoRepository,
                         ExercicioRepository exercicioRepository,
                         SerieRepository serieRepository,
                         UsuarioService usuarioService) {
        this.rotinaRepository = rotinaRepository;
        this.itemTreinoRepository = itemTreinoRepository;
        this.registroTreinoRepository = registroTreinoRepository;
        this.exercicioRepository = exercicioRepository;
        this.serieRepository = serieRepository;
        this.usuarioService = usuarioService;
    }

    // --- MÉTODOS DE ROTINAS DE TREINO ---

    public List<RotinaTreino> listarTodasRotinas() {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        return rotinaRepository.findByUsuarioIdOrderByOrdemAscIdAsc(usuario.getId());
    }

    public List<RotinaTreino> buscarTreinosDeHoje() {
        DiaSemana diaAtual = DiaSemana.fromDayOfWeek(LocalDate.now().getDayOfWeek());
        return listarTodasRotinas().stream()
                .filter(r -> r.getDiasSemana() != null && r.getDiasSemana().contains(diaAtual))
                .collect(Collectors.toList());
    }

    public RotinaTreino buscarRotinaPorId(Long id) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        return rotinaRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new RuntimeException("Rotina de treino não encontrada com o ID: " + id));
    }

    public RotinaTreino salvarRotina(RotinaTreino rotina) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        if (rotina.getId() == null) {
            List<RotinaTreino> rotinas = rotinaRepository.findByUsuarioIdOrderByOrdemAscIdAsc(usuario.getId());
            rotina.setOrdem(rotinas.stream().map(RotinaTreino::getOrdem).filter(java.util.Objects::nonNull)
                    .mapToInt(Integer::intValue).max().orElse(-1) + 1);
        }
        rotina.setUsuario(usuario);
        return rotinaRepository.save(rotina);
    }

    @Transactional
    public void reordenarRotinas(ReordenacaoDTO reordenacao) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        List<RotinaTreino> rotinas = rotinaRepository.findByUsuarioIdOrderByOrdemAscIdAsc(usuario.getId());
        List<Long> idsRecebidos = reordenacao == null ? null : reordenacao.ids();
        validarIdsReordenados(idsRecebidos, rotinas.stream().map(RotinaTreino::getId).toList());

        Map<Long, RotinaTreino> rotinasPorId = new HashMap<>();
        rotinas.forEach(rotina -> rotinasPorId.put(rotina.getId(), rotina));
        for (int ordem = 0; ordem < idsRecebidos.size(); ordem++) {
            rotinasPorId.get(idsRecebidos.get(ordem)).setOrdem(ordem);
        }
        rotinaRepository.saveAll(rotinas);
    }

    @Transactional
    public RotinaTreino atualizarObservacao(Long id, String observacao) {
        RotinaTreino rotina = buscarRotinaPorId(id);
        rotina.setObservacao(observacao);
        return rotinaRepository.save(rotina);
    }

    public void deletarRotina(Long id) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        rotinaRepository.findByIdAndUsuarioId(id, usuario.getId()).ifPresent(rotinaRepository::delete);
    }

    // --- MÉTODOS DE ITENS DE TREINO ---

    public List<ItemTreino> listarItensPorRotina(Long rotinaId) {
        buscarRotinaPorId(rotinaId);
        return itemTreinoRepository.findByRotinaTreinoIdOrderByOrdemAscIdAsc(rotinaId);
    }

    public ItemTreino adicionarExercicioNaRotina(Long rotinaId, ItemTreino item) {
        RotinaTreino rotina = buscarRotinaPorId(rotinaId);
        item.setId(null);
        List<ItemTreino> itens = itemTreinoRepository.findByRotinaTreinoIdOrderByOrdemAscIdAsc(rotinaId);
        item.setOrdem(itens.stream().map(ItemTreino::getOrdem).filter(java.util.Objects::nonNull)
                .mapToInt(Integer::intValue).max().orElse(-1) + 1);
        item.setRotinaTreino(rotina);
        return itemTreinoRepository.save(item);
    }

    @Transactional
    public void reordenarExercicios(Long rotinaId, ReordenacaoDTO reordenacao) {
        buscarRotinaPorId(rotinaId);
        List<ItemTreino> itens = itemTreinoRepository.findByRotinaTreinoIdOrderByOrdemAscIdAsc(rotinaId);
        List<Long> idsRecebidos = reordenacao == null ? null : reordenacao.ids();
        validarIdsReordenados(idsRecebidos, itens.stream().map(ItemTreino::getId).toList());

        Map<Long, ItemTreino> itensPorId = new HashMap<>();
        itens.forEach(item -> itensPorId.put(item.getId(), item));
        for (int ordem = 0; ordem < idsRecebidos.size(); ordem++) {
            itensPorId.get(idsRecebidos.get(ordem)).setOrdem(ordem);
        }
        itemTreinoRepository.saveAll(itens);
    }

    private void validarIdsReordenados(List<Long> idsRecebidos, List<Long> idsAtuais) {
        if (idsRecebidos == null || idsRecebidos.size() != idsAtuais.size()
                || !new HashSet<>(idsRecebidos).equals(new HashSet<>(idsAtuais))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A lista de itens enviada é inválida.");
        }
    }

    @Transactional
    public ItemTreino atualizarItemTreino(Long itemId, ItemTreinoDTO dados) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        ItemTreino item = itemTreinoRepository.findByIdAndRotinaTreinoUsuarioId(itemId, usuario.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercício não encontrado."));

        item.setSeries(dados.series());
        item.setRepeticoes(dados.repeticoes());
        item.setPeso(dados.peso());
        return itemTreinoRepository.save(item);
    }

    public void removerItemDoTreino(Long itemId) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        itemTreinoRepository.findByIdAndRotinaTreinoUsuarioId(itemId, usuario.getId())
                .ifPresent(itemTreinoRepository::delete);
    }

    // --- MÉTODOS DE REGISTRO / CONCLUSÃO DE TREINO ---

    public List<RegistroTreino> listarHistoricoTreinos() {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        return registroTreinoRepository.findByUsuarioIdOrderByDataDesc(usuario.getId());
    }

    @Transactional
    public RegistroTreino concluirTreino(Long rotinaId, String observacoesDia) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        RotinaTreino rotina = buscarRotinaPorId(rotinaId);

        RegistroTreino registro = new RegistroTreino();
        registro.setData(LocalDate.now());
        registro.setRotina(rotina);
        registro.setUsuario(usuario);
        registro.setObservacoesDia(observacoesDia);
        RegistroTreino salvo = registroTreinoRepository.save(registro);

        for (ItemTreino item : rotina.getItens()) {
            Exercicio exercicio = exercicioRepository
                    .findFirstByUsuarioIdAndNomeIgnoreCase(usuario.getId(), item.getNomeExercicio())
                    .orElseGet(() -> {
                        Exercicio novoExercicio = new Exercicio();
                        novoExercicio.setNome(item.getNomeExercicio());
                        novoExercicio.setGrupoMuscular("Geral");
                        novoExercicio.setUsuario(usuario);
                        novoExercicio.setPesoInicialKg(item.getPeso() != null ? item.getPeso() : 0.0);
                        novoExercicio.setPesoAtualKg(item.getPeso() != null ? item.getPeso() : 0.0);
                        novoExercicio.setRecordePessoalKg(item.getPeso() != null ? item.getPeso() : 0.0);
                        return exercicioRepository.save(novoExercicio);
                    });

            double peso = item.getPeso() != null ? item.getPeso() : 0.0;
            if (peso > exercicio.getRecordePessoalKg()) {
                exercicio.setRecordePessoalKg(peso);
            }
            exercicio.setPesoAtualKg(peso);
            exercicioRepository.save(exercicio);

            int numSeries = (item.getSeries() != null && item.getSeries() > 0) ? item.getSeries() : 1;
            int numReps = item.getRepeticoes() != null ? item.getRepeticoes() : 10;

            for (int s = 1; s <= numSeries; s++) {
                Serie serie = new Serie();
                serie.setRegistroTreino(salvo);
                serie.setExercicio(exercicio);
                serie.setNumeroSerie(s);
                serie.setPesoKg(peso);
                serie.setRepeticoes(numReps);
                serieRepository.save(serie);
                salvo.getSeries().add(serie);
            }
        }

        return salvo;
    }
}
