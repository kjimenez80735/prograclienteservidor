package org.example;

public class Documental extends AudioVisual implements Comparable<Documental> {

    private String tema;

    public Documental(String titulo, int duracion, String genero, int anio, String tema) {
        super(titulo, duracion, genero, anio);
        this.tema = tema;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    @Override
    public int compareTo(Documental otro) {
        return this.getTitulo().compareToIgnoreCase(otro.getTitulo());
    }
}
