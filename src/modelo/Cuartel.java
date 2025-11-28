package modelo;

import utilidades.EstadoFonologico;

import java.util.ArrayList;
public class Cuartel {
    private int id;
    private float superficie;
    private EstadoFonologico estado;
    private ArrayList<PlanCosecha> planCosechas;
    private Cultivo cultivo;
    private Huerto huerto;

    public Cuartel(int id, float superficie, Cultivo cultivo, Huerto huerto ) {
        this.id = id;
        this.superficie = superficie;
        this.cultivo = cultivo;
        this.huerto = huerto;
        this.planCosechas = new ArrayList<>();
        this.estado = EstadoFonologico.REPOSO_INVERNAL;

        if (cultivo != null) {
            cultivo.addCuartel(this);
        }
    }

    public int getId() {
        return id;
    }

    public float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }

    public float getRendimientoEsperado () {
        if(cultivo == null){
            return 0;
        }
        return cultivo.getRendimiento() * superficie;
    }
    public EstadoFonologico getEstado() {
        return estado;
    }

    public boolean setEstado(EstadoFonologico estado) {
        if(this.estado == estado){
            return true;
        }

        if(this.estado == EstadoFonologico.POSTCOSECHA && estado == EstadoFonologico.REPOSO_INVERNAL){
            this.estado = estado;
            return true;
        }
        EstadoFonologico [] orden = new EstadoFonologico[]{
                EstadoFonologico.REPOSO_INVERNAL,
                EstadoFonologico.FLORACION,
                EstadoFonologico.CUAJA,
                EstadoFonologico.FRUCTIFICACION,
                EstadoFonologico.MADURACION,
                EstadoFonologico.COSECHA,
                EstadoFonologico.POSTCOSECHA
        };

        int i = -1;
        int j = -1;

        for(int k = 0; k < orden.length; k++) {
            if (orden[k] == this.estado) {
                i = k;
            }
            if (orden[k] == estado) {
                j = k;
            }
        }
        if (i == -1 || j == -1) {
            return false;
        }
        if (j > i) {
            this.estado = estado;
            return true;
        }
        return false;
    }
    public Cultivo getCultivo(){
        return cultivo;
    }
    public Huerto getHuerto(){
        return huerto;
    }
    public PlanCosecha[] getPlanesCosecha(){
        return planCosechas.
                stream()
                .toArray(PlanCosecha[]::new);
    }

    public void addPlanCosecha(PlanCosecha planCosecha){
        if(planCosechas != null &&  !planCosechas.contains(planCosecha)){
            planCosechas.add(planCosecha);
        }
    }
}