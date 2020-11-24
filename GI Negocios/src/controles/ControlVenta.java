/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles;

import Entidades.*;
import interfaz.IDatosGI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class ControlVenta {
    private IDatosGI datos;
    
    public ControlVenta(IDatosGI datos){
        this.datos = datos;
    }
    
    public int verificarVenta(DefaultTableModel tabla, float importe, float moneda, float tarjeta){
        boolean ventaConDoctor = false;
        
        // Si no hay productos
        if(tabla.getRowCount()==0)
            return -1;
        
        
        // Itera por los productos vendidos
        for(int i = 0; i<tabla.getRowCount();i++){
            try{
                int id = Integer.parseInt(tabla.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(tabla.getValueAt(i, 2).toString());
                
                // Verifica que el producto exista
                Producto aux = datos.encontrarProducto(id);
                if(aux==null)
                    return -1;
                if(aux.getClasificacion().equals(Clasificacion.ANALGESICO) || aux.getClasificacion().equals(Clasificacion.ANTIBIOTICO))
                    ventaConDoctor = true;

                // Verifica que es una cantidad mayor a cero
                if (cantidad<1)
                    return -1;
            }catch(NumberFormatException e){
                // Si hay cosas que no son numeros
                return -1;
            }
        }
        // Verifica que los cobros cubran el importe
        if(moneda+tarjeta<importe)
            return -1;
        
        // -1 = mal
        // 0 = bien
        // 1 = con doctor
        if(ventaConDoctor)
            return 1;
        return 0;
    }
    
    public int validarVentaDoctor(String RFC, String nombre, boolean nuevo) throws Exception{
        if(!nuevo){
            // Verifica que el doctor exista
            Doctor aux = datos.encontrarDoctor(RFC);
            if(aux==null)
                return -1;
            return 0;
        }else{
            Doctor aux = new Doctor(RFC, nombre);
            datos.agregarDoctor(aux);
        }
        return 0;
    }
    
    public void guardarVenta(DefaultTableModel tabla, float importe, float moneda, float tarjeta,
            int idCliente, int idUsuario, String RFC) throws Exception{
        
        // Obtiene el cliente
        Cliente cliente = datos.encontrarCliente(idCliente);
        
        // Obtiene el usuario
        Usuario usuario = datos.encontrarUsuario(idUsuario);
        
        // Obtiene el doctor
        Doctor doctor = datos.encontrarDoctor(RFC);
        
        // Obtiene la fecha
        Date fecha = Calendar.getInstance().getTime();
        
        
        
        Venta aux = new Venta(cliente, usuario, doctor, fecha, importe);
        
        
        List<ProductoVenta> productoVenta = new ArrayList();
        // Itera sobre la tabla para crear los productoVenta
        for(int i=0;i<tabla.getRowCount();i++){
            int id = Integer.parseInt(tabla.getValueAt(i, 0).toString());
            int cantidad = Integer.parseInt(tabla.getValueAt(i, 2).toString());
            float importeP = Float.parseFloat(tabla.getValueAt(i, 4).toString());
            
            // Obtiene el producto
            Producto producto = datos.encontrarProducto(id);
                if(aux==null)
                    throw new Exception("Producto no existente");
                
            productoVenta.add(new ProductoVenta(producto, null, cantidad, importeP));
        }
        
        // Otiene los pagos
        List<Pago> pagos = new ArrayList<>();
        pagos.add(new Pago(null,TipoPago.EFECTIVO, moneda));
        pagos.add(new Pago(null,TipoPago.TARJETA_CREDITO, tarjeta));
        
        
        // Agrega los productosVenta a la base de datos y guarda una referencia
        int antesPV = datos.listaProductoVentas().size();
        int cPV = 0;
        for(ProductoVenta pvv : productoVenta){
            cPV++;
            datos.agregarProductoVenta(pvv);
        }
        
        List<ProductoVenta> nuevaPV = datos.listaParcialProductoVentas(cPV, antesPV);
        
        // Agrega los pagos a la base de datos y guarda una referencia
        int antesPago = datos.listaProductoVentas().size();
        int cPago = 0;
        for(Pago ppp : pagos){
            cPago++;
            datos.agregarPago(ppp);
        }
        
        List<Pago> nuevaPago = datos.listaParcialPagos(cPago, antesPago);
        
        
        aux.setPagos(nuevaPago);
        aux.setProductoVenta(nuevaPV);
        
        
        
        datos.agregarVenta(aux);
    }
    
    
}
