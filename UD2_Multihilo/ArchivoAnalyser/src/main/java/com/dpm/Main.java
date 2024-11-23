package com.dpm;

/**
 * @author danielpm.dev
 */
public class Main {
    public static void main(String[] args) {
        Control objetoControl = new Control();

        HiloAcumulador hiloAcumulador = new HiloAcumulador(objetoControl);
        HiloContador hiloPar = new HiloContador(objetoControl, 0);
        HiloContador hiloImpar = new HiloContador(objetoControl, 1);

        hiloPar.start();
        hiloImpar.start();
        hiloAcumulador.start();

        //ESPERAMOS A QUE ACABEN LOS HILOS
        objetoControl.esperarContadorHilos();

        System.out.println("El fichero " + objetoControl.getFichero().getName() + " tiene un total de " + hiloAcumulador.getPalabras()+
                " y un tamaño total de " + hiloAcumulador.getTamano() + " bytes");
    }
}