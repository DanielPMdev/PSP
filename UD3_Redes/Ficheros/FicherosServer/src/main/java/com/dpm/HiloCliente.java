package com.dpm;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class HiloCliente extends Thread {
    private Socket socket;
    private VentanaServidor ventana;
    // Crear una lista para almacenar los ficheros
    ArrayList<Fichero> listaFicheros;

    public HiloCliente(Socket socket, VentanaServidor ventana) {
        this.socket = socket;
        this.listaFicheros = new ArrayList<>();
        this.ventana = ventana;
    }

    @Override
    public void run() {
        try (ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
             BufferedOutputStream bOut = new BufferedOutputStream(socket.getOutputStream())) {

            while (true) {
                try {
                    // Recibir el comando procedente del cliente
                    String comando = oIn.readUTF().toUpperCase().trim();
                    System.out.println(comando);

                    if (comando.isEmpty()) {
                        oOut.writeUTF("Comando vacío recibido");
                        oOut.flush();
                        continue; // Continuar esperando nuevos comandos
                    }

                    String[] partes = comando.split(" ");
                    String accion = partes[0];

                    switch (accion) {
                        case "LIST": {
                            // Obtener el modelo del JTree (puedes adaptar esto según cómo estés configurando el JTree)
                            DefaultTreeModel treeModel = (DefaultTreeModel) ventana.getTreeArchivos().getModel();
                            TreeNode root = (TreeNode) treeModel.getRoot();

                            // Crear la lista donde se almacenarán los ficheros
                            ArrayList<Fichero> listaFicheros = new ArrayList<>();

                            // Recorrer solo los hijos directos del nodo raíz
                            if (root.getChildCount() > 0) {
                                for (int i = 0; i < root.getChildCount(); i++) {
                                    TreeNode child = root.getChildAt(i);
                                    // Llamar a la función para recorrer solo los hijos directos
                                    recorrerArbol(child, listaFicheros);  // Recursión para los nodos hijos directos
                                }
                            }

                            if (!listaFicheros.isEmpty()) {
                                //this.listaFicheros.addAll(listaFicheros);
                                this.listaFicheros = listaFicheros;
                            }

                            // Enviar la lista de ficheros al cliente
                            oOut.writeObject(this.listaFicheros);
                            oOut.flush();
                            break;
                        }


                        case "GET": {
                            if (partes.length < 2) {
                                oOut.writeUTF("NO GET - Comando mal formado");
                                oOut.flush();
                                break;
                            }

                            int indice;
                            try {
                                indice = Integer.parseInt(partes[1]); // Obtener el índice del comando
                            } catch (NumberFormatException e) {
                                oOut.writeUTF("NO GET");
                                oOut.flush();
                                break;
                            }

                            // Verificar que el índice esté dentro de los límites de la lista de ficheros
                            if (indice < 0 || indice >= listaFicheros.size()) {
                                oOut.writeUTF("NO GET");
                                oOut.flush();
                                break;
                            }

                            Fichero fichero = listaFicheros.get(indice);
                            File archivo = new File(ventana.getTxfDirectorio() + File.separator + fichero.getNombre());

                            // Verificar si el archivo existe
                            if (archivo.exists() && archivo.isFile()) {
                                oOut.writeUTF("OK GET");
                                oOut.flush();

                                try (BufferedInputStream bIn = new BufferedInputStream(new FileInputStream(archivo))) {
                                    // Enviar el tamaño del archivo
                                    oOut.writeLong(archivo.length());
                                    oOut.flush();

                                    sendFile(archivo.getPath(), bOut);
                                }
                            } else {
                                oOut.writeUTF("NO GET");
                                oOut.flush();
                            }
                            break;
                        }

                        default: {
                            oOut.writeUTF("Comando " + accion + " inválido");
                            oOut.flush();
                            break;
                        }
                    }
                } catch (IOException | ClassCastException e) {
                    System.err.println("Error procesando comando del cliente : " + e.getMessage());
                    break; // Salir del bucle si ocurre un error en el cliente
                }
            }
        } catch (IOException e) {
            System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
        } finally {
            HiloServidor.finalizarCliente();
            System.out.println("Cliente finalizado");
        }
    }


    private void recorrerArbol(TreeNode node, ArrayList<Fichero> listaFicheros) {
        if (node instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode mutableNode = (DefaultMutableTreeNode) node;
            Object userObject = mutableNode.getUserObject();

            // Obtener el directorio raíz
            String path = ventana.getTxfDirectorio().trim();

            if (userObject instanceof String) {
                // Crear una ruta absoluta combinando el directorio raíz y el nombre del archivo
                File file = new File(path, (String) userObject);

                if (file.exists() && file.isFile()) {
                    String nombre = file.getName();
                    int tam = (int) (file.length() / 1024); // Tamaño en KB
                    Fichero fichero = new Fichero(nombre, tam);
                    listaFicheros.add(fichero);
                }
            }
        }
    }

    private void sendFile(String path, BufferedOutputStream bOut){
        byte[] buffer = new byte[4*1024];

        try(BufferedInputStream bIn = new BufferedInputStream(new FileInputStream(path))){

            int longitud = 0;

            //Mientras no se llegue al final del fichero
            while((longitud = bIn.read(buffer)) != -1){
                bOut.write(buffer,0,longitud);
            }

            bOut.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
