package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.Conexao;
import entity.TurmaDisciplina;


public class TurmaDisciplinaDAO {
    public void criarTurmaDisciplina(TurmaDisciplina td){
        String sql = "INSERT INTO Turmas_Disciplinas (turma_id, disciplina_id) VALUES (?, ?)";

        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, td.getTurmaId());
            ps.setInt(2, td.getDisciplinaId());

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

    public int getTurmaDisciplinaId(int turmaId, int disciplinaId) {
        String sql = "SELECT td.id FROM Turmas_Disciplinas td WHERE td.turma_id = ? AND td.disciplina_id = ?";
        int turmaDisciplinaId = -1;  // Valor padrão para indicar que não foi encontrado

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, turmaId);
            ps.setInt(2, disciplinaId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    turmaDisciplinaId = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return turmaDisciplinaId;
    }
}
