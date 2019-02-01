/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FamiliaOperacion;

import FamiliaPersona.Comprador;
import FamiliaPersona.Vendedor;
import PatronStrategy.PagoEfectivo;
import PatronStrategy.PagoElectronico;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public abstract class Operacion {

    protected int idOperacion, idVendedor, idComprador;
    protected LocalDateTime fechaHora;
    protected String tipo;
    private static Map<String, Operacion> mapaTipoOperacionInstancia = new HashMap<>();

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public int getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(int idComprador) {
        this.idComprador = idComprador;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static Map<String, Operacion> getMapaTipoOperacionInstancia() {
        mapaTipoOperacionInstancia.put("Compra", new Compra());
        mapaTipoOperacionInstancia.put("Entrega", new Entrega());
        mapaTipoOperacionInstancia.put("PagoElectronico", new PagoElectronico());
        mapaTipoOperacionInstancia.put("PagoEfectivo", new PagoEfectivo());

        return mapaTipoOperacionInstancia;
    }

}
