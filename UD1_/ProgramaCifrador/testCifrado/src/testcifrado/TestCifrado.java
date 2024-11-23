/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package testcifrado;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author DanielPM.dev
 */
public class TestCifrado {

    public static final String JAR_FILE = ".." + File.separator + "Cifrado"
            + File.separator + "dist" + File.separator + "Cifrado.jar";
    //"/media/usumaniana/Daniel SSD/Estudios/2_DAM/PSP/UD1 - x/Cifrado/dist/Cifrado.jar"

    public static final String PATH_IN_FILE = ".." + File.separator + "ficheros"
            + File.separator + "actividad1.txt";

    public static final String PATH_OUT_FILE = ".." + File.separator + "ficheros"
            + File.separator + "actividad1Cifrado.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(JAR_FILE);
        File fEntrada = new File(PATH_IN_FILE);
        File fSalida = new File(PATH_OUT_FILE);

        ProcessBuilder pb = new ProcessBuilder();

        pb.command("java", "-jar", JAR_FILE);

        if (fEntrada.exists()) {
            pb.redirectInput(fEntrada);
        } else{
            System.out.println("ERORR: El fichero no se encontro");
        }

        if (fEntrada.exists()) {
            pb.redirectOutput(fSalida);
        } else{
            System.out.println("ERORR: Hubo un error en el fichero de salida");
        }
                
        try {
            Process proceso = pb.start();

            if (proceso.waitFor() != 0) {
                System.out.println("Se ha producido un error");
                BufferedReader bf = proceso.errorReader();
                String linea;
                while ((linea = bf.readLine())!= null)  System.out.println(linea);
                
                
            } else {
                System.out.println("Se ha cifrado correctamente el fichero");
            }

        } catch (IOException | InterruptedException ex) {
            java.util.logging.Logger.getLogger(TestCifrado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
