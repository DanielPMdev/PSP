package com.dpm;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author danielpm.dev
 */
public class HiloProvincia extends Thread {
    //Ruta del Fichero de la provincia que va a a leer
    private File fichero;

    //Modelo del Jtable en el cuál va a insertar datos
    private DefaultTableModel model;

    //Objeto que sincroniza el acceso a los recursos comunes
    private Sincronizacion sincronizacion;

    private CountDownLatch cuentaAtras;

    public HiloProvincia(File fichero, DefaultTableModel model, Sincronizacion sincronizacion, CountDownLatch cuentaAtras) {
        this.fichero = fichero;
        this.model = model;
        this.sincronizacion = sincronizacion;
        this.cuentaAtras = cuentaAtras;
    }

    @Override
    public void run() {
        String linea = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(fichero);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                //Añadimos la población a la tabla
                model.addRow(new String[]{linea});
                //Aumentamos la barra de progreso y calculamos el procentaje de la etiqueta
                sincronizacion.aumentarBarra();
                sleep(100);

            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        cuentaAtras.countDown();
    }
}
