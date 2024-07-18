package entity;

public class Disciplina {
    public String nome;
    public int id;

    //construtor
    public Disciplina(String nome) {
        this.nome = nome;
    }

    public Disciplina(int id, String nome){
        this.id = id;
        this.nome = nome;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
