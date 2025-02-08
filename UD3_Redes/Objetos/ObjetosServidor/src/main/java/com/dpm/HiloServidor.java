package com.dpm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * @author danielpm.dev
 */
public class HiloServidor extends Thread {
    private ServerSocket serverSocket;
    private static final int MAX_CLIENTS = 100;
    private static Semaphore semaforoLimite = new Semaphore(MAX_CLIENTS);

    public HiloServidor(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void finalizarCliente(){
        semaforoLimite.release();
    }

    @Override
    public void run() {
        try {
            while (true) {
                //Verificamos si no hemos llegado a las máximas conexiones permitidas
                semaforoLimite.acquire();

                //Aceptamos conexiones con los clientes
                Socket socket = serverSocket.accept();

                //Atendemos al cliente de manera individualizada a través de un hilo
                new HiloCliente(socket).start();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Se ha cerrado el puerto");
        } finally {
            // Asegúrate de cerrar el ServerSocket cuando el hilo se detenga
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el ServerSocket: " + e.getMessage());
            }
        }
    }
}
