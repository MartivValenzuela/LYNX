package controlador;

import modelo.Cultivo;
import modelo.PagoPesaje;
import modelo.Persona;
import modelo.PlanCosecha;
import utilidades.Calidad;
import utilidades.EstadoFonologico;
import utilidades.EstadoPlan;
import utilidades.Rut;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ControlProduccion {
    private ArrayList<Cosechador>  cosechadores;
    private ArrayList<PlanCosecha> planes;
    private ArrayList<Supervisor>  supervisores;
    private ArrayList<Cultivo> cultivos;
    private ArrayList<Huerto> huertos;
    private ArrayList<Propietario> propietarios;
    private DateTimeFormatter F = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
    public void changeEstadoCuartel(String nombreHuerto, int idCuartel,EstadoFonologico estado){

    }
    public boolean createPlanCosecha (int idPlan, String nom, LocalDate inicio, LocalDate finEstim, double meta, float precioBase, String nomHuerto, int idCuartel){
        if(findPlanById(idPlan) == null){
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
    public void changeEstadoPlan(int idPlan, EstadoPlan estado){

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
    public void addPesaje(int id, Rut rutCosechador, int idPlan, int idCuadrilla, float cantidadKg, Calidad calidad) {
    }
    public double addPagoPesaje(int id, Rut rutCosechador){

    }
    public String[] listCultivos() {
        String[] out = new String[cultivos.size()];

        for (int i = 0; i < cultivos.size(); i++) {
            Cultivo cultivo = cultivos.get(i);

            // se cuentan cuantos cuarteles se utilizan en este cultivo
            int nCuarteles = 0;
            for (Huerto huerto : huertos) {
                Cuartel[] cuarteles = huerto.getCuarteles();
                if (cuarteles == null) continue;

                for (Cuartel cuartel : cuarteles) {
                    if (cuartel.getCultivo() != null &&
                            cuartel.getCultivo().getId() == cultivo.getId()) {
                        nCuarteles++;
                    }
                }
            }

            // esta parte evita nulos en texto
            String especie = "";
            String variedad = "";

            if (cultivo.getEspecie() != null) {
                especie = cultivo.getEspecie();
            }
            if (cultivo.getVariedad() != null) {
                variedad = cultivo.getVariedad();
            }

            // Se formatea la línea
            out[i] = String.format(
                    "%-6d %-15s %-20s %-15.2f %-15d",
                    cultivo.getId(),
                    especie,
                    variedad,
                    cultivo.getRendimiento(),
                    nCuarteles
            );
        }

        return out;
    }
    public String[] listHuertos(){
        if (huertos.isEmpty()) {
            return new String[0];
        }
        String [] arr = new String[huertos.size()];
        for (int i = 0; i < huertos.size(); i++) {
            Huerto h = huertos.get(i);
            String propRut = "";
            String nomProp = "";
            if(h.getPropietario() != null) {
                if (h.getPropietario().getRut() != null) {
                    propRut = h.getPropietario().getRut().toString();
                } else {
                    propRut = "";
                }
                if (h.getPropietario().getNombre() != null) {
                    nomProp = h.getPropietario().getNombre();
                } else {
                    nomProp = "";
                }
            }
            int nCuart;
            if(h.getCuarteles() == null){
                nCuart = 0;
            } else{
                nCuart = h.getCuarteles().length;
            }
            arr[i] = String.format("%-20s %-12.1f %-30s %-18s %-25s %-15d",
                    h.getNombre(), h.getSuperficie(), h.getUbicacion(), propRut, nomProp, nCuart);
        }
        return arr;
    }
    public String[] listPropietarios(){
        String[] lista = new String[propietarios.size()];
        for (int i = 0; i < propietarios.size(); i++) {
            Propietario prop = propietarios.get(i);
            int nrHuertos;
            if(prop.getHuertos() == null){
                nrHuertos = 0;
            } else {
                nrHuertos = prop.getHuertos().length;
            }
            lista[i] = String.format(
                    "%-14s %-28s %-30s %-30s %-22s %2d",
                    prop.getRut(),prop.getNombre(), prop.getDireccion(),prop.getEmail(), prop.getDirComercial(), nrHuertos);
        }
        return lista;
    }
    public String[] listSupervisores(){
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
            arr[i] =  String.format(
                    "%-14s %-28s %-30s %-28s %-16s %-18s",
                    s.getRut(), s.getNombre(), s.getDireccion(), s.getEmail(), s.getProfesion(), cuadNom);
        }
        return arr;
    }
    public String[] listCosechadores() {
        if(cosechadores.isEmpty()){
            return new String[0];
        }
        String [] arr = new String[cosechadores.size()];
        for(int i = 0; i < cosechadores.size(); i++){
            Cosechador c = cosechadores.get(i);
            int nCuadrillas;
            if(c.getCuadrillas() == null){
                nCuadrillas = 0;
            } else {
                nCuadrillas = c.getCuadrillas().length;
            }
            String fNac;
            if (c.getFechaNacimiento() == null) {
                fNac = "";
            } else {
                fNac = c.getFechaNacimiento().toString();
            }

            arr[i] =String.format(
                    "%-14s %-28s %-30s %-30s %-16s %2d",
                    c.getRut(), c.getNombre(), c.getDireccion(), c.getEmail(), fNac, nCuadrillas);
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
            int nCuadrillas;
            if(p.getCuadrillas() == null){
                nCuadrillas = 0;
            } else {
                nCuadrillas = p.getCuadrillas().length;
            }
            arr[i] =String.format("%-8d %-20s %-14s %-14s %-12.1f %-16.1f %-14s %-10d %-20s %-12d",
                    p.getId(),
                    p.getNombre(),
                    p.getInicio().format(F),
                    p.getFinEstimado().format(F),
                    p.getMetaKilos(),
                    p.getPrecioBaseKilo(),
                    p.getEstado(),
                    idCuartel,
                    huerto,
                    nCuadrillas);
        }
        return arr;
    }
    public String[] listPesajes(){

    }
    public String[] listPesajesCosechadores(){
    }
    public String[] listPagosPesajes(){

    }
    private void readDataFromTextFile(){

    }
    private void generateTestData(){
        propietarios.add(new Propietario(new Rut("11.111.111-1"), "Daniel Ruiz Saez",
                "daniel.ruiz@email.com", "Los Alerces 123", "Calle Comercial 456"));
        supervisores.add(new Supervisor(new Rut("22.222.222-2"), "Leonora Casas Solís",
                "leonora.casas@gmail.com", "Los pinguinos 432", "Agronomo"));
        cosechadores.add(new Cosechador(new Rut("33.333.333-3"), "David Rios Flores",
                "david.riosf@gmail.com", "Las Amapolas 234"));
    }
    private Optional<Persona> findPersonaByRut(Rut rut){

    }
    private Optional<Cultivo> findCultivoById(int id){
        for(Cultivo c : cultivos){
            if(c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    private findHuertosByNombre(String nombre){

    }
    private Optional<PlanCosecha> findPLanCosechaById(long id){

    }
    private Optional<PlanCosecha> findPasajeById(int id){

    }
    private Optional<PagoPesaje> findPagoPesajeById(int id){

    }
    private static boolean eqRut(Rut rut1, Rut rut2){
        return rut1 != null && rut2 != null && rut1.equals(rut2);
    }

    private static String toRut(Rut r) {
        if (r == null) return "";
        return r.toString();
    }

}