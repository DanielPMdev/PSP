package com.dpm;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * @author danielpm.dev
 */
public class Control {
    public static final int NUM_PREMIOS = 1807;
    private ArrayList<Integer> bolas;
    private ArrayList<Integer> premios;

    //Clase para sincronizar, si hay algun recurso común tambien se queda aqui
    private Semaphore semaforoNumero;
    private Semaphore semaforoPremio;

    //Asignar el NumeroPremiado
    private ArrayList<Premio> listaNumPremiados;
    private int numeroPremiado;
    private int dineroPremiado;
    private CountDownLatch contador;


    public Control() {
        bolas = new ArrayList<Integer>();
        premios = new ArrayList<Integer>();
        listaNumPremiados = new ArrayList<Premio>();
        contador =  new CountDownLatch(2);

        //QUEREMOS QUE SE EJECUTE PRIMERO EL DE NUMERO
        semaforoNumero = new Semaphore(1);
        semaforoPremio = new Semaphore(0);

        //Generamos todos los números
        for (int i = 0; i < 100000; i++) {
            bolas.add(i);
        }

        //Generamos todos los premios
        premios.add(4000000);
        premios.add(1250000);
        premios.add(500000);
        premios.add(200000);
        premios.add(200000);

        for (int i = 0; i < 8; i++) {
            premios.add(60000);
        }

        for (int i = 0; i < 1794; i++) {
            premios.add(1000);
        }
    }

    public void accesoNumero(){
        try {
            semaforoNumero.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void accesoPremio(){
        try {
            semaforoPremio.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Cuando acaba el numero, abrimos el de premio
    public void finNumero(){
        semaforoPremio.release();
    }

    //Cuando acaba el premio, abrimos el de numero
    public void finPremio(){
        semaforoNumero.release();
    }

    public void sacarNumero(){
        int posicion = (int) (Math.random() * bolas.size());
        numeroPremiado = bolas.get(posicion);
        bolas.remove(posicion);
    }

    public void sacarPremio(){
        int posicion = (int) (Math.random() * premios.size());
        dineroPremiado =  premios.get(posicion);
        premios.remove(posicion);

        System.out.println(numeroPremiado + "," + dineroPremiado);

        Premio premio = new Premio(numeroPremiado, dineroPremiado);
        listaNumPremiados.add(premio);
    }

    public void decrementarContador(){
        contador.countDown();
    }

    public void esperarCuenta(){
        try {
            //Si es ejecutado y le contador es > a 0 se bloquea y si es menor a 0 se desbloquea
            contador.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String comprobarNumero (int numeroComprobar){
        try {
            Premio premio = listaNumPremiados.get(listaNumPremiados.indexOf(new Premio(numeroComprobar, 0)));
            if (premio == null) {
                return "El número " + numeroComprobar + " no está premiado";
            }
            return "El número " + numeroComprobar + " es premiado con la cantidad de " + premio.getPremio() + "€";
        } catch (IndexOutOfBoundsException e) {
            return "El número " + numeroComprobar + " no existe o no esta premiado";
        }
    }
}
