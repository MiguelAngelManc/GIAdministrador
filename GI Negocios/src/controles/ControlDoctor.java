/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles;

import Entidades.Doctor;
import interfaz.IDatosGI;
import java.util.List;

/**
 *
 * @author Home
 */
public class ControlDoctor {
    private IDatosGI datos;

    public ControlDoctor(IDatosGI datos) {
        this.datos = datos;
    }
    
    public List<Doctor> soliciarListaDoctores(){
        List<Doctor> lista;
        
        lista = datos.listaDoctores();
        lista.remove(new Doctor("null", "ninguno"));
        return lista;
    }
    
    public void guardarDoctor(Doctor doctor) throws Exception{
        datos.agregarDoctor(doctor);
    }
}
