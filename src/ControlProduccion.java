import java.util.ArrayList;
import java.util.Date;

public class ControlProduccion {
    private ArrayList<Cultivo> cultivos;
    private ArrayList<Huerto> huertos;
    public boolean createPropietario (Rut rut, String nombre, String email, String dirParticular, String dirComercial){

    }
    public boolean createSupervisor (Rut rut, String nombre, String email, String dirrecion, String profesion){

    }
    public boolean createCosechador (Rut rut, String nombre, String email, String dirrecion, Date fechaNacimiento){

    }
    public boolean createCultivo (int id,String especie, String variedad,float rendimiento){
        for (Cultivo cultivo : cultivos) {
            if (cultivo.getId() == id) {
                return false;
            }else {
                Cultivo nuevocultivo = new Cultivo(id, especie, variedad, rendimiento)
                cultivo.add(nuevocultivo);
            }
        }
    }
    public boolean createHuerto (String nombre, float superficie, String ubicacion, Rut rutPropietario){
        for (Huerto huerto : huertos){
            if (huerto.getNombre().equals(nombre)) {
                return false;
            }else {

            }
        }
    }
    public boolean addCuartelToHuerto (String nombreHuerto, int idCuartel, float superficie, int idCultivo){
        for (Huerto huerto: huertos) {
            if (huerto.getNombre().equalsIgnoreCase(nombreHuerto)){
                Cuartel nuevoCuartel = new Cuartel(idCuartel, superficie, idCultivo);
                huerto.addCuartel(nuevoCuartel);
                return true;
            }else {
                return false;
            }
        }

    }
    public boolean createPlanCosecha (int idPlan, String nom, Date inicio, Date finEstim, double meta, float precioBase, String nomHuerto, int idCuartel){

    }
    public boolean addCuadrillaToPlan (int idPlan, int idCuad, String nomCuad, Rut sutSup){

    }
    public boolean addCosechadorToCuadrilla (int idPlan, int idCuadrilla, Date fInicio, Date fFin, double meta, Rut rutCosechador){

    }
    public String[] listCultivos(){
        if (cultivos == null) {
            System.out.println("no hay cultivos listados");
        }else{
            for (int i = 0; i < cultivos.size(); i++) {
                Cultivo c = cultivos.get(i);

            }
        }
    }
    public String[] listHuertos(){
        if (huertos == null) {
            System.out.println("no hay huertos listados");
        }else{
            for (int i = 0; i < huertos.size(); i++) {
                Huerto c = huertos.get(i);

            }
        }
    }
    public String[] listPropietarios(){

    }
    public String[] listSupervisores(){

    }
    public String[] listCosechadores(){

    }
    public String[] listPlanesCosecha(){

    }
    private void generateTestData(){

    }
}
