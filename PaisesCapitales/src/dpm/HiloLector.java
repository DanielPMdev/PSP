package dpm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class HiloLector extends Thread {
    private PaisesCapitales paisesCapitales;
    private String paisBuscado;
    private boolean encontrado;

    public HiloLector(PaisesCapitales paisesCapitales, String paisBuscado) {
        this.paisesCapitales = paisesCapitales;
        this.paisBuscado = paisBuscado;
        this.encontrado = false;
    }

    @Override
    public void run() {
        while (!encontrado) {
            paisesCapitales.accesoLector();

            // Acceso a la región crítica para lectores
            this.buscarPais();
            // Fin de la región crítica para lectores

            paisesCapitales.liberarLector();

            // Si no se encontró el país, el hilo podría quedarse esperando a que un escritor lo añada
            if (!encontrado) {
                System.err.println("Lectura " + Thread.currentThread().getName() + ": El pais " + paisBuscado + " no se encontro, esperando que un escritor lo aniada...");
                //Bloqueo el lector a la espera de una nueva actualización
                paisesCapitales.bloquearLector();
            }
        }
        paisesCapitales.decrementarCuenta();
    }

    private void buscarPais() {
        try (BufferedReader br = new BufferedReader(new FileReader(paisesCapitales.getFichero()))) {
            String linea;
            while (!encontrado && (linea = br.readLine()) != null) {
                String[] partes = linea.split(":"); // Separa país y capital por los dos puntos
                encontrado = (partes != null && partes.length == 2 && partes[0].equalsIgnoreCase(paisBuscado));
                if (encontrado) {
                    System.out.println("Lectura " + Thread.currentThread().getName() + ": La capital de " + partes[0] + " es " + partes[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}