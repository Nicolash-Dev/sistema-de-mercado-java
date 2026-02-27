/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemademercado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SistemaMercadoGUI extends JFrame {

    private JTextField campoNome;
    private JTextField campoPreco;
    private JTextField campoQuantidade;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JLabel labelTotal;

    private double totalGeral = 0;

    public SistemaMercadoGUI() {

        setTitle("Sistema de Mercado");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== PAINEL SUPERIOR =====
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(new Color(30, 144, 255));
        painelTopo.setLayout(new FlowLayout());

        JLabel titulo = new JLabel("SISTEMA DE MERCADO");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        painelTopo.add(titulo);

        add(painelTopo, BorderLayout.NORTH);

        // ===== PAINEL CENTRAL =====
        modelo = new DefaultTableModel();
        modelo.addColumn("Nome");
        modelo.addColumn("Preço");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Total");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        add(scroll, BorderLayout.CENTER);

        // ===== PAINEL INFERIOR =====
        JPanel painelBaixo = new JPanel();
        painelBaixo.setLayout(new GridLayout(3, 1));

        JPanel painelCampos = new JPanel(new FlowLayout());

        campoNome = new JTextField(10);
        campoPreco = new JTextField(5);
        campoQuantidade = new JTextField(5);

        painelCampos.add(new JLabel("Nome:"));
        painelCampos.add(campoNome);
        painelCampos.add(new JLabel("Preço:"));
        painelCampos.add(campoPreco);
        painelCampos.add(new JLabel("Qtd:"));
        painelCampos.add(campoQuantidade);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnRemover = new JButton("Remover");
        JButton btnFinalizar = new JButton("Finalizar Compra");

        painelCampos.add(btnAdicionar);
        painelCampos.add(btnRemover);
        painelCampos.add(btnFinalizar);

        painelBaixo.add(painelCampos);

        labelTotal = new JLabel("Total: R$ 0.00");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 18));
        labelTotal.setHorizontalAlignment(SwingConstants.CENTER);

        painelBaixo.add(labelTotal);

        add(painelBaixo, BorderLayout.SOUTH);

        // ===== AÇÕES DOS BOTÕES =====

        btnAdicionar.addActionListener((ActionEvent e) -> {
            try {
                String nome = campoNome.getText();
                double preco = Double.parseDouble(campoPreco.getText());
                int qtd = Integer.parseInt(campoQuantidade.getText());
                
                double totalProduto = preco * qtd;
                totalGeral += totalProduto;
                
                modelo.addRow(new Object[]{nome, preco, qtd, totalProduto});
                
                labelTotal.setText("Total: R$ " + String.format("%.2f", totalGeral));
                
                campoNome.setText("");
                campoPreco.setText("");
                campoQuantidade.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(SistemaMercadoGUI.this, "Preencha os campos corretamente!");
            }
        });

        btnRemover.addActionListener((ActionEvent e) -> {
            int linha = tabela.getSelectedRow();

            if (linha != -1) {
                double valorRemovido = (double) modelo.getValueAt(linha, 3);
                totalGeral -= valorRemovido;

                modelo.removeRow(linha);
                labelTotal.setText("Total: R$ " + String.format("%.2f", totalGeral));
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto para remover.");
            }
        });

        btnFinalizar.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this,
                    "Compra finalizada!\nTotal: R$ " +
                            String.format("%.2f", totalGeral));
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaMercadoGUI().setVisible(true);
        });
    }
}

