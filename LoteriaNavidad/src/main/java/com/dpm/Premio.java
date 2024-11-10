package com.dpm;

import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * @author danielpm.dev
 */
public class Premio {
    private int numero;
    private int premio;

    public Premio() {
    }

    public Premio(int numero, int premio) {
        this.numero = numero;
        this.premio = premio;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPremio() {
        return premio;
    }

    public void setPremio(int premio) {
        this.premio = premio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Premio premio = (Premio) o;
        return numero == premio.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }
}
