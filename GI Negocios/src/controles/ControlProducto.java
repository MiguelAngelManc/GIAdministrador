/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles;

import Entidades.Producto;
import interfaz.IDatosGI;
import java.util.List;

/**
 *
 * @author Home
 */
public class ControlProducto {
    private IDatosGI datos;

    public ControlProducto(IDatosGI datos) {
        this.datos = datos;
    }
    
    public List<Producto> soliciarListaProductos() {
        List<Producto> lista = null;
        
        lista = datos.listaProductos();
        
        return lista;
    }
    
    public boolean validarProducto(Producto p) throws Exception{
        
        Producto aux = datos.encontrarProducto(p.getIdProducto());
        
        if(aux==null)
            return false;
        
        // True si existe, falso si no
        return aux.equals(p);
    }
    
    // 1 - agregar, 2 - editar, 3 - eliminar
    public void administrarProducto(int opcion, Producto p) throws Exception{
        switch(opcion){
            case 1:
                datos.agregarProducto(p);
                break;
            case 2:
                datos.actualizarProducto(p);
                break;
            case 3:
                datos.eliminarProducto(p.getIdProducto());
                break;
        }
    }
    
    
    
    
}
