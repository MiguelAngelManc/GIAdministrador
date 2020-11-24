/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import Entidades.*;
import controles.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class CNegociosGI implements INegociosGI {
    public static CNegociosGI instance;
    
    private IDatosGI datos;
    private ControlCliente cl;
    private ControlDoctor d;
    private ControlProducto pr;
    private ControlVenta v;
    // Tambien van los otros pero creo que nomas ocupamos estos por ahora? ya que solo estamos haciendo un CU

    private CNegociosGI() throws Exception{
        this.datos = CDatosGIFactory.buildDatos();
        cl = new ControlCliente(datos);
        d = new ControlDoctor(datos);
        pr = new ControlProducto(datos);
        v = new ControlVenta(datos);
        // Tambien van los otros pero creo que nomas ocupamos estos por ahora? ya que solo estamos haciendo un CU
        
    }
    
    public static CNegociosGI getInstance() throws Exception{
        if(instance == null)
            instance = new CNegociosGI();
        return instance;
    }
    
    
    
    
    @Override
    public List<Cliente> soliciarListaClientes() {
        return cl.soliciarListaClientes();
    }

    @Override
    public List<Producto> soliciarListaProductos() {
        return pr.soliciarListaProductos();
    }

    @Override
    public int verificarVenta(DefaultTableModel tabla, float importe, float moneda, float tarjeta) {
        return v.verificarVenta(tabla, importe, moneda, tarjeta);
    }

    @Override
    public int validarVentaDoctor(String RFC, String nombre, boolean nuevo) throws Exception{
        return v.validarVentaDoctor(RFC, nombre, nuevo);
    }

    @Override
    public void guardarVenta(DefaultTableModel tabla, float importe, float moneda, float tarjeta,
            int idCliente, int idUsuario, String RFC) throws Exception {
        v.guardarVenta(tabla, importe, moneda, tarjeta, idCliente, idUsuario, RFC);
    }

    @Override
    public List<Doctor> soliciarListaDoctores() {
        return d.soliciarListaDoctores();
    }

    @Override
    public void guardarDoctor(Doctor doctor) throws Exception {
        d.guardarDoctor(doctor);
    }

    @Override
    public boolean validarCliente(Cliente c) throws Exception{
        return cl.validarCliente(c);
    }

    @Override
    public void administrarCliente(int opcion, Cliente c) throws Exception{
        cl.administrarCliente(opcion, c);
    }

    @Override
    public boolean validarProducto(Producto p) throws Exception{
        return pr.validarProducto(p);
    }

    @Override
    public void administrarProducto(int opcion, Producto p) throws Exception{
        pr.administrarProducto(opcion, p);
    }
    
    
    
    
    
}
