package com.dpm;

import javax.swing.*;
import java.awt.*;

/**
 * @author danielpm.dev
 */
public class HiloPremio extends Thread {

    Control objetoControl;
    private JProgressBar progressBar;
    private JLabel label;

    public HiloPremio(Control objetoControl, JProgressBar progressBar, JLabel label) {
        this.objetoControl = objetoControl;
        this.progressBar = progressBar;
        this.label = label;
    }

    @Override
    public void run() {
        for (int i = 1; i <= Control.NUM_PREMIOS ; i++) {

            objetoControl.accesoPremio();

            objetoControl.sacarPremio();

            objetoControl.finPremio();

            //Cambiamos la progress bar y la etiqueta
            progressBar.setValue(i+1);
            label.setText("SORTEO COMPLETADO: " + (int) ((i * 100)/Control.NUM_PREMIOS) +" %");
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        objetoControl.decrementarContador();
    }
}
