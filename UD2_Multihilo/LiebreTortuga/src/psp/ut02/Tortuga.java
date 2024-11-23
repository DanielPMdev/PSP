/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psp.ut02;

import java.util.Random;
import javax.swing.JProgressBar;

/**
 *
 * @author DanielPM.dev
 */
public class Tortuga extends Thread{   
    private JProgressBar tortuga;
    private boolean finalizadoTortuga = false;
    
    //private VentanaPrincipal ventana;

    public Tortuga(JProgressBar tortuga) {
        this.tortuga = tortuga;
    }

    public boolean isFinalizadoTortuga() {
        return finalizadoTortuga;
    }

    public void setFinalizadoTortuga(boolean finalizadoTortuga) {
        this.finalizadoTortuga = finalizadoTortuga;
    }
        

    @Override
public void run() {                
    while (!finalizadoTortuga) {
        int random = generarNumeroAleatorio();

        if (random <= 50) {
            // Evita que el valor exceda el máximo
            tortuga.setValue(Math.min(tortuga.getValue() + 3, tortuga.getMaximum()));
        } else if (random >= 51 && random <= 70) {
            // Evita que el valor sea menor que 0
            tortuga.setValue(Math.max(tortuga.getValue() - 6, 0));
        } else {
            // Incremento menor controlando que no exceda el máximo
            tortuga.setValue(Math.min(tortuga.getValue() + 1, tortuga.getMaximum()));
        }

        tortuga.repaint();

        // Verifica si la barra está llena y finaliza el bucle si es necesario
        if (tortuga.getValue() >= tortuga.getMaximum()) {
            System.out.println("¡La tortuga ha llegado a la meta!");
            finalizadoTortuga = true;
        }

        try {
            // Pausa de 0.2 segundos (200 milisegundos)
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

        
    public static int generarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(100) + 1; // Genera un número entre 1 y 100
    }
}
