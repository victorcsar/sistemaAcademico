import java.util.List;
import java.util.Scanner;

import DAO.AlunoDAO;
import DAO.DisciplinaDAO;
import DAO.NotaDAO;
import DAO.TurmaDAO;
import DAO.TurmaDisciplinaDAO;
import entity.Aluno;
import entity.Disciplina;
import entity.Nota;
import entity.Turma;
import entity.TurmaDisciplina;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            // Menu
            System.out.println("\nBem vindo(a) ao nosso sistema, o que deseja fazer hoje? \n");
            System.out.println("[1] Gerenciar Alunos \n[2] Gerenciar Turmas \n[3] Gerenciar Disciplinas \n[4] Gerenciar Notas \n[5] Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    boolean submenu1 = true;
                    while (submenu1) {
                        System.out.println("\n##### Gerenciar Alunos #######\n");
                        System.out.println("[1]Cadastrar Aluno \n[2]Listar Alunos \n[3]Deletar Aluno \n[4]Alterar Dados \n[5]Voltar");
                        int opcaoSub1 = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoSub1) {
                            case 1:
                                System.out.println("Informe o nome do aluno:");
                                String nome = scanner.nextLine();
                                
                                System.out.println("Informe o login do aluno:");
                                String login = scanner.nextLine();
                                
                                System.out.println("Crie uma senha para o aluno:");
                                String senha = scanner.nextLine();
                                
                                Aluno a = new Aluno(nome, login, senha);
            
                                try {
                                    AlunoDAO aluno_dao = new AlunoDAO();
            
                                    aluno_dao.cadastrarAluno(a);
                                    System.out.println("Aluno(a) " + nome + " criado com sucesso");
            
                                    System.out.println("Selecione a turma que esse aluno fará parte:");

                                    // adicionando aluno a uma turma
                                    List<Turma> turmas = new TurmaDAO().getTurmas();
                                    if(turmas.size() != 0){
                                        for (Turma turma : turmas) {
                                            System.out.printf("[%d] %s", turma.getId(), turma.getNome());
                                            System.out.println();
                                        }
                                    }else{
                                        System.out.println("Nenhuma turma disponivel, Cadastre uma turma primeiro");
                                        Thread.sleep(3000);
                                        break;
                                    }
                                    int turmaInput = scanner.nextInt();
                                    scanner.nextLine();
                                    
                                    Turma turmaSelecionada = null;
                                    for (Turma turma : turmas) {
                                        if (turma.getId() == turmaInput) {
                                            turmaSelecionada = turma;
                                        }
                                    }
            
                                    if (turmaSelecionada != null) {
                                        aluno_dao.atribuirTurma(turmaSelecionada, a);
                                        System.out.printf("Aluno criado e adicionado a turma %s", turmaSelecionada.getNome());
                                        Thread.sleep(3000);
                                    } else {
                                        System.out.println("Opção inválida!");
                                        Thread.sleep(3000);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Algo deu errado");
                                }

                            break;

                            case 2:
                                try {
                                    List<Aluno> alunos = new AlunoDAO().getAlunos();
                                    System.out.println("Lista de Alunos Cadastrados");
                                        if(alunos.size() != 0){
                                            TurmaDAO turmaDAO = new TurmaDAO();
                                            Turma turma = null;
                                            for (Aluno aluno : alunos) {
                                                turma = turmaDAO.getTurmaProcurada(aluno.getTurma_id());
                                                if (turma != null) {
                                                    System.out.printf("Nome: %s, Matricula: %s, Turma: %s", aluno.getNome(), aluno.getMatricula(), turma.getNome());
                                                } else {
                                                    System.out.printf("Nome: %s, Matricula: %s", aluno.getNome(), aluno.getMatricula());
                                                }
                                                System.out.println();
                                            }
                                            Thread.sleep(3000);
                                        }else{
                                            System.out.println("Nenhuma Aluno Cadastrado!");
                                            Thread.sleep(3000);
                                            break;
                                        }
                                } catch (Exception e) {
                                    System.out.println("Ocorreu um erro ao lista os Alunos!");
                                    System.out.println(e);
                                }
                            
                                break;
                            case 3:
                                System.out.println("Digite a matricula do aluno que deseja deletar:\n");
                                String matricula = scanner.nextLine();
            
                                try {
                                    Aluno aluno = new AlunoDAO().getAluno(matricula);
                                    if(aluno == null){
                                        System.out.println("Aluno não encontrado!");
                                    }else{
                                        try {
                                            boolean deletado = new AlunoDAO().deletarAluno(aluno.getId());
                                            if(deletado){
                                                System.out.printf("Aluno %s deletado com sucesso\n", aluno.getNome());
                                                Thread.sleep(2000);
                                            }else{
                                                System.out.printf("Aluno %s não foi deletado\n", aluno.getNome());
                                                Thread.sleep(2000);
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Ocorreu um erro ao deletar o aluno");
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Ocorreu ao procurar pelo Aluno");
                                    System.out.println(e);
                                }
                                break;

                            case 4:
                                System.out.println("Digite a matricula do aluno que deseja editar:\n");
                                String matriculaa = scanner.nextLine();
                                
                                try {
                                    Aluno aluno = new AlunoDAO().getAluno(matriculaa);
                                    if(aluno == null){
                                        System.out.println("Aluno não encontrado!");
                                    }else{
                                        try {
                                            System.out.println("Informe o nome do aluno:");
                                            String nomeU = scanner.nextLine();
                                            
                                            System.out.println("Informe o novo login do aluno:");
                                            String loginU = scanner.nextLine();
                                            
                                            System.out.println("Crie uma nova senha para o aluno:");
                                            String senhaU = scanner.nextLine();
                                    
                                            Aluno updated = new Aluno(nomeU, loginU, senhaU);
                                            new AlunoDAO().updateAluno(aluno.getMatricula(), updated);
                                            System.out.printf("Aluno %s editado com sucesso!", updated.getNome());
                                        } catch (Exception e) {
                                            System.out.println("Ocorreu um erro ao editar o aluno");
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Ocorreu ao procurar pelo Aluno");
                                    System.out.println(e);
                                }
                                break;

                            case 5:
                                submenu1 = false;
                                break;
                            default:
                                System.out.println("Opção inválida, por favor tente novamente.");
                                break;
                        }
                        
                    }
                    
                    break;
                
                case 2:
                    boolean submenu2 = true;
                    while (submenu2) {
                        System.out.println("\n##### Gerenciar Turmas #######\n");
                        System.out.println("[1]Cadastrar Turma \n[2]Listar Turmas \n[3]Deletar Turma \n[4]Alterar Dados \n[5]Voltar");
                        int opcaoSub2 = scanner.nextInt();
                        scanner.nextLine();
                        
                        switch (opcaoSub2) {
                            case 1:
                                System.out.println("Informe o nome da turma a ser criada:");
                                String nomeTurma = scanner.nextLine();

                                Turma t = new Turma(nomeTurma);
                                try {
                                    new TurmaDAO().cadastrarTurma(t);
                                    System.out.printf("Turma %s criada com sucesso!", nomeTurma);
                                } catch (Exception e) {
                                    System.out.println("Ocorreu um erro ao criar turma");
                                }
                                
                                break;
                            case 2:
                                List<Turma> turmas = new TurmaDAO().getTurmas();
                                if(turmas.size() != 0){
                                    System.out.println("Lista de Turmas Cadastradas:\n");
                                    for (Turma turma : turmas) {
                                        System.out.printf("Turma: %s", turma.getNome());
                                        System.out.println();
                                    }
                                    Thread.sleep(3000);
                                }else{
                                    System.out.println("Nenhuma turma disponivel, Cadastre uma turma primeiro");
                                    Thread.sleep(3000);
                                }

                                break;
                            case 3:
                                System.out.println("Escolha a turma que deseja deletar:\n");
                                List<Turma> turmasD = new TurmaDAO().getTurmas();
                                
                                if(turmasD.size() != 0){
                                    for (Turma turma : turmasD) {
                                        System.out.printf("[%d] %s", turma.getId(), turma.getNome());
                                        System.out.println();
                                    }
                                    int turmaSelected = scanner.nextInt();
                                    scanner.nextLine();
                                    Turma turmaADeletar = new TurmaDAO().getTurmaProcurada(turmaSelected);

                                    if (turmaADeletar != null) {
                                        boolean turmaDeletada = new TurmaDAO().deletarTurma(turmaADeletar);
                                        
                                        if (turmaDeletada) {
                                            System.out.println("Turma deletada com sucesso!");
                                        } else {
                                            System.out.println("Erro ao deletar turma");
                                        }
                                    } else {
                                        System.out.println("Opção inválida");
                                    }

                                }else{
                                    System.out.println("Nenhuma turma cadastrada");
                                }

                                break;
                            case 4:
                                System.out.println("Escolha a turma que deseja editar:");
                                List<Turma> turmasU = new TurmaDAO().getTurmas();
                                
                                if (turmasU.size() != 0) {
                                    for (Turma turma : turmasU) {
                                        System.out.printf("[%d] %s", turma.getId(), turma.getNome());
                                        System.out.println();
                                    }
                                    int turmaSelected = scanner.nextInt();
                                    scanner.nextLine();
                                    Turma turmaEditar = new TurmaDAO().getTurmaProcurada(turmaSelected);

                                    if (turmaEditar != null) {
                                        System.out.println("Informe o novo nome da turma:");
                                        String novoNome = scanner.nextLine();
                                        
                                        boolean editado = new TurmaDAO().updateTurma(turmaEditar.getId(), novoNome);
                                        if (editado) {
                                            System.out.println("Turma editada com sucesso!");
                                        } else {
                                            System.out.println("Ocorreu um erro ao editar turma");
                                        }
                                    } else {
                                        System.out.println("Opção inválida");
                                    }

                                } else {
                                    System.out.println("Nenhuma turma cadastrada");
                                }

                                break;
                            case 5:
                                submenu2 = false;
                                break;
                        
                            default:
                                System.out.println("Opção inválida, por favor tente novamente.");
                                break;
                        }
                    
                    }  
                    break;
                
                case 3:
                    
                    boolean submenu3 = true;
                        while (submenu3) {
                            System.out.println("\n##### Gerenciar Diciplinas #######\n");
                            System.out.println("[1]Cadastrar Disciplina \n[2]Listar Disciplinas \n[3]Deletar Disciplina \n[4]Alterar Dados \n[5]Voltar");
                            int opcaoSub3 = scanner.nextInt();
                            scanner.nextLine();

                            switch (opcaoSub3) {
                                case 1:

                                    System.out.println("Selecione a turma para cadastrar disciplina:");

                                    try {
                                        List<Turma> turmas = new TurmaDAO().getTurmas();
                                        if(turmas.size() != 0){
                                            for (Turma turma : turmas) {
                                                System.out.printf("[%d] %s", turma.getId(), turma.getNome());
                                                System.out.println();
                                            }
                                        }else{
                                            System.out.println("Nenhuma turma disponivel. Cadastre uma turma primeiro");
                                            Thread.sleep(3000);
                                            break;
                                        }
                                        int turmaInput = scanner.nextInt();
                                        scanner.nextLine();
                
                                        Turma turmaSelecionada = null;
                                        for (Turma turma : turmas) {
                                            if (turma.getId() == turmaInput) {
                                                turmaSelecionada = turma;
                                            }
                                        }
                
                                        System.out.println("Digite o nome da Disciplina:");
                                        String nomeDisciplina = scanner.nextLine();
                
                                        Disciplina d = new Disciplina(nomeDisciplina);
                
                                        try {
                                            new DisciplinaDAO().cadastrarDiscplina(d);
                                            System.out.printf("Disciplina %s criada com sucesso!", nomeDisciplina);
                                            System.out.println();
                                            try {
                                                Disciplina lastdisciplina = new DisciplinaDAO().getLastDisciplina();
                                                TurmaDisciplina td = new TurmaDisciplina(turmaSelecionada.getId(), lastdisciplina.getId());
                                                
                                                // atribuindo disciplina a uma turma
                                                new TurmaDisciplinaDAO().criarTurmaDisciplina(td);
                                                System.out.printf("Disciplina %s foi cadastrada na turma %s com Sucesso", lastdisciplina.getNome(), turmaSelecionada.getNome());
                                                System.out.println();
                                                Thread.sleep(3000);
                                                
                                            } catch (Exception e) {
                                                System.out.println("Ocorreu um erro ao atribuir disciplina");
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Ocorreu um erro ao criar disciplina");
                                        }
                
                                    } catch (Exception e) {
                                        System.out.println("Ocorreu um erro cadastrar disciplina!");
                                    }
                                    
                                    break;
                                
                                case 2:
                                    List<Disciplina> disciplinas = new DisciplinaDAO().getDisciplinas();
                                    if(disciplinas.size() != 0){
                                        System.out.println("Lista de Disciplinas Cadastradas:\n");
                                        for (Disciplina disciplina : disciplinas) {
                                            System.out.printf("Disciplina/cod:[%d] %s", disciplina.getId(), disciplina.getNome());
                                            System.out.println();
                                        }
                                        Thread.sleep(3000);
                                    }else{
                                        System.out.println("Nenhuma disciplina encontrada. Cadastre uma disciplina primeiro");
                                        Thread.sleep(3000);
                                    }

                                    break;
                                case 3:
                                    System.out.println("Escolha a disciplina que deseja deletar:\n");
                                    List<Disciplina> disciplinasD = new DisciplinaDAO().getDisciplinas();
                                    
                                    if(disciplinasD.size() != 0){
                                        for (Disciplina disciplina : disciplinasD) {
                                            System.out.printf("[%d] %s", disciplina.getId(), disciplina.getNome());
                                            System.out.println();
                                        }
                                        int disciplinaSelected = scanner.nextInt();
                                        scanner.nextLine();
                                        Disciplina disciplinaDeletar = new DisciplinaDAO().getDisciplinaProcurada(disciplinaSelected);

                                        if (disciplinaDeletar != null) {
                                            boolean disciplinaDeletada = new DisciplinaDAO().deletarDisciplina(disciplinaDeletar);
                                            
                                            if (disciplinaDeletada) {
                                                System.out.println("Disciplina deletada com sucesso!");
                                            } else {
                                                System.out.println("Erro ao deletar disciplina");
                                            }
                                        } else {
                                            System.out.println("Opção inválida");
                                        }

                                    }else{
                                        System.out.println("Nenhuma disciplina cadastrada");
                                    }

                                    break;
                                case 4:
                                    System.out.println("Escolha a disciplina que deseja editar:");
                                    List<Disciplina> disciplinasU = new DisciplinaDAO().getDisciplinas();
                                    
                                    if (disciplinasU.size() != 0) {
                                        for (Disciplina disciplina : disciplinasU) {
                                            System.out.printf("[%d] %s", disciplina.getId(), disciplina.getNome());
                                            System.out.println();
                                        }
                                        int disciplinaSelected = scanner.nextInt();
                                        scanner.nextLine();
                                        Disciplina disciplinaEditar = new DisciplinaDAO().getDisciplinaProcurada(disciplinaSelected);

                                        if (disciplinaEditar != null) {
                                            System.out.println("Informe o novo nome da disciplina:");
                                            String novoNome = scanner.nextLine();
                                            
                                            boolean editado = new DisciplinaDAO().updateDisciplina(disciplinaEditar.getId(), novoNome);
                                            if (editado) {
                                                System.out.println("Disciplina editada com sucesso!");
                                            } else {
                                                System.out.println("Ocorreu um erro ao editar disciplina");
                                            }
                                        } else {
                                            System.out.println("Opção inválida");
                                        }

                                    } else {
                                        System.out.println("Nenhuma disciplina cadastrada");
                                    }
                                    break;
                                
                                case 5:
                                    submenu3 = false;
                                    break;
                            
                                default:
                                    System.out.println("Opção inválida, por favor tente novamente.");
                                    break;
                            }

                        }

                    break;
                
                case 4:
                    boolean submenu4 = true;
                    while (submenu4) {
                        System.out.println("\n##### Gerenciar Notas #######\n");
                        System.out.println("[1]Lançar nota de aluno \n[2]Visualizar notas do aluno \n[3]Voltar");
                        int opcaoSub4 = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoSub4) {
                            case 1:
                                System.out.println("Digite a matricula do aluno para lançar a nota:");
                                String matricula = scanner.nextLine();
            
                                try {
                                    Aluno aluno = new AlunoDAO().getAluno(matricula);
                                    if(aluno == null){
                                        System.out.println("Aluno não encontrado!");
                                    }else{
                                        System.out.println();
                                        System.out.printf("Materias do aluno: %s / Matricula: %s \n",aluno.getNome(), aluno.getMatricula());
                                    }
                                    try {
                                        // listando disciplinas do aluno
                                        List<Disciplina> disciplinas = new DisciplinaDAO().getDisciplinasPorAluno(aluno.getId());
                                        if(disciplinas.size() !=0){
                                            for(Disciplina disciplina: disciplinas){
                                                System.out.printf("Cod Disciplina: [%d] / Nome: %s \n", disciplina.getId(), disciplina.getNome());
                                            }
                                            System.out.println();
                                            System.out.println("Digite o cod da disciplina para lançar a nota:");
            
                                        }else{
                                            System.out.println("Não foi encontrada nenhuma disciplina!");
                                        }
            
                                        int disciplinaInput = scanner.nextInt();
                                        scanner.nextLine();
                                        
                                        // selecionando disciplina que o aluno receberá a nota
                                        Disciplina disciplinaSelecionada = null;
                                        for (Disciplina disciplina : disciplinas) {
                                            if (disciplina.getId() == disciplinaInput) {
                                                disciplinaSelecionada = disciplina;
                                            }
                                        }
                                        
                                        if(disciplinaSelecionada != null){
                                            // atribuindo a nota a disciplina
                                            System.out.println("Digite a nota: EX(7,5 ou 7,50)");
                                            double valorNota = scanner.nextDouble();
            
                                            try {
                                                int turmaDisciplinaId = new TurmaDisciplinaDAO().getTurmaDisciplinaId(aluno.getTurma_id(), disciplinaSelecionada.getId());
                                                Nota nota = new Nota(aluno.getId(), turmaDisciplinaId, valorNota);
                                                new NotaDAO().casdastrarNota(nota);
            
                                                System.out.println("Nota atribuida com sucesso!");
                                                Thread.sleep(3000);
            
                                            } catch (Exception e) {
                                                System.out.println("Ocorreu um erro ao atribuir nota!");
                                            }
            
                                        }else{
                                            System.out.println("Não foi encontrada essa materia!");
                                        }
            
            
                                    } catch (Exception e) {
                                        System.out.println("Ocorreu um erro ao procurar as materias!");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Ocorreu um erro ao procurar aluno!");
                                }
                                
                                break;

                            case 2:
                                try {
                                    System.out.println("Digite a matricula do aluno:");
                                    String matriculaNota = scanner.nextLine();
                                    Aluno aluno = new AlunoDAO().getAluno(matriculaNota);
            
                                    if(aluno == null){
                                        System.out.println("Aluno não encontrado!");
                                    }else{
                                        System.out.println();
                                        System.out.printf("Materias do aluno: %s / Matricula: %s \n",aluno.getNome(), aluno.getMatricula());
                                    }
            
                                    try {
                                        // listando disciplinas do aluno
                                        List<Disciplina> disciplinas = new DisciplinaDAO().getDisciplinasPorAluno(aluno.getId());
                                        if(disciplinas.size() !=0){
                                            for(Disciplina disciplina: disciplinas){
                                                System.out.printf("Cod Materia: [%d] / Nome: %s \n", disciplina.getId(), disciplina.getNome());
                                            }
                                            System.out.println();
                                            System.out.println("Digite o cod da materia para visualizar a nota:");
            
                                        }else{
                                            System.out.println("Não foi encontrada nenhuma materia!");
                                        }
            
                                        int disciplinaInput = scanner.nextInt();
                                        scanner.nextLine();
                
                                        // selecionando disciplina a exibir nota
                                        Disciplina disciplinaSelecionada = null;
                                        for (Disciplina disciplina : disciplinas) {
                                            if (disciplina.getId() == disciplinaInput) {
                                                disciplinaSelecionada = disciplina;
                                            }
                                        }
            
                                        if(disciplinaSelecionada != null){
            
                                            try {
                                                // exibindo notas do aluno naquela disciplina
                                                List<Nota> notas = new NotaDAO().getNotasByDisciplinaId(disciplinaSelecionada.getId());
                                                if(!notas.isEmpty()){
                                                    for (Nota nota : notas) {
                                                        System.out.printf("Materia: [%d] %s -- Nota: %.2f\n", disciplinaSelecionada.getId(), disciplinaSelecionada.getNome(), nota.getNota());
                                                    }
                                                } else {
                                                    System.out.println("Nenhuma nota encontrada para a disciplina com ID fornecido.");
                                                }
                                                Thread.sleep(3000);
            
                                            } catch (Exception e) {
                                                System.out.println("Ocorreu um erro ao lista nota!");
                                            }
            
                                        }else{
                                            System.out.println("Não foi encontrada essa materia!");
                                        }
                                        
            
            
            
                                    }catch (Exception e) {
                                        System.out.println("Ocorreu ao procurar as disciplinas!");
                                    }
            
                                }catch (Exception e) {
                                    System.out.println("Ocorreu ao procurar aluno!");
                                    System.out.println(e);
                                }

                                break;
                            case 3:
                                submenu4 = false;
                                break;
                        
                            default:
                                System.out.println("Opção inválida, por favor tente novamente.");
                                break;
                        }

                    }
                    break;

                case 5:
                    continuar = false;
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida, por favor tente novamente.");
                    break;
            }
        }
        scanner.close();
    }
}
