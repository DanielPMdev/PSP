package com.dpm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author danielpm.dev
 */
public class Principal {
    private static final int PUERTO = 10000;


    public static void main(String[] args) {
        Socket socket = null;
        DataInputStream dIn = null;
        DataOutputStream dOut = null;
        Scanner teclado = new Scanner(System.in);

        try {
            socket = new Socket("192.168.21.10", PUERTO);
            dOut = new DataOutputStream(socket.getOutputStream());
            dIn = new DataInputStream(socket.getInputStream());

            //1º Enviamos al servidor el nombre del cliente
            System.out.println("Introduce tu nombre: ");
            dOut.writeUTF(teclado.nextLine());
            dOut.flush();

            //2º
            String operacion = "";
            while (true) {
                System.out.println("Introduce la operacion a realizar (sumar, resta, multiplicar, dividir o fin): ");
                operacion = teclado.nextLine().toLowerCase();

                if (operacion.equals("fin")) socket.close();

                dOut.writeUTF(operacion);
                dOut.flush();

                switch (operacion) {
                    case "sumar":
                    case "resta":
                    case "multiplicar":
                    case "dividir":
                        System.out.println("Dame el primer número: ");
                        dOut.writeInt(teclado.nextInt());
                        System.out.println("Dame el segundo número: ");
                        dOut.writeInt(teclado.nextInt());
                        dOut.flush();

                        teclado.nextLine();

                }

                //Recibimos la respuesta del servidor
                System.out.println("Servidor: " + dIn.readUTF());
            }


        } catch (IOException e) {
            System.err.println("Fin de la conexión con el servidor");
        } finally {
            if (socket != null && socket.isConnected()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
