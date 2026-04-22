package org.example;

import java.sql .*;
import java.util.ArrayList;
import java.util.List;

public class Conexion {
    private static final String URL =
            "jdbc:mysql://localhost:3306/example?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "my-secret-pw";

    private DocumentalPojo documentalPojo;

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

    public List<DocumentalPojo> getAllDocumentales() {
        List<DocumentalPojo> lista = new ArrayList<>();
        String query = "SELECT * FROM Documental";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                DocumentalPojo doc = new DocumentalPojo();
                doc.setId(rs.getInt("id"));
                doc.setTitulo(rs.getString("titulo"));
                doc.setDuracion(rs.getInt("duracion"));
                doc.setGenero(rs.getString("genero"));
                doc.setAnio(rs.getInt("anio"));
                doc.setTema(rs.getString("tema"));
                lista.add(doc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // New method to persist a documental in the database
    public boolean addDocumental(int id, String titulo, int duracion, String genero, int anio, String tema) {
        String query = "INSERT INTO Documental (id, titulo, duracion, genero, anio, tema) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, titulo);
            pstmt.setInt(3, duracion);
            pstmt.setString(4, genero);
            pstmt.setInt(5, anio);
            pstmt.setString(6, tema);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addTermino(String palabra, String concepto) {
        String query = "INSERT INTO Terminos (palabra, concepto) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(2, palabra);
            pstmt.setString(3, concepto);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Java
    public List<Terminos> buscarTermino(String termino) {
        List<Terminos> lista = new ArrayList<>();
        String query = "SELECT * FROM Terminos WHERE palabra = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, termino);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Terminos term = new Terminos(
                            rs.getString("palabra"),
                            rs.getString("concepto")
                    );
                    lista.add(term);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }




}

