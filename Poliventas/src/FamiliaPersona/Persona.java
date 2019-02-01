/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FamiliaPersona;

import java.util.List;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public abstract class Persona {

    protected String cedula, nombres, apellidos, telefonoActual;
    protected boolean tieneWhatsapp;
    protected List<String> correos, domicilios, telefonosSecundarios;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefonoActual() {
        return telefonoActual;
    }

    public void setTelefonoActual(String telefonoActual) {
        this.telefonoActual = telefonoActual;
    }

    public boolean isTieneWhatsapp() {
        return tieneWhatsapp;
    }

    public void setTieneWhatsapp(boolean tieneWhatsapp) {
        this.tieneWhatsapp = tieneWhatsapp;
    }

    public List<String> getCorreos() {
        return correos;
    }

    public void setCorreos(List<String> correos) {
        this.correos = correos;
    }

    public List<String> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(List<String> domicilios) {
        this.domicilios = domicilios;
    }

    public List<String> getTelefonosSecundarios() {
        return telefonosSecundarios;
    }

    public void setTelefonosSecundarios(List<String> telefonosSecundarios) {
        this.telefonosSecundarios = telefonosSecundarios;
    }

}
