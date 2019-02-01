/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesAuxiliares;

import java.time.LocalDateTime;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class CompraSaldo {

    private int idCompraSaldo;
    private LocalDateTime fechaHora;
    private double saldoDisponible;
    private String celularAsociado;

    public int getIdCompraSaldo() {
        return idCompraSaldo;
    }

    public void setIdCompraSaldo(int idCompraSaldo) {
        this.idCompraSaldo = idCompraSaldo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public String getCelularAsociado() {
        return celularAsociado;
    }

    public void setCelularAsociado(String celularAsociado) {
        this.celularAsociado = celularAsociado;
    }

}
