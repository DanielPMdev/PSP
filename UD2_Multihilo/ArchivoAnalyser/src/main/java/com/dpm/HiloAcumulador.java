package com.dpm;

/**
 * @author danielpm.dev
 */
public class HiloAcumulador extends Thread {

    private Control objetoControl;
    private int palabras;
    private long tamano;

    public HiloAcumulador(Control objetoControl) {
        this.objetoControl = objetoControl;
        palabras = 0;
        tamano = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < objetoControl.getNumLineas(); i++) {

            //Accede el Acumulador
            objetoControl.accesoAcumulador();

            //REALIZA SUS COSAS
            String[] palabrasSeparadas = objetoControl.getLinea().split("[\s]+");
            palabras += palabrasSeparadas.length;
            for (String palabra : palabrasSeparadas) {
                for (char caracter: palabra.toCharArray()) {
                    if (Character.isDigit(caracter)) {
                        tamano += 8;
                    } else {
                        tamano += 16;
                    }
                }
            }

            objetoControl.finAcumulador();

        }
        objetoControl.decrementarContadorHilos();
    }

    public int getPalabras() {
        return palabras;
    }

    public long getTamano() {
        return tamano;
    }

}
