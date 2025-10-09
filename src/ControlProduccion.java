import java.util.Date;
import java.util.ArrayList;
import java.util.List;
public class ControlProduccion {
    private List<Propietario> propietarios;
    private List<Supervisor> supervisores;
    private List<Cosechador> cosechadores;

    public ControlProduccion() {
        this.propietarios = new ArrayList<>();
        this.supervisores = new ArrayList<>();
        this.cosechadores = new ArrayList<>();
        this.generateTestData();
    }
    public boolean createPropietario (Rut rut, String nombre, String email, String dirParticular, String dirComercial){
        return;
    }
    public boolean createSupervisor (Rut rut, String nombre, String email, String direccion, String profesion){
        for (Supervisor supervisor : supervisores) {
            if (supervisor.getRut().equals(rut)) {
                return false;
            }
        }
        Supervisor nuevoSupervisor = new Supervisor(rut, nombre, email, direccion, profesion);
        supervisores.add(nuevoSupervisor);
        return true;
    }
    public boolean createCosechador (Rut rut, String nombre, String email, String direccion, Date fechaNacimiento){
        for (Cosechador cosechador : cosechadores) {
            if (cosechador.getRut().equals(rut)) {
                return false;
            }
        }
        Cosechador nuevoCosechador = new Cosechador(rut, nombre, email, direccion, fechaNacimiento);
        cosechadores.add(nuevoCosechador);
        return true;
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
    return new String[0];
    }
    public String[] listHuertos(){

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
    public String[] listSupervisores(){

    }
    public String[] listCosechadores(){

    }
    public String[] listPlanesCosecha(){

    }
    private void generateTestData(){
            propietarios.add(new Propietario(new Rut("11.111.111-1"), "Daniel Ruiz Saez",
                    "daniel.ruiz@email.com", "Los Alerces 123", "Calle Comercial 456"));
            supervisores.add(new Supervisor(new Rut("22.222.222-2"), "Leonora Casas Solís",
                    "leonora.casas@gmail.com", "Los pinguinos 432", "Agronomo"));
            cosechadores.add(new Cosechador(new Rut("33.333.333-3"), "David Rios Flores",
                    "david.riosf@gmail.com", "Las Amapolas 234", new Date()));
        }

    }
}
