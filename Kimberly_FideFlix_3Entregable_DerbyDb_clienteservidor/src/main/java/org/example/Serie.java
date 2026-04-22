package org.example;

public class Serie extends AudioVisual implements Comparable<Serie> {

    private int temporadas;

    public Serie(String titulo, int duracion, String genero, int anio, int temporadas) {
        super(titulo, duracion, genero, anio);
        this.temporadas = temporadas;
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    @Override
    public int compareTo(Serie otraSerie) {
        // Comparar por título alfabéticamente
        return this.getTitulo().compareToIgnoreCase(otraSerie.getTitulo());
    }
}

