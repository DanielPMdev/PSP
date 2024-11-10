package com.dpm;

/**
 * @author danielpm.dev
 */
public class HiloNumero extends Thread {

    Control objetoControl;

    public HiloNumero(Control objetoControl) {
        this.objetoControl = objetoControl;
    }

    @Override
    public void run() {
        for (int i = 0; i < Control.NUM_PREMIOS ; i++) {

          objetoControl.accesoNumero();

          //R.C -- Sacar Bola de Numero
          objetoControl.sacarNumero();

          objetoControl.finNumero();
        }
        objetoControl.decrementarContador();
    }
}
