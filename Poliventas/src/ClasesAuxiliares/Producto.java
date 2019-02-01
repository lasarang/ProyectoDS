/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesAuxiliares;

import java.time.LocalDate;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Producto {

    private int idProducto;
    private int idProvider;
    private int calificacionPromedio;
    private int nroBusquedas;
    private LocalDate fechaIngreso;
    private InfoProducto infoProducto;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getNroBusquedas() {
        return nroBusquedas;
    }

    public void setNroBusquedas(int nroBusquedas) {
        this.nroBusquedas = nroBusquedas;
    }

    public int getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(int idProvider) {
        this.idProvider = idProvider;
    }

    public int getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(int calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public InfoProducto getInfoProducto() {
        return infoProducto;
    }

    public void setInfoProducto(InfoProducto infoProducto) {
        this.infoProducto = infoProducto;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", idProvider=" + idProvider + ", calificacionPromedio=" + calificacionPromedio + ", nroBusquedas=" + nroBusquedas + ", fechaIngreso=" + fechaIngreso + ", infoProducto=" + infoProducto + '}';
    }

   
}
