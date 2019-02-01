/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Producto;

import ClasesAuxiliares.InfoProducto;
import ClasesAuxiliares.Producto;
import Extra.Validate;
import PatronSingleton.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class ProductoDAOImpl implements IProductoDAO {

    private final Conexion conexionMySql = Conexion.getInstancia();
    private final Connection connection = conexionMySql.getConnection();
    private CallableStatement cs1, cs2;
    private PreparedStatement ps1;
    private ResultSet rs1, rs2;
    private Producto articulo;
    private InfoProducto infoArticulo;
    private List<Producto> articulos;

    @Override
    public void create(Producto producto) throws Exception {
        infoArticulo = producto.getInfoProducto();

        cs1 = connection.prepareCall("{CALL createProducto(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

        cs1.setInt(1, producto.getIdProvider());
        cs1.setString(2, infoArticulo.getNombre());
        cs1.setDouble(3, infoArticulo.getPrecioUnitario());
        cs1.setString(4, infoArticulo.getCategoria());
        cs1.setString(5, infoArticulo.getDescripcion());
        cs1.setInt(6, infoArticulo.getCantidadDisponible());
        cs1.setObject(7, infoArticulo.getTiempoMaxEntrega());
        cs1.setInt(8, 0);//Sin busquedas
        cs1.setObject(9, producto.getFechaIngreso());

        cs1.executeUpdate();
        cs1.close();
        System.out.println("Creacion exitosa de un nuevo producto!");
    }

    @Override
    public void update(Map<String, Object> nuevosValores, int idProducto) throws Exception {
        for (String columna : nuevosValores.keySet()) {
            ps1 = connection.prepareStatement("UPDATE Productos SET " + columna + "=? WHERE ?=Matricula");
            ps1.setObject(1, nuevosValores.get(columna));
            ps1.setInt(2, idProducto);
            ps1.executeUpdate();
            ps1.close();
        }
    }

    @Override
    public void delete(int idProducto) throws Exception {
        cs1 = connection.prepareCall("{CALL updateVisibleProducto(?)}");
        cs1.setInt(1, idProducto);
        cs1.executeUpdate();
        cs1.close();
        System.out.println("Se ha eliminado un producto exitosamente!");
    }

    @Override
    public List<Producto> readMasBuscados() throws Exception {
        articulos = new ArrayList<>();

        cs1 = connection.prepareCall("{CALL readProductosMasBuscados()}");
        rs1 = cs1.executeQuery();

        while (rs1.next()) {
            articulo = new Producto();
            infoArticulo = new InfoProducto();

            int idProducto = rs1.getInt("idProducto");
            int idProvider = rs1.getInt("idProvider");
            int cantidadDisponible = rs1.getInt("CantidadDisponible");
            int nroBusquedas = rs1.getInt("NroBusquedas");

            double precioUnitario = rs1.getDouble("PrecioUnitario");
            String nombre = rs1.getString("Nombre");
            String categoria = rs1.getString("Categoria");
            String descripcion = rs1.getString("Descripcion");
            Time tiempoMaxEntrega = (Time) rs1.getObject("TiempoMaxEntrega");
            Date fechaIngreso = (Date) rs1.getObject("FechaIngreso");

            infoArticulo.setNombre(nombre);
            infoArticulo.setCantidadDisponible(cantidadDisponible);
            infoArticulo.setPrecioUnitario(precioUnitario);
            infoArticulo.setCategoria(categoria);
            infoArticulo.setDescripcion(descripcion);
            infoArticulo.setTiempoMaxEntrega(LocalTime.parse(tiempoMaxEntrega.toString()));

            articulo.setIdProducto(idProducto);
            articulo.setIdProvider(idProvider);
            articulo.setNroBusquedas(nroBusquedas);
            articulo.setFechaIngreso(fechaIngreso.toLocalDate());
            articulo.setInfoProducto(infoArticulo);
            articulo.setCalificacionPromedio(obtenerCalificacionPromedioProducto(idProducto));

            articulos.add(articulo);
        }
        rs1.close();
        cs1.close();
        return articulos;
    }

    @Override
    public List<Producto> readMisProductos(int matricula) throws Exception {
        articulos = new ArrayList<>();

        cs1 = connection.prepareCall("{CALL readMisProductos(?)}");
        cs1.setInt(1, matricula);
        rs1 = cs1.executeQuery();

        while (rs1.next()) {
            articulo = new Producto();
            infoArticulo = new InfoProducto();

            int idProducto = rs1.getInt("idProducto");
            int idProvider = rs1.getInt("idProvider");
            int cantidadDisponible = rs1.getInt("CantidadDisponible");
            int nroBusquedas = rs1.getInt("NroBusquedas");

            double precioUnitario = rs1.getDouble("PrecioUnitario");
            String nombre = rs1.getString("Nombre");
            String categoria = rs1.getString("Categoria");
            String descripcion = rs1.getString("Descripcion");
            Time tiempoMaxEntrega = (Time) rs1.getObject("TiempoMaxEntrega");
            Date fechaIngreso = (Date) rs1.getObject("FechaIngreso");

            infoArticulo.setNombre(nombre);
            infoArticulo.setCantidadDisponible(cantidadDisponible);
            infoArticulo.setPrecioUnitario(precioUnitario);
            infoArticulo.setCategoria(categoria);
            infoArticulo.setDescripcion(descripcion);
            infoArticulo.setTiempoMaxEntrega(LocalTime.parse(tiempoMaxEntrega.toString()));

            articulo.setIdProducto(idProducto);
            articulo.setIdProvider(idProvider);
            articulo.setNroBusquedas(nroBusquedas);
            articulo.setFechaIngreso(fechaIngreso.toLocalDate());
            articulo.setInfoProducto(infoArticulo);
            articulo.setCalificacionPromedio(obtenerCalificacionPromedioProducto(idProducto));

            articulos.add(articulo);
        }
        rs1.close();
        cs1.close();
        return articulos;
    }

    @Override
    public List<Producto> busquedaSencillaNombre(String nombreProducto) throws Exception {
        articulos = new ArrayList<>();

        if (Validate.validarCadenaBusquedaSencilla(nombreProducto)) {
            nombreProducto = Validate.normalizeString(nombreProducto);

        } else {
            throw new Exception("Nombre invalido, mínimo debe ingresar 3 caracteres distintos de espacio y debe tener contenido");
        }

        cs1 = connection.prepareCall("{CALL readProductoNombre(?)}");
        cs1.setString(1, nombreProducto.trim());
        rs1 = cs1.executeQuery();

        while (rs1.next()) {
            articulo = new Producto();
            infoArticulo = new InfoProducto();

            int idProducto = rs1.getInt("idProducto");
            int idProvider = rs1.getInt("idProvider");
            int cantidadDisponible = rs1.getInt("CantidadDisponible");
            int nroBusquedas = rs1.getInt("NroBusquedas");

            double precioUnitario = rs1.getDouble("PrecioUnitario");
            String nombre = rs1.getString("Nombre");
            String categoria = rs1.getString("Categoria");
            String descripcion = rs1.getString("Descripcion");
            Time tiempoMaxEntrega = (Time) rs1.getObject("TiempoMaxEntrega");
            Date fechaIngreso = (Date) rs1.getObject("FechaIngreso");

            infoArticulo.setNombre(nombre);
            infoArticulo.setCantidadDisponible(cantidadDisponible);
            infoArticulo.setPrecioUnitario(precioUnitario);
            infoArticulo.setCategoria(categoria);
            infoArticulo.setDescripcion(descripcion);
            infoArticulo.setTiempoMaxEntrega(LocalTime.parse(tiempoMaxEntrega.toString()));

            articulo.setIdProducto(idProducto);
            articulo.setIdProvider(idProvider);
            articulo.setNroBusquedas(nroBusquedas);
            articulo.setFechaIngreso(fechaIngreso.toLocalDate());
            articulo.setInfoProducto(infoArticulo);
            articulo.setCalificacionPromedio(obtenerCalificacionPromedioProducto(idProducto));

            articulos.add(articulo);
        }
        rs1.close();
        cs1.close();
        return articulos;

    }

    @Override
    public List<Producto> busquedaSencillaDescripcion(String descripcionProducto) throws Exception {
        articulos = new ArrayList<>();
        if (Validate.validarCadenaBusquedaSencilla(descripcionProducto)) {
            descripcionProducto = Validate.normalizeString(descripcionProducto);

        } else {
            throw new Exception("Descripcion invalida, mínimo debe ingresar 3 caracteres distintos de espacio y debe tener contenido");
        }

        cs1 = connection.prepareCall("{CALL readProductoDescripcion(?)}");
        cs1.setString(1, descripcionProducto.trim());
        rs1 = cs1.executeQuery();

        while (rs1.next()) {
            articulo = new Producto();
            infoArticulo = new InfoProducto();

            int idProducto = rs1.getInt("idProducto");
            int idProvider = rs1.getInt("idProvider");
            int cantidadDisponible = rs1.getInt("CantidadDisponible");
            int nroBusquedas = rs1.getInt("NroBusquedas");

            double precioUnitario = rs1.getDouble("PrecioUnitario");
            String nombre = rs1.getString("Nombre");
            String categoria = rs1.getString("Categoria");
            String descripcion = rs1.getString("Descripcion");
            Time tiempoMaxEntrega = (Time) rs1.getObject("TiempoMaxEntrega");
            Date fechaIngreso = (Date) rs1.getObject("FechaIngreso");
            infoArticulo.setNombre(nombre);
            infoArticulo.setCantidadDisponible(cantidadDisponible);
            infoArticulo.setPrecioUnitario(precioUnitario);
            infoArticulo.setCategoria(categoria);
            infoArticulo.setDescripcion(descripcion);
            infoArticulo.setTiempoMaxEntrega(LocalTime.parse(tiempoMaxEntrega.toString()));

            articulo.setIdProducto(idProducto);
            articulo.setIdProvider(idProvider);
            articulo.setNroBusquedas(nroBusquedas);
            articulo.setFechaIngreso(fechaIngreso.toLocalDate());
            articulo.setInfoProducto(infoArticulo);
            articulo.setCalificacionPromedio(obtenerCalificacionPromedioProducto(idProducto));

            articulos.add(articulo);
        }
        rs1.close();
        cs1.close();
        return articulos;

    }

    public int obtenerCalificacionPromedioProducto(int idProducto) throws Exception {
        cs2 = connection.prepareCall("{CALL obtenerCalificacionPromedioProducto(?)}");
        cs2.setInt(1, idProducto);
        rs2 = cs2.executeQuery();
        if (rs2.next()) {
            return rs2.getInt("PromProducto");
        } else {
            throw new Exception("EL idProducto que ingreso, no existe!");
        }

    }

}
