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
public class PagoElectronico extends Pago {

    public PagoElectronico() {
        setTipo("PagoElectronico");
    }

    @Override
    public void pagar() throws Exception {
        pdao.create(this);
    }

    @Override
    public String toString() {
        return "PagoElectronico{" + "idPago=" + idPago + ", idPay=" + idPay + ", idPurchase=" + idPurchase + ", valorAbonado=" + valorAbonado + '}';
    }

}
