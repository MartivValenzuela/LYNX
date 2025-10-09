import java.util.ArrayList;

public class Cuartel {
    private int id;
    private float superficie;
    private EstadoFonologico estado;
    PlanCosecha plan;
    private Huerto huerto;
    private ArrayList<PlanCosecha> planCosechas;
    private Cultivo cultivo;

    public Cuartel(int id, float superficie, Cultivo cultivo, Huerto huerto ) {
        this.id = id;
        this.superficie = superficie;
        this.cultivo = cultivo;
        this.huerto = huerto;
        this.planCosechas = new ArrayList<>();
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
        if(getCultivo() == null){
            return 0;
        }
        return getCultivo().getRendimiento() * getSuperficie();
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
        return planCosechas.toArray(new  PlanCosecha[0]);
    }
}
