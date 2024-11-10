package dpm;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * @author DanielPM.dev
 */
public class PaisesCapitales {
    private Semaphore semaforo;  // Controla la cantidad de lectores
    private Semaphore semaforoBloqueo; // Semaforo para bloquear lectores que no han encontrado su pais

    //Numero de hilos que pueden leer en el mismo instante de tiempo sonre el fichero
    private static final int NUM_LECTORES = 4;
    private static final String FILE_PATH = "./src/archivos/PaisesCapitales.txt";
    private File fichero;

    //Cuenta Atras
    CountDownLatch cuentaAtras;

    public PaisesCapitales(int numVeces) {
        //this.semaforoEscritores = new Semaphore(1, true);  // Solo 1 escritor
        fichero = new File(FILE_PATH);
        semaforo = new Semaphore(NUM_LECTORES); // Máximo 4 lectores
        semaforoBloqueo = new Semaphore(0); //Para bloquear lo ponemos a 0, =! 0 --> Entra
        //Lo inicializamos a 0 porque queremos bloquear hilo de manera
        // intencionada, por tanto el semáforo debe estar cerrado
        cuentaAtras = new CountDownLatch(numVeces);
    }

    public File getFichero() {
        return fichero;
    }

    // Acceso al recurso común en modo lectura
    // Lectores
    public void accesoLector() {
        try {
            semaforo.acquire();  // Adquiere un permiso de lectura
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void liberarLector() {
        semaforo.release();  // Libera el permiso de lectura
    }

    // Escritores
    public void accesoEscritor() {
        try {
            semaforo.acquire(NUM_LECTORES);  // Bloquea todos los permisos de lectura
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void liberarEscritor() {
        semaforo.release(NUM_LECTORES);  // Libera los permisos de lectura
    }

    public void bloquearLector() {
        try {
            semaforoBloqueo.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void desbloquearLectores() {
        //Desbloquea a todos los hilos que se encuentran bloqueados
        semaforoBloqueo.release(semaforoBloqueo.getQueueLength());
    }

    public void decrementarCuenta(){
        cuentaAtras.countDown();
    }

    public void esperarCuenta(){
        //Esperamos la finalización de todos los hilos
        try {
            cuentaAtras.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
