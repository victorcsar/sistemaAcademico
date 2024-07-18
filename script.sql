CREATE DATABASE IF NOT EXISTS projeto;
USE projeto;

-- Criando a tabela Turmas
CREATE TABLE IF NOT EXISTS Turmas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Criando a tabela Disciplinas
CREATE TABLE IF NOT EXISTS Disciplinas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Criando a tabela Alunos
CREATE TABLE IF NOT EXISTS Alunos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(255),
    senha VARCHAR(45),
    matricula VARCHAR(20),
    turma_id INT,
    FOREIGN KEY (turma_id) REFERENCES Turmas(id) ON DELETE SET NULL
);

-- Criando a tabela intermediária Turmas_Disciplinas
CREATE TABLE IF NOT EXISTS Turmas_Disciplinas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    turma_id INT NOT NULL,
    disciplina_id INT NOT NULL,
    FOREIGN KEY (turma_id) REFERENCES Turmas(id) ON DELETE CASCADE,
    FOREIGN KEY (disciplina_id) REFERENCES Disciplinas(id) ON DELETE CASCADE
);

-- Criando a tabela Notas
CREATE TABLE IF NOT EXISTS Notas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    aluno_id INT NOT NULL,
    turma_disciplina_id INT NOT NULL,
    nota DECIMAL(5, 2),
    FOREIGN KEY (aluno_id) REFERENCES Alunos(id),
    FOREIGN KEY (turma_disciplina_id) REFERENCES Turmas_Disciplinas(id) ON DELETE CASCADE
);

-- Criando o trigger para deletar registros na tabela Notas ao deletar uma entrada na tabela Turmas_Disciplinas
DELIMITER //
CREATE TRIGGER after_delete_turmas_disciplinas
AFTER DELETE ON Turmas_Disciplinas
FOR EACH ROW
BEGIN
    DELETE FROM Notas WHERE turma_disciplina_id = OLD.id;
END;
//
DELIMITER ;
