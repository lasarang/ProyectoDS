/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Pago;

import Extra.Validate;
import PatronDAO.Operacion.OperacionDAOImpl;
import PatronSingleton.Conexion;
import FamiliaOperacion.Pago;
import PatronStrategy.PagoEfectivo;
import PatronStrategy.PagoElectronico;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class PagoDAOImpl implements IPagoDAO {

    private final Conexion conexionMySql = Conexion.getInstancia();
    private final Connection connection = conexionMySql.getConnection();
    private CallableStatement cs1, cs2;
    private ResultSet rs1, rs2;
    private Validate validate = new Validate();
    private Pago pago;
    private final OperacionDAOImpl opdao = new OperacionDAOImpl();

    @Override
    public void create(Pago pago) throws Exception {
        opdao.create(pago);

        int idOperacion = Integer.parseInt(validate.ultimoId("Operaciones"));
        cs1 = connection.prepareCall("{CALL createPago(?, ?, ?, ?)}");

        cs1.setInt(1, idOperacion);
        cs1.setInt(2, pago.getIdPurchase());// ultimo idCompraVenta
        cs1.setDouble(3, pago.getValorAbonado());
        cs1.setString(4, pago.getTipo());
        cs1.executeUpdate();
        cs1.close();

        if (pago instanceof PagoEfectivo) {
            createPagoEfectivo((PagoEfectivo) pago);
        }
    }

    private void createPagoEfectivo(PagoEfectivo pagoEfectivo) throws Exception {
        cs1 = connection.prepareCall("{CALL createPagoEfectivo(?, ?, ?)}");

        cs1.setInt(1, pagoEfectivo.getIdPago());
        cs1.setDouble(2, pagoEfectivo.getValorAbonado());
        cs1.setDouble(3, pagoEfectivo.getCambio());
        cs1.executeUpdate();
        cs1.close();
    }

    //Para leer se utiliza en el CompraDAOImpl, depende del idCompraVenta
    @Override
    public Pago read(int idCompraVenta) throws Exception {

        cs1 = connection.prepareCall("{CALL readPagoOperacion(?)}");
        cs1.setInt(1, idCompraVenta);
        rs1 = cs1.executeQuery();
        if (rs1.next()) {
            String formaPago = rs1.getString("FormaPago");

            pago = (Pago.getMapaFormaPagoInstancia()).get(formaPago);
            int idOperacion = rs1.getInt("idOperacion");
            int idPerson1 = rs1.getInt("idPerson1");
            int idPerson2 = rs1.getInt("idPerson2");
            Timestamp fechaHora = (Timestamp) rs1.getObject("FechaHora");

            pago.setIdOperacion(idOperacion);
            pago.setIdVendedor(idPerson1);
            pago.setIdComprador(idPerson2);
            pago.setFechaHora(fechaHora.toLocalDateTime());

            int idPago = rs1.getInt("idPago");
            double valorAbonado = rs1.getDouble("ValorAbonado");
            pago.setIdPago(idPago);
            pago.setValorAbonado(valorAbonado);

            if (pago instanceof PagoEfectivo) {
                pago = readPagoEfectivo((PagoEfectivo) pago);
            } else if (pago instanceof PagoElectronico) {
                disminuirSaldo(pago.getIdComprador(), pago.getValorAbonado());
            }

        } else {
            throw new Exception("No existe Pago con ese idCompraVenta");
        }

        rs1.close();
        cs1.close();

        return pago;
    }

    private PagoEfectivo readPagoEfectivo(PagoEfectivo pagoEfectivo) throws Exception {

        cs2 = connection.prepareCall("{CALL readPagoEfectivo(?)}");
        cs2.setInt(1, pagoEfectivo.getIdPago());
        rs2 = cs2.executeQuery();
        if (rs2.next()) {
            int idPagoEfectivo = rs2.getInt("idPagoEfectivo");
            double valorEntregado = rs2.getDouble("ValorEntregado");
            double cambio = rs2.getDouble("Cambio");

            pagoEfectivo.setIdPagoEfectivo(idPagoEfectivo);
            pagoEfectivo.setValorEntregado(valorEntregado);
            pagoEfectivo.setCambio(cambio);

        } else {
            throw new Exception("No existe Pago Efectivo con ese idPago");
        }
        rs2.close();
        cs2.close();

        return pagoEfectivo;
    }

    private void disminuirSaldo(int matricula, double debito) throws Exception {
        cs1 = connection.prepareCall("{CALL updateSaldoComprador(?, ?)}");
        cs1.setInt(1, matricula);
        cs1.setDouble(2, debito);
        cs1.executeUpdate();
        cs1.close();

    }

}
