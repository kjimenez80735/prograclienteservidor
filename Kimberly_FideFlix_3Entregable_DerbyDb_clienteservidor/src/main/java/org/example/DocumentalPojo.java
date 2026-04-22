package org.example;

public class DocumentalPojo {
    private int id;
    private String titulo;
    private int duracion;
    private String genero;
    private int anio;
    private String tema;

    public DocumentalPojo() {
    }

    public DocumentalPojo(int id, String titulo, int duracion, String genero, int anio, String tema) {
        this.id = id;
        this.titulo = titulo;
        this.duracion = duracion;
        this.genero = genero;
        this.anio = anio;
        this.tema = tema;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    @Override
    public String toString() {
        return "DocumentalPojo{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", duracion=" + duracion +
                ", genero='" + genero + '\'' +
                ", anio=" + anio +
                ", tema='" + tema + '\'' +
                '}';
    }
}