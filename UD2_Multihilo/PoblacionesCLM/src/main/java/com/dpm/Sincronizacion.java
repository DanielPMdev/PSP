package com.dpm;

import javax.swing.*;
import java.util.concurrent.Semaphore;

/**
 * @author danielpm.dev
 */
public class Sincronizacion {
    private Semaphore semaforo;
    private static final int NUM_POBLACIONES = 917;

    //Recursos Comunes
    private JProgressBar barra;
    private JLabel etiqueta;

    public Sincronizacion(JProgressBar barra, JLabel etiqueta) {
        this.barra = barra;
        this.etiqueta = etiqueta;

        //Inicializamos el semanoforo
        semaforo = new Semaphore(1);
    }

    public void aumentarBarra(){
        try {
            semaforo.acquire();

            //R.C
            barra.setValue(barra.getValue()+1);
            int porcentaje = barra.getValue() * 100 / NUM_POBLACIONES;
            etiqueta.setText("Cargando: " + porcentaje + "%");
            //FIN R.C

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaforo.release();
        }
    }
}
