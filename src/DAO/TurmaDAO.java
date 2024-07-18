package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;

import conexao.Conexao;
import entity.Turma;

public class TurmaDAO {

     public void cadastrarTurma(Turma turma) {
        String sql = "INSERT INTO TURMAS (nome) VALUES (?)";
    
        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, turma.getNome());
    
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
    
    public List<Turma> getTurmas() {
        String sql = "SELECT id, nome FROM TURMAS";
        List<Turma> turmas = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Turma turma = new Turma(rs.getString("nome"));
                turma.setId(rs.getInt("id"));
                turmas.add(turma);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turmas;
    }

    public Turma getTurmaProcurada(Integer id) {
        String sql = "SELECT * FROM Turmas WHERE id = ?";
        Turma turma = null;

        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    turma = new Turma(rs.getString("nome"));
                    turma.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return turma;
    }

    public boolean updateTurma(Integer id_turma, String novoNome) {
        String sql = "UPDATE TURMAS SET NOME = ? WHERE ID = ?";

        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, novoNome);
            ps.setInt(2, id_turma);

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

    public boolean deletarTurma(Turma turma) {
        String deleteTurmaSql = "DELETE FROM Turmas WHERE id = ?";

        PreparedStatement psDeleteTurma = null;

        try {
            Connection conn = Conexao.getConexao();
            // deletar turma
            psDeleteTurma = conn.prepareStatement(deleteTurmaSql);
            psDeleteTurma.setInt(1, turma.getId());
            int linhasAfetadas = psDeleteTurma.executeUpdate();

            if (linhasAfetadas > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (psDeleteTurma != null) {
                    psDeleteTurma.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
