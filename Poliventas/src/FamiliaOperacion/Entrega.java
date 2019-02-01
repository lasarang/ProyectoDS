/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FamiliaOperacion;

import ClasesAuxiliares.EntregaProducto;
import java.util.List;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Entrega extends Operacion {

    private int idEntrega, idPedido;
    private String localizacionQuiosco, observacion;
    private List<EntregaProducto> entregaProductos;

    public Entrega() {
        setTipo("Entrega");
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public String getLocalizacionQuiosco() {
        return localizacionQuiosco;
    }

    public void setLocalizacionQuiosco(String localizacionQuiosco) {
        this.localizacionQuiosco = localizacionQuiosco;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<EntregaProducto> getEntregaProductos() {
        return entregaProductos;
    }

    public void setEntregaProductos(List<EntregaProducto> entregaProductos) {
        this.entregaProductos = entregaProductos;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    @Override
    public String toString() {
        return "Entrega{" + "idEntrega=" + idEntrega + ", idPedido=" + idPedido + ", localizacionQuiosco=" + localizacionQuiosco + ", observacion=" + observacion + ", entregaProductos=" + entregaProductos + '}';
    }

}
