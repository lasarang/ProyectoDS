/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Estudiante;

import FamiliaPersona.Estudiante;
import java.util.Map;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public interface IEstudianteDAO {

    public void create(Estudiante estudiante) throws Exception;

    public Estudiante read(int matricula) throws Exception;

    public void update(Map<String,Object> nuevosValores, int matricula) throws Exception;

    public void delete(int matricula) throws Exception;

}
