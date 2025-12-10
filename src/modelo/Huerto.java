package modelo;

import utilidades.EstadoFonologico;
import utilidades.GestionHuertosException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class Huerto implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public void addCuartel(int id, float sup, Cultivo cul) throws GestionHuertosException {
        boolean existeId = cuarteles
                .stream()
                .anyMatch(c -> c.getId() == id);
        if (existeId) {
            throw new GestionHuertosException("Ya existe en el huerto un cuartel con el id indicado");
        }


        float total = sup + (float) cuarteles
                            .stream()
                            .mapToDouble(Cuartel::getSuperficie)
                            .sum();

        if(total > this.superficie){
            throw new GestionHuertosException("La superficie del cuartel excederá la superficie del huerto, al sumarle la\n" +
                    "superficie de sus cuarteles actuales");
        }

        Cuartel nuevo = new Cuartel(id, sup, cul, this);
        cuarteles.add(nuevo);
    }

    public Cuartel[] getCuarteles() {
        return cuarteles
                .stream()
                .toArray(Cuartel[]::new);
    }

    public Optional<Cuartel> getCuartelById(int id) {
        return cuarteles
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    public void setEstadoCuartel(int id, EstadoFonologico estado)
        throws GestionHuertosException {
        Optional<Cuartel> cuartel = getCuartelById(id);
        if(cuartel.isEmpty()){
            throw new GestionHuertosException("No existe en el huerto un cuartel con id indicado");
        }

        Cuartel c = cuartel.get();
        boolean cambio = c.setEstado(estado);
        if(!cambio){
            throw new GestionHuertosException("No está permitido el cambio de estado solicitado");
        }
    }
}