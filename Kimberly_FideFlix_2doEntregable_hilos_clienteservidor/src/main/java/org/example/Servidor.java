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
            }else {
                String[] partes = accion.split(",");
                accion = "agregar";
                String tituloField = "";
                String duracionField = "";
                String generoField = "";
                String anioField = "";
                String temaField = "";

                if (partes.length >= 6) {
                    tituloField = partes[1];
                    duracionField = partes[2];
                    generoField = partes[3];
                    anioField = partes[4];
                    temaField = partes[5];
                }
                conn.insertDocumental(tituloField, duracionField, generoField, anioField, temaField);
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