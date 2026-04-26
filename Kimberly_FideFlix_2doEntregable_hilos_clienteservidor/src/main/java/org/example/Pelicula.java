package org.example;

public class Pelicula extends AudioVisual implements Comparable<Pelicula> {

    public Pelicula(String titulo, int duracion, String genero, int ano ) {
        super(titulo, duracion, genero, ano);
    }


    @Override
    public int compareTo(Pelicula o) {
        String t1 = this.getTitulo();
        String t2 = o.getTitulo();
        if (t1 == null && t2 == null) return 0;
        if (t1 == null) return -1;
        if (t2 == null) return 1;
        return t1.compareToIgnoreCase(t2);
    }
}
