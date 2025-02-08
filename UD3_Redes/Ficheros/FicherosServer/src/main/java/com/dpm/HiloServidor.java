package com.dpm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class HiloServidor extends Thread {
    private ServerSocket serverSocket;
    private static int MAX_CLIENTS = 0;
    private static Semaphore semaforoLimite;
    private VentanaServidor ventanaServidor;

    public HiloServidor(ServerSocket serverSocket, int MAX_CLIENTS, VentanaServidor ventanaServidor) {
        this.serverSocket = serverSocket;
        HiloServidor.MAX_CLIENTS = MAX_CLIENTS;
        semaforoLimite = new Semaphore(MAX_CLIENTS);
        this.ventanaServidor =  ventanaServidor;
    }

    public static void finalizarCliente(){
        semaforoLimite.release();
    }

    @Override
    public void run() {
        ArrayList<Socket> clientes = new ArrayList<>();

        try {
            while (true) {
                //Verificamos si no hemos llegado a las máximas conexiones permitidas
                semaforoLimite.acquire();

                //Aceptamos conexiones con los clientes
                Socket socket = serverSocket.accept();

                //Atendemos al cliente de manera individualizada a través de un hilo
                new HiloCliente(socket, ventanaServidor).start();
                clientes.add(socket);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Se ha cerrado el puerto");
        } finally {
            // Asegúrate de cerrar el ServerSocket cuando el hilo se detenga
            try {
                clientes .forEach(socket -> {
                    if (socket.isConnected()){
                        try {
                            socket.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el ServerSocket: " + e.getMessage());
            }
        }
    }
}
