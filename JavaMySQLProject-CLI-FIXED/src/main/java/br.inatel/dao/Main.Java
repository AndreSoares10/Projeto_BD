package br.inatel;

import br.inatel.dao.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectionDAO connection = new ConnectionDAO();
        if (!connection.connect()) {
            System.out.println("Falha na conexão com o banco de dados.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== SISTEMA DE GERENCIAMENTO EDUCACIONAL ===");
            System.out.println("1 - Perfil");
            System.out.println("2 - Matrícula");
            System.out.println("3 - Curso");
            System.out.println("4 - Categoria");
            System.out.println("5 - Usuário");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch(opcao) {
                case 1 -> menuPerfil(sc);
                case 2 -> menuMatricula(sc);
                case 3 -> menuCurso(sc);
                case 4 -> menuCategoria(sc);
                case 5 -> menuUsuario(sc);
                case 0 -> System.out.println("Encerrando o sistema. Até mais!");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while(opcao != 0);

        sc.close();
    }

    private static void menuPerfil(Scanner sc) {
        PerfilDAO dao = new PerfilDAO();
        int op;
        do {
            System.out.println("\n=== MENU PERFIL ===");
            System.out.println("1 - Inserir novo perfil");
            System.out.println("2 - Atualizar perfil");
            System.out.println("3 - Deletar perfil");
            System.out.println("4 - Listar todos os perfis");
            System.out.println("5 - Buscar perfil por ID");
            System.out.println("6 - Listar perfis com matrícula (JOIN)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt(); sc.nextLine();

            switch(op) {
                case 1 -> {
                    System.out.print("ID do perfil: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Descrição do perfil: ");
                    String info = sc.nextLine();
                    dao.inserirPerfil(id, info);
                }
                case 2 -> {
                    System.out.print("ID do perfil a atualizar: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Nova descrição: ");
                    String info = sc.nextLine();
                    dao.atualizarPerfil(id, info);
                }
                case 3 -> {
                    System.out.print("ID do perfil a deletar: ");
                    int id = sc.nextInt(); sc.nextLine();
                    dao.deletarPerfil(id);
                }
                case 4 -> dao.listarPerfis();
                case 5 -> {
                    System.out.print("ID para buscar: ");
                    int id = sc.nextInt(); sc.nextLine();
                    dao.buscarPerfilPorId(id);
                }
                case 6 -> dao.listarPerfisComMatricula();
                case 0 -> System.out.println("Retornando ao menu principal.");
                default -> System.out.println("Opção inválida.");
            }
        } while(op != 0);
    }

    private static void menuMatricula(Scanner sc) {
        MatriculaDAO dao = new MatriculaDAO();
        ProcedureDAO proc = new ProcedureDAO();
        MatriculaCursoDAO mcDao = new MatriculaCursoDAO();
        int op;
        do {
            System.out.println("\n=== MENU MATRÍCULA ===");
            System.out.println("1 - Inserir novo aluno");
            System.out.println("2 - Atualizar aluno");
            System.out.println("3 - Deletar aluno");
            System.out.println("4 - Listar todos os alunos");
            System.out.println("5 - Listar alunos com cursos (VIEW)");
            System.out.println("6 - Vincular aluno a curso");
            System.out.println("7 - Listar cursos de um aluno");
            System.out.println("8 - Listar alunos de um curso");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt(); sc.nextLine();

            switch(op) {
                case 1 -> {
                    System.out.print("Nome do aluno: ");
                    String nome = sc.nextLine();
                    System.out.print("ID do perfil: ");
                    int p = sc.nextInt(); sc.nextLine();
                    proc.inserirAluno(nome, p);
                }
                case 2 -> {
                    System.out.print("ID matrícula a atualizar: ");
                    int m = sc.nextInt(); sc.nextLine();
                    System.out.print("Novo nome: ");
                    String nome2 = sc.nextLine();
                    System.out.print("Novo ID perfil: ");
                    int p2 = sc.nextInt(); sc.nextLine();
                    dao.atualizarAluno(m, nome2, p2);
                }
                case 3 -> {
                    System.out.print("ID matrícula a deletar: ");
                    int m = sc.nextInt(); sc.nextLine();
                    dao.deletarAluno(m);
                }
                case 4 -> dao.listarAlunos();
                case 5 -> dao.listarAlunosComCursos();
                case 6 -> {
                    System.out.print("ID matrícula: ");
                    int m = sc.nextInt();
                    System.out.print("ID curso: ");
                    int c = sc.nextInt(); sc.nextLine();
                    mcDao.vincularAlunoACurso(m, c);
                }
                case 7 -> {
                    System.out.print("ID da matrícula para listar cursos: ");
                    int m = sc.nextInt(); sc.nextLine();
                    mcDao.listarCursosDoAluno(m);
                }
                case 8 -> {
                    System.out.print("ID do curso para listar alunos: ");
                    int c = sc.nextInt(); sc.nextLine();
                    mcDao.listarAlunosDoCurso(c);
                }
                case 0 -> System.out.println("Retornando ao menu principal.");
                default -> System.out.println("Opção inválida.");
            }
        } while(op != 0);
    }

    private static void menuCurso(Scanner sc) {
        CursoDAO dao = new CursoDAO();
        int op;
        do {
            System.out.println("\n=== MENU CURSO ===");
            System.out.println("1 - Inserir curso");
            System.out.println("2 - Atualizar curso");
            System.out.println("3 - Deletar curso");
            System.out.println("4 - Listar todos os cursos");
            System.out.println("5 - Buscar curso por ID");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt(); sc.nextLine();

            switch(op) {
                case 1 -> {
                    System.out.print("ID do curso: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Nome do curso: ");
                    String nome = sc.nextLine();
                    System.out.print("Carga horária: ");
                    int carga = sc.nextInt(); sc.nextLine();
                    System.out.print("Disponível? (1=Sim,0=Não): ");
                    boolean disp = (sc.nextInt()==1);
                    sc.nextLine();
                    dao.inserirCurso(id, nome, carga, disp);
                }
                case 2 -> {
                    System.out.print("ID curso a atualizar: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Novo nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Nova carga: ");
                    int carga = sc.nextInt(); sc.nextLine();
                    System.out.print("Disponível? (1=Sim,0=Não): ");
                    boolean disp = (sc.nextInt()==1);
                    sc.nextLine();
                    dao.atualizarCurso(id, nome, carga, disp);
                }
                case 3 -> {
                    System.out.print("ID curso a deletar: ");
                    dao.deletarCurso(sc.nextInt());
                    sc.nextLine();
                }
                case 4 -> dao.listarCursos();
                case 5 -> {
                    System.out.print("ID para buscar: ");
                    dao.buscarCursoPorId(sc.nextInt());
                    sc.nextLine();
                }
                case 0 -> System.out.println("Retornando ao menu principal.");
                default -> System.out.println("Opção inválida.");
            }
        } while(op != 0);
    }

    private static void menuCategoria(Scanner sc) {
        CategoriaDAO dao = new CategoriaDAO();
        int op;
        do {
            System.out.println("\n=== MENU CATEGORIA ===");
            System.out.println("1 - Inserir categoria");
            System.out.println("2 - Atualizar categoria");
            System.out.println("3 - Deletar categoria");
            System.out.println("4 - Listar todas as categorias");
            System.out.println("5 - Buscar categoria por ID");
            System.out.println("6 - Listar categorias por curso");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt(); sc.nextLine();

            switch(op) {
                case 1 -> {
                    System.out.print("ID da categoria: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Nome da categoria: ");
                    String nome = sc.nextLine();
                    dao.inserirCategoria(id, nome);
                }
                case 2 -> {
                    System.out.print("ID categoria a atualizar: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Novo nome: ");
                    dao.atualizarCategoria(id, sc.nextLine());
                }
                case 3 -> {
                    System.out.print("ID categoria a deletar: ");
                    dao.deletarCategoria(sc.nextInt());
                    sc.nextLine();
                }
                case 4 -> dao.listarCategorias();
                case 5 -> {
                    System.out.print("ID buscar: ");
                    dao.buscarCategoriaPorId(sc.nextInt());
                    sc.nextLine();
                }
                case 6 -> {
                    System.out.print("ID do curso para listar categorias: ");
                    dao.listarCategoriasPorCurso(sc.nextInt());
                    sc.nextLine();
                }
                case 0 -> System.out.println("Retornando ao menu principal.");
                default -> System.out.println("Opção inválida.");
            }
        } while(op != 0);
    }

    private static void menuUsuario(Scanner sc) {
        UsuarioDAO dao = new UsuarioDAO();
        int op;
        do {
            System.out.println("\n=== MENU USUÁRIO ===");
            System.out.println("1 - Inserir usuário");
            System.out.println("2 - Atualizar usuário");
            System.out.println("3 - Deletar usuário");
            System.out.println("4 - Listar todos os usuários");
            System.out.println("5 - Buscar usuário por ID");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt(); sc.nextLine();

            switch(op) {
                case 1 -> {
                    System.out.print("ID do usuário: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("ID do perfil: ");
                    int p = sc.nextInt(); sc.nextLine();
                    dao.inserirUsuario(id, nome, email, p);
                }
                case 2 -> {
                    System.out.print("ID usuário a atualizar: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Novo nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Novo email: ");
                    String email = sc.nextLine();
                    System.out.print("Novo ID do perfil: ");
                    int p = sc.nextInt(); sc.nextLine();
                    dao.atualizarUsuario(id, nome, email, p);
                }
                case 3 -> {
                    System.out.print("ID usuário a deletar: ");
                    dao.deletarUsuario(sc.nextInt());
                    sc.nextLine();
                }
                case 4 -> dao.listarUsuarios();
                case 5 -> {
                    System.out.print("ID para buscar: ");
                    dao.buscarUsuarioPorId(sc.nextInt());
                    sc.nextLine();
                }
                case 0 -> System.out.println("Retornando ao menu principal.");
                default -> System.out.println("Opção inválida.");
            }
        } while(op != 0);
    }
}
