package org.example;

public class Terminos {

    private String palabra;

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    private String concepto;;

    public Terminos(String palabra, String concepto) {
        this.palabra = palabra;
        this.concepto = concepto;
    }

}
