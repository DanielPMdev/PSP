package com.dpm;

import java.io.Serializable;

public class Fichero implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private int tamaño;


    public Fichero() {
    }

    public Fichero(String nombre, int tamaño) {
        this.nombre = nombre;
        this.tamaño = tamaño;
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
}
