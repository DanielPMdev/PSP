package com.dpm;

import javax.swing.*;

/**
 * @author danielpm.dev
 */
public class HiloLanzador extends Thread {
    private Control objetoControl;
    private JProgressBar progressBar;
    private JLabel label;
    private JButton comenzar;
    private JButton comprobar;

    public HiloLanzador(Control objetoControl, JProgressBar progressBar, JLabel label, JButton comenzar, JButton comprobar) {
        this.objetoControl = objetoControl;
        this.progressBar = progressBar;
        this.label = label;
        this.comenzar = comenzar;
        this.comprobar = comprobar;
    }

    @Override
    public void run() {
        HiloNumero hiloNumero = new HiloNumero(objetoControl);
        HiloPremio hiloPremio = new HiloPremio(objetoControl, progressBar, label);
        hiloNumero.start();
        hiloPremio.start();
        comenzar.setEnabled(false);

        objetoControl.esperarCuenta();
        comprobar.setEnabled(true);
    }
}
