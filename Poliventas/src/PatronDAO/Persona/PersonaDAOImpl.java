/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Persona;

import FamiliaPersona.Estudiante;
import FamiliaPersona.Persona;
import PatronSingleton.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class PersonaDAOImpl implements IPersonaDAO {

    private final Conexion conexion = Conexion.getInstancia();
    private final Connection connection = conexion.getConnection();
    private CallableStatement cs1, cs2;
    private PreparedStatement ps1;
    private ResultSet rs1, rs2;
    private List<String> lista;
    private Persona estudiante;

    @Override
    public void create(Persona persona) throws Exception {
        cs1 = connection.prepareCall("{CALL createPersona(?, ?, ?, ?, ?)}");
        cs1.setString(1, persona.getCedula());
        cs1.setString(2, persona.getNombres());
        cs1.setString(3, persona.getApellidos());
        cs1.setBoolean(4, persona.isTieneWhatsapp());
        cs1.setString(5, persona.getTelefonoActual());

        cs1.executeUpdate();
        cs1.close();

        createPersonaTelefonos(persona.getTelefonosSecundarios(), persona.getCedula());
        createPersonaCorreos(persona.getCorreos(), persona.getCedula());
        createPersonaDomicilios(persona.getDomicilios(), persona.getCedula());
    }

    private void createPersonaTelefonos(List<String> telefonos, String cedula) throws Exception {
        for (String telefono : telefonos) {

            cs1 = connection.prepareCall("{CALL createPersonaTelefono(?, ?)}");
            cs1.setString(1, cedula);
            cs1.setString(2, telefono);
            cs1.executeUpdate();
            cs1.close();
        }
    }

    private void createPersonaCorreos(List<String> correos, String cedula) throws Exception {
        for (String correo : correos) {
            cs1 = connection.prepareCall("{CALL createPersonaCorreo(?, ?)}");
            cs1.setString(1, cedula);
            cs1.setString(2, correo);
            cs1.executeUpdate();
            cs1.close();
        }
    }

    private void createPersonaDomicilios(List<String> domicilios, String cedula) throws Exception {
        for (String direccion : domicilios) {
            cs1 = connection.prepareCall("{CALL createPersonaDomicilio(?, ?)}");
            cs1.setString(1, cedula);
            cs1.setString(2, direccion);
            cs1.executeUpdate();
            cs1.close();
        }
    }

    @Override
    public Persona read(String cedula, String rol) throws Exception {

        cs1 = connection.prepareCall("{CALL readPersonaXCedula(?)}");
        cs1.setString(1, cedula);
        rs1 = cs1.executeQuery();
        rs1.next();

        estudiante = (Estudiante.getMapaRol()).get(rol);
        String nombres = rs1.getString("Nombres");
        String apellidos = rs1.getString("Apellidos");
        boolean tieneWhatsapp = rs1.getBoolean("TieneWhatsapp");
        String telefonoActual = rs1.getString("TelefonoActual");

        estudiante.setCedula(cedula);
        estudiante.setNombres(nombres);
        estudiante.setApellidos(apellidos);
        estudiante.setTieneWhatsapp(tieneWhatsapp);
        estudiante.setTelefonoActual(telefonoActual);
        estudiante.setTelefonosSecundarios(readPersonaTelefonos(cedula));
        estudiante.setCorreos(readPersonaCorreos(cedula));
        estudiante.setDomicilios(readPersonaDomicilios(cedula));

        rs1.close();
        cs1.close();
        return estudiante;
    }

    private List<String> readPersonaTelefonos(String cedula) throws Exception {
        lista = new ArrayList<>();

        cs2 = connection.prepareCall("{CALL readCedulaTelefonos(?)}");
        cs2.setString(1, cedula);
        rs2 = cs2.executeQuery();

        while (rs2.next()) {
            lista.add(rs2.getString("Telefono"));
        }

        rs2.close();
        cs2.close();
        return lista;
    }

    private List<String> readPersonaCorreos(String cedula) throws Exception {
        lista = new ArrayList<>();
        cs2 = connection.prepareCall("{CALL readCedulaCorreos(?)}");
        cs2.setString(1, cedula);
        rs2 = cs2.executeQuery();

        while (rs2.next()) {
            lista.add(rs2.getString("Correo"));
        }

        rs2.close();
        cs2.close();
        return lista;
    }

    private List<String> readPersonaDomicilios(String cedula) throws Exception {
        lista = new ArrayList<>();
        cs2 = connection.prepareCall("{CALL readCedulaDomicilios(?)}");
        cs2.setString(1, cedula);
        rs2 = cs2.executeQuery();

        while (rs2.next()) {
            lista.add(rs2.getString("Direccion"));
        }

        rs2.close();
        cs2.close();
        return lista;
    }

    @Override
    public void update(Map<String, Object> nuevosValores, String cedula) throws Exception {
        for (String columna : nuevosValores.keySet()) {
            ps1 = connection.prepareStatement("UPDATE Personas SET " + columna + "=? WHERE ?=Cedula");
            ps1.setObject(1, nuevosValores.get(columna));
            ps1.setString(2, cedula);
            ps1.executeUpdate();
            ps1.close();
            System.out.println("Actualizacion exitosa de datos de la Persona!");
        }

    }

    @Override
    public void addCorreos(List<String> nuevosCorreos, String cedula) throws Exception {
        for (String correo : nuevosCorreos) {
            ps1 = connection.prepareStatement("INSERT INTO PersonasCorreos(idPersonEmail, Correo) VALUES(?,?)");
            ps1.setString(1, cedula);
            ps1.setString(2, correo);
            ps1.executeUpdate();
            ps1.close();
            System.out.println("Se han agregado exitosamente nuevos correos!");
        }
    }

    @Override
    public void addTelefonos(List<String> nuevosTelefonos, String cedula) throws Exception {
        for (String telefono : nuevosTelefonos) {
            ps1 = connection.prepareStatement("INSERT INTO PersonasTelefonos(idPersonPhone, Telefono) VALUES(?,?)");
            ps1.setString(1, cedula);
            ps1.setString(2, telefono);
            ps1.executeUpdate();
            ps1.close();
            System.out.println("Se han agregado exitosamente nuevos telefonos!");
        }
    }

    @Override
    public void addDomicilios(List<String> nuevosDomicilios, String cedula) throws Exception {
        for (String direccion : nuevosDomicilios) {
            ps1 = connection.prepareStatement("INSERT INTO PersonasDomicilios(idPersonHome, Direccion) VALUES(?,?)");
            ps1.setString(1, cedula);
            ps1.setString(2, direccion);
            ps1.executeUpdate();
            ps1.close();
            System.out.println("Se han agregado exitosamente nuevos domicilios!");
        }
    }

}
