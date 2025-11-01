package controlador;

import modelo.*;
import utilidades.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ControlProduccion {

    private static ControlProduccion instance;

    private List<Cosechador> cosechadores = new ArrayList<>();
    private List<PlanCosecha> planes = new ArrayList<>();
    private List<Supervisor>  supervisores = new ArrayList<>();
    private List<Cultivo> cultivos = new ArrayList<>();
    private List<Huerto> huertos = new ArrayList<>();
    private List<Propietario> propietarios = new ArrayList<>();
    private List<Pesaje> pesajes = new ArrayList<>();
    private List<PagoPesaje> pagosPesajes = new ArrayList<>();
    private DateTimeFormatter F = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private ControlProduccion(){}
    public static ControlProduccion getInstance(){
        if(instance==null){
            instance = new ControlProduccion();
        }
        return instance;
    }

    public void createPropietario (Rut rut, String nombre, String email, String dirParticular, String dirComercial)
        throws GestionHuertosException {
        if(findPropietarioByRut(rut).isPresent()){
            throw new GestionHuertosException("Ya existe un propietario con el rut indicado");
        }
        propietarios.add(new Propietario(rut, nombre, email, dirParticular, dirComercial));
    }

    public void createSupervisor (Rut rut, String nombre, String email, String direccion, String profesion)
        throws GestionHuertosException {
        if(findSupervisorByRut(rut).isPresent()){
            throw new GestionHuertosException("Ya existe un supervisor con el rut indicado");
        }
        supervisores.add(new Supervisor(rut, nombre, email, direccion, profesion));
    }
    public void createCosechador (Rut rut, String nombre, String email, String direccion, LocalDate fechaNacimiento)
        throws GestionHuertosException {
        if(findCosechadorByRut(rut).isPresent()){
            throw new GestionHuertosException("Ya existe un cosechador con el rut indicado");
        }
        cosechadores.add(new Cosechador(rut, nombre, email, direccion, fechaNacimiento));
    }
    public void createCultivo (int id,String especie, String variedad,float rendimiento)
        throws GestionHuertosException {
        if(findCultivoById(id).isPresent()){
            throw new GestionHuertosException("Ya existe un cultivo con el id indicado");
        }
        cultivos.add(new Cultivo(id, especie, variedad, rendimiento));
    }
    public void createHuerto (String nombre, float superficie, String ubicacion, Rut rutPropietario)
        throws GestionHuertosException {
        if(findHuertoByName(nombre).isPresent()){
            throw new GestionHuertosException("Ya existe un huerto con el nombre indicado");
        }
        Optional<Propietario> op = findPropietarioByRut(rutPropietario);
        if(op.isEmpty()){
            throw new GestionHuertosException("No existe un propietario con el rut indicado");
        }
        huertos.add(new Huerto(nombre, superficie, ubicacion, op.get()));
    }
    public void addCuartelToHuerto (String nombreHuerto, int idCuartel, float superficie, int idCultivo)
        throws GestionHuertosException {
        Optional<Huerto> oh = findHuertoByName(nombreHuerto);
       if(oh.isEmpty()){
           throw new GestionHuertosException("No existe un huerto con el nombre indicado");
       }
       Optional<Cultivo> oc = findCultivoById(idCultivo);
       if(oc.isEmpty()){
           throw new GestionHuertosException("No existe un cultivo con el id indicado");
       }
       oh.get().addCuartel(idCuartel, superficie, oc.get());
    }
    public void changeEstadoCuartel(String nomHuerto, int idCuartel, EstadoFonologico estado)
        throws GestionHuertosException {
        Optional<Huerto> oh = findHuertoByName(nomHuerto);
        if(oh.isEmpty()){
            throw new GestionHuertosException("No existe un huerto con el nombre indicado");
        }
        if(oh.get().getCuartelById(idCuartel).isEmpty()){
            throw new GestionHuertosException("No existe un cultivo con el id indicado");
        }
        oh.get().setEstadoCuartel(idCuartel,estado);
    }

    public void createPlanCosecha (int idPlan, String nom, LocalDate inicio, LocalDate finEstim, double meta, float precioBase, String nomHuerto, int idCuartel)
        throws GestionHuertosException{
        if(findPlanById(idPlan).isPresent()){
            throw new GestionHuertosException("Ya existe un plan con el id indicado");
        }
        Optional<Huerto> oh = findHuertoByName(nomHuerto);
        if(oh.isEmpty()){
            throw new GestionHuertosException("No existe un huerto con el nombre indicado");
        }
        Optional<Cuartel> oc = oh.get().getCuartelById(idCuartel);
        if(oc.isEmpty()){
            throw new GestionHuertosException("No existe un cuartel con el id indicado");
        }
        PlanCosecha plan = new PlanCosecha(idPlan, nom, inicio, finEstim, meta, precioBase, oc.get());
        planes.add(plan);
        oc.get().addPlanCosecha(plan);
    }

    public void changeEstadoPlan(int idPlan, EstadoPlan estado)
        throws GestionHuertosException{
        Optional<PlanCosecha> op = findPlanById(idPlan);
        if(op.isEmpty()){
            throw new GestionHuertosException("No existe un plan con el id indicado");
        }
        boolean ok = op.get().setEstado(estado);
        if(!ok){
            throw new GestionHuertosException("No esta permitido el cambio de estado solicitado");
        }
    }
    public void addCuadrillaToPlan (int idPlan, int idCuad, String nomCuad, Rut rutSup)
        throws GestionHuertosException{
        Optional<PlanCosecha> op = findPlanById(idPlan);
        if(op.isEmpty()){
            throw new GestionHuertosException("No existe un plan con el id indicado");
        }
        Optional<Supervisor> os = findSupervisorByRut(rutSup);
        if(os.isEmpty()){
            throw new GestionHuertosException("No existe un supervisor con el id indicado");
        }
        if(os.get().getCuadrilla() != null){
            throw new GestionHuertosException("El supervisor ya tiene asignada una cuadrilla a su cargo");
        }
        op.get().addCuadrilla(idCuad, nomCuad, os.get());
    }
    public void addCosechadorToCuadrilla (int idPlan, int idCuadrilla, LocalDate fInicio, LocalDate fFin, double meta, Rut rutCosechador)
        throws GestionHuertosException{
        Optional<PlanCosecha> op = findPlanById(idPlan);
        if(op.isEmpty()){
            throw new GestionHuertosException("No existe un plan con el id indicado");
        }
        Optional<Cosechador> oc =  findCosechadorByRut(rutCosechador);
        if(oc.isEmpty()){
            throw new GestionHuertosException("No existe un cosechador con el id indicado");
        }
        PlanCosecha plan = op.get();
        if(fInicio.isBefore(plan.getInicio()) || fFin.isAfter(plan.getFinEstimado())){
            throw new GestionHuertosException("El rango de fechas de asignación del cosechador a la cuadrilla está fuera del rango de fechas del plan");
        }
        Cuadrilla c = null;
        for(Cuadrilla cc : plan.getCuadrillas()){
            if(c.getId() == idCuadrilla){
                c = cc;
                break;
            }
        }
        if(c.getMaximoCosechadores() > 0 && c.getCosechadores().length >= c.getMaximoCosechadores()){
            throw new GestionHuertosException("El numero de cosechadores ya alcanzo el maximo permitido");
        }
        c.addCosechador(fInicio, fFin, meta, oc.get());
    }

    public void addPesaje(int id, Rut rutCosechador, int idPlan, int idCuadrilla, float cantidadKg, Calidad calidad)
        throws GestionHuertosException{


    }

    public double addPagoPesaje(int id, Rut rutCosechador)
        throws GestionHuertosException{


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

    private Optional<Cosechador> findCosechadorByRut(Rut rut){
        for(Cosechador c : cosechadores){
            if(c.getRut().equals(rut)){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
    private Optional<Cultivo> findCultivoById(int id){
        for(Cultivo c : cultivos){
            if(c.getId() == id) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
    private Optional<Propietario> findPropietarioByRut(Rut rut){
        for(Propietario p : propietarios){
            if(p.getRut().equals(rut)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
    private Optional<PlanCosecha> findPlanById(int id){
        for(PlanCosecha p : planes) {
            if(p.getId() == id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    private Optional<Huerto> findHuertoByName(String name){
        for(Huerto h : huertos) {
            if(h.getNombre().equalsIgnoreCase(name)) {
                return Optional.of(h);
            }
        }
        return Optional.empty();
    }
    private Optional<Supervisor> findSupervisorByRut(Rut rut){
        for(Supervisor sup : supervisores){
            if(sup.getRut().equals(rut)) {
                return Optional.of(sup);
            }
        }
        return Optional.empty();
    }

}