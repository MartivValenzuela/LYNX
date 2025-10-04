import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ControlProduccion {
    private ArrayList<Cosechador>  cosechadores;
    private ArrayList<PlanCosecha> planes;
    private ArrayList<Supervisor>  supervisores;

    public boolean createPropietario (Rut rut, String nombre, String email, String dirParticular, String dirComercial) {
    }

    public boolean createSupervisor (Rut rut, String nombre, String email, String dirrecion, String profesion){

    }
    public boolean createCosechador (Rut rut, String nombre, String email, String dirrecion, Date fechaNacimiento){

    }
    public boolean createCultivo (int id,String especie, String variedad,float Rendimiento){

    }
    public boolean createHuerto (String nombre, float superficie, String ubicacion, Rut rutPropietario){

    }
    public boolean addCuartelToHuerto (String nombreHuerto, int idCuartel, float superficie, int idCultivo){

    }
    public boolean createPlanCosecha (int idPlan, String nom, Date inicio, Date finEstim, double meta, float precioBase, String nomHuerto, int idCuartel){

    }
    public boolean addCuadrillaToPlan (int idPlan, int idCuad, String nomCuad, Rut rutSup){
        PlanCosecha plan = findPlanById(idPlan);
        if(plan == null){
            return false;
        }
        Supervisor sup = findSupervisorByRut(rutSup);
        if(sup == null){
            return false;
        }
        if(sup.getCuadrilla() != null){
            return false;
        }
        return plan.addCuadrilla(idCuad, nomCuad, sup);
    }
    public boolean addCosechadorToCuadrilla (int idPlan, int idCuadrilla, LocalDate fInicio, LocalDate fFin, double meta, Rut rutCosechador){
        PlanCosecha plan = findPlanById(idPlan);
        if(plan == null){
            return false;
        }
        if(fInicio == null || fFin == null || fInicio.isAfter(fFin)){
            return false;
        }
        if(fInicio.isBefore(plan.getInicio()) || fFin.isAfter(plan.getFinEstimado())){
            return false;
        }
        Cosechador cos = findCosechadorByRut(rutCosechador);
        if(cos == null){
            return false;
        }
        return plan.addCosechadorToCuadrilla(idCuadrilla, fInicio, fFin, meta, cos);
    }
    public String[] listCultivos(){

    }
    public String[] listHuertos(){

    }
    public String[] listPropietarios(){

    }
    public String[] listSupervisores(){

    }
    public String[] listCosechadores() {
        if(cosechadores.isEmpty()){
            return new String[0];
        }
        String [] arr = new String[cosechadores.size()];
        for(int i = 0; i < cosechadores.size(); i++){
            Cosechador c = cosechadores.get(i);
            int nCuadrillas = c.getCuadrillas().length;
            String fNac;
            if (c.getFechaNacimiento() == null) {
                fNac = "";
            } else {
                fNac = c.getFechaNacimiento().toString();
            }
            arr[i] =toRut(c.getRut()) + ", " + c.getNombre() + ", " + c.getEmail() + ", " + c.getDirrecion() + ", " + fNac + ", " + nCuadrillas;
        }
        return arr;
    }
    public String[] listPlanesCosecha(){
        if(planes.isEmpty()){
            return new String[0];
        }
        String [] arr = new String[planes.size()];
        for(int i = 0; i < planes.size(); i++){
            PlanCosecha p = planes.get(i);
            String huerto = "";
            if (p.getCuartel() != null && p.getCuartel().getHuerto() != null) {
                huerto = p.getCuartel().getHuerto().getNombre();
            }
            int idCuartel = 0;
            if (p.getCuartel() != null) {
                idCuartel = p.getCuartel().getId();
            }
            int nCuadrillas = p.getCuadrillas().length;
            arr[i] =p.getId() + ", " + p.getNombre() + ", " + p.getInicio() + ", " + p.getFinEstimado() + ", " + p.getMetaKilos() + ", " + p.getPrecioBaseKilo() + ", " + huerto + ", " + idCuartel + ", " + nCuadrillas;
        }
        return arr;
    }
    private void generateTestData(){

    }

    private Cosechador findCosechadorByRut(Rut rut){
        for(Cosechador c : cosechadores) if(eqRut(c.getRut(), rut)) return c;
        return null;
    }
    private PlanCosecha findPlanById(int id){
        for(PlanCosecha p : planes) if(p.getId() == id) return p;
        return null;
    }

    private Supervisor findSupervisorByRut(Rut rut){
        for(Supervisor sup : supervisores) if(eqRut(sup.getRut(), rut)) return sup;
        return null;
    }

    private static boolean eqRut(Rut rut1, Rut rut2){
        return rut1 != null && rut2 != null && rut1.equals(rut2);
    }

    private static String toRut(Rut r) {
        if (r == null) return "";
        return r.toString();
    }

}
