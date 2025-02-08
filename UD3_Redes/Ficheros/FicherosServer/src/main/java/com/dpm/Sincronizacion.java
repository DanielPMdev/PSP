package com.dpm;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author danielpm.dev
 */
public class Sincronizacion {

    private List<Fichero> listaFicheros;

    public Sincronizacion(File directorio) {
        listaFicheros = Arrays.stream(directorio.listFiles())
                .filter(fichero -> fichero.isFile())
                .map(fichero -> new Fichero(fichero.getName(), fichero.length()))
                .toList();
    }

    public List<Fichero> getListaFicheros() {
        return listaFicheros;
    }
}
