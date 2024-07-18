package entity;

public class Nota {
    private int alunoId;
    private double nota;
    private int turmaDisciplinaId;

    //construtor
    public Nota(int alunoId, int turmaDisciplinaId, double nota) {
        this.alunoId = alunoId;
        this.turmaDisciplinaId = turmaDisciplinaId;
        this.nota = nota;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getTurmaDisciplinaId() {
        return turmaDisciplinaId;
    }
    
    public void setTurmaDisciplinaId(int turmaDisciplinaId) {
        this.turmaDisciplinaId = turmaDisciplinaId;
    }
    
}
