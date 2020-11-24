/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Aldeco
 */
@Entity
@Table (name = "ventas")
public class Venta implements Serializable {
    
    
    @Id
    @Column (name = "IdVenta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVenta;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "IdCliente")
    private Cliente cliente;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "RFC")
    private Doctor doctor;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Fecha", nullable = false)
    private Date fecha;
    
    @Column(name = "Importe", nullable = false)
    private float importe;
    
    @OneToMany(mappedBy = "venta")
    private List<Pago> pagos;
    
    @OneToMany(mappedBy = "venta")
    private List<ProductoVenta> productoVenta;
    
    
    
    

    public Venta() {
        productoVenta = new ArrayList();
        pagos = new ArrayList<>();
    }

    public Venta(Cliente cliente, Usuario usuario, Doctor doctor, List<Pago> pagos, List<ProductoVenta> productoVenta, Date fecha, float importe) {
        this.cliente = cliente;
        this.usuario = usuario;
        this.doctor = doctor;
        this.pagos = pagos;
        this.productoVenta = productoVenta;
        this.fecha = fecha;
        this.importe = importe;
    }

    public Venta(int idVenta, Cliente cliente, Usuario usuario, Doctor doctor, List<Pago> pagos, List<ProductoVenta> productoVenta, Date fecha, float importe) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.usuario = usuario;
        this.doctor = doctor;
        this.pagos = pagos;
        this.productoVenta = productoVenta;
        this.fecha = fecha;
        this.importe = importe;
    }

    public Venta(Cliente cliente, Usuario usuario, Doctor doctor, Date fecha, float importe) {
        this();
        this.cliente = cliente;
        this.usuario = usuario;
        this.doctor = doctor;
        this.fecha = fecha;
        this.importe = importe;
    }

    public Venta(int idVenta, Cliente cliente, Usuario usuario, Doctor doctor, Date fecha, float importe) {
        this();
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.usuario = usuario;
        this.doctor = doctor;
        this.fecha = fecha;
        this.importe = importe;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public List<ProductoVenta> getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(List<ProductoVenta> productoVenta) {
        this.productoVenta = productoVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idVenta);
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
        final Venta other = (Venta) obj;
        if (!Objects.equals(this.idVenta, other.idVenta)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    @Override
    public String toString() {
        String pagosS = "";
        for(int i=0;i<pagos.size();i++){
            pagosS+=""+pagos.get(i)+"\n";
        }
        String productoS = "";
        for(int i=0;i<productoVenta.size();i++){
            productoS+=""+productoVenta.get(i)+"\n";
        }
        
        return "IdVenta: " + this.idVenta + ", Fecha: " + this.fecha.toString() + ", Importe: " + this.importe +
                "\n Doctor: "+doctor+"\n Productos: "+productoS+"\n Pagos: "+pagosS;
    }
    
}
    
