package controlador;

import modelo.*;
import utilidades.GestionHuertosException;
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

    public void createPropietario (Rut rut, String nombre, String email, String dirParticular, String dirComercial) throws GestionHuertosException {
        Optional<Propietario> existe = findPropietarioByRut(rut);
        if (existe.isPresent()){
            throw new GestionHuertosException("Ya existe un trabajador con el rut indicado");
        }
        Propietario nuevoPropetario = new Propietario(rut , nombre, email, dirParticular, dirComercial);
        propietarios.add(nuevoPropetario);

    }

    public void createSupervisor (Rut rut, String nombre, String email, String direccion, String profesion) throws GestionHuertosException{
        Optional<Supervisor> existe = findSupervisorByRut(rut);
        if (existe.isPresent()){
            throw new GestionHuertosException("Ya existe un supervisor con el rut indicado");
        }
        Supervisor nuevoSupervisor = new Supervisor(rut, nombre, email, direccion, profesion);
        supervisores.add(nuevoSupervisor);
    }
    public void createCosechador (Rut rut, String nombre, String email, String direccion, LocalDate fechaNacimiento){
        Optional<Cosechador> existe = findCosechadorByRut(rut);
        if(existe.isPresent()){
            throw new GestionHuertosException("Ya existe un Cosechador con el rut indicado");
        }
        Cosechador nuevoCosechador = new Cosechador(rut, nombre, email, direccion);
        cosechadores.add(nuevoCosechador);
        return true;
    }
    public boolean createCultivo (int id,String especie, String variedad,float rendimiento) throws GestionHuertosException{
        Optional<Cultivo> existe = findCultivoById(id);
        if (existe.isPresent()){
            throw new GestionHuertosException("Ya existe un cultivo con ese Id");
        }
        Cultivo nuevoCultivo = new Cultivo(id, especie, variedad, rendimiento);
        cultivos.add(nuevoCultivo);
        return true;
    }
    public void createHuerto (String nombre, float superficie, String ubicacion, Rut rutPropietario) throws GestionHuertosException{
        Optional<Propietario> existeProp = findPropietarioByRut(rutPropietario);
        Optional<Huerto> existeHuerto = findHuertoByName(nombre);
        if (!existeProp.isPresent()){
            throw new GestionHuertosException("No existe un Propietario con el rut indicado");
        }
        if (!existeHuerto.isPresent()){
            throw new GestionHuertosException("No existe un huerto con el nombre indicado");
        }

        Huerto h = new Huerto(nombre, superficie, ubicacion, prop);
        huertos.add(h);

    }
    public void addCuartelToHuerto (String nombreHuerto, int idCuartel, float superficie, int idCultivo) throws GestionHuertosException{
        Optional<Huerto> existeHuerto = findHuertoByName(nombreHuerto);
        Optional<Cultivo> existeCultivo = findCultivoById(idCultivo);
        if (!existeHuerto.isPresent()){
            throw new GestionHuertosException("No existe huerto con el nombre indicado");
        }
        if (!existeCultivo.isPresent()){
            throw new GestionHuertosException("No existe cultivo con el ID indicado");
        }
        Huerto h = findHuertoByName(nombreHuerto);
        h.addCuartel(idCuartel, superficie, cul);

    }
    public void createPlanCosecha (int idPlan, String nom, LocalDate inicio, LocalDate finEstim, double meta, float precioBase, String nomHuerto, int idCuartel) throws GestionHuertosException{
        Optional<PlanCosecha> existePlan = findPlanById(idPlan);
        Optional<Huerto> existeHuertos = findHuertoByName(nomHuerto);
        Optional<Huerto> existeHuertoID = findCultivoById(idCuartel);

        if (existePlan.isPresent()){
            throw new GestionHuertosException("Ya existe un plan con el ID indicado");
        }

        if (!existeHuertos.isPresent()){
            throw new GestionHuertosException("No existe un huerto con el nombre indicado");
        }

        if (!existeHuertoID.isPresent()){
            throw new GestionHuertosException("No existe un huerto con el ID indicado");
        }
        Huerto h = findHuertoByName(nomHuerto);
        Cuartel c = h.getCuartelById(idCuartel);

        PlanCosecha plan = new PlanCosecha(idPlan, nom, inicio, finEstim, meta, precioBase, c);
        planes.add(plan);
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
            arr[i] =  String.format(
                    "%-14s %-28s %-30s %-28s %-16s %-18s",
                    s.getRut(), s.getNombre(), s.getDireccion(), s.getEmail(), s.getProfesion(), cuadNom);
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
    private void generateTestData(){
        propietarios.add(new Propietario(new Rut(11.111.111-1, "Daniel Ruiz Saez",
                "daniel.ruiz@email.com", "Los Alerces 123", "Calle Comercial 456");
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
            if(h.getNombre().equals(name) && h.getNombre() == null) {
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