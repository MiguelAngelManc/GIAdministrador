/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Aldeco
 */
@Entity
@Table (name = "clientes")
public class Cliente implements Serializable {
    
    
    @Id
    @Column (name = "IdCliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    @Column(name = "Nombre", nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "Correo", nullable = false, length = 50)
    private String correo;
    
    @Column(name = "Telefono", nullable = false, length = 50)
    private String telefono;
    
    @Column(name = "DirCalle", nullable = false, length = 50)
    private String dirCalle;
    
    @Column(name = "DirInt", nullable = false)
    private int dirInt;
    
    @Column(name = "DirExt", nullable = false)
    private int dirExt;
    
    @Column(name = "DirColonia", nullable = false, length = 50)
    private String dirColonia;
    
    @Column(name = "DirEntre", nullable = false, length = 50)
    private String dirEntre;
    
    @Column(name = "DirCP", nullable = false)
    private int dirCP;

    public Cliente() {
    }

    public Cliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(String nombre, String correo, String telefono, String dirCalle, int dirInt, int dirExt, String dirColonia, String dirEntre, int dirCP) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.dirCalle = dirCalle;
        this.dirInt = dirInt;
        this.dirExt = dirExt;
        this.dirColonia = dirColonia;
        this.dirEntre = dirEntre;
        this.dirCP = dirCP;
    }

    

    public Cliente(int idCliente, String nombre) {
        this.idCliente = idCliente;
        this.nombre = nombre;
    }

    public String getDirCalle() {
        return dirCalle;
    }

    public void setDirCalle(String dirCalle) {
        this.dirCalle = dirCalle;
    }

    public int getDirInt() {
        return dirInt;
    }

    public void setDirInt(int dirInt) {
        this.dirInt = dirInt;
    }

    public int getDirExt() {
        return dirExt;
    }

    public void setDirExt(int dirExt) {
        this.dirExt = dirExt;
    }

    public String getDirColonia() {
        return dirColonia;
    }

    public void setDirColonia(String dirColonia) {
        this.dirColonia = dirColonia;
    }

    public String getDirEntre() {
        return dirEntre;
    }

    public void setDirEntre(String dirEntre) {
        this.dirEntre = dirEntre;
    }

    public int getDirCP() {
        return dirCP;
    }

    public void setDirCP(int dirCP) {
        this.dirCP = dirCP;
    }
    
    

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idCliente;
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
        final Cliente other = (Cliente) obj;
        if (this.idCliente != other.idCliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
    
}
