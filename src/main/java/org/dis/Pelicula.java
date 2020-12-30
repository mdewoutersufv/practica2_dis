package org.dis;

import java.util.List;

public class Pelicula {



    private String titulo;
    private String sinopsis;
    private String genero;
    private String enlace;
    private int agno;
    private int duracion;
    private Reparto reparto;


    public Pelicula(String titulo, String sinopsis, String genero, String enlace, int agno, int duracion, Reparto reparto) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.enlace = enlace;
        this.agno = agno;
        this.duracion = duracion;
        this.reparto = reparto;
    }

    public Pelicula(String titulo, String sinopsis, String enlace, int agno, int duracion, Reparto reparto) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.genero = null;
        this.enlace = enlace;
        this.agno = agno;
        this.duracion = duracion;
        this.reparto = reparto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public Reparto getReparto() {
        return reparto;
    }

    @Override
    public String toString() {
        return "Titulo=" + titulo + ", Sinopsis=" + sinopsis + ", Genero=" + genero + ", Enlace=" + enlace +
                ", Año=" + agno + ", Duracion=" + duracion + ", reparto=" + reparto;
    }

}
