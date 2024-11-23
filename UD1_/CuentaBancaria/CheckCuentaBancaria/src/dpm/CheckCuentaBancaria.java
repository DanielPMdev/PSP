/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dpm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Esta clase es usada para válidar cuentas bancarias
 *
 * @author DanielPM.dev
 */
public class CheckCuentaBancaria {

    //4717 2542 74 6019854875 --> length 
    // 0    1   2    3              4
    
    /**
     * Este método se encarga de validar una cuenta bancaria en formato estándar, comprobando que
     * las partes de la cuenta cumplen con la estructura requerida (entidad, oficina, dígitos de control,
     * y número de cuenta) y que los dígitos de control calculados son correctos.
     *
     * @param mCBancaria La cuenta bancaria
     * @return Un String indicando si la cuenta es "VALIDA" o "NO VALIDA".
     */
    public static String validaCBancaria(String mCBancaria) {

        String CBancaria = mCBancaria.replace("-", " ");
        String[] estructura = CBancaria.split(" ");

        if (estructura.length != 4) {
            return "La cuenta " + mCBancaria + " es NO VALIDA";
        }

        String E = estructura[0];
        String O = estructura[1];
        //String D = estructura[2];
        String N = estructura[3];

        boolean esValida = true;

        String[] regexes = {
            "\\d{4}", // Entidad --> 4 Numeros
            "\\d{4}", // Oficina --> 4 Numeros
            "\\d{2}", // DigitoControl --> 2 Numeros VALIDADOS
            "\\d{10}" // NumeroC --> 10 Numeros
        };

        for (int i = 0; i < estructura.length; i++) {
            boolean resultado;

            if (i == 2) { // Ejemplo: validación personalizada para la tercera palabra
                resultado = Pattern.matches(regexes[i], estructura[i]) && validarDigitosControl(estructura[i], E, O, N);
            } else {
                resultado = Pattern.matches(regexes[i], estructura[i]);
            }

            // Si alguna validación falla, esValida se vuelve false
            if (!resultado) {
                esValida = false;
                break; // Salimos del ciclo si falla
            }
        }

            return "La cuenta " + mCBancaria + " es " + (esValida ? "VALIDA" : "NO VALIDA");
    }
    
    /**
     * Método auxiliar que valida si los dígitos de control proporcionados en una cuenta bancaria
     * son correctos. Calcula los dígitos de control basados en los factores asignados y los compara
     * con los dígitos de control ingresados por el usuario.
     *
     * @param D Los dígitos de control proporcionados (2 dígitos).
     * @param E El código de la entidad (4 dígitos).
     * @param O El código de la oficina (4 dígitos).
     * @param N El número de cuenta (10 dígitos).
     * @return true si los dígitos de control calculados coinciden con los proporcionados, false en caso contrario.
     */
    private static boolean validarDigitosControl(String D, String E, String O, String N) {
        // Factores asociados a las posiciones de los dígitos
        int[] factores = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};

        // Concatenar el código de Entidad y Oficina
        String codigoEntidadOficina = E + O;
        // Calcular el primer dígito de control (código de entidad + oficina)
        int primerDigitoControl = calcularDigitoControl("00" + codigoEntidadOficina, factores);

        // Calcular el segundo dígito de control (número de cuenta)
        int segundoDigitoControl = calcularDigitoControl(N, factores);

        // Verificar si los dígitos de control calculados coinciden con los proporcionados
        return (Character.getNumericValue(D.charAt(0)) == primerDigitoControl &&
                Character.getNumericValue(D.charAt(1)) == segundoDigitoControl);
    }
    
    /**
     * Este método calcula un dígito de control a partir de una cadena de 10 dígitos
     * multiplicando cada dígito por un factor específico y aplicando las reglas de módulo 11.
     *
     * @param codigo La cadena de 10 dígitos a partir de la cual se calculará el dígito de control.
     * @param factores Array de factores asociados a cada posición del dígito.
     * @return El dígito de control calculado, ajustado según las reglas (0-9).
     */
    private static int calcularDigitoControl(String codigo, int[] factores) {
        int suma = 0;
        for (int i = 0; i < 10; i++) {
            suma += Character.getNumericValue(codigo.charAt(i)) * factores[i];
        }

        // Obtener el dígito de control
        int resto = suma % 11;
        int digitoControl = 11 - resto;

        // Ajustar según las reglas
        if (digitoControl == 10) {
            return 1;
        } else if (digitoControl == 11) {
            return 0;
        }
        return digitoControl;
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
                System.out.println(validaCBancaria(linea));
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(CheckCuentaBancaria.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(CheckCuentaBancaria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
