/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package psp.ud1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usumaniana
 */
public class TestCalculadora {

    private static final String JAR_FILE = "/media/usumaniana/Daniel SSD/Estudios/2º DAM/Programacion de Servicios y Procesos/UD1 - x/Calculadora/dist/Calculadora.jar";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ProcessBuilder pb = new ProcessBuilder();
                       
        pb.command("java", "-jar", JAR_FILE);
        
        BufferedReader bEntrada = null;
        BufferedWriter bSalida = null;
        
        try {
            Process proceso = pb.start();            
            bSalida = proceso.outputWriter();
            
            //Enviamos primero ya que el lee
            bSalida.write("a");          
            bSalida.newLine(); //Reemplazable añadiendo /n arriba
            bSalida.write("5");
            bSalida.newLine();
            
            //Vaciamos el buffer para asegurarnos de que se ha enviado toda la info
            bSalida.flush();
            
            if (proceso.waitFor() == 0){
                //bEntrada = new BufferedReader(new InputStreamReader(proceso.getInputStream()));            
                bEntrada = proceso.inputReader();
            } else{
                bEntrada = proceso.errorReader();
            }
            
            String linea;
            
            while((linea = bEntrada.readLine()) != null)
                System.out.println(linea);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(TestCalculadora.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if (bEntrada != null) {
                try{
                    bEntrada.close();
                } catch (IOException ex){
                     Logger.getLogger(TestCalculadora.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (bSalida != null) {
                try{
                    bSalida.close();
                } catch (IOException ex){
                     Logger.getLogger(TestCalculadora.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
