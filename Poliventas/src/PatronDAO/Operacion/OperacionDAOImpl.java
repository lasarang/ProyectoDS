/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Operacion;

import FamiliaOperacion.Operacion;
import PatronSingleton.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class OperacionDAOImpl implements IOperacionDAO {

    private final Conexion conexionMySql = Conexion.getInstancia();
    private final Connection connection = conexionMySql.getConnection();
    private CallableStatement cs1;

    @Override
    public void create(Operacion operacion) throws Exception {

        cs1 = connection.prepareCall("{CALL createOperacion(?, ?, ?, ?)}");

        cs1.setInt(1, operacion.getIdVendedor());
        cs1.setInt(2, operacion.getIdComprador());
        cs1.setObject(3, operacion.getFechaHora());
        cs1.setString(4, operacion.getTipo());
        cs1.executeUpdate();
        cs1.close();
        System.out.println("Creacion exitosa de nueva operacion!");
    }

   
}
