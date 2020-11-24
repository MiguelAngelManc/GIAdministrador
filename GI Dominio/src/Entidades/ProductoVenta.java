/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Aldeco
 */
@Entity
@Table (name = "productosventas")
public class ProductoVenta implements Serializable {
    
    @Id
    @Column (name = "IdProductoVenta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProductoVenta;
    
    @ManyToOne
    @JoinColumn(name = "IdProducto")
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "IdVenta")
    private Venta venta;
    
    @Column(name = "Cantidad", nullable = false)
    private int cantidad;
    
    @Column(name = "Importe", nullable = false)
    private float importe;

    public ProductoVenta() {}

    public ProductoVenta(int idProductoVenta, Producto producto, Venta venta, int cantidad, float importe) {
        this.idProductoVenta = idProductoVenta;
        this.producto = producto;
        this.venta = venta;
        this.cantidad = cantidad;
        this.importe = importe;
    }

    public ProductoVenta(Producto producto, Venta venta, int cantidad, float importe) {
        this.producto = producto;
        this.venta = venta;
        this.cantidad = cantidad;
        this.importe = importe;
    }

    public int getIdProductoVenta() {
        return idProductoVenta;
    }

    public void setIdProductoVenta(int idProductoVenta) {
        this.idProductoVenta = idProductoVenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.idProductoVenta);
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
        final ProductoVenta other = (ProductoVenta) obj;
        if (!Objects.equals(this.idProductoVenta, other.idProductoVenta)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "IdProductoVenta: " + this.idProductoVenta + ", IdProducto: " + this.producto.getIdProducto() + 
                ", IdVenta: " + this.venta.getIdVenta() + ", Importe: " + this.importe;
    }
    
}
