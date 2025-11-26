package modelo;

import utilidades.EstadoPlan;
import utilidades.GestionHuertosException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlanCosecha implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;
    private LocalDate inicio;
    private LocalDate finEstimado;
    private LocalDate finReal;
    private double metaKilos;
    private double precioBaseKilo;
    private EstadoPlan estado;
    private Cuartel cuartel;
    private List<Cuadrilla> cuadrillas;

    public PlanCosecha(int id, String nom, LocalDate ini, LocalDate finEst, double meta, double precio, Cuartel cuartel) {
        this.id = id;
        this.nombre = nom;
        this.inicio = ini;
        this.finEstimado = finEst;
        this.metaKilos = meta;
        this.precioBaseKilo = precio;
        this.cuartel = cuartel;
        this.cuadrillas = new ArrayList<>();
        this.estado = EstadoPlan.PLANIFICADO;
        if(cuartel != null){
            cuartel.addPlanCosecha(this);
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

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFinEstimado() {
        return finEstimado;
    }

    public LocalDate getFinReal() {
        return finReal;
    }
    public void setFinReal(LocalDate finReal) {
        this.finReal = finReal;
    }
    public double getMetaKilos() {
        return metaKilos;
    }

    public void setMetaKilos(double metaKilos) {
        this.metaKilos = metaKilos;
    }

    public double getPrecioBaseKilo() {
        return precioBaseKilo;
    }

    public void setPrecioBaseKilo(double precioBaseKilo) {
        this.precioBaseKilo = precioBaseKilo;
    }

    public EstadoPlan getEstado() {
        return estado;
    }

    public boolean setEstado(EstadoPlan nuevo) {
        if (estado == nuevo) {
            return true;
        }
        if (estado == EstadoPlan.PLANIFICADO) {
            if (nuevo == EstadoPlan.EJECUTANDO) {
                estado = nuevo;
                return true;
            }
            if (nuevo == EstadoPlan.CANCELADO) {
                estado = nuevo;
                return true;
            }
        } else {
            if (estado == EstadoPlan.EJECUTANDO) {
                if (nuevo == EstadoPlan.CERRADO) {
                    estado = nuevo;
                    return true;
                }
                if (nuevo == EstadoPlan.CANCELADO) {
                    estado = nuevo;
                    return true;
                }
            }
        }
        return false;
    }
    public Cuartel getCuartel(){
        return cuartel;
    }

    public double getCumplimientoMeta(){
        double kilos = 0.0;
        for(Cuadrilla c : cuadrillas){
            kilos += c.getKilosPesados();
        }
        if(metaKilos <= 0.0){
            return 0.0;
        }
        double pct = (kilos / metaKilos) * 100.0;
        if(pct < 0.0){
            return 0.0;
        }
        return pct;
    }
    public void addCuadrilla (int idCuad, String nomCuad, Supervisor supervisor)
        throws GestionHuertosException{

        for(Cuadrilla c : cuadrillas){
            if(c.getId() == idCuad){
                throw new GestionHuertosException("Ya existe una cuadrilla con el ID indicado.");
            }
        }
        if(supervisor.getCuadrilla() != null){
            throw new GestionHuertosException("El supervisor ya tiene una cuadrilla asignada.");
        }

        Cuadrilla nueva = new Cuadrilla(idCuad, nomCuad, supervisor, this);
        cuadrillas.add(nueva);
        supervisor.setCuadrilla(nueva);
    }
    public void addCosechadorToCuadrilla(int idCuad, LocalDate fIni, LocalDate fFin, double meta, Cosechador cos)
        throws GestionHuertosException {
        Cuadrilla encontrada = null;
        for(Cuadrilla c : cuadrillas){
            if(c.getId() == idCuad){
                encontrada = c;
                break;
            }
        }

        if(encontrada == null){
            throw new GestionHuertosException("No existe una cuadrilla con el ID indicado.");
        }
        encontrada.addCosechador(fIni, fFin, meta, cos);
    }
    public Cuadrilla[] getCuadrillas(){
        return cuadrillas.toArray(new Cuadrilla[0]);
    }

}
