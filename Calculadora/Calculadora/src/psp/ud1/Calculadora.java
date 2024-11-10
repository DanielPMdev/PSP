/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package psp.ud1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usumaniana
 */
public class Calculadora {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
            BufferedReader bf = null;
            
            bf = new BufferedReader(new InputStreamReader(System.in));
            
            
        try {
            //System.out.println("Introduce un número:");
            int num1 = Integer.parseInt(bf.readLine());
            
            //System.out.println("Introduce un número:");
            int num2 = Integer.parseInt(bf.readLine());
            
            System.out.println(num1+ " + " +num2 + " = " +(num1+num2));
            
        } catch (IOException ex) {
            Logger.getLogger(Calculadora.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex){
            System.err.println("ERROR: Los datos deben ser númericos");
            System.exit(1);
        }
        
    }

}
