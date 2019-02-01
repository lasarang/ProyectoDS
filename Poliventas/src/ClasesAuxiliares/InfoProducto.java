/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesAuxiliares;

import java.time.LocalTime;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class InfoProducto {

    private String nombre;
    private String descripcion;
    private String categoria;
    private double precioUnitario;
    private int cantidadDisponible;
    private LocalTime tiempoMaxEntrega;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public LocalTime getTiempoMaxEntrega() {
        return tiempoMaxEntrega;
    }

    public void setTiempoMaxEntrega(LocalTime tiempoMaxEntrega) {
        this.tiempoMaxEntrega = tiempoMaxEntrega;
    }

    @Override
    public String toString() {
    return "InfoProducto{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", categoria=" + categoria + ", precioUnitario=" + precioUnitario + ", cantidadDisponible=" + cantidadDisponible + ", tiempoMaxEntrega=" + tiempoMaxEntrega + '}';
    }

}
