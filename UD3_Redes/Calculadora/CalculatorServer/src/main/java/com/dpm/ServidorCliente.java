package com.dpm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Clase que atiende de manera individualizada el servidor a un cliente determinado
 *
 * @author danielpm.dev
 */
public class ServidorCliente extends Thread {

    private Socket socket;

    public ServidorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //Clases que permiten la transmisión de datos primitivos (int, float, double, String)
        DataInputStream dIn = null;
        DataOutputStream dOut = null;

        String nombreCliente = null;

        try {
            dIn = new DataInputStream(socket.getInputStream());
            dOut = new DataOutputStream(socket.getOutputStream());

            nombreCliente = dIn.readUTF();
            System.out.println("Bienvenido cliente: " + nombreCliente);

            while (true) {
                //Recibimos el comando que quiere ejecutar el cliente
                String operacion = dIn.readUTF().toLowerCase() ;

                String resultado = null;

                switch (operacion) {
                    case "sumar":
                    case "multiplicar":
                    case "restar":
                    case "dividir":
                        int num1 = dIn.readInt();
                        int num2 = dIn.readInt();
                        resultado = calcular(num1, num2, operacion);
                        break;
                    default:
                        resultado = "Operacion no valida";
                        break;
                }
                dOut.writeUTF(resultado);
                dOut.flush();
            }

        } catch (IOException e) {
            System.err.println("El cliente  " + nombreCliente + " ha cerrado la conexion");
        } finally {
           if (socket != null && !socket.isClosed()) {
               try {
                   socket.close();
               } catch (IOException e) {
                   //throw new RuntimeException(e);
               }
           }
        }
    }

    private String calcular(int num1, int num2, String operacion) {
        String resultado = null;
        switch (operacion) {
            case "sumar":
                resultado = num1 + " + " + num2 + " = " + (num1 + num2);
                break;
            case "multiplicar":
                resultado = num1 + " * " + num2 + " = " + (num1 * num2);
                break;
            case "restar":
                resultado = num1 + " - " + num2 + " = " + (num1 - num2);
                break;
            case "dividir":
                resultado = num1 + " / " + num2 + " = " + (num1 / num2);
                break;
        }
        return resultado;
    }
}
