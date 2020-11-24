/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import Entidades.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public interface INegociosGI {
    
    
    // Cliente
    public List<Cliente> soliciarListaClientes();
    public boolean validarCliente(Cliente c) throws Exception;
    public void administrarCliente(int opcion, Cliente c) throws Exception;
    
    
    // Producto
    public List<Producto> soliciarListaProductos();
    public boolean validarProducto(Producto p) throws Exception;
    public void administrarProducto(int opcion, Producto p) throws Exception;
    
    
    // Doctor
    public List<Doctor> soliciarListaDoctores();
    public void guardarDoctor(Doctor doctor) throws Exception;
    
    
    // Venta
    public int verificarVenta(DefaultTableModel tabla, float importe, float moneda, float tarjeta);
    public int validarVentaDoctor(String RFC, String nombre, boolean nuevo) throws Exception;
    public void guardarVenta(DefaultTableModel tabla, float importe, float moneda, float tarjeta,
            int idCliente, int idUsuario, String RFC) throws Exception;
    
    
    
}
