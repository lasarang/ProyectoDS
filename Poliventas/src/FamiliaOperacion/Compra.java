/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FamiliaOperacion;

import ClasesAuxiliares.CompraProducto;
import ClasesAuxiliares.InfoProducto;
import PatronDAO.Compra.CompraDAOImpl;
import java.util.List;
import PatronObserver.IObservable;
import java.util.ArrayList;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Compra extends Operacion {

    private int idCompra;
    private List<CompraProducto> productos;
    private String estado;
    private List<IObservable> observadores=new ArrayList<>();
    private double montoTotal;
    private Pago pago;
    private Entrega entrega;
    private final CompraDAOImpl cdao = new CompraDAOImpl();

    public Compra() {
        setTipo("CompraVenta");
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public List<CompraProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<CompraProducto> productos) {
        this.productos = productos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
        notificarTodosObservadores();
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public double getMontoTotal() {
        setMontoTotal(calcularTotal());
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void registrarCompra() throws Exception {
        cdao.create(this);
        //pago.pagar();
    }

    /*
    public void pagarCompra() throws Exception {
    pago.pagar();
    }*/
    public void agregar(IObservable obs) {
        observadores.add(obs);
    }

    public void notificarTodosObservadores() {
        observadores.forEach(x -> x.actualizar());
    }

    private double calcularTotal() {
        double acum = 0;
        for (CompraProducto cp : productos) {
            InfoProducto info = cp.getProducto().getInfoProducto();
            acum += cp.getCantidad() * info.getPrecioUnitario();
        }
        return acum;
    }

    @Override
    public String toString() {
        return "Compra{" + "idCompra=" + idCompra + ", productos=" + productos + ", estado=" + estado + ", montoTotal=" + montoTotal + ", pago=" + pago + ", entrega=" + entrega + '}';
    }

}
