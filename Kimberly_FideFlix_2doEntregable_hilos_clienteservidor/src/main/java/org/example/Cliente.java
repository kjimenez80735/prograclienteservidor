package org.example;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;

public class Cliente {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5435;

    public static void main(String[] args) {

        try (
                Socket socket = new Socket(HOST, PORT);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream())
        ) {

            System.out.println("✅ Connected to server");

            String user = "sa";
            String password = "sa";

            // Send data
            System.out.println("📤 Sending user and password...");
            dos.writeUTF(user);
            dos.writeUTF(password);
            dos.flush();

            // Read response
            boolean response = dis.readBoolean();

            Connection conn = Conexion.getConnection();

            if (conn != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Connection failed!");
            }

            Conexion.getAllDocumentales();

            System.out.println("📥 Server response: " + response);

            if (response) {
                System.out.println("✅ Login SUCCESS");
            } else {
                System.out.println("❌ Login FAILED");
            }

        } catch (IOException e) {
            System.out.println("❌ Client error: " + e.getMessage());
        }
    }
}