package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class ClienteUI extends JFrame {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5435;

    private JTextField tituloField, duracionField, generoField, anioField, temaField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton verDocumentosButton;

    private JRadioButton verRadioButton, agregarRadioButton, eliminarRadioButton;
    private ButtonGroup radioGroup;

    public ClienteUI() {
        setTitle("Cliente UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

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

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tituloField = new JTextField(10);
        duracionField = new JTextField(10);
        generoField = new JTextField(10);
        anioField = new JTextField(10);
        temaField = new JTextField(10);

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; inputPanel.add(tituloField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Duración:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; inputPanel.add(duracionField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; inputPanel.add(generoField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(new JLabel("Año:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; inputPanel.add(anioField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; inputPanel.add(temaField, gbc);

        String[] columns = {"id", "titulo", "duracion", "genero", "anio", "tema"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        verDocumentosButton = new JButton("proceder");
        buttonPanel.add(verDocumentosButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(radioPanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        verDocumentosButton.addActionListener(e -> {
            AtomicReference<String> optionSeleccionada = new AtomicReference<>("");
            new Thread(() -> {
                try (
                        Socket socket = new Socket(HOST, PORT);
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        DataInputStream dis = new DataInputStream(socket.getInputStream())
                ) {
                    String parametros = String.join(",",
                            tituloField.getText(),
                            duracionField.getText(),
                            generoField.getText(),
                            anioField.getText(),
                            temaField.getText()
                    );
                    if (getOpcionSeleccionada().equals("verDocumentos")) {
                        optionSeleccionada.set("verDocumentos");
                    }else {
                        String fin = "agregar," + parametros;
                        optionSeleccionada.set(fin);
                    }

                    dos.writeUTF(optionSeleccionada.get());
                    dos.flush();
                    String response = dis.readUTF();
                    System.out.println("Server response: " + response);

                    SwingUtilities.invokeLater(() -> {
                        tableModel.setRowCount(0);

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
                    System.out.println("Client error: " + ex.getMessage());
                }
            }).start();
        });
    }

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

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(2000); // wait 2 seconds
        SwingUtilities.invokeLater(() -> {
            new ClienteUI().setVisible(true);
        });
    }
}

