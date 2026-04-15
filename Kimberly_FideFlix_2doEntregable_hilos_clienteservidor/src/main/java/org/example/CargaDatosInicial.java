package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.AudioVisual.AgregarComentario;
import static org.example.AudioVisual.getComentarios;

public class CargaDatosInicial {
    List<Usuario> usuarios;
    public CargaDatosInicial() {
        usuarios = new ArrayList<>();
        Pelicula pelicula1 = new Pelicula(
                "Inception", 148, "Ciencia Ficción", 2010);
        Pelicula pelicula2 = new Pelicula(
                "Titanic", 195, "Drama", 1997);

        Serie serie1 = new Serie(
                "Breaking Bad", 50, "Drama", 2008, 5);
        Serie serie2 = new Serie(
                "Stranger Things", 55, "Ciencia Ficción", 2016, 4);

        Documental documental1 = new Documental(
                "Planeta Tierra", 60, "Naturaleza", 2006, "Vida Salvaje");
        Documental documental2 = new Documental(
                "The Social Dilemma", 94, "Tecnología", 2020, "Redes Sociales");

        AgregarComentario("Muy buena película!");

        System.out.println("OPCIONES");
        System.out.println(pelicula1);
        System.out.println(pelicula2);
        System.out.println(serie1);
        System.out.println(serie2);
        System.out.println(documental1);
        System.out.println(documental2);

        System.out.println("=== Comentarios ===");
        System.out.println(getComentarios());

        // Colección de usuarios


        // Agregar 10 usuarios diferentes
        usuarios.add(new Usuario("hernanjimenez", "hernan@example.com", "pass123"));
        usuarios.add(new Usuario("kimberly", "kimberly@example.com", "kimberly2024"));
        usuarios.add(new Usuario("juanperez", "juanperez@example.com", "juanpass"));
        usuarios.add(new Usuario("mariagomez", "maria@example.com", "mariagomez"));
        usuarios.add(new Usuario("carloslopez", "carlos@example.com", "carloslopez"));
        usuarios.add(new Usuario("sofiarodriguez", "sofia@example.com", "sofiarod"));
        usuarios.add(new Usuario("pedroalvarez", "pedro@example.com", "pedroalv"));
        usuarios.add(new Usuario("laurafernandez", "laura@example.com", "laurafern"));
        usuarios.add(new Usuario("lucasramirez", "lucas@example.com", "lucasram"));
        usuarios.add(new Usuario("andreavargas", "andrea@example.com", "andreavarg"));
        usuarios.add(new Usuario("sa", "sa@sa", "sa"));

        System.out.println("Lista de usuarios");
        for (Usuario u : usuarios) {
            System.out.println(u.getNombreUsuario() + " - " + u.getCorreo());
        }

        String[] usuariosAEliminar = {"juanperez", "kimberlyMaria"};
        for (String usuarioAEliminar : usuariosAEliminar) {
            Usuario usuarioEncontrado = null;
            for (Usuario u : usuarios) {
                if (u.getNombreUsuario().equals(usuarioAEliminar)) {
                    usuarioEncontrado = u;
                    break;
                }
            }
            if (usuarioEncontrado != null) {
                usuarios.remove(usuarioEncontrado);
                System.out.println("Usuario eliminado: " + usuarioAEliminar);
            } else {
                System.out.println("---------------------------");
                System.out.println("ALERTA !!!!  --- Usuario no encontrado: " + usuarioAEliminar);
                System.out.println("---------------------------");
            }
        }

        System.out.println("-------------------------------------------");
        System.out.println("LISTA USUARIOS ORDENADOS ALFABETICAMENTE");
        System.out.println("-------------------------------------------");
        Collections.sort(usuarios);
        for (Usuario u : usuarios) {
            System.out.println(u.getNombreUsuario() + " - " + u.getCorreo());
        }
    }

    public boolean autenticarUsuario(String nombreUsuario, String contrasena) {
        boolean resultado = false;
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario) && u.getContrasena().equals(contrasena)) {
                resultado = true;
            }
        }
        return resultado;
    }
}
