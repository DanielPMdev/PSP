/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dpm;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author DanielPM.dev
 */
public class LectorPaisesCapitales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PaisesCapitales pc = new PaisesCapitales(7);

        String[] paisesLector = {"Japon", "Alemania", "Portugal", "Canada"};
        String[][] paisesEscritor = {
                                    {"Noruega", "Oslo"},
                                    {"Argentina", "Buenos Aires"},
                                    {"Belgica", "Bruselas"}
                                    };

        for (String pais : paisesLector) {
            HiloLector hilo = new HiloLector(pc, pais);
            hilo.start();
        }

        for (String[]pais: paisesEscritor) {
            HiloEscritor hilo = new HiloEscritor(pc, pais[0], pais[1]);
            hilo.start();
        }

        pc.esperarCuenta();
    }
    
}
