/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Estudiante;

import Extra.Validate;
import FamiliaPersona.Administrador;
import FamiliaPersona.Comprador;
import FamiliaPersona.Estudiante;
import FamiliaPersona.Vendedor;
import PatronDAO.Persona.PersonaDAOImpl;
import PatronDAO.Producto.ProductoDAOImpl;
import PatronSingleton.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class EstudianteDAOImpl implements ILoginDAO, IEstudianteDAO {

    private final Conexion conexion = Conexion.getInstancia();
    private final Connection connection = conexion.getConnection();
    private CallableStatement cs1, cs2;
    private PreparedStatement ps1;
    private ResultSet rs1, rs2;
    private Estudiante estudiante;
    private Comprador comprador;
    private Vendedor vendedor;
    private Administrador administrador;
    private final PersonaDAOImpl personaDao = new PersonaDAOImpl();
    private final ProductoDAOImpl productoDao = new ProductoDAOImpl();
    private final Validate validate = new Validate();

    @Override
    public boolean authenticarEstudiante(String usuario, String pwd) throws Exception {
        cs1 = connection.prepareCall("{CALL verificarEstudiante(?, ?)}");
        cs1.setString(1, usuario);
        cs1.setString(2, pwd);
        rs1 = cs1.executeQuery();
        rs1.next();
        int matricula = rs1.getInt("Matricula");
        rs1.close();
        cs1.close();

        if (matricula == 0) {
            return false;
        } else {
            return true;
        }
    }

    // Registra un nuevo Estudiante con su rol
    @Override
    public void create(Estudiante estudiante) throws Exception {

        personaDao.create(estudiante);

        cs1 = connection.prepareCall("{CALL createEstudiante(?, ?, ?, ?, ?)}");
        cs1.setInt(1, estudiante.getMatricula());
        cs1.setString(2, estudiante.getCedula());
        cs1.setString(3, estudiante.getUsuario());
        cs1.setString(4, estudiante.getContraseña());
        cs1.setInt(5, obtenerIdRole(estudiante.getRol()));

        cs1.executeUpdate();
        cs1.close();

        if (estudiante instanceof Comprador) {
            crearComprador(estudiante.getMatricula());

            if (estudiante instanceof Vendedor) {
                crearVendedor();
            }
        } else if (estudiante instanceof Administrador) {
            crearAdministrador(estudiante.getMatricula());
        }

        System.out.println("Creacion exitosa de un nuevo Estudiante!");
    }

    private void crearComprador(int matricula) throws Exception {
        cs2 = connection.prepareCall("{CALL createComprador(?, ?)}");
        cs2.setInt(1, matricula);
        cs2.setDouble(2, 200); //saldo inicial
        cs2.executeUpdate();
        cs2.close();
    }

    private void crearVendedor() throws Exception {
        int idComprador = Integer.parseInt(validate.ultimoId("Compradores"));
        cs2 = connection.prepareCall("{CALL createVendedor(?)}");
        cs2.setInt(1, idComprador);
        cs2.executeUpdate();
        cs2.close();
    }

    private void crearAdministrador(int matricula) throws Exception {
        cs2 = connection.prepareCall("{CALL createAdministrador(?)}");
        cs2.setInt(1, matricula);
        cs2.executeUpdate();
        cs2.close();
    }

    // Busca a un estudiante en especifico por su matricula
    @Override
    public Estudiante read(int matricula) throws Exception {

        cs1 = connection.prepareCall("{CALL readEstudianteXMatricula(?)}");
        cs1.setInt(1, matricula);
        rs1 = cs1.executeQuery();

        if (rs1.next()) {

            String cedula = rs1.getString("idStudent");

            int idRole = rs1.getInt("idRole");
            String rol = obtenerRol(idRole);

            estudiante = (Estudiante) personaDao.read(cedula, rol);
            //leer mas roles comprador, vendedor 

            String usuario = rs1.getString("Usuario");
            String contraseña = rs1.getString("Contraseña");
            estudiante.setMatricula(matricula);
            estudiante.setUsuario(usuario);
            estudiante.setContraseña(contraseña);

            if (estudiante instanceof Comprador) {
                comprador = (Comprador) estudiante;
                comprador = readComprador(comprador);

                if (estudiante instanceof Vendedor) {

                    vendedor = (Vendedor) estudiante;
                    vendedor.setIdVendedor(readIdVendedor(vendedor.getIdComprador()));
                    vendedor.setCalificacionPromedio(obtenerCalificacionPromedioVendedor(vendedor.getIdVendedor()));
                    vendedor.setMisProductos(productoDao.readMisProductos(vendedor.getMatricula()));
                    estudiante = vendedor;
                }
                estudiante = comprador;
            } else if (estudiante instanceof Administrador) {
                administrador = (Administrador) estudiante;
                administrador.setIdAdministrador(readIdAdministrador(administrador.getMatricula()));
                estudiante = administrador;
            }

        } else {
            throw new Exception("No existe un estudiante con esa matricula!");
        }
        rs1.close();
        cs1.close();
        return estudiante;

    }

    private Comprador readComprador(Comprador comprador) throws Exception {

        cs2 = connection.prepareCall("{CALL readComprador(?)}");
        cs2.setInt(1, comprador.getMatricula());
        rs2 = cs2.executeQuery();

        if (rs2.next()) {
            int idComprador = rs2.getInt("idComprador");
            double saldoDisponible = rs2.getDouble("SaldoDisponible");
            comprador.setIdComprador(idComprador);
            comprador.setSaldoDisponible(saldoDisponible);
        } else {
            throw new Exception("No existe un comprador con esa matricula!");
        }
        rs2.close();
        cs2.close();

        return comprador;

    }

    private int readIdVendedor(int idComprador) throws Exception {

        cs2 = connection.prepareCall("{CALL readVendedor(?)}");
        cs2.setInt(1, idComprador);
        rs2 = cs2.executeQuery();

        if (rs2.next()) {

            return rs2.getInt("idVendedor");

        } else {
            throw new Exception("No existe un vendedor con ese idComprador!");
        }

    }

    private int readIdAdministrador(int matricula) throws Exception {

        cs2 = connection.prepareCall("{CALL readAdministrador(?)}");
        cs2.setInt(1, matricula);
        rs2 = cs2.executeQuery();

        if (rs2.next()) {

            return rs2.getInt("idAdministrador");

        } else {
            throw new Exception("No existe un administrador con esa matricula!");
        }

    }

    private String obtenerRol(int idRole) throws Exception {
        ps1 = connection.prepareStatement("SELECT Rol FROM RolesEstudiantes WHERE ?=idRol");
        ps1.setInt(1, idRole);
        rs2 = ps1.executeQuery();
        rs2.next();
        String rol = rs2.getString("Rol");
        rs2.close();
        ps1.close();

        if (rol.equals("null")) {
            throw new Exception("No existe un Rol con ese idRole");
        } else {
            return rol;
        }
    }

    private int obtenerIdRole(String rol) throws Exception {
        ps1 = connection.prepareStatement("SELECT idRol FROM RolesEstudiantes WHERE ?=Rol");
        ps1.setString(1, rol);
        rs2 = ps1.executeQuery();

        int idRole = 0;
        if (rs2.next()) {
            idRole = rs2.getInt("idRol");
        } else {
            throw new Exception("No existe idRol con ese Rol");
        }

        rs2.close();
        ps1.close();

        return idRole;
    }

    public int obtenerIdComprador(int matricula) throws Exception {
        ps1 = connection.prepareStatement("SELECT idComprador FROM Compradores WHERE ?=idBuyer");
        ps1.setInt(1, matricula);
        rs1 = ps1.executeQuery();

        int idComprador = 0;
        if (rs1.next()) {
            idComprador = rs1.getInt("idComprador");
        } else {
            throw new Exception("No existe idComprador con esa matricula");
        }

        rs1.close();
        ps1.close();

        return idComprador;
    }

    @Override
    public void update(Map<String, Object> nuevosValores, int matricula) throws Exception {
        for (String columna : nuevosValores.keySet()) {
            ps1 = connection.prepareStatement("UPDATE Estudiantes SET " + columna + "=? WHERE ?=Matricula");
            ps1.setObject(1, nuevosValores.get(columna));
            ps1.setInt(2, matricula);
            ps1.executeUpdate();
            ps1.close();
            System.out.println("Actualizacion exitosa de datos del Estudiante!");
        }

    }

    @Override
    public void delete(int matricula) throws Exception {
        cs1 = connection.prepareCall("{CALL updateVisibleEstudiante(?)}");
        cs1.setInt(1, matricula);
        cs1.executeUpdate();
        cs1.close();
        System.out.println("Se ha eliminado un estudiante exitosamente!");
    }

    public int obtenerCalificacionPromedioVendedor(int idVendedor) throws Exception {
        cs2 = connection.prepareCall("{CALL obtenerCalificacionPromedioVendedor(?)}");
        cs2.setInt(1, idVendedor);
        rs2 = cs2.executeQuery();
        if (rs2.next()) {
            return rs2.getInt("PromVendedor");
        } else {
            throw new Exception("EL idVendedor que ingreso, no existe!");
        }

    }

}
