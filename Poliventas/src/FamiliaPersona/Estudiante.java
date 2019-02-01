/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FamiliaPersona;

import ClasesAuxiliares.Producto;
import PatronDAO.Producto.ProductoDAOImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public abstract class Estudiante extends Persona {

    protected int matricula;
    protected String usuario, contraseña, rol;
    protected ProductoDAOImpl productoDao = new ProductoDAOImpl();
    private static Map<String, Estudiante> mapaRol = new HashMap<>();

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public static Map<String, Estudiante> getMapaRol() {
        mapaRol.put("Comprador", new Comprador());
        mapaRol.put("Vendedor", new Vendedor());
        mapaRol.put("Administrador", new Administrador());
        return mapaRol;
    }

    public List<Producto> realizarBusquedaSencillaNombre(String nombre) throws Exception {
        return productoDao.busquedaSencillaNombre(nombre);
    }

    public List<Producto> realizarBusquedaSencillaDescripcion(String descripcion) throws Exception {
        return productoDao.busquedaSencillaDescripcion(descripcion);
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Estudiante{" + "matricula=" + matricula + ", usuario=" + usuario + ", contrase\u00f1a=" + contraseña + ", rol=" + rol + '}';
    }
    

}
