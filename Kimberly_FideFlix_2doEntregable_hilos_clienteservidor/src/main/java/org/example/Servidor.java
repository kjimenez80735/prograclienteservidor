package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static final int PORT = 5435;
    private CargaDatosInicial cargaDatos;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("✅ Server started on port " + PORT);
            System.out.println("⏳ Waiting for client...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("🔌 Client connected: " + socket.getInetAddress());

                handleClient(socket);

                System.out.println("🔄 Ready for next connection...\n");
            }

        } catch (IOException e) {
            System.out.println("❌ Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) {
        try (
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream())
        ) {
            // Receive data
            String user = dis.readUTF();
            String password = dis.readUTF();
            CargaDatosInicial carga = new CargaDatosInicial();

            System.out.println("📥 Received user: " + user);
            System.out.println("📥 Received password: " + password);
            String responseText = "Hola soy el SERVER - mensaje recibido";

            // IMPORTANT: write boolean and flush
            dos.writeUTF(responseText);
            dos.flush();

        } catch (IOException e) {
            System.out.println("❌ Error handling client: " + e.getMessage());
        }
    }

    private static boolean validateUser(String user, String password) {
        // Example logic
        return "admin".equals(user) && "1234".equals(password);
    }
}