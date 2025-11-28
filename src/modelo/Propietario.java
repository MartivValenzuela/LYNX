package modelo;

import utilidades.Persona;
import utilidades.Rut;

import java.util.ArrayList;

public class Propietario extends Persona {
    private String direccionComercial;
    private ArrayList<Huerto> huertos;

    public Propietario(Rut rut, String nombre, String email, String direccion, String direccionComercial) {
        super(rut, nombre, email, direccion);
        this.direccionComercial = direccionComercial;
        this.huertos = new ArrayList<>();
    }

    public String getDirComercial() {
        return direccionComercial;
    }

    public void setDirComercial(String direccion) {
        this.direccionComercial = direccion;
    }

    public boolean addHuerto(Huerto huerto) {
        if (huerto == null) {
            return false;
        }

        boolean existe = huertos.stream()
                .anyMatch(h -> h.equals(huerto));

        if(existe){
            return false;
        }
        huerto.setPropietario(this);
        huertos.add(huerto);
        return true;
    }

    public Huerto[] getHuertos() {
        return huertos.stream()
                .toArray(Huerto[]::new);
    }
}