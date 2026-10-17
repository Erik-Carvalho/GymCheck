package com.gymcheck.gymcheck.service;

import com.gymcheck.gymcheck.model.*;
import com.gymcheck.gymcheck.repository.*;
import com.gymcheck.gymcheck.dto.ItemTreinoDTO;
import com.gymcheck.gymcheck.dto.ProgressaoTreinoDTO;
import com.gymcheck.gymcheck.dto.ReordenacaoDTO;
import com.gymcheck.gymcheck.dto.RegistroTreinoDetalheDTO;
import com.gymcheck.gymcheck.dto.SessaoTreinoForm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TreinoService {

    private final RotinaTreinoRepository rotinaRepository;
    private final ItemTreinoRepository itemTreinoRepository;
    private final RegistroTreinoRepository registroTreinoRepository;
    private final ExercicioRepository exercicioRepository;
    private final SerieRepository serieRepository;
    private final UsuarioService usuarioService;
    private final Clock clock;

    public TreinoService(RotinaTreinoRepository rotinaRepository,
                         ItemTreinoRepository itemTreinoRepository,
                         RegistroTreinoRepository registroTreinoRepository,
                         ExercicioRepository exercicioRepository,
                         SerieRepository serieRepository,
                         UsuarioService usuarioService,
                         Clock clock) {
        this.rotinaRepository = rotinaRepository;
        this.itemTreinoRepository = itemTreinoRepository;
        this.registroTreinoRepository = registroTreinoRepository;
        this.exercicioRepository = exercicioRepository;
        this.serieRepository = serieRepository;
        this.usuarioService = usuarioService;
        this.clock = clock;
    }

    // --- MÉTODOS DE ROTINAS DE TREINO ---

    public List<RotinaTreino> listarTodasRotinas() {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        return rotinaRepository.findByUsuarioIdOrderByOrdemAscIdAsc(usuario.getId());
    }

    public List<Exercicio> listarExercicios() {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        return exercicioRepository.findByUsuarioIdOrderByNomeAsc(usuario.getId());
    }

    public Exercicio salvarExercicio(Exercicio exercicio) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        exercicio.setId(null);
        exercicio.setNome(exercicio.getNome().trim());
        exercicio.setGrupoMuscular(exercicio.getGrupoMuscular().trim());
        exercicio.setUsuario(usuario);
        return exercicioRepository.save(exercicio);
    }

    public List<RotinaTreino> buscarTreinosDeHoje() {
        DiaSemana diaAtual = DiaSemana.fromDayOfWeek(LocalDate.now(clock).getDayOfWeek());
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

        public Map<Long, Long> buscarRegistrosHojePorRotina() {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        Map<Long, Long> registrosPorRotina = new LinkedHashMap<>();
        registroTreinoRepository.findByUsuarioIdAndDataOrderByIdDesc(usuario.getId(), LocalDate.now(clock))
            .forEach(registro -> registrosPorRotina.putIfAbsent(registro.getRotina().getId(), registro.getId()));
        return registrosPorRotina;
        }

        public RegistroTreinoDetalheDTO buscarRegistroTreino(Long registroId) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        RegistroTreino registro = registroTreinoRepository.findByIdAndUsuarioId(registroId, usuario.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de treino não encontrado."));

        Map<Long, List<Serie>> seriesPorExercicio = registro.getSeries().stream()
            .collect(Collectors.groupingBy(serie -> serie.getExercicio().getId(), LinkedHashMap::new, Collectors.toList()));
        List<RegistroTreinoDetalheDTO.ExercicioRegistradoDTO> exercicios = seriesPorExercicio.values().stream()
            .map(series -> new RegistroTreinoDetalheDTO.ExercicioRegistradoDTO(
                series.get(0).getExercicio().getNome(),
                series.stream()
                    .sorted(java.util.Comparator.comparing(Serie::getNumeroSerie))
                    .map(serie -> new RegistroTreinoDetalheDTO.SerieRegistradaDTO(
                        serie.getNumeroSerie(), serie.getRepeticoes(), serie.getPesoKg()))
                    .toList()))
            .toList();

        return new RegistroTreinoDetalheDTO(
            registro.getData(), registro.getRotina().getNome(), registro.getObservacoesDia(), exercicios);
        }

    public ProgressaoTreinoDTO buscarProgressaoCarga(Long rotinaId) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        buscarRotinaPorId(rotinaId);
        List<RegistroTreino> registros = registroTreinoRepository
                .findByUsuarioIdAndRotina_IdOrderByDataAsc(usuario.getId(), rotinaId);
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM");
        List<String> datas = registros.stream().map(registro -> registro.getData().format(formatoData)).toList();
        List<String> nomesExercicios = registros.stream()
                .flatMap(registro -> registro.getSeries().stream())
                .map(serie -> serie.getExercicio().getNome())
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .toList();

        Map<String, List<Double>> cargasPorExercicio = new LinkedHashMap<>();
        for (String nomeExercicio : nomesExercicios) {
            List<Double> cargas = registros.stream().map(registro -> registro.getSeries().stream()
                    .filter(serie -> nomeExercicio.equals(serie.getExercicio().getNome()))
                    .map(Serie::getPesoKg)
                    .filter(Objects::nonNull)
                    .max(Double::compareTo)
                    .orElse(null)).toList();
            cargasPorExercicio.put(nomeExercicio, cargas);
        }
        return new ProgressaoTreinoDTO(datas, cargasPorExercicio);
    }

    @Transactional
    public RegistroTreino concluirTreino(Long rotinaId, SessaoTreinoForm sessaoForm) {
        Usuario usuario = usuarioService.getUsuarioAutenticado();
        RotinaTreino rotina = buscarRotinaPorId(rotinaId);
        List<ItemTreino> itensRotina = rotina.getItens();

        if (sessaoForm == null || sessaoForm.getExercicios() == null
                || sessaoForm.getExercicios().size() != itensRotina.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A sessão enviada não corresponde à ficha.");
        }

        Map<Long, ItemTreino> itensPorId = new HashMap<>();
        itensRotina.forEach(item -> itensPorId.put(item.getId(), item));
        Set<Long> itensEnviados = new HashSet<>();
        int seriesRealizadas = 0;

        for (SessaoTreinoForm.ExercicioSessaoForm exercicioForm : sessaoForm.getExercicios()) {
            if (exercicioForm == null || exercicioForm.getItemTreinoId() == null
                    || !itensEnviados.add(exercicioForm.getItemTreinoId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Há exercícios inválidos ou repetidos na sessão.");
            }

            ItemTreino item = itensPorId.get(exercicioForm.getItemTreinoId());
            if (item == null || exercicioForm.getSeries() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A sessão contém um exercício que não pertence à ficha.");
            }

            for (SessaoTreinoForm.SerieSessaoForm serieForm : exercicioForm.getSeries()) {
                if (serieForm == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Há séries inválidas na sessão.");
                }
                if (!Boolean.TRUE.equals(serieForm.getRealizada())) continue;
                if (serieForm.getPesoKg() == null || serieForm.getPesoKg() < 0
                        || serieForm.getRepeticoes() == null || serieForm.getRepeticoes() <= 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe carga e repetições válidas nas séries realizadas.");
                }
                seriesRealizadas++;
            }
        }

        if (!itensEnviados.equals(itensPorId.keySet()) || seriesRealizadas == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marque pelo menos uma série realizada.");
        }

        RegistroTreino registro = new RegistroTreino();
        registro.setData(LocalDate.now(clock));
        registro.setRotina(rotina);
        registro.setUsuario(usuario);
        registro.setObservacoesDia(sessaoForm.getObservacoesDia());
        RegistroTreino salvo = registroTreinoRepository.save(registro);

        for (SessaoTreinoForm.ExercicioSessaoForm exercicioForm : sessaoForm.getExercicios()) {
            ItemTreino item = itensPorId.get(exercicioForm.getItemTreinoId());
            List<SessaoTreinoForm.SerieSessaoForm> seriesFeitas = exercicioForm.getSeries().stream()
                    .filter(serie -> Boolean.TRUE.equals(serie.getRealizada()))
                    .toList();
            if (seriesFeitas.isEmpty()) continue;

            Exercicio exercicio = exercicioRepository
                    .findFirstByUsuarioIdAndNomeIgnoreCase(usuario.getId(), item.getNomeExercicio())
                    .orElseGet(() -> {
                        Exercicio novoExercicio = new Exercicio();
                        novoExercicio.setNome(item.getNomeExercicio());
                        novoExercicio.setGrupoMuscular("Geral");
                        novoExercicio.setUsuario(usuario);
                        double primeiraCarga = seriesFeitas.get(0).getPesoKg();
                        novoExercicio.setPesoInicialKg(primeiraCarga);
                        novoExercicio.setPesoAtualKg(primeiraCarga);
                        novoExercicio.setRecordePessoalKg(0.0);
                        return exercicioRepository.save(novoExercicio);
                    });

            Double ultimaCargaRealizada = null;
            for (int indiceSerie = 0; indiceSerie < exercicioForm.getSeries().size(); indiceSerie++) {
                SessaoTreinoForm.SerieSessaoForm serieForm = exercicioForm.getSeries().get(indiceSerie);
                if (!Boolean.TRUE.equals(serieForm.getRealizada())) continue;

                Serie serie = new Serie();
                serie.setRegistroTreino(salvo);
                serie.setExercicio(exercicio);
                serie.setNumeroSerie(indiceSerie + 1);
                serie.setPesoKg(serieForm.getPesoKg());
                serie.setRepeticoes(serieForm.getRepeticoes());
                serieRepository.save(serie);
                salvo.getSeries().add(serie);

                ultimaCargaRealizada = serieForm.getPesoKg();
                double recordeAtual = exercicio.getRecordePessoalKg() != null ? exercicio.getRecordePessoalKg() : 0.0;
                if (serieForm.getPesoKg() > recordeAtual) {
                    exercicio.setRecordePessoalKg(serieForm.getPesoKg());
                }
            }

            if (ultimaCargaRealizada != null) {
                exercicio.setPesoAtualKg(ultimaCargaRealizada);
                exercicioRepository.save(exercicio);
            }
        }

        return salvo;
    }
}
