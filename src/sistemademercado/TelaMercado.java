/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemademercado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TelaMercado extends JFrame {

    private final JTextField txtNome;
    private final JTextField txtPreco;
    private final JTextField txtQuantidade;
    private final JTable tabela;
    private final DefaultTableModel modelo;
    private final JLabel lblTotal;

    private final ArrayList<Produto> lista = new ArrayList<>();

    public TelaMercado() {

        setTitle("Sistema de Mercado");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== PAINEL SUPERIOR =====
        JPanel painelTopo = new JPanel(new GridLayout(2, 4, 10, 10));
        painelTopo.setBorder(BorderFactory.createTitledBorder("Cadastro de Produto"));

        txtNome = new JTextField();
        txtPreco = new JTextField();
        txtQuantidade = new JTextField();

        painelTopo.add(new JLabel("Nome:"));
        painelTopo.add(txtNome);
        painelTopo.add(new JLabel("Preço:"));
        painelTopo.add(txtPreco);
        painelTopo.add(new JLabel("Quantidade:"));
        painelTopo.add(txtQuantidade);

        JButton btnAdicionar = new JButton("Adicionar");
        painelTopo.add(btnAdicionar);

        add(painelTopo, BorderLayout.NORTH);

        // ===== TABELA =====
        modelo = new DefaultTableModel();
        modelo.addColumn("Nome");
        modelo.addColumn("Preço");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Total");

        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // ===== PAINEL INFERIOR =====
        JPanel painelBaixo = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnRemover = new JButton("Remover");
        JButton btnFinalizar = new JButton("Finalizar Compra");

        lblTotal = new JLabel("Total: R$ 0.00");

        painelBaixo.add(lblTotal);
        painelBaixo.add(btnRemover);
        painelBaixo.add(btnFinalizar);

        add(painelBaixo, BorderLayout.SOUTH);

        // ===== AÇÕES =====

        btnAdicionar.addActionListener((ActionEvent e) -> adicionarProduto());

        btnRemover.addActionListener((ActionEvent e) -> removerProduto());

        btnFinalizar.addActionListener((ActionEvent e) -> finalizarCompra());
    }

    private void adicionarProduto() {
        try {
            String nome = txtNome.getText();
            double preco = Double.parseDouble(txtPreco.getText());
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            Produto p = new Produto(nome, preco, quantidade);
            lista.add(p);

            modelo.addRow(new Object[]{
                    p.getNome(),
                    p.getPreco(),
                    p.getQuantidade(),
                    p.calcularTotal()
            });

            atualizarTotal();

            txtNome.setText("");
            txtPreco.setText("");
            txtQuantidade.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preencha os campos corretamente!");
        }
    }

    private void removerProduto() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            lista.remove(linha);
            modelo.removeRow(linha);
            atualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.");
        }
    }

    private void finalizarCompra() {
        JOptionPane.showMessageDialog(this,
                "Compra finalizada!\n" + lblTotal.getText());
    }

    private void atualizarTotal() {
        double total = 0;
        for (Produto p : lista) {
            total += p.calcularTotal();
        }
        lblTotal.setText("Total: R$ " + String.format("%.2f", total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaMercado().setVisible(true));
    }
}