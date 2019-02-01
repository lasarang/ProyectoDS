/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Persona;

import FamiliaPersona.Persona;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public interface IPersonaDAO {

    public void create(Persona persona) throws Exception;

    public Persona read(String cedula, String rol) throws Exception;

    public void update(Map<String, Object> nuevosValores, String cedula) throws Exception;

    public void addCorreos(List<String> nuevosCorreos, String cedula) throws Exception;

    public void addTelefonos(List<String> nuevosTelefonos, String cedula) throws Exception;

    public void addDomicilios(List<String> nuevosDomicilios, String cedula) throws Exception;

}
