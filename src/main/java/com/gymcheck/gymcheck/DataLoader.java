package com.gymcheck.gymcheck;

import com.gymcheck.gymcheck.model.DiaSemana;
import com.gymcheck.gymcheck.model.ItemTreino;
import com.gymcheck.gymcheck.model.RotinaTreino;
import com.gymcheck.gymcheck.model.Usuario;
import com.gymcheck.gymcheck.repository.ItemTreinoRepository;
import com.gymcheck.gymcheck.repository.RotinaTreinoRepository;
import com.gymcheck.gymcheck.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile({"dev", "local", "test"})
public class DataLoader implements CommandLineRunner {

    private final RotinaTreinoRepository rotinaRepository;
    private final ItemTreinoRepository itemTreinoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RotinaTreinoRepository rotinaRepository,
                      ItemTreinoRepository itemTreinoRepository,
                      UsuarioRepository usuarioRepository,
                      PasswordEncoder passwordEncoder) {
        this.rotinaRepository = rotinaRepository;
        this.itemTreinoRepository = itemTreinoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Cria usuário padrão de demonstração se não existir
        Usuario demoUser = usuarioRepository.findByEmail("admin@gymcheck.com")
                .orElseGet(() -> {
                    Usuario u = new Usuario();
                    u.setEmail("admin@gymcheck.com");
                    u.setSenha(passwordEncoder.encode("123456"));
                    return usuarioRepository.save(u);
                });

        if (rotinaRepository.findByUsuarioIdOrderByIdAsc(demoUser.getId()).isEmpty()) {
            RotinaTreino treinoA = new RotinaTreino();
            treinoA.setNome("Treino A - Peito e Tríceps");
            treinoA.setDescricao("Foco em hipertrofia e carga progressiva");
            treinoA.setUsuario(demoUser);
            treinoA.setDiasSemana(Set.of(DiaSemana.SEGUNDA, DiaSemana.QUINTA));
            rotinaRepository.save(treinoA);

            ItemTreino item1 = new ItemTreino();
            item1.setNomeExercicio("Supino Reto com Barra");
            item1.setSeries(4);
            item1.setRepeticoes(10);
            item1.setPeso(30.0);
            item1.setRotinaTreino(treinoA);
            itemTreinoRepository.save(item1);

            ItemTreino item2 = new ItemTreino();
            item2.setNomeExercicio("Tríceps Pulley");
            item2.setSeries(3);
            item2.setRepeticoes(12);
            item2.setPeso(25.0);
            item2.setRotinaTreino(treinoA);
            itemTreinoRepository.save(item2);
        }
    }
}
