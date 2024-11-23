/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dpm;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Esta clase es utilizada para recibir un programa
 * que valida cuentas bancaraias, este lee esas cuentas
 * mediante un fichero y expulsa otro fichero con el resultado
 * @author DanielPM.dev
 */
public class TestCuentaBancaria {

    public static final String JAR_FILE = ".." + File.separator + "CheckCuentaBancaria" + 
            File.separator + "dist" + File.separator + "CheckCuentaBancaria.jar" ;
    //java -jar "/media/usumaniana/Daniel SSD/Estudios/2_DAM/PSP/UD1 - x/CheckCuentaBancaria/dist/CheckCuentaBancaria.jar"
    
    public static final String PATH_IN_FILE = ".." + File.separator + "ficheros" + 
            File.separator + "actividad2.txt";
    
    public static final String PATH_OUT_FILE = ".." + File.separator + "ficheros" + 
            File.separator + "actividad2Validado.txt";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        File fEntrada = new File(PATH_IN_FILE);
        File fSalida = new File(PATH_OUT_FILE);
        
        ProcessBuilder pb = new ProcessBuilder();
        
        pb.command("java", "-jar", JAR_FILE);
        
        //COMPROBAR SI EL FICHERO fEntrada existe
        pb.redirectInput(fEntrada);
        pb.redirectOutput(fSalida);
        
        try{
            Process proceso = pb.start();
            
            if (proceso.waitFor() != 0) {
                System.out.println("Se ha producido un error");
            } else{
                System.out.println("Se ha validado correctamente el fichero de Cuentas Bancarias");
            }
            
        } catch(IOException | InterruptedException ex){
            java.util.logging.Logger.getLogger(TestCuentaBancaria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
