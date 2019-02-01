/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Entrega;

import FamiliaOperacion.Entrega;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public interface IEntregaDAO {

    public void create(Entrega entrega) throws Exception;

    public Entrega read(int idEntrega) throws Exception;

}
