import java.time.LocalDate;
import java.util.ArrayList;

public class Cuadrilla {
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

    private Cosechador findCosechadorByRut(Cosechador cos) {
        for(CosechadorAsignado ca : asignaciones) {
            if(ca.getCosechador().getRut().equals(cos.getRut())) {
                return ca.getCosechador();
            }
        }
        return null;
    }
    public boolean addCosechador (LocalDate fIni, LocalDate fFin, double meta, Cosechador cosechador){
        if(asignaciones.size() >= maximoCosechadores){
            return false;
        }
        if (cosechador == null || fIni == null || fFin == null) {
            return false;
        }
        if(findCosechadorByRut(cosechador) != null){
            return false;
        }

        CosechadorAsignado nuevo = new CosechadorAsignado(fIni, fFin, meta, this, cosechador);
        asignaciones.add(nuevo);
        return true;
    }
    public Cosechador[] getCosechadores(){
        Cosechador[] resultado = new Cosechador[asignaciones.size()];
        for (int i = 0; i < asignaciones.size(); i++) {
            resultado[i] = asignaciones.get(i).getCosechador();
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
