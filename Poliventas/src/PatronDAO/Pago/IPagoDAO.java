/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Pago;

import FamiliaOperacion.Pago;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public interface IPagoDAO {

    public void create(Pago pago) throws Exception;

    public Pago read(int idPago) throws Exception;

}
