/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemademercado;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class SistemaDeMercado {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Produto[] produtos = new Produto[100];
        int qtdProdutos = 0;

        // LER ARQUIVO AO INICIAR
        try {
            File arquivo = new File("carrinho.txt");
            try (Scanner leitor = new Scanner(arquivo)) {
                System.out.println("=== REGISTROS ANTERIORES ===");
                while (leitor.hasNextLine()) {
                    System.out.println(leitor.nextLine());
                }
            }
            System.out.println("============================");

        } catch (FileNotFoundException e) {
            System.out.println("Nenhum registro anterior encontrado.");
        }

        int opcao;

        while (true) {

            System.out.println("\n1 - Adicionar produto");
            System.out.println("2 - Ver carrinho");
            System.out.println("3 - Finalizar compra");
            System.out.println("4 - Remover produto");
            System.out.println("5 - Salvar em arquivo");
            System.out.println("6 - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();

            switch (opcao) {

                case 1 -> {
                    scanner.nextLine();

                    System.out.print("Nome do produto: ");
                    String nome = scanner.nextLine();

                    System.out.print("Preco do produto: ");
                    double preco = scanner.nextDouble();

                    System.out.print("Quantidade: ");
                    int quantidade = scanner.nextInt();

                    produtos[qtdProdutos] = new Produto(nome, preco, quantidade);
                    qtdProdutos++;

                    System.out.println("Produto adicionado com sucesso!");
                }

                case 2 -> {
                    System.out.println("=== CARRINHO ===");

                    if (qtdProdutos == 0) {
                        System.out.println("Carrinho vazio!");
                    } else {
                        for (int i = 0; i < qtdProdutos; i++) {
                            System.out.println(i + " - " + produtos[i]);
                        }
                    }
                }/**/
                case 3 -> {

                    if (qtdProdutos == 0) {
                        System.out.println("Carrinho vazio!");
                        break;
                    }

                    double subtotal = 0;

                    System.out.println("======= RELATÓRIO =======");

                    for (int i = 0; i < qtdProdutos; i++) {
                        if (produtos[i] != null) {
                            System.out.println(produtos[i]);
                            subtotal += produtos[i].calcularTotal();
                        }
                    }

                    System.out.println("-------------------------");
                    System.out.println("Subtotal: R$ " + subtotal);

                    scanner.nextLine();
                    System.out.print("Deseja aplicar desconto? (s/n): ");
                    String resp = scanner.nextLine();

                    double desconto = 0;

                    if (resp.equalsIgnoreCase("s")) {
                        System.out.print("Digite a porcentagem de desconto: ");
                        double porcentagem = scanner.nextDouble();
                        desconto = subtotal * (porcentagem / 100);
                    }

                    double totalFinal = subtotal - desconto;

                    System.out.println("Desconto: R$ " + desconto);
                    System.out.println("Total com desconto: R$ " + totalFinal);

                    System.out.println("\nForma de pagamento:");
                    System.out.println("1 - Dinheiro");
                    System.out.println("2 - Cartao Debito");
                    System.out.println("3 - Carteo Credito");
                    System.out.print("Escolha: ");
                    int pagamento = scanner.nextInt();

                    String forma;
                    forma = switch (pagamento) {
                        case 1 -> "Dinheiro";
                        case 2 -> "Cartao Debito";
                        case 3 -> "Cartao Credito";
                        default -> "Não informado";
                    };

                    System.out.println("\n========= NOTA FISCAL =========");
                    for (int i = 0; i < qtdProdutos; i++) {
                        System.out.println(produtos[i]);
                    }
                    System.out.println("-------------------------------");
                    System.out.println("Total Pago: R$ " + totalFinal);
                    System.out.println("Forma de Pagamento: " + forma);
                    System.out.println("Obrigado pela preferência!");
                    System.out.println("================================");
                }


                case 4 -> {
                    System.out.print("Digite o índice do produto para remover: ");
                    int indice = scanner.nextInt();

                    if (indice >= 0 && indice < qtdProdutos) {

                        for (int i = indice; i < qtdProdutos - 1; i++) {
                            produtos[i] = produtos[i + 1];
                        }

                        produtos[qtdProdutos - 1] = null; // limpa última posição
                        qtdProdutos--;

                        System.out.println("Produto removido!");
                    } else {
                        System.out.println("Índice inválido!");
                    }
                }

                case 5 -> {
                    try {
                        try (FileWriter writer = new FileWriter("carrinho.txt")) {
                            for (int i = 0; i < qtdProdutos; i++) {
                                writer.write(produtos[i].toString() + "\n");
                            }
                        }
                        System.out.println("Dados salvos em carrinho.txt");

                    } catch (IOException e) {
                        System.out.println("Erro ao salvar arquivo.");
                    }
                }

                case 6 -> {
                    System.out.println("Encerrando programa...");
                    return;
                }

                default -> System.out.println("Opção inválida!");
            }
        }
    }
}