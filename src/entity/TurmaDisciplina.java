package entity;

public class TurmaDisciplina {
    private int turmaId;
    private int disciplinaId;

    public TurmaDisciplina(int turmaId, int disciplinaId) {
        this.disciplinaId = disciplinaId;
        this.turmaId = turmaId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public int getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(int turmaId) {
        this.turmaId = turmaId;
    }

}
