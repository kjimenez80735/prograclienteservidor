package org.example;

import java.util.ArrayList;

public abstract class AudioVisual {

    protected String titulo;
    protected int duracion;
    protected String genero;
    protected int anio;

    // Colección para almacenar comentarios
    protected static ArrayList<String> comentarios = new ArrayList<>();

    public AudioVisual(String titulo, int duracion, String genero, int anio) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.genero = genero;
        this.anio = anio;
    }

    // Método estático para agregar comentarios
    public static void AgregarComentario(String comentario) {
        comentarios.add(comentario);
    }

    // Método opcional para ver los comentarios
    public static ArrayList<String> getComentarios() {
        return comentarios;
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

}
