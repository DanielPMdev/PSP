package com.dpm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author danielpm.dev
 */
public class HiloContador extends Thread {
    private Control objetoControl;
    private int tipo; // 0 --> Par, 1 --> Impar

    public HiloContador(Control objetoControl, int tipo) {
        this.objetoControl = objetoControl;
        this.tipo = tipo;
    }

    @Override
    public void run() {
        for (int i = tipo; i < objetoControl.getNumLineas(); i+=2) {

            //Accede Primero un Contador
            objetoControl.accesoContador();

            // R.C
            objetoControl.setLinea(leerLinea(i));
            // Fin R.C

            objetoControl.finContador();

        }
        objetoControl.decrementarContadorHilos();
    }

    public String leerLinea(int linea) {
        try {
            return Files.readAllLines(Paths.get(objetoControl.getFichero().getAbsolutePath())).get(linea);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("La línea especificada no existe en el archivo.");
        }
        return null; // Retorna null en caso de error
    }
}
