package modelo;

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

    public void setEstado(EstadoFonologico estado) {
        this.estado = estado;
    }
    public Cultivo getCultivo(){
        return cultivo;
    }
    public Huerto getHuerto(){
        return huerto;
    }
    public PlanCosecha[] getPlanesCosecha(){
        PlanCosecha[] resultado = new PlanCosecha[planCosechas.size()];
        for (int i = 0; i < planCosechas.size(); i++) {
            resultado[i] = planCosechas.get(i);
        }
        return resultado;
    }

    public void addPlanCosecha(PlanCosecha planCosecha){
        if(planCosechas != null &&  !planCosechas.contains(planCosecha)){
            planCosechas.add(planCosecha);
        }
    }
}