package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import entity.Aluno;
import entity.Turma;

public class AlunoDAO {
    
    public void cadastrarAluno(Aluno aluno) {
        String sql = "INSERT INTO ALUNOS (nome, matricula, login, senha) VALUES (?, ?, ?, ?)";
    
        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getMatricula());
            ps.setString(3, aluno.getLogin());
            ps.setString(4, aluno.getSenha());
    
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    public void atribuirTurma(Turma turma, Aluno aluno) {
        String sql = "UPDATE ALUNOS SET TURMA_ID = ? WHERE MATRICULA = ?";
    
        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, turma.getId());
            ps.setString(2, aluno.getMatricula());
    
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Aluno> getAlunos() {
        String sql = "SELECT * FROM ALUNOS";
        List<Aluno> alunos = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno(rs.getInt("id"), rs.getString("nome"), rs.getString("matricula"), rs.getInt("turma_id"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public Aluno getAluno(String matricula) {
        String sql = "SELECT * FROM ALUNOS WHERE matricula = ?";
        Aluno aluno = null;
    
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, matricula);
    
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    aluno = new Aluno(rs.getInt("id"), rs.getString("nome"), rs.getString("matricula"), rs.getInt("turma_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aluno;
    }

    public boolean deletarAluno(int alunoId) {
        String deleteNotasSql = "DELETE FROM Notas WHERE aluno_id = ?";
        String deleteAlunoSql = "DELETE FROM Alunos WHERE id = ?";
    
        PreparedStatement psDeleteNotas = null;
        PreparedStatement psDeleteAluno = null;
    
        try {
            Connection conn = Conexao.getConexao();
            // Deleta as notas do aluno
            psDeleteNotas = conn.prepareStatement(deleteNotasSql);
            psDeleteNotas.setInt(1, alunoId);
            psDeleteNotas.executeUpdate();
    
            // Deleta o aluno
            psDeleteAluno = conn.prepareStatement(deleteAlunoSql);
            psDeleteAluno.setInt(1, alunoId);
            int linhasAfetadas = psDeleteAluno.executeUpdate();
    
            if (linhasAfetadas > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (psDeleteNotas != null) {
                    psDeleteNotas.close();
                }
                if (psDeleteAluno != null) {
                    psDeleteAluno.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void updateAluno(String matricula, Aluno updatedAluno) {
        String sql = "UPDATE ALUNOS SET NOME = ?, LOGIN = ?, SENHA = ? WHERE MATRICULA = ?";
    
        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, updatedAluno.getNome());
            ps.setString(2, updatedAluno.getLogin());
            ps.setString(3, updatedAluno.getSenha());
            ps.setString(4, matricula);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
