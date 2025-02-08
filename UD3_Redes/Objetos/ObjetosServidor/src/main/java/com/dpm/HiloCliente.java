package com.dpm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author danielpm.dev
 */
public class HiloCliente extends Thread {
    private Socket socket;
    private ModeloVideojuegos modeloVideojuegos;

    public HiloCliente(Socket socket) {
        this.socket = socket;
        this.modeloVideojuegos = new ModeloVideojuegos();
    }

    @Override
    public void run() {
        try (DataInputStream dIn = new DataInputStream(socket.getInputStream());
             ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
             BufferedOutputStream bOut = new BufferedOutputStream(socket.getOutputStream())) {
            while (true) {



                //Recibimos el comando procendente del cliente
                String comando = dIn.readUTF().toUpperCase();

                switch (comando) {
                    case "BUSCAR": {
                        int id = dIn.readInt();
                        oOut.writeObject(modeloVideojuegos.getById(id));
                        oOut.flush();
                        break;
                    }
                    case "LISTAR": {
                        String tipo = dIn.readUTF().toUpperCase();
                        switch (tipo) {
                            case "GEN": {
                                String genero = dIn.readUTF();
                                oOut.writeObject(modeloVideojuegos.getByGenero(genero));
                                oOut.flush();
                                break;
                            }
                            case "DES": {
                                String desarroladora = dIn.readUTF();
                                oOut.writeObject(modeloVideojuegos.getByDesarrolladora(desarroladora));
                                oOut.flush();
                                break;
                            }
                            default: {
                                oOut.writeObject("Listado no permitido");
                                oOut.flush();
                                break;
                            }
                        }
                        break;
                    }
                    case "PORTADA": {
                        int id = dIn.readInt();
                        Videojuego videojuego = (modeloVideojuegos.getById(id));

                        //Enviamos el objeto videojuego
                        oOut.writeObject(videojuego);
                        oOut.flush();

                        if (videojuego != null && videojuego.getRutaPortada() != null) {
                            File portada = new File(videojuego.getRutaPortada());

                            //Transferimos la portada del videojuego
                            try (BufferedInputStream bIn = new BufferedInputStream(new FileInputStream(portada))){
                                //ENVIAMOS EL TAMAÑO DEL FICHERO
                                oOut.writeLong(portada.length());
                                oOut.flush();

                                byte[] bytes = new byte[8*1024];
                                int longitud;

                                while ((longitud = bIn.read(bytes)) != -1) {
                                    bOut.write(bytes, 0, longitud);
                                }
                                bOut.flush();
                            }
                        }

                        break;
                    }
                    default: {
                        oOut.writeObject("Comando " + comando + " invalido");
                        oOut.flush();
                        break;
                    }
                }

            }
        } catch (IOException e) {

        }
        HiloServidor.finalizarCliente();
        System.out.println("Cliente finalizado");
    }
}
