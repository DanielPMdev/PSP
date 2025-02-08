package com.dpm;

import java.io.Serializable;

public class Fichero implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private int tamaño;
    private String path;

    public Fichero() {
    }

    public Fichero(String nombre, long size) {
        this.nombre = nombre;
        this.tamaño = (int) size;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
