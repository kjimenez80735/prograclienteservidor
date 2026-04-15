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

}

