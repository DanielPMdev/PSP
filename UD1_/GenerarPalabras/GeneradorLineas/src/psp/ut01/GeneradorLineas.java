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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DanielPM.dev
 */
public class GeneradorLineas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Recibir 2 parámetros
        //  --> Ruta Absoluta del fichero
        //  --> Cadenna de Caracteres
        // La aplicación escribirá la cadena de caracteres dentro del fichero
        
        
        //Chequeamos el número de parámetros
        if (args.length != 2) {
            System.err.println("Número de argumentos erroneo");
            System.exit(1);
        }
        
        File fichero = new File(args[0]);
        FileOutputStream fo = null;
        PrintWriter pw = null;
        
        //CANDADO QUE NOS PERMITE VERIFICAR SI EL FICHERO ESTA SIENDO UTILIZADO
        FileLock candado = null;
        
        try{
            fo = new FileOutputStream(fichero, true);
            pw = new PrintWriter (fo);
            
            //VERIFICAMOS SI EL CANDADO ESTA ABIERTO
            //Si esta abierto continua su ejecución, en caso contrario,
            //el proceso se bloquea hasta que el recurso se libere
            
            candado = fo.getChannel().lock();
            
            //REGION CRITICA - DOS PROCESOS NO PUEDEN COMPARTIRLO
            pw.println("Inicio: "+args[1]);
            pw.flush();
            pw.println("Fin: "+args[1]);
            pw.flush();
            
        } catch (FileNotFoundException ex){
            System.err.println("FICHERO NO ENCONTRADO");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        finally{
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
