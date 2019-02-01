/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Compra;

import FamiliaOperacion.Compra;
import java.util.List;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public interface ICompraDAO {

    public void create(Compra compra) throws Exception;

    public List<Compra> readPendientes(int matricula, String tipo) throws Exception;
}
