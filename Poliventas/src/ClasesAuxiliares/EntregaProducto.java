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
public class EntregaProducto {

    private int idEntregaProducto;
    private int idDeliveryProduct;
    private int idArticle;
    private int cantidadEntregada;

    public int getIdEntregaProducto() {
        return idEntregaProducto;
    }

    public void setIdEntregaProducto(int idEntregaProducto) {
        this.idEntregaProducto = idEntregaProducto;
    }

    public int getIdDeliveryProduct() {
        return idDeliveryProduct;
    }

    public void setIdDeliveryProduct(int idDeliveryProduct) {
        this.idDeliveryProduct = idDeliveryProduct;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(int cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

}
