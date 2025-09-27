import java.time.LocalDate;
import java.util.Date;

public class Cuadrilla {
    private int id;
    private String nombre;
    private static int maximoCosechadores;
    private Supervisor supervisor;
    private PlanCosecha planCosecha;
    private CosechadorAsignado[] asignaciones;
    private int nroAsignaciones;

    public Cuadrilla(int id, String nom, Supervisor sup, PlanCosecha plan) {
        this.id = id;
        this.nombre = nom;
        this.supervisor = sup;
        this.planCosecha = plan;
        this.maximoCosechadores = 0;
        this.asignaciones = new CosechadorAsignado[50];
        this.nroAsignaciones = 0;

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

    private Cosechador findCosechadorByRut(Cosechador cos) {
        for(int i = 0; i < nroAsignaciones; i++) {
            if(asignaciones[i].getCosechador().getRut().equals(cos.getRut())) {
                return asignaciones[i].getCosechador();
            }
        }
        return null;
    }
    public boolean addCosechador (LocalDate fIni, LocalDate fFin, double meta, Cosechador cosechador){
        if(nroAsignaciones >= maximoCosechadores){
            return false;
        }
        if(findCosechadorByRut(cosechador) != null){
            return false;
        }

        CosechadorAsignado nuevo = new CosechadorAsignado(fIni, fFin, meta, this, cosechador);
        asignaciones[nroAsignaciones++] = nuevo;
        return true;
    }
    public Cosechador[] getCosechadores(){
        Cosechador[] resultado = new Cosechador[nroAsignaciones];
        for (int i = 0; i < nroAsignaciones; i++) {
            resultado[i] = asignaciones[i].getCosechador();
        }
        return resultado;
    }
    public static int getMaximoCosechadores(){
        return  maximoCosechadores;
    }

    public static void setMaximoCosechadores(int max) {
        maximoCosechadores = max;
    }
}
