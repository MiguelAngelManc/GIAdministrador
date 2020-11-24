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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table (name = "pagos")
public class Pago implements Serializable {
    
    @Id
    @Column (name = "IdPago")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPago;
    
    @ManyToOne
    @JoinColumn(name="IdVenta")
    private Venta venta;

    @Enumerated(EnumType.STRING)
    @Column(name = "TipoPago", nullable = false, length = 50)
    private TipoPago tipoPago;
    
    @Column(name = "Monto", nullable = false)
    private float monto;

    public Pago() {}

    public Pago(Venta venta, TipoPago tipoPago, float monto) {
        this.venta = venta;
        this.tipoPago = tipoPago;
        this.monto = monto;
    }

    public Pago(int idPago, Venta venta, TipoPago tipoPago, float monto) {
        this.idPago = idPago;
        this.venta = venta;
        this.tipoPago = tipoPago;
        this.monto = monto;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.idPago);
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
        final Pago other = (Pago) obj;
        if (!Objects.equals(this.idPago, other.idPago)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pago{" + "idPago=" + idPago + ", venta=" + venta.getIdVenta() + ", tipoPago=" + tipoPago + ", monto=" + monto + '}';
    }
    
    
    
    
}
    

