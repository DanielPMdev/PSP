package com.dpm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author danielpm.dev
 */
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Socket socket = null;
            boolean conectado = false;

            while (!conectado) {
                System.out.println("IP Servidor -->");
                String ip = scanner.nextLine();
                System.out.println("Puerto -->");
                int puerto = Integer.parseInt(scanner.nextLine());

                try {
                    socket = new Socket(ip, puerto);
                    conectado = true;

                } catch (IOException e) {
                    System.out.println("No se ha podido conectar el servidor");
                }
            }

            //TUBERIAS PARA LA COMUNICACION CON EL SERVIDOR
            try (DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                 ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
                 BufferedInputStream bIn = new BufferedInputStream(socket.getInputStream())) {

                while (true) {
                    System.out.println("Comandos --> (BUSCAR, LISTAR, PORTADA, END)");
                    String comando = scanner.nextLine().toUpperCase();

                    if (comando.equalsIgnoreCase("END")) {
                        System.out.println("Fin de la conexión");
                        socket.close();
                    }

                    dOut.writeUTF(comando);
                    //dOut.flush();

                    switch (comando) {
                        case "BUSCAR": {
                            //Pedimos al usuario el ID del videojuego
                            System.out.println("Indique el ID del videojuego -> ");
                            int id = Integer.parseInt(scanner.nextLine());

                            //Enviamos el ID
                            dOut.writeInt(id);
                            dOut.flush();

                            Videojuego videojuego = (Videojuego) oIn.readObject();

                            if (videojuego != null) {
                                System.out.println(videojuego.toString());
                            } else {
                                System.out.println("No existe el videojuego con el ID: " + id);
                            }

                            break;
                        }
                        case "LISTAR": {
                            String tipo = "";
                            while (!tipo.equalsIgnoreCase("GEN") && !tipo.equalsIgnoreCase("DES")){
                                System.out.println("¿Por qué criterio quieres listar? GEN, DES");
                                tipo = scanner.nextLine().toUpperCase();
                            }

                            //ENVIAMOS la opción a listar al servidor
                            dOut.writeUTF(tipo);
                            dOut.flush();


                            switch (tipo) {
                                case "GEN": {
                                    System.out.println("Indique el genero a buscar: ");
                                    String genero = scanner.nextLine();
                                    dOut.writeUTF(genero);
                                    dOut.flush();

                                    ArrayList<Videojuego> videojuegoImportados;

                                    videojuegoImportados = (ArrayList<Videojuego>) oIn.readObject();

                                    if (videojuegoImportados != null || !videojuegoImportados.isEmpty()) {
                                        for (Videojuego videojuego : videojuegoImportados) {
                                            System.out.println(videojuego.toString());
                                        }
                                    } else {
                                        System.out.println("No hay videojuegos con el genero " + genero);
                                    }

                                    break;
                                }
                                case "DES": {
                                    System.out.println("Indique la desarroladora a buscar: ");
                                    String desarroladora = scanner.nextLine();
                                    dOut.writeUTF(desarroladora);
                                    dOut.flush();

                                    List<Videojuego> videojuegoImportados;

                                    videojuegoImportados = (List<Videojuego>) oIn.readObject();

                                    if (videojuegoImportados != null) {
                                        for (Videojuego videojuego : videojuegoImportados) {
                                            System.out.println(videojuego.toString());
                                        }
                                    } else {
                                        System.out.println("No hay videojuegos de la desarrolladora " + desarroladora);
                                    }

                                    break;
                                }
                            }
                            break;
                        }
                        case "PORTADA": {
                            //Pedimos al usuario el ID del videojuego
                            System.out.println("Indique el ID del videojuego -> ");
                            int id = Integer.parseInt(scanner.nextLine());

                            //Enviamos el ID
                            dOut.writeInt(id);
                            dOut.flush();

                            Videojuego videojuego = (Videojuego) oIn.readObject();

                            if (videojuego != null) {
                                //ESPERAMOS LA RECEPCION DE LA LONGITUD DEL FICHERO
                                long size = oIn.readLong();

                                //ESPERAMOS LA RECEPCION DE LA PORTADA
                                byte[] bytes = new byte[8*1024];
                                try (BufferedOutputStream bOut = new BufferedOutputStream(new FileOutputStream(new File(videojuego.getRutaPortada())))) {
                                    int longitud;

                                    while ((size > 0) && (longitud = bIn.read(bytes)) != -1) {
                                        bOut.write(bytes, 0, longitud);
                                        size -= longitud;
                                    }
                                    bOut.flush();
                                    System.out.println("Fichero " + videojuego.getRutaPortada() + " recibido correctamente");
                                }

                            } else {
                                System.out.println("No existe el videojuego con el ID: " + id + " y por tanto su caratula");
                            }

                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error de comunicación con el servidor: " + e.getMessage());
            e.printStackTrace(); // Esto mostrará el stack trace para mayor detalle.
        } catch (ClassNotFoundException e) {
            System.out.println("Error al leer el objeto: " + e.getMessage());
        }


    }
}