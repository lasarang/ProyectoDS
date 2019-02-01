/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Entrega;

import Extra.Validate;
import FamiliaOperacion.Entrega;
import PatronDAO.Operacion.OperacionDAOImpl;
import PatronSingleton.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class EntregaDAOImpl implements IEntregaDAO {

    private final Conexion conexionMySql = Conexion.getInstancia();
    private final Connection connection = conexionMySql.getConnection();
    private CallableStatement cs1, cs2;
    private ResultSet rs1;
    private Entrega entrega;
    private final OperacionDAOImpl opdao = new OperacionDAOImpl();
    private Validate validate;

    @Override
    public void create(Entrega entrega) throws Exception {
        opdao.create(entrega);
        int idOperacion = Integer.parseInt(validate.ultimoId("Operaciones"));
        cs1 = connection.prepareCall("{CALL createEntrega(?, ?, ?)}");
        cs1.setInt(1, idOperacion);
        cs1.setInt(2, entrega.getIdPedido());
        cs1.setString(3, entrega.getLocalizacionQuiosco());
        cs1.setString(4, entrega.getObservacion());
        cs1.executeQuery();
        cs1.close();
    }

    //Para leer se utiliza en el CompraDAOImpl, depende del idCompraVenta
    @Override
    public Entrega read(int idCompraVenta) throws Exception {
        entrega = new Entrega();
        cs2 = connection.prepareCall("{CALL readEntrega(?)}");
        cs2.setInt(1, idCompraVenta);
        rs1 = cs1.executeQuery();
        if (rs1.next()) {

            int idEntrega = rs1.getInt("idEntrega");
            int idDelivery = rs1.getInt("idDelivery");
            String localizacionQuiosco = rs1.getString("LocalizacionQuiosco");
            String observacion = rs1.getString("Observacion");
            entrega.setIdEntrega(idEntrega);
            entrega.setIdOperacion(idDelivery);
            entrega.setIdPedido(idCompraVenta);
            entrega.setLocalizacionQuiosco(localizacionQuiosco);
            entrega.setObservacion(observacion);
            return entrega;

        } else {
            throw new Exception("La compra pendiente no tiene una entrega establecida");
        }
    }

}
