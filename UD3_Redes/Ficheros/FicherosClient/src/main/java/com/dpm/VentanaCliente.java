package com.dpm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class VentanaCliente{
    private JPanel panelConf;
    private JTextField txfIP;
    private JTextField txfPuerto;
    private JButton btEncender;
    private JButton btApagar;
    private JPanel panelFicheros;
    private JPanel panelPrincipal;
    private JTable table;
    private JButton btDescargar;

    private Socket socket;
    private ArrayList<Fichero> listaFicheros = new ArrayList();

    private BufferedInputStream bIn;
    private ObjectOutputStream oOut;
    private ObjectInputStream oIn;


    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaCliente");
        frame.setContentPane(new VentanaCliente().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public VentanaCliente() {

        btEncender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (puertoValido() && !txfIP.getText().trim().isEmpty()) {
                        int puerto = Integer.parseInt(txfPuerto.getText().trim());
                        String ip = txfIP.getText().trim();

                        if (socket != null && !socket.isClosed()) {
                            JOptionPane.showMessageDialog(btEncender, "Ya hay una conexión activa.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else{
                            socket = new Socket(ip, puerto);
                        }

                        // Tuberías para la comunicación con el servidor
                        try {
                            oOut = new ObjectOutputStream(socket.getOutputStream());
                            oIn = new ObjectInputStream(socket.getInputStream());
                            bIn = new BufferedInputStream(socket.getInputStream());

                            // Enviar comando LIST
                            oOut.writeUTF("LIST");
                            oOut.flush();

                            // Leer lista de ficheros
                            listaFicheros = (ArrayList<Fichero>) oIn.readObject();

                            // Validar y mostrar la lista en la tabla
                            if (listaFicheros != null && !listaFicheros.isEmpty()) {
                                DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Nombre", "Tamaño (KB)"}, 0);

                                for (Fichero fichero : listaFicheros) {
                                    tableModel.addRow(new Object[]{fichero.getNombre(), fichero.getTamaño()});
                                }

                                table.setModel(tableModel);
                            } else {
                                JOptionPane.showMessageDialog(btEncender, "La lista de ficheros está vacía.", "Información",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }

                            // Deshabilitar botones y campos innecesarios
                            switchBotones(false);

                        } catch (ClassNotFoundException ex) {
                            JOptionPane.showMessageDialog(btEncender, "Error al procesar los datos recibidos.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(btEncender, "Debe ingresar una IP válida y un puerto numérico.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();  // Esto mostrará detalles completos del error
                    JOptionPane.showMessageDialog(btEncender, "No se pudo conectar al servidor. Verifique la IP y el puerto.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btApagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Cerrar la Conexión
                    socket.close();

                    //Limpiar la tablaoOut = new ObjectOutputStream(socket.getOutputStream());
                    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                    tableModel.setRowCount(0);

                    //Cambiar los estados de los botones
                    switchBotones(true);
                    txfIP.setText("localhost");
                    txfPuerto.setText("");

                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(btApagar, "Error al cerrar el puerto", "Error", JOptionPane.ERROR_MESSAGE);               }
                switchBotones(true);
            }
        });


        btDescargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar si se ha seleccionado una fila en la tabla
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(btDescargar, "Debe seleccionar un fichero en la tabla para descargar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Salir si no hay fila seleccionada
                }

                // Obtener el nombre del fichero seleccionado (suponiendo que la columna 0 tiene el nombre del archivo)
                String nombreFichero = (String) table.getValueAt(selectedRow, 0);

                // Mostrar un cuadro de diálogo para seleccionar la ubicación del archivo de destino
                JFileChooser chooser = new JFileChooser();
                chooser.setSelectedFile(new File(nombreFichero));

                if (chooser.showSaveDialog(btDescargar) == JFileChooser.APPROVE_OPTION) {
                    File ficheroDestino = chooser.getSelectedFile();  // El fichero destino donde se guardará la descarga

                    // Enviar el comando GET al servidor
                    try {
                        //oOut = new ObjectOutputStream(socket.getOutputStream());

                        oOut.writeUTF("GET " + selectedRow);
                        oOut.flush();

                        // Crear un flujo para recibir la respuesta del servidor
                        //oIn = new ObjectInputStream(socket.getInputStream());

                        String respuesta = oIn.readUTF();  // Leemos la respuesta del servidor

                        if ("NO GET".equals(respuesta)) {
                            JOptionPane.showMessageDialog(btDescargar, "El servidor no dispone del fichero solicitado.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if ("OK GET".equals(respuesta)) {
                            // El servidor tiene el archivo, proceder con la descarga
                            long archivoSize = oIn.readLong();  // Leer el tamaño del archivo

                            descargarFichero(ficheroDestino, archivoSize);

                                JOptionPane.showMessageDialog(btDescargar, "La descarga ha finalizado con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);

                        } else {
                            JOptionPane.showMessageDialog(btDescargar, "Respuesta del servidor no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(btDescargar, "Error durante la descarga: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

    }
    private void switchBotones(boolean activo){
        btEncender.setEnabled(activo);
        txfPuerto.setEnabled(activo);
        txfIP.setEnabled(activo);

        btApagar.setEnabled(!activo);
        btDescargar.setEnabled(!activo);
    }

    private boolean puertoValido() {
        try {
            int numero = Integer.parseInt(txfPuerto.getText().trim());
            return numero > 0 && numero <= 65535; // Validar que el puerto está en el rango permitido
        } catch (NumberFormatException e) {
            return false; // No es un número válido
        }
    }

    private void descargarFichero(File fichero, long size){
        byte[] buffer = new byte[8 * 1024];

        try(BufferedOutputStream bOut = new BufferedOutputStream(new FileOutputStream(fichero))){

            int longitud = 0;

            while (size > 0){
                longitud = bIn.read(buffer);
                size -= longitud;

                bOut.write(buffer, 0, longitud);
            }

            bOut.flush();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(btDescargar, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
