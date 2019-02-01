/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Producto;

import ClasesAuxiliares.Producto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public interface IProductoDAO {

    public void create(Producto producto) throws Exception;

    public List<Producto> readMasBuscados() throws Exception;

    public List<Producto> readMisProductos(int matricula) throws Exception;

    public List<Producto> busquedaSencillaNombre(String nombreProducto) throws Exception;

    public List<Producto> busquedaSencillaDescripcion(String descripcionProducto) throws Exception;

    public void update(Map<String, Object> nuevosValores, int idProducto) throws Exception;

    public void delete(int idProducto) throws Exception;

}
