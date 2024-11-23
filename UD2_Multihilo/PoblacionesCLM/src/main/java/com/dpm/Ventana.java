package com.dpm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * @author danielpm.dev
 */
public class Ventana {
    private JProgressBar pbPoblacion;
    private JLabel lbCargando;
    private JPanel panel;
    private JTabbedPane tabPanel;
    private JTable tbAlbacete;
    private JTable tbCiudadReal;
    private JTable tbCuenca;
    private JTable tbGuadalajara;
    private JTable tbToledo;

    public Ventana() {
        JFrame frame = new JFrame("Poblaciones de Castilla La Mancha");
        frame.setContentPane(this.getPanel());  // Panel de la vista
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setMinimumSize(new Dimension(420, 120));  // Ancho y alto mínimo
        frame.setMinimumSize(new Dimension(1020, 420));  // Ancho y alto mínimo
        frame.setLocationRelativeTo(null);
        pbPoblacion.setForeground(Color.BLUE);
        frame.pack();
        frame.setVisible(true);

        DefaultTableModel[] modelos = {
                (DefaultTableModel) tbAlbacete.getModel(),
                (DefaultTableModel) tbCiudadReal.getModel(),
                (DefaultTableModel) tbCuenca.getModel(),
                (DefaultTableModel) tbGuadalajara.getModel(),
                (DefaultTableModel) tbToledo.getModel()
        };

        //tabPanel.setVisible(false);
        Sincronizacion sincronizacion = new Sincronizacion(pbPoblacion,lbCargando);
        //Directorio
        File directorioFicheros = new File("./src/archivos/");
        //Array de Ficheros
        File[] ficheros = directorioFicheros.listFiles();
        CountDownLatch cuentaAtras = new CountDownLatch(ficheros.length);

        for (int i = 0; i < ficheros.length; i++) {
            modelos[i].addColumn("Poblacion");
            HiloProvincia hilo = new HiloProvincia(ficheros[i], modelos[i], sincronizacion, cuentaAtras);

            hilo.start();
        }

        try{
            cuentaAtras.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public JLabel getLbCargando() {
        return lbCargando;
    }

    public void setLbCargando(JLabel lbCargando) {
        this.lbCargando = lbCargando;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JProgressBar getPbPoblacion() {
        return pbPoblacion;
    }

    public void setPbPoblacion(JProgressBar pbPoblacion) {
        this.pbPoblacion = pbPoblacion;
    }
}
