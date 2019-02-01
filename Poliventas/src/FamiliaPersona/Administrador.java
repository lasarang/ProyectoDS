/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FamiliaPersona;

import ClasesAuxiliares.Producto;
import PatronDAO.Estudiante.EstudianteDAOImpl;
import PatronDAO.Persona.PersonaDAOImpl;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Administrador extends Estudiante {

    private int idAdministrador;

    private final EstudianteDAOImpl estudianteDao = new EstudianteDAOImpl();
    private final PersonaDAOImpl personaDao = new PersonaDAOImpl();

    public Administrador() {
        setRol("Administrador");
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    //Operaciones CRUD Estudiantes
    public void registrarEstudiante(Estudiante estudiante) throws Exception {
        estudianteDao.create(estudiante);
    }

    public Estudiante buscarEstudiante(int matricula) throws Exception {
        return estudianteDao.read(matricula);
    }

    public void agregarCorreos(List<String> nuevosCorreos, String cedula) throws Exception {
        personaDao.addCorreos(nuevosCorreos, cedula);
    }

    public void agregarTelefonos(List<String> nuevosTelefonos, String cedula) throws Exception {
        personaDao.addTelefonos(nuevosTelefonos, cedula);
    }

    public void agregarDomicilio(List<String> nuevosDomicilios, String cedula) throws Exception {
        personaDao.addDomicilios(nuevosDomicilios, cedula);
    }

    public void modificarInfoPersona(Map<String, Object> mapa, String cedula) throws Exception {
        personaDao.update(mapa, cedula);
    }

    public void modificarInfoEstudiante(Map<String, Object> mapa, String cedula) throws Exception {
        estudianteDao.update(mapa, matricula);
    }

    public void eliminarEstudiante(int matricula) throws Exception {
        estudianteDao.delete(matricula);
    }

    //Operaciones CRUD Productos
    public void registrarProducto(Producto producto) throws Exception {
        productoDao.create(producto);
    }

    public void actualizarProducto(Map<String, Object> nuevosValores, int idProducto) throws Exception {
        productoDao.update(nuevosValores, idProducto);
    }

    //Solo se debe usar cuando sepa el idProducto despues de seleccionar un producto en la busqueda
    public void eliminarProducto(int idProducto) throws Exception {
        productoDao.delete(idProducto);
    }

    @Override
    public String toString() {
        return "Administrador{" + "cedula=" + cedula + ", nombres=" + nombres + ", apellidos=" + apellidos + ", telefonoActual=" + telefonoActual + ", tieneWhatsapp=" + tieneWhatsapp + ", correos=" + correos + ", domicilios=" + domicilios + ", telefonosSecundarios=" + telefonosSecundarios + ", matricula=" + matricula + ", usuario=" + usuario + ", contrase\u00f1a=" + contraseña + ", rol=" + rol + ", idAdministrador=" + idAdministrador + '}';
    }

}
