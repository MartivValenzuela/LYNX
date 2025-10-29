package modelo;

import utilidades.Rut;

public abstract class Persona {
    private Rut rut;
    private String nombre;
    private String email;
    private String direccion;

    public Persona(Rut rut, String nom, String email, String dir) {
        this.rut = rut;
        this.nombre = nom;
        this.email = email;
        this.direccion = dir;
    }

    public Rut getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;

    }
}