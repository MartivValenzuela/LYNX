public class Cuartel {
    private int id;
    private float superficie;
    private EstadoFonologico estado;

    public Cuartel(int id, float superficie, Cultiva cultivo, Huerto huerto ) {
        this.id = id;
        this.superficie = superficie;
        this.cultivo = cultivo;
        this.huerto = huerto;
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
        return ;
    }
    public EstadoFonologico getEstado() {
        return estado;
    }

    public void setEstado(EstadoFonologico estado) {
        this.estado = estado;
    }
    public Cultivo getCultivo(){

    }
    public Huerto getHuerto(){

    }
    public PlanCosecha[] getPlanesCosecha(){

    }
}
