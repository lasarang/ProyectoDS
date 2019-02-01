/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PatronFabricaAbstracta;

import FamiliaOperacion.Operacion;
import FamiliaPersona.Persona;

/**
 * 
 * @author Luis A. Sarango-Parrales
 */
public interface FabricaAbstracta {
    public Persona getPersona(String persona);
    public Operacion getOperacion(String operacion);
}
