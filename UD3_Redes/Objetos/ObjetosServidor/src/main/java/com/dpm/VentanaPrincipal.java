package com.dpm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * @author danielpm.dev
 */
public class VentanaPrincipal {
    private JPanel principal;
    private JTextField tPuerto;
    private JButton btEncender;
    private JButton btApagar;

    private ServerSocket serverSocket;

    public VentanaPrincipal() {
        btEncender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int puerto = Integer.parseInt(tPuerto.getText().trim());
                    serverSocket = new ServerSocket(puerto);

                    //Lanzamos el hilo que acepta conexiones de los clientes
                    //No lo guardamos en un objeto porque no vamos a interactuar con él
                    new HiloServidor(serverSocket).start();
                    switchBotones(false);


                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(btEncender, "Puerto no valido", "Error",
                            JOptionPane.ERROR_MESSAGE);
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
    }

    private void switchBotones(boolean activo){
        btEncender.setEnabled(activo);
        tPuerto.setEnabled(activo);

        btApagar.setEnabled(!activo);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPrincipal");
        frame.setContentPane(new VentanaPrincipal().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    public JPanel getPrincipal() {
        return principal;
    }

    public void setPrincipal(JPanel principal) {
        this.principal = principal;
    }

    public JTextField gettPuerto() {
        return tPuerto;
    }

    public void settPuerto(JTextField tPuerto) {
        this.tPuerto = tPuerto;
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
}
