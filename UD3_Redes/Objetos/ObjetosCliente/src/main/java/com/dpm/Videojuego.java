package com.dpm;

import java.io.Serializable;

/**
 * @author danielpm.dev
 */
public class Videojuego implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private String desarrolladora;
    private String genero;
    private int edadLimite;
    private String rutaPortada;

    public Videojuego(int id, String titulo, String desarrolladora, String genero, int edadLimite, String rutaPortada) {
        this.id = id;
        this.titulo = titulo;
        this.desarrolladora = desarrolladora;
        this.genero = genero;
        this.edadLimite = edadLimite;
        this.rutaPortada = rutaPortada;
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

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdadLimite() {
        return edadLimite;
    }

    public void setEdadLimite(int edadLimite) {
        this.edadLimite = edadLimite;
    }

    public String getRutaPortada() {
        return rutaPortada;
    }

    public void setRutaPortada(String rutaPortada) {
        this.rutaPortada = rutaPortada;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", desarrolladora='" + desarrolladora + '\'' +
                ", genero='" + genero + '\'' +
                ", edadLimite=" + edadLimite +
                '}';
    }
}
