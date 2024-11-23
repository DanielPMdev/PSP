/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psp.ut02;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import static psp.ut02.Tortuga.generarNumeroAleatorio;

/**
 *
 * @author DanielPM.dev
 */
public class Liebre extends Thread {

    private JProgressBar liebre;
    private boolean finalizadoLiebre = false;

    //private VentanaPrincipal ventana;
    public Liebre(JProgressBar liebre) {
        this.liebre = liebre;
    }

    public boolean isFinalizadoLiebre() {
        return finalizadoLiebre;
    }

    public void setFinalizadoLiebre(boolean finalizadoLiebre) {
        this.finalizadoLiebre = finalizadoLiebre;
    }
        
    
    @Override
    public void run() {
        while (!finalizadoLiebre) {
            int random = generarNumeroAleatorio();
            if (random <= 20) {
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Liebre.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (random >= 21 && random <= 40) {
                liebre.setValue(Math.min(liebre.getValue() + 9, liebre.getMaximum()));

            } else if (random >= 41 && random <= 50) {
                liebre.setValue(Math.max(liebre.getValue() - 12, 0));

            } else if (random >= 51 && random <= 80) {
                liebre.setValue(Math.min(liebre.getValue() + 1, liebre.getMaximum()));

            } else {
                liebre.setValue(Math.max(liebre.getValue() - 2, 0));
            }

            liebre.repaint();

            // Verifica si la barra está llena y finaliza el bucle si es necesario
            if (liebre.getValue() >= liebre.getMaximum()) {
                System.out.println("¡La Liebre ha llegado a la meta!");
                finalizadoLiebre = true;
            }

            try {
                // Pausa de 0.2 segundos (200 milisegundos)
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
