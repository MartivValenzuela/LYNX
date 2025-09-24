import java.util.Date;

public class ControlProduccion {
    public boolean createPropietario (Rut rut, String nombre, String email, String dirParticular, String dirComercial){
        return ;
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
    public boolean addCuadrillaToPlan (int idPlan, int idCuad, String nomCuad, Rut sutSup){

    }
    public boolean addCosechadorToCuadrilla (int idPlan, int idCuadrilla, Date fInicio, Date fFin, double meta, Rut rutCosechador){

    }
    public String[] listCultivos(){

    }
    public String[] listHuertos(){

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
