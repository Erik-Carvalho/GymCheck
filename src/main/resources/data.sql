-- ==========================================================
-- GymCheck — Massa de Dados Inicial
-- Banco: H2 em memória / PostgreSQL
-- ==========================================================

-- 1. Usuário Administrador de Demonstração (senha '123456' com BCrypt)
-- Hash BCrypt para 123456: $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi
MERGE INTO usuario (id, email, senha, nome, data_nascimento, sexo, altura)
KEY(id)
VALUES (
    1,
    'admin@gymcheck.com',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi',
    'Atleta GymCheck',
    DATE '1998-05-15',
    'Masculino',
    178.0
);

-- 2. Banco de Exercícios Padrão (Supino Reto, Agachamento, Puxada Alta, etc.)
MERGE INTO exercicios (id, nome, grupo_muscular, observacoes, peso_inicial_kg, peso_atual_kg, recorde_pessoal_kg, usuario_id)
KEY(id)
VALUES (1, 'Supino Reto com Barra', 'Peitoral', 'Escápulas travadas e pés firmes no chão.', 40.0, 80.0, 100.0, 1);

MERGE INTO exercicios (id, nome, grupo_muscular, observacoes, peso_inicial_kg, peso_atual_kg, recorde_pessoal_kg, usuario_id)
KEY(id)
VALUES (2, 'Agachamento Livre', 'Pernas', 'Manter coluna neutra e quebrar a paralela.', 50.0, 100.0, 130.0, 1);

MERGE INTO exercicios (id, nome, grupo_muscular, observacoes, peso_inicial_kg, peso_atual_kg, recorde_pessoal_kg, usuario_id)
KEY(id)
VALUES (3, 'Puxada Alta Frontal', 'Costas', 'Puxar com os cotovelos e contrair o grande dorsal.', 35.0, 65.0, 75.0, 1);

MERGE INTO exercicios (id, nome, grupo_muscular, observacoes, peso_inicial_kg, peso_atual_kg, recorde_pessoal_kg, usuario_id)
KEY(id)
VALUES (4, 'Desenvolvimento com Halteres', 'Ombros', 'Executar com banco a 75-80 graus.', 12.0, 24.0, 28.0, 1);

MERGE INTO exercicios (id, nome, grupo_muscular, observacoes, peso_inicial_kg, peso_atual_kg, recorde_pessoal_kg, usuario_id)
KEY(id)
VALUES (5, 'Tríceps Pulley Barra Reta', 'Tríceps', 'Cotovelos fixos ao lado do tronco.', 20.0, 35.0, 42.5, 1);

MERGE INTO exercicios (id, nome, grupo_muscular, observacoes, peso_inicial_kg, peso_atual_kg, recorde_pessoal_kg, usuario_id)
KEY(id)
VALUES (6, 'Rosca Direta com Barra W', 'Bíceps', 'Sem balanço do tronco durante a subida.', 15.0, 30.0, 36.0, 1);

-- 3. Rotinas de Treino Exemplo
MERGE INTO rotinas_treino (id, nome, descricao, usuario_id)
KEY(id)
VALUES (1, 'Treino A - Peito, Ombro e Tríceps', 'Foco em força e hipertrofia de empurrar', 1);

MERGE INTO rotinas_treino (id, nome, descricao, usuario_id)
KEY(id)
VALUES (2, 'Treino B - Costas e Bíceps', 'Foco em tração e largura dorsal', 1);

MERGE INTO rotinas_treino (id, nome, descricao, usuario_id)
KEY(id)
VALUES (3, 'Treino C - Pernas Completo', 'Quadríceps, posteriores e panturrilhas', 1);

-- Dias da Semana vinculados às rotinas
MERGE INTO rotina_dias_semana (rotina_id, dia_semana)
KEY(rotina_id, dia_semana)
VALUES (1, 'SEGUNDA');

MERGE INTO rotina_dias_semana (rotina_id, dia_semana)
KEY(rotina_id, dia_semana)
VALUES (1, 'QUINTA');

MERGE INTO rotina_dias_semana (rotina_id, dia_semana)
KEY(rotina_id, dia_semana)
VALUES (2, 'TERCA');

MERGE INTO rotina_dias_semana (rotina_id, dia_semana)
KEY(rotina_id, dia_semana)
VALUES (2, 'SEXTA');

MERGE INTO rotina_dias_semana (rotina_id, dia_semana)
KEY(rotina_id, dia_semana)
VALUES (3, 'QUARTA');

MERGE INTO rotina_dias_semana (rotina_id, dia_semana)
KEY(rotina_id, dia_semana)
VALUES (3, 'SABADO');

-- 4. Itens de Exercício vinculados às rotinas
MERGE INTO item_treino (id, nome_exercicio, series, repeticoes, peso, rotina_treino_id)
KEY(id)
VALUES (1, 'Supino Reto com Barra', 4, 10, 80.0, 1);

MERGE INTO item_treino (id, nome_exercicio, series, repeticoes, peso, rotina_treino_id)
KEY(id)
VALUES (2, 'Desenvolvimento com Halteres', 3, 10, 24.0, 1);

MERGE INTO item_treino (id, nome_exercicio, series, repeticoes, peso, rotina_treino_id)
KEY(id)
VALUES (3, 'Tríceps Pulley Barra Reta', 3, 12, 35.0, 1);

MERGE INTO item_treino (id, nome_exercicio, series, repeticoes, peso, rotina_treino_id)
KEY(id)
VALUES (4, 'Puxada Alta Frontal', 4, 10, 65.0, 2);

MERGE INTO item_treino (id, nome_exercicio, series, repeticoes, peso, rotina_treino_id)
KEY(id)
VALUES (5, 'Rosca Direta com Barra W', 3, 12, 30.0, 2);

MERGE INTO item_treino (id, nome_exercicio, series, repeticoes, peso, rotina_treino_id)
KEY(id)
VALUES (6, 'Agachamento Livre', 4, 8, 100.0, 3);
