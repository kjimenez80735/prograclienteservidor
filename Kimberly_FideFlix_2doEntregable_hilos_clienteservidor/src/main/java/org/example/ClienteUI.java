package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ClienteUI extends JFrame {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5435;

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton verDocumentosButton, agregarDocumentoButton, borrarDocumentoButton;


    private JTextField palabraField;
    private JTextArea conceptoArea;
    private JButton guardarButton;

    private JButton buscarButton; // New button


    public ClienteUI() {
        setTitle("Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        // Main panel (gray background)
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(220, 220, 220));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Cliente", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(title, gbc);

        // Label Palabra
        JLabel palabraLabel = new JLabel("Palabra");
        palabraLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(palabraLabel, gbc);

        // TextField Palabra
        palabraField = new JTextField(20);

        gbc.gridy = 2;
        mainPanel.add(palabraField, gbc);

        // Label Concepto
        JLabel conceptoLabel = new JLabel("Concepto");
        conceptoLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridy = 3;
        mainPanel.add(conceptoLabel, gbc);

        // TextArea Concepto
        conceptoArea = new JTextArea(4, 20);
        conceptoArea.setLineWrap(true);
        conceptoArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(conceptoArea);

        gbc.gridy = 4;
        mainPanel.add(scroll, gbc);

        // Button Guardar
        guardarButton = new JButton("Guardar");
        buscarButton = new JButton("Buscar"); // Initialize new button

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(guardarButton, gbc);

        // Add the new button below "Guardar"
        gbc.gridy = 6;
        mainPanel.add(buscarButton, gbc);

        // Table and model initialization
        //tableModel = new DefaultTableModel(new Object[]{"ID", "Título", "Duración", "Género", "Año", "Tema"}, 0);
        tableModel = new DefaultTableModel(new Object[]{ }, 0);
        table = new JTable(tableModel);
        table.setVisible(false); // Oculta las columnas inicialmente


        // Add table to UI
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(new JScrollPane(table), gbc);

        // Buttons initialization
        verDocumentosButton = new JButton("Ver Documentos");
        agregarDocumentoButton = new JButton("Agregar Documento");
        borrarDocumentoButton = new JButton("Borrar Documento");

        // Add buttons panel
        JPanel buttonsPanel = new JPanel();
        //buttonsPanel.add(verDocumentosButton);
        //buttonsPanel.add(agregarDocumentoButton);
        //buttonsPanel.add(borrarDocumentoButton);

        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel);

        // Add action listener for "Ver" button
        guardarButton.addActionListener(e -> {
            new Thread(() -> {
                try (
                        Socket socket = new Socket(HOST, PORT);
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        DataInputStream dis = new DataInputStream(socket.getInputStream())
                ) {
                    dos.writeUTF("verDocumentos");
                    dos.flush();

                    String response = dis.readUTF();
                    System.out.println("📥 Server response: " + response);

                    // Parse response and update table on EDT
                    SwingUtilities.invokeLater(() -> {
                        tableModel.setRowCount(0); // Clear table

                        // Example response: [DocumentalPojo{id=1, titulo='A', duracion=90, genero='Drama', anio=2020, tema='Nature'}, ...]
                        String[] items = response.split("DocumentalPojo\\{");
                        /*for (String item : items) {
                            item = item.trim();
                            if (!item.isEmpty() && !item.equals("]")) {
                                String clean = item.replaceAll("}$", "");
                                String[] fields = clean.split(", ");
                                String[] row = new String[6];
                                for (int i = 0; i < fields.length; i++) {
                                    String[] kv = fields[i].split("=", 2);
                                    if (kv.length == 2) {
                                        row[i] = kv[1].replaceAll("^'|'$", ""); // Remove single quotes if present
                                    }
                                }
                                tableModel.addRow(row);
                            }
                        }*/
                    });

                } catch (IOException ex) {
                    System.out.println("❌ Client error: " + ex.getMessage());
                }
            }).start();
        });



        buscarButton.addActionListener(e -> {
            new Thread(() -> {
                try (
                        Socket socket = new Socket(HOST, PORT);
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        DataInputStream dis = new DataInputStream(socket.getInputStream())
                ) {
                    dos.writeUTF(palabraField.getText());
                    dos.flush();

                    String response = dis.readUTF();
                    SwingUtilities.invokeLater(() -> {
                        conceptoArea.setText(response);
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
}