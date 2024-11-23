package com.dpm;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * @author danielpm.dev
 */
public class Control {
    private Semaphore semaforoContador;
    private Semaphore semaforoAcumulador;
    private CountDownLatch contadorHilos;

    private int numLineas;
    private String linea;
    private File fichero;

    public Control() {
        semaforoContador = new Semaphore(1);
        semaforoAcumulador = new Semaphore(0);
        contadorHilos = new CountDownLatch(3);
        this.fichero = new File("./src/archivos/caminoroto.txt");
        cantidadLineas();
    }

    public void cantidadLineas() {
        try {
            FileReader fr = new FileReader(fichero);
            BufferedReader buffer = new BufferedReader(fr);
            numLineas = (int) buffer.lines().count();
        } catch (FileNotFoundException e) {
            numLineas = 0;
            throw new RuntimeException(e);
        }
    }

    public void accesoContador() {
        try {
            semaforoContador.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void accesoAcumulador() {
        try {
            semaforoAcumulador.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void finContador() {
        semaforoAcumulador.release();
    }

    public void finAcumulador() {
        semaforoContador.release();
    }

    public void decrementarContadorHilos() {
        contadorHilos.countDown();
    }

    public void esperarContadorHilos() {
        try {
            contadorHilos.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public int getNumLineas() {
        return numLineas;
    }

    public File getFichero() {
        return fichero;
    }

    public void setFichero(File fichero) {
        this.fichero = fichero;
    }
}
