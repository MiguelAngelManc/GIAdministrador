/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import Entidades.Clasificacion;
import Entidades.Cliente;
import Entidades.Doctor;
import Entidades.Producto;
import Entidades.Venta;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Home
 */
public class INegociosGITest {
    INegociosGI instance;
    
    public INegociosGITest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try{
            instance = CNegociosGIFactory.buildDatos();
        }catch(Exception e){
            fail(e.getMessage());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of soliciarListaClientes method, of class INegociosGI.
     */
    @Test
    public void testSoliciarListaClientes() {
        System.out.println("soliciarListaClientes");
        List<Cliente> expResult = null;
        List<Cliente> result = instance.soliciarListaClientes();
        assertNotEquals(expResult, result);
        for(int i=0;i<result.size();i++){
            System.out.println(result.get(i).toString());
        }
    }

    /**
     * Test of soliciarListaProductos method, of class INegociosGI.
     */
    @Test
    public void testSoliciarListaProductos() {
        System.out.println("soliciarListaProductos");
        List<Producto> expResult = null;
        List<Producto> result = instance.soliciarListaProductos();
        assertNotEquals(expResult, result);
        for(int i=0;i<result.size();i++){
            System.out.println(result.get(i).toString());
        }
    }

    /**
     * Test of verificarVenta method, of class INegociosGI.
     */
    @Test
    public void testVerificarVenta() {
        System.out.println("verificarVenta");
        int id = 0;
        int cantidad = 0;
        double importe = 0.0;
        double moneda = 0.0;
        double tarjeta = 0.0;
        int expResult = 1;
        
        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn(null);
        tabla.addColumn(null);
        tabla.addColumn(null);
        tabla.addRow(new Object[]{"1","","5"});
        tabla.addRow(new Object[]{"1","","5"});
        tabla.addRow(new Object[]{"1","","5"});
        
        int result = instance.verificarVenta(tabla, 500f, 300f, 200f);
        assertEquals(expResult, result);
    }

    /**
     * Test of validarVentaDoctor method, of class INegociosGI.
     */
    @Test
    public void testValidarVentaDoctor() throws Exception {
        System.out.println("validarVentaDoctor");
        String RFC = "null";
        String nombre = "ninguno";
        boolean nuevo = false;
        int expResult = 0;
        int result = instance.validarVentaDoctor(RFC, nombre, nuevo);
        assertEquals(expResult, result);
    }

    /**
     * Test of guardarVenta method, of class INegociosGI.
     */
    @Test
    public void testGuardarVenta() throws Exception{
        System.out.println("guardarVenta");
        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn(null);
        tabla.addColumn(null);
        tabla.addColumn(null);
        tabla.addColumn(null);
        tabla.addColumn(null);
        tabla.addRow(new Object[]{"1","","5","","50"});
        tabla.addRow(new Object[]{"1","","5","","50"});
        tabla.addRow(new Object[]{"1","","5","","50"});
        instance.guardarVenta(tabla, 500f, 300f, 200f, 1, 1, "null");
    }

    /**
     * Test of validarCliente method, of class INegociosGI.
     */
    @Test
    public void testValidarCliente() throws Exception {
        System.out.println("validarCliente");
        Cliente c = new Cliente(1);
        boolean expResult = true;
        boolean result = instance.validarCliente(c);
        assertEquals(expResult, result);
        c = new Cliente(-555);
        expResult = false;
        result = instance.validarCliente(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of administrarCliente method, of class INegociosGI.
     */
    @Test
    public void testAdministrarCliente() throws Exception {
        System.out.println("administrarCliente");
        int opcion = 1;
        Cliente c = new Cliente("s", "s", "s", "s", 0, 0, "s", "s", 0);
        try{
            instance.administrarCliente(opcion, c);
            c = new Cliente("s", "s", "s", "s", 0, 0, "s", "s", 0);
            instance.administrarCliente(2, c);
            instance.administrarCliente(3, c);
        }catch(Exception e){
            fail(e.getMessage());
        }
    }

    /**
     * Test of validarProducto method, of class INegociosGI.
     */
    @Test
    public void testValidarProducto() throws Exception {
        System.out.println("validarProducto");
        Producto p = new Producto(1);
        boolean expResult = true;
        boolean result = instance.validarProducto(p);
        assertEquals(expResult, result);
        p = new Producto(-555);
        expResult = false;
        result = instance.validarProducto(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of administrarProducto method, of class INegociosGI.
     */
    @Test
    public void testAdministrarProducto() throws Exception {
        System.out.println("administrarProducto");
        int opcion = 1;
        Producto p = new Producto(12, "prueba", 999f , Clasificacion.PLACEBO);
        try{
            instance.administrarProducto(opcion, p);
            p = new Producto(12, "sss", 111f , Clasificacion.ANTIBIOTICO);
            instance.administrarProducto(2, p);
            instance.administrarProducto(3, p);
        }catch(Exception e){
            fail(e.getMessage());
        }
    }

    /**
     * Test of soliciarListaDoctores method, of class INegociosGI.
     */
    @Test
    public void testSoliciarListaDoctores() {
        System.out.println("soliciarListaDoctores");
        List<Doctor> expResult = null;
        List<Doctor> result = instance.soliciarListaDoctores();
        if(result.isEmpty())
            fail("Lista no recuperada");
    }

    /**
     * Test of guardarDoctor method, of class INegociosGI.
     */
    @Test
    public void testGuardarDoctor() throws Exception {
//        System.out.println("guardarDoctor");
//        Doctor doctor = new Doctor("PRUEBADON2", "mr. pruebas");
//        try{
//            instance.guardarDoctor(doctor);
//        }catch(Exception e){
//            fail(e.getMessage());
//        }
    }

    
}
