package com.dpm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author danielpm.dev
 */
public class ServidorPrincipal {
    //Puerto de escucha de nuestro servicio
    private static final int PUERTO = 10000;



    public static void main(String[] args) {
        //Instanciamos el servicio
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO);

            //Atendemos las peticiones de conexión de los clientes
            while (true) {
                Socket socket = serverSocket.accept();
                ServidorCliente sCliente = new ServidorCliente(socket);
                sCliente.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
