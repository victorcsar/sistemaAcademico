package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import entity.Disciplina;
import entity.Turma;


public class DisciplinaDAO {
    public void cadastrarDiscplina(Disciplina disciplina){
        String sql = "INSERT INTO Disciplinas (nome) VALUES (?)";

        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, disciplina.getNome());

            ps.execute();
        } catch (Exception e) {
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

    public List<Disciplina> getDisciplinas() {
        String sql = "SELECT id, nome FROM DISCIPLINAS";
        List<Disciplina> disciplinas = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Disciplina disciplina = new Disciplina(rs.getString("nome"));
                disciplina.setId(rs.getInt("id"));
                disciplinas.add(disciplina);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplinas;
    }

    public Disciplina getLastDisciplina() {
        String sql = "SELECT id, nome FROM DISCIPLINAS ORDER BY id DESC LIMIT 1";
        Disciplina disciplina = null;

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                disciplina = new Disciplina(rs.getString("nome"));
                disciplina.setId(rs.getInt("id"));
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplina;
    }

    public Disciplina getDisciplinaProcurada(Integer id) {
        String sql = "SELECT id, nome FROM DISCIPLINAS WHERE id = ?";
        Disciplina disciplina = null;
    
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    disciplina = new Disciplina(rs.getString("nome"));
                    disciplina.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return disciplina;
    }

    public List<Disciplina> getDisciplinasPorAluno(int alunoId) {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT d.id, d.nome " +
                     "FROM Alunos a " +
                     "JOIN Turmas_Disciplinas td ON a.turma_id = td.turma_id " +
                     "JOIN Disciplinas d ON td.disciplina_id = d.id " +
                     "WHERE a.id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alunoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    Disciplina disciplina = new Disciplina(id, nome);
                    disciplinas.add(disciplina);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disciplinas;
    }

    public boolean updateDisciplina(Integer id_disciplina, String novoNome) {
        String sql = "UPDATE DISCIPLINAS SET NOME = ? WHERE ID = ?";

        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, novoNome);
            ps.setInt(2, id_disciplina);

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                return true;
            } else {
                return false;
            }
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
        return false;
    }

     public boolean deletarDisciplina(Disciplina disciplina) {
        String deleteDisciplinaSql = "DELETE FROM Disciplinas WHERE id = ?";

        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();

            ps = conn.prepareStatement(deleteDisciplinaSql);
            ps.setInt(1, disciplina.getId());
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                return true;
            } else {
                return false;
            }
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
        return false;
    }
    
}
