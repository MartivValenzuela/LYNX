import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ControlProduccion {
    private ArrayList<Cosechador>  cosechadores;
    private ArrayList<PlanCosecha> planes;
    private ArrayList<Supervisor>  supervisores;
    private ArrayList<Cultivo> cultivos;
    private ArrayList<Huerto> huertos;
    private ArrayList<Propietario> propietarios;

    public ControlProduccion() {
        propietarios = new ArrayList<>();
        supervisores = new ArrayList<>();
        cultivos = new ArrayList<>();
        cosechadores = new ArrayList<>();
        planes = new ArrayList<>();
        huertos = new ArrayList<>();
    }

    public boolean createPropietario (Rut rut, String nombre, String email, String dirParticular, String dirComercial) {
        for(Propietario p:propietarios){
            if(p.getRut().equals(rut)){
                return false;
            }
        }
        Propietario nuevoPropetario = new Propietario(rut , nombre, email, dirParticular, dirComercial);
        propietarios.add(nuevoPropetario);
        return true;
    }

    public boolean createSupervisor (Rut rut, String nombre, String email, String direccion, String profesion){
        for (Supervisor supervisor : supervisores) {
            if (Objects.equals(supervisor.getRut(), rut)) {
                return false;
            }
        }
        Supervisor nuevoSupervisor = new Supervisor(rut, nombre, email, direccion, profesion);
        supervisores.add(nuevoSupervisor);
        return true;
    }
    public boolean createCosechador (Rut rut, String nombre, String email, String direccion, LocalDate fechaNacimiento){
        for (Cosechador cosechador : cosechadores) {
            if (cosechador.getRut().equals(rut)) {
                return false;
            }
        }
        Cosechador nuevoCosechador = new Cosechador(rut, nombre, email, direccion);
        cosechadores.add(nuevoCosechador);
        return true;
    }
    public boolean createCultivo (int id,String especie, String variedad,float rendimiento){
        for (Cultivo cultivo : cultivos) {
            if (cultivo.getId() == id) {
                return false;
            }
        }
        Cultivo nuevoCultivo = new Cultivo(id, especie, variedad, rendimiento);
        cultivos.add(nuevoCultivo);
        return true;
    }
    public boolean createHuerto (String nombre, float superficie, String ubicacion, Rut rutPropietario){
        if(findHuertoByName(nombre) != null){
            return false;
        }
        Propietario prop = findPropietarioByRut(rutPropietario);
        if(prop == null){
            return false;
        }
        Huerto h = new Huerto(nombre, superficie, ubicacion, prop);
        huertos.add(h);
        return true;
    }
    public boolean addCuartelToHuerto (String nombreHuerto, int idCuartel, float superficie, int idCultivo){
        Huerto h = findHuertoByName(nombreHuerto);
        if(h==null){
            return false;
        }
        Cultivo cul = findCultivoById(idCultivo);
        if(cul==null){
            return false;
        }
        return h.addCuartel(idCuartel, superficie, cul);

    }
    public boolean createPlanCosecha (int idPlan, String nom, LocalDate inicio, LocalDate finEstim, double meta, float precioBase, String nomHuerto, int idCuartel){
        if(findPlanById(idPlan) != null){
            return false;
        }
        if(inicio == null ||  finEstim == null || inicio.isAfter(finEstim)){
            return false;
        }
        Huerto h = findHuertoByName(nomHuerto);
        if(h == null){
            return false;
        }
        Cuartel c = h.getCuartelById(idCuartel);
        if(c == null){
            return false;
        }
        PlanCosecha plan = new PlanCosecha(idPlan, nom, inicio, finEstim, meta, precioBase, c);
        planes.add(plan);
        return true;
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
        if (cultivos.isEmpty()) {
            System.out.println("no hay cultivos listados");
            return new String[0];
        }
        String [] arr = new String[cultivos.size()];
        for (int i = 0; i < cultivos.size(); i++) {
            Cultivo c = cultivos.get(i);
            arr[i] = c.getId() + ", " + c.getEspecie() + ", " + c.getVariedad() + ", " + c.getRendimiento();
        }
        return arr;
    }
    public String[] listHuertos(){
        if (huertos.isEmpty()) {
            return new String[0];
        }
        String [] arr = new String[huertos.size()];
        for (int i = 0; i < huertos.size(); i++) {
            Huerto h = huertos.get(i);
            String propRut;
            if(h.getPropietario() != null && h.getPropietario() != null){
                propRut = h.getPropietario().getRut().toString();
            } else {
                propRut = "";
            }
            arr[i] = h.getNombre() + ", " + h.getSuperficie() + ", " + h.getUbicacion() + ", " + propRut + ", " + h.getCuarteles();
        }
        return arr;
    }
    public String[] listPropietarios(){
        String[] lista = new String[propietarios.size()];
        for (int i = 0; i < propietarios.size(); i++) {
            Propietario prop = propietarios.get(i);
            lista[i] = prop.getRut() + ", " + prop.getNombre() + ", " + prop.getEmail() + ", " +
                    prop.getDireccion() + ", " + prop.getDirComercial();
        }
        return lista;
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
            arr[i] =toRut(c.getRut()) + ", " + c.getNombre() + ", " + c.getEmail() + ", " + c.getDireccion() + ", " + fNac + ", " + nCuadrillas;
        }
        return arr;
    }

    public String [] listSupervisores(){
        if(supervisores.isEmpty()){
            return new String[0];
        }
        String [] arr = new String[supervisores.size()];
        for(int i = 0; i < supervisores.size(); i++){
            Supervisor s = supervisores.get(i);
            String cuadNom;
            if(s.getCuadrilla() == null){
                cuadNom = "S/A";
            } else{
                cuadNom = s.getCuadrilla().getNombre();
            }
            arr[i] = toRut(s.getRut()) + ", " + s.getNombre() + ", " + s.getEmail() + ", " + s.getDireccion() + ", " + s.getProfesion() + ", " + cuadNom;
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
        propietarios.add(new Propietario(new Rut("11.111.111-1"), "Daniel Ruiz Saez",
                "daniel.ruiz@email.com", "Los Alerces 123", "Calle Comercial 456"));
        supervisores.add(new Supervisor(new Rut("22.222.222-2"), "Leonora Casas Solís",
                "leonora.casas@gmail.com", "Los pinguinos 432", "Agronomo"));
        cosechadores.add(new Cosechador(new Rut("33.333.333-3"), "David Rios Flores",
                "david.riosf@gmail.com", "Las Amapolas 234"));
    }

    private Cosechador findCosechadorByRut(Rut rut){
        for(Cosechador c : cosechadores) if(eqRut(c.getRut(), rut)) return c;
        return null;
    }
    private Cultivo findCultivoById(int id){
        for(Cultivo c : cultivos){
            if(c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    private Propietario findPropietarioByRut(Rut rut){
        for(Propietario p : propietarios){
            if(eqRut(p.getRut(), rut)) {
                return p;
            }
        }
        return null;
    }
    private PlanCosecha findPlanById(int id){
        for(PlanCosecha p : planes) {
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private Huerto findHuertoByName(String name){
        for(Huerto h : huertos) {
            if(h.getNombre() != null && h.getNombre().equals(name)){
                return h;
            }
        }
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
