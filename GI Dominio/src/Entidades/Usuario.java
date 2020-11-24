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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author Aldeco
 */
@Entity
@Table (name = "usuarios")
@Inheritance (strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {

    @Id
    @Column (name = "IdUsuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(name = "Nombre", nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "Nickname", nullable = false, length = 30)
    private String nickname;
    
    @Column(name = "Contrasena", nullable = false, length = 100)
    private String contrasena;

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String nickname, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.nickname = nickname;
        this.contrasena = contrasena;
    }

    public Usuario(String nombre, String nickname, String contrasena) {
        this.nombre = nombre;
        this.nickname = nickname;
        this.contrasena = contrasena;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.idUsuario;
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
        final Usuario other = (Usuario) obj;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "IdUsuario: " + this.idUsuario + ", Nombre: " + this.nombre + ", Nickname: " + 
                this.nickname + ", Contraseña: " + this.contrasena;
    }
    
}
