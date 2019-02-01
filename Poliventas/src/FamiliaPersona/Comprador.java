/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FamiliaPersona;

import ClasesAuxiliares.CompraSaldo;
import FamiliaOperacion.Compra;
import FamiliaOperacion.Pago;
import PatronDAO.Compra.CompraDAOImpl;
import java.util.List;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Comprador extends Estudiante {

    protected int idComprador;
    protected double saldoDisponible;
    protected CompraSaldo compraSaldo;
    protected CompraDAOImpl compraDao = new CompraDAOImpl();
    protected Compra compra;

    public Comprador() {
        setRol("Comprador");
    }

    public int getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(int idComprador) {
        this.idComprador = idComprador;
    }

    public double getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public CompraSaldo getCompraSaldo() {
        return compraSaldo;
    }

    public void setCompraSaldo(CompraSaldo compraSaldo) {
        this.compraSaldo = compraSaldo;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public void pagarNuevaCompra(Pago pay) throws Exception {
        compra.setPago(pay);
        compra.registrarCompra();
        compra.setEstado("Pendiente");
    }

    public List<Compra> obtenerComprasPendientes() throws Exception {
        return compraDao.readPendientes(this.matricula, "Compra");
    }

    @Override
    public String toString() {
        return "Comprador{" + "cedula=" + cedula + ", nombres=" + nombres + ", apellidos=" + apellidos + ", telefonoActual=" + telefonoActual + ", tieneWhatsapp=" + tieneWhatsapp + ", correos=" + correos + ", domicilios=" + domicilios + ", telefonosSecundarios=" + telefonosSecundarios + ", matricula=" + matricula + ", usuario=" + usuario + ", contrase\u00f1a=" + contraseña + ", rol=" + rol + "idComprador=" + idComprador + ", saldoDisponible=" + saldoDisponible + '}';
    }

}
