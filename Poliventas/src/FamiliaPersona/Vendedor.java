/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FamiliaPersona;

import ClasesAuxiliares.Producto;
import FamiliaOperacion.Compra;
import PatronDAO.Producto.ProductoDAOImpl;
import PatronObserver.IObservable;
import java.util.List;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Vendedor extends Comprador implements IObservable {

    private int idVendedor, calificacionPromedio;
    private List<Producto> misProductos;
    private ProductoDAOImpl productoDao = new ProductoDAOImpl();

    public Vendedor() {
        setRol("Vendedor");
    }

    public Vendedor(Compra compra) {
        setRol("Vendedor");
        this.compra = compra;
        compra.agregar(this);
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(int calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public List<Producto> getMisProductos() throws Exception {
        setMisProductos(productoDao.readMisProductos(this.matricula));
        return misProductos;
    }

    public void setMisProductos(List<Producto> misProductos) {
        this.misProductos = misProductos;
    }

    @Override
    public List<Compra> obtenerComprasPendientes() throws Exception {
        return compraDao.readPendientes(this.matricula, "Venta");
    }

    @Override
    public void actualizar() {
        System.out.println("Notificacion de Nueva Venta " + compra.getEstado() + " porfavor, realizar nueva entrega");
    }

    @Override
    public String toString() {
        return "Vendedor{" + "cedula=" + cedula + ", nombres=" + nombres + ", apellidos=" + apellidos + ", telefonoActual=" + telefonoActual + ", tieneWhatsapp=" + tieneWhatsapp + ", correos=" + correos + ", domicilios=" + domicilios + ", telefonosSecundarios=" + telefonosSecundarios + ", matricula=" + matricula + ", usuario=" + usuario + ", contrase\u00f1a=" + contraseña + ", rol=" + rol + "idComprador=" + idComprador + ", saldoDisponible=" + saldoDisponible + ", idVendedor=" + idVendedor + ", calificacionPromedio=" + calificacionPromedio + ", misProductos=" + misProductos + '}';
    }

}
