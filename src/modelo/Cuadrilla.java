package modelo;

import utilidades.GestionHuertosException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Cuadrilla implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;
    private static int maximoCosechadores;
    private Supervisor supervisor;
    private PlanCosecha planCosecha;
    private ArrayList<CosechadorAsignado> asignaciones;

    public Cuadrilla(int id, String nom, Supervisor sup, PlanCosecha plan) {
        this.id = id;
        this.nombre = nom;
        this.supervisor = sup;
        this.planCosecha = plan;
        this.asignaciones = new ArrayList<>();

        if(sup != null) {
            sup.setCuadrilla(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Supervisor getSupervisor(){
        return supervisor;
    }
    public PlanCosecha getPlanCosecha(){
        return planCosecha;
    }

    private Optional<Cosechador> findCosechadorByRut(Cosechador cos) {
        return asignaciones
                .stream()
                .map(CosechadorAsignado::getCosechador)
                .filter(c -> c.getRut().equals(cos.getRut()))
                .findFirst();
    }
    public void addCosechador (LocalDate fIni, LocalDate fFin, double meta, Cosechador cosechador)
            throws GestionHuertosException {

        if(maximoCosechadores > 0 && asignaciones.size() >= maximoCosechadores) {
            throw new GestionHuertosException("No es posible agregar el nuevo cosechador porque ya se alcanzó el máximo número de cosechadores en una cuadrilla");
        }
        if(findCosechadorByRut(cosechador).isPresent()){
            throw new GestionHuertosException("Ya existe un cosechador en la cuadrilla con el mismo rut del cosechador recibido como parámetro");
        }

        CosechadorAsignado nuevo = new CosechadorAsignado(fIni, fFin, meta, this, cosechador);
        asignaciones.add(nuevo);
        cosechador.addCuadrilla(nuevo);
    }
    public Cosechador[] getCosechadores(){
        return asignaciones
                .stream()
                .map(CosechadorAsignado::getCosechador)
                .toArray(Cosechador[]::new);
    }

    public double getKilosPesados(){
        return asignaciones.stream()
                .flatMap(asignado -> Arrays.stream(asignado.getPesajes()))
                .mapToDouble(Pesaje::getCantidadKg)
                .sum();
    }

    public CosechadorAsignado [] getAsignaciones(){
        return asignaciones.toArray(new CosechadorAsignado[0]);
    }
    public static int getMaximoCosechadores(){
        return  maximoCosechadores;
    }

    public static void setMaximoCosechadores(int max) {
        maximoCosechadores = max;
    }
}
