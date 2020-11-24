/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import Controles.*;
import Controles.exceptions.NonexistentEntityException;
import Entidades.Administrador;
import Entidades.Cajero;
import Entidades.Cliente;
import Entidades.Doctor;
import Entidades.Pago;
import Entidades.Producto;
import Entidades.ProductoVenta;
import Entidades.Usuario;
import Entidades.Venta;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Home
 */
public class CDatosGI implements IDatosGI{
    public static CDatosGI instance;
    
    private AdministradorJpaController a;
    private CajeroJpaController ca;
    private ClienteJpaController cl;
    private DoctorJpaController d;
    private PagoJpaController pa;
    private ProductoJpaController pr;
    private ProductoVentaJpaController prv;
    private UsuarioJpaController u;
    private VentaJpaController v;

    public static CDatosGI getInstance() throws Exception{
        if(instance == null)
            instance = new CDatosGI();
        return instance;
    }
    
    private CDatosGI() throws Exception{
        iniciar();
    }
    
    @Override
    public void iniciar() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GI_DatosPU");
        a = new AdministradorJpaController(emf);
        ca = new CajeroJpaController(emf);
        cl = new ClienteJpaController(emf);
        d = new DoctorJpaController(emf);
        pa = new PagoJpaController(emf);
        pr = new ProductoJpaController(emf);
        prv = new ProductoVentaJpaController(emf);
        u = new UsuarioJpaController(emf);
        v = new VentaJpaController(emf);
    }

    @Override
    public List<Administrador> listaAdministradores() {
        return a.findAdministradorEntities();
    }

    @Override
    public List<Cajero> listaCajeros() {
        return ca.findCajeroEntities();
    }

    @Override
    public List<Cliente> listaClientes() {
        return cl.findClienteEntities();
    }

    @Override
    public List<Doctor> listaDoctores() {
        return d.findDoctorEntities();
    }

    @Override
    public List<Pago> listaPagos() {
        return pa.findPagoEntities();
    }

    @Override
    public List<Producto> listaProductos() {
        return pr.findProductoEntities();
    }

    @Override
    public List<ProductoVenta> listaProductoVentas() {
        return prv.findProductoVentaEntities();
    }

    @Override
    public List<Usuario> listaUsuarios() {
        return u.findUsuarioEntities();
    }

    @Override
    public List<Venta> listaVentas() {
        return v.findVentaEntities();
    }

    @Override
    public Administrador encontrarAdministrador(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cajero encontrarCajero(int id) {
        return ca.findCajero(id);
    }

    @Override
    public Cliente encontrarCliente(int id) {
        return cl.findCliente(id);
    }

    @Override
    public Pago encontrarPago(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Producto encontrarProducto(int id) {
        return pr.findProducto(id);
    }

    @Override
    public Usuario encontrarUsuario(int id) {
        return u.findUsuario(id);
    }

    @Override
    public Venta encontrarVenta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void agregarAdministrador(Administrador object) throws NonexistentEntityException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void agregarCajero(Cajero object) throws NonexistentEntityException, Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void agregarCliente(Cliente object) throws NonexistentEntityException, Exception{
        cl.create(object);
    }

    @Override
    public void agregarPago(Pago object) throws NonexistentEntityException, Exception{
        pa.create(object);
    }

    @Override
    public void agregarProducto(Producto object) throws NonexistentEntityException, Exception{
        pr.create(object);
    }

    @Override
    public void agregarUsuario(Usuario object) throws NonexistentEntityException, Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void agregarVenta(Venta object) throws NonexistentEntityException, Exception{
        v.create(object);
    }

    @Override
    public void actualizarAdministrador(Administrador object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarCajero(Cajero object) throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarCliente(Cliente object) throws Exception{
        cl.edit(object);
    }

    @Override
    public void actualizarPago(Pago object) throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarProducto(Producto object) throws Exception{
        pr.edit(object);
    }

    @Override
    public void actualizarUsuario(Usuario object) throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarVenta(Venta object) throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarAdministrador(int id) throws NonexistentEntityException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarCajero(int id) throws NonexistentEntityException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarCliente(int id) throws NonexistentEntityException{
        cl.destroy(id);
    }

    @Override
    public void eliminarPago(int id) throws NonexistentEntityException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarProducto(int id) throws NonexistentEntityException{
        pr.destroy(id);
    }

    @Override
    public void eliminarUsuario(int id) throws NonexistentEntityException{
        u.destroy(id);
    }

    @Override
    public void eliminarVenta(int id) throws NonexistentEntityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductoVenta encontrarProductoVenta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void agregarProductoVenta(ProductoVenta object) throws NonexistentEntityException, Exception{
        prv.create(object);
    }

    @Override
    public void actualizarProductoVenta(ProductoVenta object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarProductoVenta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Doctor encontrarDoctor(String id) {
        return d.findDoctor(id);
    }

    @Override
    public void agregarDoctor(Doctor object) throws NonexistentEntityException, Exception{
        d.create(object);
    }

    @Override
    public void actualizarDoctor(Doctor object) throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarDoctor(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Administrador> listaParcialAdministrador(int max, int first) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cajero> listaParcialCajeros(int max, int first) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> listaParcialClientes(int max, int first) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Doctor> listaParcialDoctores(int max, int first) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pago> listaParcialPagos(int max, int first) {
        return pa.findPagoEntities(max, first);
    }

    @Override
    public List<Producto> listaParcialProductos(int max, int first) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProductoVenta> listaParcialProductoVentas(int max, int first) {
        return prv.findProductoVentaEntities(max, first);
    }

    @Override
    public List<Usuario> listaParcialUsuarios(int max, int first) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Venta> listaParcialVentas(int max, int first) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
    
}
