// File: ClienteUI.java
package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ClienteUI extends JFrame {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5435;

    private JTextField field1, field2, field3;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton verDocumentosButton, agregarDocumentoButton, borrarDocumentoButton;

    // Radio buttons
    private JRadioButton verRadioButton, agregarRadioButton, eliminarRadioButton;
    private ButtonGroup radioGroup;

    public ClienteUI() {
        setTitle("Cliente UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // --- Radio buttons panel ---
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        verRadioButton = new JRadioButton("Ver");
        agregarRadioButton = new JRadioButton("Agregar");
        eliminarRadioButton = new JRadioButton("Eliminar");
        radioGroup = new ButtonGroup();
        radioGroup.add(verRadioButton);
        radioGroup.add(agregarRadioButton);
        radioGroup.add(eliminarRadioButton);
        radioPanel.add(verRadioButton);
        radioPanel.add(agregarRadioButton);
        radioPanel.add(eliminarRadioButton);
        verRadioButton.setSelected(true);

        // --- Input fields panel ---
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label1 = new JLabel("Label 1:");
        JLabel label2 = new JLabel("Label 2:");
        JLabel label3 = new JLabel("Label 3:");
        field1 = new JTextField(10);
        field2 = new JTextField(10);
        field3 = new JTextField(10);

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(label1, gbc);
        gbc.gridx = 1; gbc.gridy = 0; inputPanel.add(field1, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(label2, gbc);
        gbc.gridx = 1; gbc.gridy = 1; inputPanel.add(field2, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(label3, gbc);
        gbc.gridx = 1; gbc.gridy = 2; inputPanel.add(field3, gbc);

        // Table columns match DocumentalPojo fields
        String[] columns = {"id", "titulo", "duracion", "genero", "anio", "tema"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        // Buttons
        JPanel buttonPanel = new JPanel();
        verDocumentosButton = new JButton("Ver");
        agregarDocumentoButton = new JButton("Agregar");
        borrarDocumentoButton = new JButton("Borrar");
        buttonPanel.add(verDocumentosButton);
        buttonPanel.add(agregarDocumentoButton);
        buttonPanel.add(borrarDocumentoButton);

        setLayout(new BorderLayout());
        // Add radioPanel at the very top
        add(radioPanel, BorderLayout.PAGE_START);
        add(inputPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        String optionSeleccionada = "";


        // Add action listener for "Ver" button
        verDocumentosButton.addActionListener(e -> {
            new Thread(() -> {
                try (
                        Socket socket = new Socket(HOST, PORT);
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        DataInputStream dis = new DataInputStream(socket.getInputStream())
                ) {
                    dos.writeUTF(
                            getOpcionSeleccionada().equals("agregarDocumental") ? "agregar" :
                                    getOpcionSeleccionada().equals("eliminarDocumental") ? "eliminar" :
                                            "verDocumentos"
                    );
                    dos.flush();

                    String response = dis.readUTF();
                    System.out.println("📥 Server response: " + response);

                    // Parse response and update table on EDT
                    SwingUtilities.invokeLater(() -> {
                        tableModel.setRowCount(0); // Clear table

                        String[] items = response.split("DocumentalPojo\\{");
                        for (String item : items) {
                            item = item.trim();
                            if (!item.isEmpty() && !item.equals("]")) {
                                String clean = item.replaceAll("}$", "");
                                String[] fields = clean.split(", ");

                                String[] row = new String[6];
                                for (int i = 0; i < fields.length; i++) {
                                    String[] kv = fields[i].split("=", 2);
                                    if (kv.length == 2) {
                                        row[i] = kv[1].replaceAll("^'|'$", "");
                                    }
                                }
                                tableModel.addRow(row);
                            }
                        }
                    });

                } catch (IOException ex) {
                    System.out.println("❌ Client error: " + ex.getMessage());
                }
            }).start();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClienteUI().setVisible(true);
        });
    }
    // Add this method to ClienteUI.java
    public String getOpcionSeleccionada() {
        if (verRadioButton.isSelected()) {
            return "verDocumentos";
        } else if (agregarRadioButton.isSelected()) {
            return "agregar";
        } else if (eliminarRadioButton.isSelected()) {
            return "eliminar";
        }
        return "";
    }
}