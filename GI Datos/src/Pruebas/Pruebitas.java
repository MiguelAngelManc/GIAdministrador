/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pruebas;

import Controles.PagoJpaController;
import Controles.ProductoJpaController;
import Controles.ProductoVentaJpaController;
import Controles.UsuarioJpaController;
import Controles.VentaJpaController;
import Entidades.Administrador;
import Entidades.Cajero;
import Entidades.Cliente;
import Entidades.Doctor;
import Entidades.Pago;
import Entidades.Producto;
import Entidades.ProductoVenta;
import Entidades.TipoPago;
import Entidades.Usuario;
import Entidades.Venta;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Aldeco
 */
public class Pruebitas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emFactory
                = Persistence.createEntityManagerFactory("GI_DatosPU");
        EntityManager entityManager = emFactory.createEntityManager();
        
//        System.out.println("pruebas de gets");
//        System.out.println("administradores y cajeros:");
//        System.out.println(entityManager.find(Administrador.class, 1));
//        System.out.println(entityManager.find(Cajero.class, 3));
//        System.out.println(entityManager.find(Administrador.class, 2));
//        System.out.println(entityManager.find(Cajero.class, 4));
//        System.out.println("clientes");
//        System.out.println(entityManager.find(Cliente.class, 1));
//        System.out.println(entityManager.find(Cliente.class, 2));
//        System.out.println(entityManager.find(Cliente.class, 3));
//        System.out.println("doctores:");
//        System.out.println(entityManager.find(Doctor.class, "KASDKR"));
//        System.out.println(entityManager.find(Doctor.class, "ASDKASK"));
//        
//        UsuarioJpaController u = new UsuarioJpaController(emFactory);
//        //u.create(new Usuario(5, "miguel", "pruebin", "pruebadones"));
//        
//        VentaJpaController v = new VentaJpaController(emFactory);
//        Venta v2 = v.findVenta(1);
//        System.out.println(v2);
//        
//        PagoJpaController p = new PagoJpaController(emFactory);
//        ProductoVentaJpaController pv = new ProductoVentaJpaController(emFactory);
        ProductoJpaController pr = new ProductoJpaController(emFactory);
        
//        
//        // Obtiene el cliente
//        Cliente cliente = entityManager.getReference(Cliente.class, 1);
//        
//        // Obtiene el usuario
//        Usuario usuario = entityManager.getReference(Administrador.class, 1);
//        
//        // Obtiene el doctor
//        Doctor doctor = entityManager.getReference(Doctor.class, "null");
//        
//        // Obtiene la fecha
//        Date fecha = Calendar.getInstance().getTime();
//        
//        float importe = 500f;
//        
//        Venta aux = new Venta(cliente, usuario, doctor, fecha, importe);
//        
//        
//        List<ProductoVenta> productoVenta = new ArrayList();
//        Producto producto1 = pr.findProducto(1);
//        Producto producto2 = pr.findProducto(2);
//        Producto producto3 = pr.findProducto(3);
//        ProductoVenta pv1 = new ProductoVenta(producto1, null, 1, 300);
//        ProductoVenta pv2 = new ProductoVenta(producto2, null, 2, 200);
//        ProductoVenta pv3 = new ProductoVenta(producto3, null, 4, 600);
//        productoVenta.add(pv1);
//        productoVenta.add(pv2);
//        productoVenta.add(pv3);
//        
//        int antes = pv.getProductoVentaCount();
//        int c = 0;
//        for(ProductoVenta pvv : productoVenta){
//            c++;
//            pv.create(pvv);
//        }
//        
//        List<ProductoVenta> nueva = pv.findProductoVentaEntities(c, antes);
//        aux.setProductoVenta(nueva);
//        
//        
//        // Otiene los pagos
//        List<Pago> pagos = new ArrayList<>();
//        Pago pago1 = new Pago(null,TipoPago.EFECTIVO, 300f);
//        Pago pago2 = new Pago(null,TipoPago.TARJETA_CREDITO, 200f);
//        pagos.add(pago1);
//        pagos.add(pago2);
//        
//        int antesP = p.getPagoCount();
//        int cP = 0;
//        for(Pago ppp : pagos){
//            cP++;
//            p.create(ppp);
//        }
//        
//        List<Pago> nuevaP = p.findPagoEntities(cP, antesP);
//        aux.setPagos(nuevaP);
//        
//        v.create(aux);
        
    }

}
