package com.dpm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author danielpm.dev
 */
public class LoteriaForm {
    private JProgressBar progressBar1;
    private JButton comenzarButton;
    private JTextField tDecimo;
    private JButton comprobarButton;
    private JLabel lMensaje;
    private JLabel lCargando;
    private JPanel panel;
    private Control objetoControl;

    public LoteriaForm() {
        JFrame frame = new JFrame("Loteria de Navidad");
        frame.setContentPane(this.panel);  // Panel de la vista
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1020, 420));  // Ancho y alto mínimo
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        comenzarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                objetoControl = new Control();
                HiloLanzador hiloLanzador = new HiloLanzador(objetoControl, progressBar1, lCargando, comenzarButton, comprobarButton);
                hiloLanzador.start();

                comenzarButton.setEnabled(false);

            }
        });

        comprobarButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            lMensaje.setText(objetoControl.comprobarNumero(Integer.parseInt(tDecimo.getText())));
          }
        });
    }

    public JProgressBar getProgressBar1() {
        return progressBar1;
    }

    public void setProgressBar1(JProgressBar progressBar1) {
        this.progressBar1 = progressBar1;
    }

    public JButton getComenzarButton() {
        return comenzarButton;
    }

    public void setComenzarButton(JButton comenzarButton) {
        this.comenzarButton = comenzarButton;
    }

    public JTextField gettDecimo() {
        return tDecimo;
    }

    public void settDecimo(JTextField tDecimo) {
        this.tDecimo = tDecimo;
    }

    public JButton getComprobarButton() {
        return comprobarButton;
    }

    public void setComprobarButton(JButton comprobarButton) {
        this.comprobarButton = comprobarButton;
    }

    public JLabel getlMensaje() {
        return lMensaje;
    }

    public void setlMensaje(JLabel lMensaje) {
        this.lMensaje = lMensaje;
    }
}
