package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;


public class ClienteUI extends JFrame {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5435;

    private JTextField field1, field2, field3;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton;

    public ClienteUI() {
        setTitle("Cliente UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Top panel for labels and text fields
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

        // Table
        String[] columns = {"Col 1", "Col 2", "Col 3"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        // Buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Layout
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClienteUI().setVisible(true);
        });

        try (
                Socket socket = new Socket(HOST, PORT);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream())
        ) {
            System.out.println("📤 Sending user and password...");
            dos.writeUTF("user");
            dos.writeUTF("password");
            dos.flush();

            String response = dis.readUTF();
            System.out.println("📥 Server response: " + response);


            /*Connection conn = Conexion.getConnection();
            if (conn != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Connection failed!");
            }
            Conexion.getAllDocumentales();*/

        } catch (IOException e) {
            System.out.println("❌ Client error: " + e.getMessage());
        }
    }

}