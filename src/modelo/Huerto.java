package modelo;

import utilidades.EstadoFonologico;
import utilidades.GestionHuertosException;

import java.util.ArrayList;
import java.util.Optional;

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

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public void addCuartel(int id, float sup, Cultivo cul) throws GestionHuertosException {
        Optional<Cuartel> existe = getCuartelById(id);
        if (existe.isPresent()){
            throw new GestionHuertosException("Ya existe en el huerto un cuartel con el id indicado ");
        }
        float superficieOcupada = 0;
        for (Cuartel c : cuarteles){
            superficieOcupada += c.getSuperficie();
        }

        if ((superficieOcupada + sup) > this.superficie){
            throw new GestionHuertosException("La superficie del cuartel excederá la superficie del huerto, al sumarle la superficie de sus cuarteles actuales");
        }

        Cuartel c = new Cuartel(id, sup, cul, this);
        cuarteles.add(c);

    }


    public Cuartel[] getCuarteles() {
        Cuartel[] resultado = new Cuartel[cuarteles.size()];
        for (int i = 0; i < cuarteles.size(); i++) {
            resultado[i] = cuarteles.get(i);
        }
        return resultado;
    }

    public Optional<Cuartel> getCuartelById(int id) {
        for (Cuartel c : cuarteles) {
            if (c.getId() == id) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
    public void setEstadoCuartel(int id, EstadoFonologico estado) throws GestionHuertosException {
        Optional<Cuartel> existe = getCuartelById(id);
        if (existe.isEmpty()){
            throw new GestionHuertosException("No existe en el huerto un cuartel con id indicado");
        }
        Cuartel cuartel = existe.get();
        boolean ok = cuartel.setEstado(estado);
        if (!ok){
            throw new GestionHuertosException("No está permitido el cambio de estado solicitado (lo informa el cuartel)");
        }
        }
    }
