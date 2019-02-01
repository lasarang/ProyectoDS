/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronDAO.Estudiante;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public interface ILoginDAO {

    public boolean authenticarEstudiante(String usuario, String pwd) throws Exception;

}
