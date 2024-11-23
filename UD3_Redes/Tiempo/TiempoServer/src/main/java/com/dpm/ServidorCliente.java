package com.dpm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author danielpm.dev
 */
public class ServidorCliente extends Thread {

    private Socket socket;

    public ServidorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //Clases que permiten la transmisión de datos primitivos (int, float, double, String)
        DataInputStream dIn = null;
        DataOutputStream dOut = null;

        String nombreCliente = null;

        try {
            dIn = new DataInputStream(socket.getInputStream());
            dOut = new DataOutputStream(socket.getOutputStream());

            nombreCliente = dIn.readUTF();
            System.out.println("Bienvenido cliente: " + nombreCliente);

            String operacion;

            while (true) {
                //Recibimos el comando que quiere ejecutar el cliente
                operacion = dIn.readUTF().toLowerCase();

                if (operacion.equals("end")) break;

                String salida;
                Date date = new Date();
                DateFormat dateFormat = null;

                switch (operacion) {
                    case "day":
                        dateFormat = new SimpleDateFormat("dd");
                        break;
                    case "month":
                        dateFormat = new SimpleDateFormat("MM");
                        break;
                    case "year":
                        dateFormat = new SimpleDateFormat("yyyy");
                        break;
                    case "all":
                        dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                        break;
                    default:
                        salida = "No se reconoce el comando solicitado";
                        dOut.writeUTF(salida);
                        dOut.flush();
                        break;

                }
                if (dateFormat != null) {
                    dOut.writeUTF(dateFormat.format(date));
                    dOut.flush();
                }
            }

        } catch (IOException e) {
            System.err.println("El cliente  " + nombreCliente + " ha cerrado la conexion");
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    //throw new RuntimeException(e);
                }
            }
        }
    }
}
