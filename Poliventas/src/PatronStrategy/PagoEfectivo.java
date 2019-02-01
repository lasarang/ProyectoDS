/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronStrategy;

import FamiliaOperacion.Pago;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class PagoEfectivo extends Pago {

    private int idPagoEfectivo;
    private double valorEntregado, cambio;

    public PagoEfectivo() {
        setTipo("PagoEfectivo");
    }

    public int getIdPagoEfectivo() {
        return idPagoEfectivo;
    }

    public void setIdPagoEfectivo(int idPagoEfectivo) {
        this.idPagoEfectivo = idPagoEfectivo;
    }

    public double getValorEntregado() {
        return valorEntregado;
    }

    public void setValorEntregado(double valorEntregado) {
        this.valorEntregado = valorEntregado;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    @Override
    public void pagar() throws Exception {
        pdao.create(this);
    }

    @Override
    public String toString() {
        return "PagoEfectivo{" + "idPagoEfectivo=" + idPagoEfectivo + ", valorEntregado=" + valorEntregado + ", cambio=" + cambio + '}';
    }

}
