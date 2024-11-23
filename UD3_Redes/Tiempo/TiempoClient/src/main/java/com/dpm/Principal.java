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
    private static final int PUERTO = 50000;


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
            String comando = "";
            while (true) {
                System.out.println("Introduce el comando a realizar (day, month, year, all o end): ");
                comando = teclado.nextLine().toLowerCase();

                if (comando.equals("end")) socket.close();

                dOut.writeUTF(comando);
                dOut.flush();

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
