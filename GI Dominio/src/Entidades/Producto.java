/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Aldeco
 */
@Entity
@Table(name = "productos")
public class Producto implements Serializable {
    
    @Id
    @Column(name = "IdProducto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;
    
    @Column(name = "Nombre", nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "Precio", nullable = false)
    private float precio;
    
    @Column(name = "Contenido")
    private String contenido;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Clasificacion", nullable = false, length = 50)
    private Clasificacion clasificacion;
    
    @OneToMany(mappedBy = "producto")
    private List<ProductoVenta> productoVenta;

    public Producto() {
        productoVenta = new ArrayList();
    }

    public Producto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(String nombre, float precio, String contenido, Clasificacion clasificacion) {
        this.nombre = nombre;
        this.precio = precio;
        this.contenido = contenido;
        this.clasificacion = clasificacion;
    }
    
    

    public Producto(int idProducto, String nombre, float precio, String contenido, Clasificacion clasificacion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.contenido = contenido;
        this.clasificacion = clasificacion;
    }

    public Producto(int idProducto, String nombre, float precio, Clasificacion clasificacion) {
        this();
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.clasificacion = clasificacion;
    }

    public Producto(String nombre, float precio, Clasificacion clasificacion, List<ProductoVenta> productoVenta) {
        this.nombre = nombre;
        this.precio = precio;
        this.clasificacion = clasificacion;
        this.productoVenta = productoVenta;
    }

    public Producto(int idProducto, String nombre, float precio, Clasificacion clasificacion, List<ProductoVenta> productoVenta) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.clasificacion = clasificacion;
        this.productoVenta = productoVenta;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public List<ProductoVenta> getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(List<ProductoVenta> productoVenta) {
        this.productoVenta = productoVenta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.idProducto;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        if(idProducto==-1)
            return "Seleccione un producto.";
        return ""+idProducto+"    |     "+nombre;
    }
    
}
