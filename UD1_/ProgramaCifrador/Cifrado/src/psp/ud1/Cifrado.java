/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package psp.ud1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.System.Logger;
import java.util.logging.Level;

/**
 *
 * @author usumaniana
 */
public class Cifrado {

    public static String cifrarM(String mensaje) {
        String R1 = "";
        String R2 = "";
        String mCifrado = "";
        int n;

        String[] palabras = mensaje.split(" ");

        for (String palabra : palabras) {
            for (n = 0; n < palabra.length(); n++) {
                char letra = palabra.charAt(n);  // Obtenemos el carácter en la posición n
                //deja de espiarme
                //0123 01 01234567
                if (n % 2 == 0) { // Posiciones pares ya que empezamos desde 0
                    R1 += letra;
                } else {
                    R2 += letra;
                }
            }
            mCifrado += R1 + R2;
            R1 = "";
            R2 = "";
            mCifrado += " ";
        }

        //System.out.println(mCifrado);
        return mCifrado;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(System.in));

            String linea;

            while ((linea = bf.readLine()) != null) {
                System.out.println(cifrarM(linea));
            }
        } catch (IOException ex){
             System.err.println(ex.getMessage());

            //java.util.logging.Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if (bf != null){
                try{
                    bf.close();
                } catch (IOException ex){
                     System.err.println(ex.getMessage());
                }
            }
        }
    }
}
