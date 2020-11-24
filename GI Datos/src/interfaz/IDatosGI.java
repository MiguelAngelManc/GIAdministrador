/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import Controles.exceptions.NonexistentEntityException;
import Entidades.*;
import java.util.List;

/**
 *
 * @author Home
 */
public interface IDatosGI {
    public void iniciar() throws Exception;
    
    public List<Administrador> listaAdministradores();
    public List<Cajero> listaCajeros();
    public List<Cliente> listaClientes();
    public List<Doctor> listaDoctores();
    public List<Pago> listaPagos();
    public List<Producto> listaProductos();
    public List<ProductoVenta> listaProductoVentas();
    public List<Usuario> listaUsuarios();
    public List<Venta> listaVentas();
    
    public List<Administrador> listaParcialAdministrador(int max, int first);
    public List<Cajero> listaParcialCajeros(int max, int first);
    public List<Cliente> listaParcialClientes(int max, int first);
    public List<Doctor> listaParcialDoctores(int max, int first);
    public List<Pago> listaParcialPagos(int max, int first);
    public List<Producto> listaParcialProductos(int max, int first);
    public List<ProductoVenta> listaParcialProductoVentas(int max, int first);
    public List<Usuario> listaParcialUsuarios(int max, int first);
    public List<Venta> listaParcialVentas(int max, int first);
    
    
    public Administrador encontrarAdministrador(int id);
    public Cajero encontrarCajero(int id);
    public Cliente encontrarCliente(int id);
    public Doctor encontrarDoctor(String id);
    public Pago encontrarPago(int id);
    public Producto encontrarProducto(int id);
    public Usuario encontrarUsuario(int id);
    public Venta encontrarVenta(int id);
    public ProductoVenta encontrarProductoVenta(int id);
    
    public void agregarAdministrador(Administrador object) throws NonexistentEntityException, Exception;
    public void agregarCajero(Cajero object) throws NonexistentEntityException, Exception;
    public void agregarCliente(Cliente object) throws NonexistentEntityException, Exception;
    public void agregarDoctor(Doctor object) throws NonexistentEntityException, Exception;
    public void agregarPago(Pago object) throws NonexistentEntityException, Exception;
    public void agregarProducto(Producto object) throws NonexistentEntityException, Exception;
    public void agregarUsuario(Usuario object) throws NonexistentEntityException, Exception;
    public void agregarVenta(Venta object) throws NonexistentEntityException, Exception;
    public void agregarProductoVenta(ProductoVenta object) throws NonexistentEntityException, Exception;
    
    
    public void actualizarAdministrador(Administrador object)throws Exception;
    public void actualizarCajero(Cajero object) throws Exception;
    public void actualizarCliente(Cliente object)throws Exception;
    public void actualizarDoctor(Doctor object)throws Exception;
    public void actualizarPago(Pago object)throws Exception;
    public void actualizarProducto(Producto object)throws Exception;
    public void actualizarUsuario(Usuario object)throws Exception;
    public void actualizarVenta(Venta object)throws Exception;
    public void actualizarProductoVenta(ProductoVenta object)throws Exception;
    
    public void eliminarAdministrador(int id) throws NonexistentEntityException;
    public void eliminarCajero(int id) throws NonexistentEntityException;
    public void eliminarCliente(int id) throws NonexistentEntityException;
    public void eliminarDoctor(String id) throws NonexistentEntityException;
    public void eliminarPago(int id) throws NonexistentEntityException;
    public void eliminarProducto(int id) throws NonexistentEntityException;
    public void eliminarUsuario(int id) throws NonexistentEntityException;
    public void eliminarVenta(int id) throws NonexistentEntityException;
    public void eliminarProductoVenta(int id) throws NonexistentEntityException;
}
