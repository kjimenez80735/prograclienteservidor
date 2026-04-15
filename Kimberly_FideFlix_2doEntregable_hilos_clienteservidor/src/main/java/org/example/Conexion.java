package org.example;

import java.sql .*;

public class Conexion {
    private static final String URL =
            "jdbc:mysql://localhost:3306/example?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "my-secret-pw";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Coneccion exitosa");
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro al conectar");
            e.printStackTrace();
            return null;
        }
    }

    public static void getAllDocumentales() {
        String query = "SELECT * FROM Documental";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("📄 List of Documentales:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                int duracion = rs.getInt("duracion");
                String genero = rs.getString("genero");
                int anio = rs.getInt("anio");
                String tema = rs.getString("tema");
                System.out.println(
                        id + " | " +
                                titulo + " | " +
                                duracion + " min | " +
                                genero + " | " +
                                anio + " | " +
                                tema
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

