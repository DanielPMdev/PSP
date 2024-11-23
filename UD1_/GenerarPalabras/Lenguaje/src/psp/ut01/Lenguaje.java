/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package psp.ut01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileLock;
import java.util.Random;

/**
 *
 * @author DanielPM.dev
 */
public class Lenguaje {

    public static String generarPalabraAleatoria(int longitud) {
        Random random = new Random();
        StringBuilder palabra = new StringBuilder(longitud);

        // Rango ASCII para letras minúsculas (a-z: 97-122)
        for (int i = 0; i < longitud; i++) {
            palabra.append((char) (random.nextInt(26) + 97)); // Genera una letra minúscula aleatoria 97 + 26 (-1)
        }

        return palabra.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Recibir 2 parámetros
        //  --> Número de Palabras
        //  --> Ruta Absoluta del fichero
        // La aplicación escribirá la cadena de caracteres dentro del fichero

        //Chequeamos el número de parámetros
        if (args.length != 2) {
            System.err.println("Número de argumentos erroneo");
            System.exit(1);
        }

        File fichero = new File(args[1]);
        FileOutputStream fo = null;
        PrintWriter pw = null;

        //CANDADO QUE NOS PERMITE VERIFICAR SI EL FICHERO ESTA SIENDO UTILIZADO
        FileLock candado = null;

        try {
            fo = new FileOutputStream(fichero, true);
            pw = new PrintWriter(fo);

            //VERIFICAMOS SI EL CANDADO ESTA ABIERTO
            //Si esta abierto continua su ejecución, en caso contrario,
            //el proceso se bloquea hasta que el recurso se libere
            candado = fo.getChannel().lock();

            //REGION CRITICA - DOS PROCESOS NO PUEDEN COMPARTIRLO
            for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                Random random = new Random();
                int longitud = random.nextInt(7) + 4;  //longitud 4 + 7 (-1) = 10    
                String palabraGenerada = generarPalabraAleatoria(longitud) + " Proceso: " + i;
                pw.println(palabraGenerada);
                pw.flush();
            }

        } catch (FileNotFoundException ex) {
            System.err.println("FICHERO NO ENCONTRADO");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (candado != null) {
                try {
                    //ABRIMOS EL CANDADO
                    candado.release();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }

            if (pw != null) {
                pw.close();
            }
        }
    }

}
