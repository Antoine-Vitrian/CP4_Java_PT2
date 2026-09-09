package br.com.fiap.delegacia;

import br.com.fiap.delegacia.dao.DelegaciaDAO;
import br.com.fiap.delegacia.dao.PolicialDAO;
import br.com.fiap.delegacia.exception.PolicialException;
import br.com.fiap.delegacia.factory.DAOFactory;
import br.com.fiap.delegacia.model.Delegacia;
import br.com.fiap.delegacia.model.Policial;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final PolicialDAO policialDAO =
            DAOFactory.criarPolicialDAO();

    private static final DelegaciaDAO delegaciaDAO =
            DAOFactory.criarDelegaciaDAO();

    public static void main(String[] args) {

        int opcao;

        do {
            exibirMenu();

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {

                    case 1:
                        cadastrarPolicial();
                        break;

                    case 2:
                        listarPoliciais();
                        break;

                    case 3:
                        buscarPolicial();
                        break;

                    case 4:
                        alterarPolicial();
                        break;

                    case 5:
                        excluirPolicial();
                        break;

                    case 6:
                        System.out.println("\nSistema encerrado.");
                        break;

                    default:
                        System.out.println("\nOpção inválida.");
                }

            } catch (Exception e) {

                System.out.println("\nDigite apenas números.");

                scanner.nextLine();
                opcao = 0;
            }

        } while (opcao != 6);

        scanner.close();
    }

    // ==========================================
    // MENU
    // ==========================================

    private static void exibirMenu() {

        System.out.println();
        System.out.println("=================================");
        System.out.println(" SISTEMA DE GESTÃO DE DELEGACIA");
        System.out.println("=================================");
        System.out.println("1 - Cadastrar policial");
        System.out.println("2 - Listar policiais");
        System.out.println("3 - Buscar policial por matrícula");
        System.out.println("4 - Alterar policial");
        System.out.println("5 - Excluir policial");
        System.out.println("6 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    // ==========================================
    // CADASTRAR
    // ==========================================

    private static void cadastrarPolicial() {

        try {

            System.out.println();
            System.out.println("--- CADASTRO DE POLICIAL ---");

            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Matrícula: ");
            String matricula = scanner.nextLine();

            System.out.print("Cargo: ");
            String cargo = scanner.nextLine();

            System.out.println("\nDelegacias disponiveis:");
            for (Delegacia delegacia : delegaciaDAO.listar()) {
                System.out.println("  ID " + delegacia.getId() + " - " + delegacia.getNome());
            }

            System.out.print("ID da delegacia: ");
            Long delegaciaId = scanner.nextLong();
            scanner.nextLine();

            Policial policial = new Policial();

            policial.setNome(nome);
            policial.setCpf(cpf);
            policial.setMatricula(matricula);
            policial.setCargo(cargo);
            policial.setDelegaciaId(delegaciaId);

            policialDAO.cadastrar(policial);

            System.out.println("\nPolicial cadastrado com sucesso!");

        } catch (PolicialException e) {

            System.out.println("\nErro: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("\nErro ao realizar cadastro.");
            scanner.nextLine();
        }
    }

    // ==========================================
    // LISTAR
    // ==========================================

    private static void listarPoliciais() {

        try {

            System.out.println();
            System.out.println("--- LISTA DE POLICIAIS ---");

            List<Policial> policiais = policialDAO.listar();

            if (policiais.isEmpty()) {

                System.out.println("Nenhum policial cadastrado.");
                return;
            }

            for (Policial policial : policiais) {

                System.out.println(policial);
            }

        } catch (PolicialException e) {

            System.out.println("\nErro: " + e.getMessage());
        }
    }

    // ==========================================
    // BUSCAR
    // ==========================================

    private static void buscarPolicial() {

        try {

            System.out.println();
            System.out.println("--- BUSCAR POLICIAL ---");

            System.out.print("Digite a matrícula: ");
            String matricula = scanner.nextLine();

            Policial policial =
                    policialDAO.buscarPorMatricula(matricula);

            System.out.println();
            System.out.println("Policial encontrado:");
            System.out.println(policial);

        } catch (PolicialException e) {

            System.out.println("\nErro: " + e.getMessage());
        }
    }

    // ==========================================
    // ALTERAR
    // ==========================================

    private static void alterarPolicial() {

        try {

            System.out.println();
            System.out.println("--- ALTERAR POLICIAL ---");

            System.out.print("ID do policial: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Matrícula: ");
            String matricula = scanner.nextLine();

            System.out.print("Cargo: ");
            String cargo = scanner.nextLine();

            System.out.print("ID da delegacia: ");
            Long delegaciaId = scanner.nextLong();
            scanner.nextLine();

            Policial policial = new Policial();

            policial.setId(id);
            policial.setNome(nome);
            policial.setCpf(cpf);
            policial.setMatricula(matricula);
            policial.setCargo(cargo);
            policial.setDelegaciaId(delegaciaId);

            policialDAO.alterar(policial);

            System.out.println("\nPolicial alterado com sucesso!");

        } catch (PolicialException e) {

            System.out.println("\nErro: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("\nErro ao alterar policial.");
            scanner.nextLine();
        }
    }

    // ==========================================
    // EXCLUIR
    // ==========================================

    private static void excluirPolicial() {

        try {

            System.out.println();
            System.out.println("--- EXCLUIR POLICIAL ---");

            System.out.print("ID do policial: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            System.out.print(
                    "Tem certeza que deseja excluir? (S/N): "
            );

            String confirmacao = scanner.nextLine();

            if (!confirmacao.equalsIgnoreCase("S")) {

                System.out.println("Exclusão cancelada.");
                return;
            }

            policialDAO.excluir(id);

            System.out.println("\nPolicial excluído com sucesso!");

        } catch (PolicialException e) {

            System.out.println("\nErro: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("\nErro ao excluir policial.");
            scanner.nextLine();
        }
    }
}