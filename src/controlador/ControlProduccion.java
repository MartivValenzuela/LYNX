package controlador;

import modelo.*;
import utilidades.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private ArrayList<Pesaje> pesajes;
    private ArrayList<PagoPesaje> pagos;
    private DateTimeFormatter F = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public ControlProduccion() {
        propietarios = new ArrayList<>();
        supervisores = new ArrayList<>();
        cultivos = new ArrayList<>();
        cosechadores = new ArrayList<>();
        planes = new ArrayList<>();
        huertos = new ArrayList<>();
        pesajes = new ArrayList<>();
        pagos = new ArrayList<>();
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
    public void createCosechador (Rut rut, String nombre, String email, String direccion, LocalDate fechaNacimiento) throws GestionHuertosException{
        Optional<Cosechador> existe = findCosechadorByRut(rut);
        if(existe.isPresent()){
            throw new GestionHuertosException("Ya existe un Cosechador con el rut indicado");
        }
        Cosechador nuevoCosechador = new Cosechador(rut, nombre, email, direccion);
        cosechadores.add(nuevoCosechador);

    }
    public void createCultivo (int id,String especie, String variedad,float rendimiento) throws GestionHuertosException{
        Optional<Cultivo> existe = findCultivoById(id);
        if (existe.isPresent()){
            throw new GestionHuertosException("Ya existe un cultivo con ese Id");
        }
        Cultivo nuevoCultivo = new Cultivo(id, especie, variedad, rendimiento);
        cultivos.add(nuevoCultivo);

    }
    public void createHuerto (String nombre, float superficie, String ubicacion, Rut rutPropietario) throws GestionHuertosException{
        Optional<Propietario> existeProp = findPropietarioByRut(rutPropietario);
        Optional<Huerto> existeHuerto = findHuertoByName(nombre);
        if (!existeProp.isPresent()){
            throw new GestionHuertosException("No existe un Propietario con el rut indicado");
        }
        if (existeHuerto.isPresent()){
            throw new GestionHuertosException("Ya existe un huerto con el nombre indicado");
        }
        Propietario prop = existeProp.get();
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
        Huerto h = existeHuerto.get();
        Cultivo cul = existeCultivo.get();
        h.addCuartel(idCuartel, superficie, cul);

    }
    public void createPlanCosecha (int idPlan, String nom, LocalDate inicio, LocalDate finEstim, double meta, float precioBase, String nomHuerto, int idCuartel) throws GestionHuertosException{
        Optional<PlanCosecha> existePlan = findPlanById(idPlan);
        Optional<Huerto> existeHuertos = findHuertoByName(nomHuerto);


        if (existePlan.isPresent()){
            throw new GestionHuertosException("Ya existe un plan con el ID indicado");
        }

        if (!existeHuertos.isPresent()){
            throw new GestionHuertosException("No existe un huerto con el nombre indicado");
        }


        Huerto h = existeHuertos.get();
        Optional<Cuartel> existeCuartel = h.getCuartelById(idCuartel);

        if (!existeCuartel.isPresent()){
            throw new GestionHuertosException("No existe un Cuartel con el ID indicado");
        }
        Optional<Cuartel> c = h.getCuartelById(idCuartel);
        PlanCosecha plan = new PlanCosecha(idPlan, nom, inicio, finEstim, meta, precioBase, c.get());
        planes.add(plan);
    }
    public void addCuadrillaToPlan (int idPlan, int idCuad, String nomCuad, Rut rutSup) throws GestionHuertosException {
        Optional<PlanCosecha> existe = findPlanById(idPlan);
        Optional<Supervisor> existeSup = findSupervisorByRut(rutSup);
        if (!existe.isPresent()) {
            throw new GestionHuertosException("No existe un plan con el Id indicado");
        }
        if (!existeSup.isPresent()) {
            throw new GestionHuertosException("No existe un supervisor con el Rut indicado");
        }


        Supervisor sup = existeSup.get();
        if (sup.getCuadrilla() != null) {
            throw new GestionHuertosException("El supervisor ya tiene asignada una cuadrilla a su cargo");
        }
        PlanCosecha plan = existe.get();
        plan.addCuadrilla(idCuad, nomCuad, sup);


    }
    public void addCosechadorToCuadrilla (int idPlan, int idCuadrilla, LocalDate fInicio, LocalDate fFin, double meta, Rut rutCosechador) throws GestionHuertosException{
        Optional<PlanCosecha> existePlan = findPlanById(idPlan);
        Optional<Cosechador> existeCosechador = findCosechadorByRut(rutCosechador);
        if (!existePlan.isPresent()){
            throw new GestionHuertosException("No existe un plan con el id indicado ");
        }

        if (!existeCosechador.isPresent()){
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }
        PlanCosecha plan = existePlan.get();
        Cosechador cos = existeCosechador.get();
        if (fInicio.isAfter(fFin)) {
            throw new GestionHuertosException("La fecha de inicio de asignación debe ser anterior a la fecha de término");
        }

        if (fInicio.isBefore(plan.getInicio()) || fFin.isAfter(plan.getFinEstimado())) {
            throw new GestionHuertosException("El rango de fechas de asignación del cosechador a la cuadrilla está fuera del rango de fechas del plan");
        }
        plan.addCosechadorToCuadrilla(idCuadrilla, fInicio, fFin, meta, cos);
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
    /*private void generateTestData(){
        propietarios.add(new Propietario(new Rut(11.111.111-1, "Daniel Ruiz Saez",
                "daniel.ruiz@email.com", "Los Alerces 123", "Calle Comercial 456");
        supervisores.add(new Supervisor(new Rut("22.222.222-2"), "Leonora Casas Solís",
                "leonora.casas@gmail.com", "Los pinguinos 432", "Agronomo"));
        cosechadores.add(new Cosechador(new Rut("33.333.333-3"), "David Rios Flores",
                "david.riosf@gmail.com", "Las Amapolas 234"));
    }
    */
    //Funciones hechas por martin
    public void changeEstadoCuartel(String nomHuerto, int idCuartel, EstadoFonologico estado)
            throws GestionHuertosException {
        Optional<Huerto> oh = findHuertoByName(nomHuerto);
        if(oh.isEmpty()){
            throw new GestionHuertosException("No existe un huerto con el nombre indicado");
        }
        if(oh.get().getCuartelById(idCuartel).isEmpty()){
            throw new GestionHuertosException("No existe en el huerto un cuartel con el id indicado");
        }
        oh.get().setEstadoCuartel(idCuartel,estado);
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
    //

    public void addPesaje(int id, Rut rutCosechador, int idPlan, int idCuadrilla, float cantidadKg, Calidad calidad)
            throws GestionHuertosException{
        if (findPesajeById(id).isPresent()) {
            throw new GestionHuertosException("Ya existe un pesaje con id indicado");
        }
        Optional<Cosechador> optCosechador = findCosechadorByRut(rutCosechador);
        if (optCosechador.isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }
        Cosechador cosechador = optCosechador.get();
        Optional<PlanCosecha> existePlan = findPlanById(idPlan);
        if (existePlan.isEmpty()) {
            throw new GestionHuertosException("No existe un plan con el id indicado");
        }
        PlanCosecha plan = existePlan.get();
        if (plan.getCuartel().getEstado() != EstadoFonologico.COSECHA){
            throw new GestionHuertosException("El cuartel no se encuentra en estado fenológico cosecha");
        }
        Optional<CosechadorAsignado> existeAsignacion = cosechador.getAsignacion(idCuadrilla,idPlan);
        if (existeAsignacion.isEmpty()){
            throw new GestionHuertosException("El Cosechador no tiene una asignaciona una cuadrilla con el id indicado en el plan con el id señalado");
        }
        CosechadorAsignado asignar = existeAsignacion.get();
        LocalDate hoy = LocalDate.now();
        if (hoy.isBefore(asignar.getDesde()) || hoy.isAfter(asignar.getHasta())) {
            throw new GestionHuertosException("La fecha no está en el rango de la asignación del cosechador a la cuadrilla");
        }
        double precioKilo = plan.getPrecioBaseKilo();
        Pesaje nuevo = new Pesaje(id,cantidadKg,calidad,hoy.atStartOfDay(),asignar);
        this.pesajes.add(nuevo);
    }

    public double addPagoPesaje(int id, Rut rutCosechador) throws GestionHuertosException{
        if (findPagoById(id).isPresent()) {
            throw new GestionHuertosException("Ya existe un pago de pesaje con el id indicado");
        }

        Optional<Cosechador> existeCos = findCosechadorByRut(rutCosechador);
        if (existeCos.isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado" );
        }
        Cosechador cosechador1 = existeCos.get();
        ArrayList<Pesaje> pesajes1 = new ArrayList<>();
        for (Pesaje p : this.pesajes){
            Cosechador cPesaje = p.getCosechadorAsignado().getCosechador();
            if (p.getPagoPesaje() == null && cPesaje.getRut().equals(rutCosechador)){
                pesajes1.add(p);
            }
        }
        if (pesajes1.isEmpty()){
            throw new GestionHuertosException("El cosechador no tiene pesajes impagos");
        }
        LocalDate fechaPago = LocalDate.now();

        PagoPesaje nuevopago = new PagoPesaje(id,fechaPago,pesajes1,cosechador1);
        this.pagos.add(nuevopago);
        this.pagos.add(nuevopago);return nuevopago.getMonto();

    }


    private Optional<Cosechador> findCosechadorByRut(Rut rut){
        for(Cosechador c : cosechadores) if(eqRut(c.getRut(), rut)) return Optional.of(c);
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
            if(eqRut(p.getRut(), rut)) {
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
            if (h.getNombre() != null && h.getNombre().equals(name)){
                return Optional.of(h);
            }
        }
        return Optional.empty();
    }
    private Optional<Supervisor> findSupervisorByRut(Rut rut){
        for(Supervisor sup : supervisores) if(eqRut(sup.getRut(), rut)) return Optional.of(sup);
        return Optional.empty();
    }

    private static boolean eqRut(Rut rut1, Rut rut2){
        return rut1 != null && rut2 != null && rut1.equals(rut2);
    }

    private static String toRut(Rut r) {
        if (r == null) return "";
        return r.toString();
    }

}