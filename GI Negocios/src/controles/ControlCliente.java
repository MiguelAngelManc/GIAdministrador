/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles;

import Entidades.Cliente;
import interfaz.IDatosGI;
import java.util.List;

/**
 *
 * @author Home
 */
public class ControlCliente {
    private IDatosGI datos;

    public ControlCliente(IDatosGI datos) {
        this.datos = datos;
    }
    
    public List<Cliente> soliciarListaClientes(){
        List<Cliente> lista = null;
        
        lista = datos.listaClientes();
        
        return lista;
    }
    
    public boolean validarCliente(Cliente c) throws Exception{
        
        Cliente aux = datos.encontrarCliente(c.getIdCliente());
        
        if(aux==null)
            return false;
        
        // True si existe, falso si no
        return aux.equals(c);
    }
    
    // 1 - agregar, 2 - editar, 3 - eliminar
    public void administrarCliente(int opcion, Cliente c) throws Exception{
        switch(opcion){
            case 1:
                datos.agregarCliente(c);
                break;
            case 2:
                datos.actualizarCliente(c);
                break;
            case 3:
                datos.eliminarCliente(c.getIdCliente());
                break;
        }
    }
    
}
