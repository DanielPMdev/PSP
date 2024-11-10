package dpm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HiloEscritor extends Thread {
    private PaisesCapitales paisesCapitales;
    private String pais;
    private String capital;

    public HiloEscritor(PaisesCapitales paisesCapitales, String pais, String capital) {
        this.paisesCapitales = paisesCapitales;
        this.pais = pais;
        this.capital = capital;
    }

    @Override
    public void run() {
        paisesCapitales.accesoEscritor();

        // Instrucciones de Escritura - R.C
        this.actualizarFichero();
        // Fin de la R.C. (Región Crítica)
        paisesCapitales.liberarEscritor();
        //Desbloqueamos a los lectores que estan esperando leer
        paisesCapitales.desbloquearLectores();
        paisesCapitales.decrementarCuenta();
    }

    private void actualizarFichero() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(paisesCapitales.getFichero(), true))) { // true para añadir al archivo
            bw.newLine();
            bw.write(pais + ":" + capital);
            bw.flush();
            System.out.println("Escritura  " + Thread.currentThread().getName() + ": " + pais + " - " + capital);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}