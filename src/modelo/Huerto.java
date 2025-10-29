package modelo;

import utilidades.EstadoFonologico;

import java.util.ArrayList;

public class Huerto {
    private String nombre;
    private float superficie;
    private String ubicacion;
    private ArrayList<Cuartel> cuarteles;
    private Propietario propietario;

    public Huerto(String nom, float sup, String ubi, Propietario prop) {
        this.nombre = nom;
        this.superficie = sup;
        this.ubicacion = ubi;
        this.propietario = prop;
        this.cuarteles = new ArrayList<>();

        if (prop != null) {
            prop.addHuerto(this);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Persona getPropietario() {
        return propietario;
    }

    public void setPropietario(Persona propietario) {
        this.propietario = propietario;
    }

    public boolean addCuartel(int id, float sup, Cultivo cult) {
        if (cult == null) {
            return false;
        }
        if (getCuartelById(id) != null) {
            return false;
        }
        Cuartel c = new Cuartel(id, sup, cult, this);
        cuarteles.add(c);
        return true;
    }

    public Cuartel getCuartel(int id) {
        return getCuartelById(id);
    }

    public Cuartel[] getCuarteles() {
        Cuartel[] resultado = new Cuartel[cuarteles.size()];
        for (int i = 0; i < cuarteles.size(); i++) {
            resultado[i] = cuarteles.get(i);
        }
        return resultado;
    }

    public void setEstadoCuartel(int id, EstadoFonologico estado){
        this.estadoCuartel = estadoCuartel;
    }

}