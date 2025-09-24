import java.util.Date;

public class Cuadrilla {
    private int id;
    private String nombre;
    private int maximoCosechadores;

    public Cuadrilla(int id, String nom, Supervisor sup, PlanCosecha plan) {
        this.id = id;
        this.nombre = nom;
        this.supervisor = sup;
        this.planCosecha = plan;
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
    public boolean addCosechador (Date fIni, Date fFin, double meta, Cosechador cos){

    }
    public Cosechador[] getCosechadores(){

    }
    public int getMaximoCosechadores(){

    }

    public void setMaximoCosechadores(int max) {
        this.maximoCosechadores = max;
    }
}
