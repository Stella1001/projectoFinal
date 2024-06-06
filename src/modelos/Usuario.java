package modelos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
public class Usuario implements Serializable{

    String tipoCuenta;
    String nombre;
    String contrasena;
    String email;
    String genero;
    boolean comparteDatos;

    public String getTipoCuenta() {
        return tipoCuenta;
    }
    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getEmail() {
        return email;
    }

    public String getGenero() {
        return genero;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    public boolean isComparteDatos() {
        return comparteDatos;
    }

    public void setComparteDatos(boolean comparteDatos) {
        this.comparteDatos = comparteDatos;
    }

    @JsonCreator
    public Usuario(@JsonProperty("tipoCuenta") String tipoCuenta,
                   @JsonProperty("nombre") String nombre,
                   @JsonProperty("contrasena") String contrasena,
                   @JsonProperty("email") String email,
                   @JsonProperty("genero") String genero,
                   @JsonProperty("comparteDatos") boolean comparteDatos) {
        this.tipoCuenta = tipoCuenta;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.email = email;
        this.genero = genero;
        this.comparteDatos = comparteDatos;
    }
    public String toString(){

        return tipoCuenta + ", " + nombre + ", " + contrasena + ", " + email + ", " + genero;
    }
}
