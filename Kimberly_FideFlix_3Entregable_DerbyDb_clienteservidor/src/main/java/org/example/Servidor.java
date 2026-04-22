package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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

    String respuesta = null;
    private static void handleClient(Socket socket) {
        try (
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream())
        ) {
            String accion = dis.readUTF();
            System.out.println("📥 Received action: " + accion);
            Conexion conn = new Conexion();
            List<DocumentalPojo> documentales = null;

            if ("verDocumentos".equals(accion)) {
                documentales = conn.getAllDocumentales();
            }
            if ("addTermino".equals(accion)) {
                String[] parts = accion.split(",", 2);

                if (parts.length == 2) {
                    String palabra = parts[0];
                    String concepto = parts[1];
                    boolean success = conn.addTermino(palabra, concepto);
                }

                String titulo = dis.readUTF();
                String genero = dis.readUTF();

                boolean success = conn.addTermino(titulo,genero);
                dos.writeUTF(success ? "Documental added successfully" : "Failed to add documental");
                dos.flush();
                return; // Exit after handling add action
            }
            if ("buscarTermino".equals(accion)) {


                String titulo = dis.readUTF();
                String respuesta = conn.buscarTermino(titulo).toString();

                dos.writeUTF(respuesta);
                dos.flush();
                return; // Exit after handling add action
            }

            String response = (documentales != null) ? documentales.toString() : "[]";
            System.out.println("📤 Sending response: " + response);
            dos.writeUTF(response);
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