/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesAuxiliares;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class CompraProducto {

    private int idProductoCompra, cantidad;
    private Producto producto;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getIdProductoCompra() {
        return idProductoCompra;
    }

    public void setIdProductoCompra(int idProductoCompra) {
        this.idProductoCompra = idProductoCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ProductoCompra{" + "idProductoCompra=" + idProductoCompra + ", cantidad=" + cantidad + ", producto=" + producto + '}';
    }

    
}
