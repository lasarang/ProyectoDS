/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FamiliaOperacion;

import PatronDAO.Pago.PagoDAOImpl;
import PatronStrategy.PagoEfectivo;
import PatronStrategy.PagoElectronico;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public abstract class Pago extends Operacion {

    protected int idPago, idPay, idPurchase;
    protected double valorAbonado;
    protected PagoDAOImpl pdao= new PagoDAOImpl();
    private static Map<String, Pago> mapaFormaPagoInstancia = new HashMap<>();

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdPay() {
        return idPay;
    }

    public void setIdPay(int idPay) {
        this.idPay = idPay;
    }

    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    public double getValorAbonado() {
        return valorAbonado;
    }

    public void setValorAbonado(double valorAbonado) {
        this.valorAbonado = valorAbonado;
    }

    public abstract void pagar() throws Exception;

    public static Map<String, Pago> getMapaFormaPagoInstancia() {
        mapaFormaPagoInstancia.put("PagoEfectivo", new PagoEfectivo());
        mapaFormaPagoInstancia.put("PagoElectronico", new PagoElectronico());

        return mapaFormaPagoInstancia;
    }

}
