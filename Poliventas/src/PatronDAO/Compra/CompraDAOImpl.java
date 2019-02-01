/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Compra;

import ClasesAuxiliares.InfoProducto;
import ClasesAuxiliares.Producto;
import ClasesAuxiliares.CompraProducto;
import Extra.Validate;
import FamiliaOperacion.Compra;
import FamiliaOperacion.Entrega;
import FamiliaOperacion.Pago;
import PatronDAO.Entrega.EntregaDAOImpl;
import PatronDAO.Operacion.OperacionDAOImpl;
import PatronDAO.Pago.PagoDAOImpl;
import PatronDAO.Producto.ProductoDAOImpl;
import PatronSingleton.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class CompraDAOImpl implements ICompraDAO {

    private final Conexion conexionMySql = Conexion.getInstancia();
    private final Connection connection = conexionMySql.getConnection();
    private CallableStatement cs1, cs2, cs3;
    private PreparedStatement ps;
    private ResultSet rs1, rs2, rs3;
    private Compra compra;
    private List<Compra> compras;
    private Producto producto;
    private CompraProducto productoCompra;
    private List<CompraProducto> productosCompra;
    private InfoProducto infoProducto;
    private final ProductoDAOImpl pdao = new ProductoDAOImpl();
    //private Entrega entrega;
    private final PagoDAOImpl pagoDao = new PagoDAOImpl();
    private final EntregaDAOImpl entregaDao = new EntregaDAOImpl();
    private final Validate validate = new Validate();
    private final OperacionDAOImpl opdao = new OperacionDAOImpl();
    private Pago pago;

    @Override
    public void create(Compra compra) throws Exception {
        try {
            connection.setAutoCommit(false);
            opdao.create(compra);

            int idOperacion = Integer.parseInt(validate.ultimoId("Operaciones"));
            System.out.println("Dentro de create compra: " + idOperacion);

            cs1 = connection.prepareCall("{CALL createCompraVenta(?, ?, ?)}");
            cs1.setInt(1, idOperacion);
            cs1.setDouble(2, compra.getMontoTotal());
            cs1.setInt(3, 1);// estado inicial "Pendiente"
            cs1.executeUpdate();
            cs1.close();

            int idCompraVenta = Integer.parseInt(validate.ultimoId("ComprasVentas"));
            System.out.println("idCompraVenta: " + idCompraVenta);
            createComprasVentasProductos(compra.getProductos(), idCompraVenta);

            //Crea el Pago despues de la CompraVenta
            pago = compra.getPago();
            pago.setIdPurchase(idCompraVenta);
            pago.setIdComprador(compra.getIdComprador());
            pago.setIdVendedor(compra.getIdVendedor());
            pago.pagar();

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
        System.out.println("Registo de compra y pago exitoso!");
    }

    private void createComprasVentasProductos(List<CompraProducto> compraProductos, int idCompraVenta) throws Exception {
        for (CompraProducto cp : compraProductos) {
            cs1 = connection.prepareCall("{CALL createCompraVentaProducto(?, ?, ?)}");
            cs1.setInt(1, idCompraVenta);
            cs1.setInt(2, (cp.getProducto()).getIdProducto());
            cs1.setInt(3, cp.getCantidad());
            cs1.executeUpdate();
            cs1.close();
        }
    }

    // Lee las compras y ventas pendientes
    // usa la matricula del estudiante (Comprador o Vendedor)
    // se especifica un tipo que puede se "Compra" o "Venta"
    @Override
    public List<Compra> readPendientes(int matricula, String tipo) throws Exception {
        compras = new ArrayList<>();

        cs1 = connection.prepareCall("{CALL readComprasPendientes(?, ?)}");
        cs1.setInt(1, matricula);
        cs1.setString(2, tipo);
        rs1 = cs1.executeQuery();

        while (rs1.next()) {
            compra = new Compra();

            Timestamp fechaHora = (Timestamp) rs1.getObject("FechaHora");
            int idCompraVenta = rs1.getInt("idCompraVenta");
            double montoTotal = rs1.getDouble("MontoTotal");

            compra.setFechaHora(fechaHora.toLocalDateTime());
            compra.setIdCompra(idCompraVenta);
            compra.setMontoTotal(montoTotal);
            compra.setProductos(readProductosCompra(idCompraVenta));
            compra.setEstado("Pendiente");
            //compra.setEntrega(entregaDao.read(idCompraVenta));

            compra.setPago(pagoDao.read(idCompraVenta));

            if (tipo.equals("Compra")) {
                int idVendedor = rs1.getInt("idPerson1");
                compra.setIdVendedor(idVendedor);
            } else if (tipo.equals("Venta")) {
                int idComprador = rs1.getInt("idPerson2");
                compra.setIdVendedor(idComprador);
            }
            compras.add(compra);

        }
        rs1.close();
        cs1.close();
        return compras;
    }

    private List<CompraProducto> readProductosCompra(int idCompraVenta) throws Exception {

        productosCompra = new ArrayList<>();

        cs2 = connection.prepareCall("{CALL readComprasVentasProductos(?)}");
        cs2.setInt(1, idCompraVenta);
        rs2 = cs2.executeQuery();

        while (rs2.next()) {

            productoCompra = new CompraProducto();

            int idCompraVentaProducto = rs2.getInt("idCompraVentaProducto");
            int cantidad = rs2.getInt("Cantidad");
            productoCompra.setIdProductoCompra(idCompraVentaProducto);
            productoCompra.setCantidad(cantidad);

            producto = new Producto();
            infoProducto = new InfoProducto();

            int idProducto = rs2.getInt("idProducto");
            int idProvider = rs2.getInt("idProvider");
            int cantidadDisponible = rs2.getInt("CantidadDisponible");
            int nroBusquedas = rs2.getInt("NroBusquedas");
            double precioUnitario = rs2.getDouble("PrecioUnitario");
            String nombre = rs2.getString("Nombre");
            String categoria = rs2.getString("Categoria");
            String descripcion = rs2.getString("Descripcion");
            Time tiempoMaxEntrega = (Time) rs2.getObject("TiempoMaxEntrega");
            Date fechaIngreso = (Date) rs2.getObject("FechaIngreso");
            infoProducto.setNombre(nombre);
            infoProducto.setCantidadDisponible(cantidadDisponible);
            infoProducto.setPrecioUnitario(precioUnitario);
            infoProducto.setCategoria(categoria);
            infoProducto.setDescripcion(descripcion);
            infoProducto.setTiempoMaxEntrega(LocalTime.parse(tiempoMaxEntrega.toString()));

            producto.setIdProducto(idProducto);
            producto.setIdProvider(idProvider);
            producto.setNroBusquedas(nroBusquedas);
            producto.setFechaIngreso(fechaIngreso.toLocalDate());
            producto.setInfoProducto(infoProducto);
            producto.setCalificacionPromedio(pdao.obtenerCalificacionPromedioProducto(idProducto));

            productoCompra.setProducto(producto);
            productosCompra.add(productoCompra);

        }
        rs2.close();
        cs2.close();
        return productosCompra;
    }

}
