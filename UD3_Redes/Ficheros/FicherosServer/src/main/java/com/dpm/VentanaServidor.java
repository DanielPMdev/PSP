package com.dpm;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class VentanaServidor {
    private JPanel panelConf;
    private JTextField txfIP;
    private JTextField txfPuerto;
    private JButton btEncender;
    private JButton btApagar;
    private JTextField txfClientes;
    private JButton btSeleccionar;
    private JTextField txfDirectorio;
    private JTree treeArchivos;
    private JPanel panelDirec;
    private JPanel panelTree;
    private JPanel panelPrincipal;

    private ServerSocket serverSocket;
    private DefaultTreeModel treeModel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaServidor");
        frame.setContentPane(new VentanaServidor().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 600));
        frame.pack();
        frame.setVisible(true);
    }

    public VentanaServidor() {
        //createUIComponents();

        treeModel = new DefaultTreeModel(null);
        treeArchivos.setModel(treeModel);

        btEncender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (puertoValido()) {
                        int puerto = Integer.parseInt(txfPuerto.getText().trim());

                        if (!txfDirectorio.getText().trim().isEmpty()) { //AÑADIR CONDITION DE DIRECTORIO VÁLIDO
                            //Lanzamos el hilo que acepta conexiones de los clientes
                            //No lo guardamos en un objeto porque no vamos a interactuar con él
                            if (clientesValido()) {
                                int maxClientes = Integer.parseInt(txfClientes.getText());

                                serverSocket = new ServerSocket(puerto);
                                new HiloServidor(serverSocket, maxClientes, VentanaServidor.this).start();
                                switchBotones(false);
                            }else{
                                JOptionPane.showMessageDialog(btEncender, "Los clientes máximos deben ser validos", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else{
                            JOptionPane.showMessageDialog(btEncender, "Debes seleccionar el directorio a compartir",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(btEncender, "El puerto debe ser numérico", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(btEncender, "El número del pueto ya está abiero por otra aplicación", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btApagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    serverSocket.close();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(btApagar, "Error al cerrar el puerto", "Error", JOptionPane.ERROR_MESSAGE);               }
                switchBotones(true);
            }
        });


        btSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la ruta de la carpeta "Documentos"
                File documentosFolder = new File(System.getProperty("user.home"));

                // Crear un JFileChooser para seleccionar directorios
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Solo directorios

                // Establecer el directorio inicial como "Documentos"
                fileChooser.setCurrentDirectory(documentosFolder);

                // Mostrar el cuadro de diálogo
                int result = fileChooser.showOpenDialog(null);

                // Comprobar si el usuario seleccionó un directorio
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Obtener el directorio seleccionado
                    File selectedDirectory = fileChooser.getSelectedFile();

                    // Comprobar si el directorio contiene archivos
                    File[] files = selectedDirectory.listFiles();
                    if (files != null && files.length > 0) {
                        // Actualizar el campo de texto con la ruta del directorio
                        txfDirectorio.setText(selectedDirectory.getAbsolutePath());

                        // Crear un nodo raíz para el JTree
                        DefaultMutableTreeNode root = new DefaultMutableTreeNode(selectedDirectory.getName());

                        // Filtrar solo archivos (ignorar directorios)
                        for (File file : files) {
                            if (file.isFile()) {  // Solo agregar archivos, ignorar directorios
                                DefaultMutableTreeNode node = new DefaultMutableTreeNode(file.getName());
                                root.add(node);
                            }
                        }

                        // Crear un modelo de árbol y asignarlo al JTree
                        treeModel = new DefaultTreeModel(root);
                        treeArchivos.setModel(treeModel);

                    } else {
                        JOptionPane.showMessageDialog(null, "El directorio seleccionado está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se seleccionó ningún directorio.");
                }
            }
        });

    }

    private void switchBotones(boolean activo){
        btEncender.setEnabled(activo);
        btSeleccionar.setEnabled(activo);
        txfPuerto.setEnabled(activo);
        txfClientes.setEnabled(activo);


        btApagar.setEnabled(!activo);
    }

    private boolean puertoValido() {
        try {
            int numero = Integer.parseInt(txfPuerto.getText().trim());
            return numero > 0 && numero <= 65535; // Validar que el puerto está en el rango permitido
        } catch (NumberFormatException e) {
            return false; // No es un número válido
        }
    }

    private boolean clientesValido() {
        try {

            // Obtener el valor del JSpinner y convertirlo a entero
            int numero = Integer.parseInt(txfClientes.getText().trim());

            // Validar que el número esté en el rango permitido
            return numero >= 1 && numero <= 100;
        } catch (Exception e) {
            return false; // Si ocurre algún error, devolvemos false
        }
    }

//    private void createUIComponents() {
//        txfClientes = new JSpinner(new SpinnerNumberModel(5, 1, 100, 5));
//    }

    public JPanel getPanelConf() {
        return panelConf;
    }

    public void setPanelConf(JPanel panelConf) {
        this.panelConf = panelConf;
    }

    public JTextField getTxfIP() {
        return txfIP;
    }

    public void setTxfIP(JTextField txfIP) {
        this.txfIP = txfIP;
    }

    public JTextField getTxfPuerto() {
        return txfPuerto;
    }

    public void setTxfPuerto(JTextField txfPuerto) {
        this.txfPuerto = txfPuerto;
    }

    public JButton getBtEncender() {
        return btEncender;
    }

    public void setBtEncender(JButton btEncender) {
        this.btEncender = btEncender;
    }

    public JButton getBtApagar() {
        return btApagar;
    }

    public void setBtApagar(JButton btApagar) {
        this.btApagar = btApagar;
    }

    public JTextField getsClientes() {
        return txfClientes;
    }

    public void setsClientes(JTextField txfClientes) {
        this.txfClientes = txfClientes;
    }

    public JButton getBtSeleccionar() {
        return btSeleccionar;
    }

    public void setBtSeleccionar(JButton btSeleccionar) {
        this.btSeleccionar = btSeleccionar;
    }

    public String getTxfDirectorio() {
        return txfDirectorio.getText().trim();
    }

    public void setTxfDirectorio(JTextField txfDirectorio) {
        this.txfDirectorio = txfDirectorio;
    }

    public JTree getTreeArchivos() {
        return treeArchivos;
    }

    public void setTreeArchivos(JTree treeArchivos) {
        this.treeArchivos = treeArchivos;
    }

    public JPanel getPanelDirec() {
        return panelDirec;
    }

    public void setPanelDirec(JPanel panelDirec) {
        this.panelDirec = panelDirec;
    }

    public JPanel getPanelTree() {
        return panelTree;
    }

    public void setPanelTree(JPanel panelTree) {
        this.panelTree = panelTree;
    }
}
