package controlador;

import modelo.*;
import utilidades.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public ControlProduccion(){}
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
            if(cc.getId() == idCuadrilla){
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
        if (plan.getEstado() != EstadoPlan.EJECUTANDO) {
            throw new GestionHuertosException("El plan no se encuentra en estado en ejecución");
        }
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
        if (findPagoPesajeById(id).isPresent()) {
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
            Rut r = cPesaje.getRut();
            if (p.getPagoPesaje() == null && r.getNumero() == rutCosechador.getNumero() && r.getDv() == rutCosechador.getDv()) {
                pesajes1.add(p);
            }
        }
        if (pesajes1.isEmpty()){
            throw new GestionHuertosException("El cosechador no tiene pesajes impagos");
        }
        LocalDate fechaPago = LocalDate.now();

        PagoPesaje nuevopago = new PagoPesaje(id,fechaPago,pesajes1);
        this.pagosPesajes.add(nuevopago);
        return nuevopago.getMonto();
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

            double montoImpago = 0.0;
            double montoPagado = 0.0;

            for(CosechadorAsignado asignacion : c.getAsignaciones()){
                montoImpago += asignacion.getMontoPesajesImpagos();
                montoPagado += asignacion.getMontoPesajesPagados();
            }

            arr[i] = String.format(
                    "%-14s %-28s %-30s %-30s %-16s %-16d %-16.1f %-16.1f",
                    c.getRut(), c.getNombre(), c.getDireccion(), c.getEmail(),
                    fNac, nCuadrillas, montoImpago, montoPagado);
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
            double kilosPesados = 0.0;
            int nroPesajesImpagos = 0;

            Cuadrilla cuad = s.getCuadrilla();

            if(cuad == null){
                cuadNom = "S/A";
            } else{
                cuadNom = cuad.getNombre();
                kilosPesados = cuad.getKilosPesados();

                for(CosechadorAsignado asignado : cuad.getAsignaciones()){
                    nroPesajesImpagos += asignado.getNroPesajesImpagos();
                }
            }

            arr[i] =  String.format(
                    "%-14s %-28s %-30s %-28s %-16s %-18s %-12.1f %-12d",
                    s.getRut(),
                    s.getNombre(),
                    s.getDireccion(),
                    s.getEmail(),
                    s.getProfesion(),
                    cuadNom,
                    kilosPesados,
                    nroPesajesImpagos
            );
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

    public String[] listPesajes() {
        if (pesajes.isEmpty()) {
            return new String[0];
        }

        String[] lista = new String[pesajes.size()];
        for (int i = 0; i < pesajes.size(); i++) {
            Pesaje p = pesajes.get(i);


            String pagadoEl = "Impago";
            if (p.getPagoPesaje() != null) {
                pagadoEl = p.getPagoPesaje().getFecha().format(F);
            }

            lista[i] = String.format("%-5d %-12s %-15s %-12s %-12.1f %-10.1f %-10.1f %-12s", p.getId(), p.getFechaHora().toLocalDate().format(F), p.getCosechadorAsignado().getCosechador().getRut().toString(), p.getCalidad(), p.getCantidadKg(), p.getPrecioKg(), p.getMonto(), pagadoEl
            );
        }
        return lista;
    }

    public String[] listPesajesCosechador(Rut rutCosechador)
        throws GestionHuertosException{
        Optional<Cosechador> optCosechador = findCosechadorByRut(rutCosechador);
        if (optCosechador.isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }
        Cosechador cosechador = optCosechador.get();


        ArrayList<Pesaje> pesajesCosechador = new ArrayList<>();
        for (Pesaje p : this.pesajes) {
            Rut r = p.getCosechadorAsignado().getCosechador().getRut();
            if (r.getNumero() == rutCosechador.getNumero() && r.getDv() == rutCosechador.getDv()) {
                pesajesCosechador.add(p);
            }
        }

        if (pesajesCosechador.isEmpty()) {
            return new String[0];
        }


        String[] lista = new String[pesajesCosechador.size()];
        for (int i = 0; i < pesajesCosechador.size(); i++) {
            Pesaje p = pesajesCosechador.get(i);

            String pagadoEl = "Impago";
            if (p.getPagoPesaje() != null) {
                pagadoEl = p.getPagoPesaje().getFecha().format(F);
            }

            lista[i] = String.format("%-5d %-12s %-12s %-12.1f %-10.1f %-10.1f %-12s", p.getId(), p.getFechaHora().toLocalDate().format(F), p.getCalidad(), p.getCantidadKg(), p.getPrecioKg(), p.getMonto(), pagadoEl
            );
        }
        return lista;
    }

    public String[] listPagosPesajes() {
        List<String> lineas = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (PagoPesaje pago : this.pagosPesajes) {
            int id = pago.getId();
            String fecha = pago.getFecha().format(dtf);
            double monto = pago.getMonto();
            int numPesajes = pago.getPesajes().length;

            String rut = "S/D";
            if (numPesajes > 0 &&
                    pago.getPesajes()[0] != null &&
                    pago.getPesajes()[0].getCosechadorAsignado() != null &&
                    pago.getPesajes()[0].getCosechadorAsignado().getCosechador() != null &&
                    pago.getPesajes()[0].getCosechadorAsignado().getCosechador().getRut() != null) {

                rut = pago.getPesajes()[0].getCosechadorAsignado().getCosechador().getRut().toString();
            }

            String linea = String.format("%d;%s;%.1f;%d;%s",
                    id, fecha, monto, numPesajes, rut);

            lineas.add(linea);
        }

        return lineas.toArray(new String[0]);
    }

    private void readDataFromTextFile(String path)
        throws FileNotFoundException, GestionHuertosException {
        DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            Scanner leer = new Scanner(new File("archivoGestion.txt"));
            leer.useDelimiter("[;\r\n]+");
            leer.useLocale(Locale.US);

            while (leer.hasNext()) {
                String token = leer.next().trim();
                if (token.isEmpty() || token.startsWith("#")) continue;


                String operacion = token;
                int n = leer.nextInt();

                for (int i = 0; i < n; i++) {
                    try {
                        switch (operacion) {
                            case "createPropietario": {
                                String rut = leer.next().trim();
                                String nombre = leer.next().trim();
                                String email = leer.next().trim();
                                String direccion = leer.next().trim();
                                String dirComercial = leer.next().trim();
                                createPropietario(Rut.of(rut), nombre, email, direccion, dirComercial);
                                break;
                            }

                            case "createSupervisor": {
                                String rut = leer.next().trim();
                                String nombre = leer.next().trim();
                                String email = leer.next().trim();
                                String direccion = leer.next().trim();
                                String profesion = leer.next().trim();
                                createSupervisor(Rut.of(rut), nombre, email, direccion, profesion);
                                break;
                            }

                            case "createCosechador": {
                                String rut = leer.next().trim();
                                String nombre = leer.next().trim();
                                String email = leer.next().trim();
                                String direccion = leer.next().trim();
                                LocalDate fnac = LocalDate.parse(leer.next().trim(), DF);
                                createCosechador(Rut.of(rut), nombre, email, direccion, fnac);
                                break;
                            }

                            case "createCultivo": {
                                int id = leer.nextInt();
                                String especie = leer.next().trim();
                                String variedad = leer.next().trim();
                                float rendimiento = leer.nextFloat();
                                createCultivo(id, especie, variedad, rendimiento);
                                break;
                            }

                            case "createHuerto": {
                                String nombre = leer.next().trim();
                                float superficie = leer.nextFloat();
                                String ubicacion = leer.next().trim();
                                String rutProp = leer.next().trim();
                                createHuerto(nombre, superficie, ubicacion, Rut.of(rutProp));
                                break;
                            }

                            case "addCuartelToHuerto": {
                                String nomHuerto = leer.next().trim();
                                int idCuartel = leer.nextInt();
                                float sup = leer.nextFloat();
                                int idCultivo = leer.nextInt();
                                addCuartelToHuerto(nomHuerto, idCuartel, sup, idCultivo);
                                break;
                            }

                            case "createPlanCosecha": {
                                int idPlan = leer.nextInt();
                                String nombrePlan = leer.next().trim();
                                LocalDate ini = LocalDate.parse(leer.next().trim(), DF);
                                LocalDate fin = LocalDate.parse(leer.next().trim(), DF);
                                double meta = leer.nextDouble();
                                double precio = leer.nextDouble();
                                String nomHuerto = leer.next().trim();
                                int idCuartel = leer.nextInt();
                                createPlanCosecha(idPlan, nombrePlan, ini, fin, meta, (float) precio, nomHuerto, idCuartel);
                                break;
                            }

                            case "addCuadrillaToPlan": {
                                int idPlan = leer.nextInt();
                                int idCuad = leer.nextInt();
                                String nomCuad = leer.next().trim();
                                String rutSup = leer.next().trim();
                                addCuadrillaToPlan(idPlan, idCuad, nomCuad, Rut.of(rutSup));
                                break;
                            }

                            case "addCosechadorToCuadrilla": {
                                int idPlan = leer.nextInt();
                                int idCuad = leer.nextInt();
                                LocalDate finicio = LocalDate.parse(leer.next().trim(), DF);
                                LocalDate ffin = LocalDate.parse(leer.next().trim(), DF);
                                double meta = leer.nextDouble();
                                String rutCosech = leer.next().trim();
                                addCosechadorToCuadrilla(idPlan, idCuad, finicio, ffin, meta, Rut.of(rutCosech));
                                break;
                            }

                            case "changeEstadoPlan": {
                                int idPlan = leer.nextInt();
                                EstadoPlan nuevo = EstadoPlan.valueOf(leer.next().trim().toUpperCase());
                                changeEstadoPlan(idPlan, nuevo);
                                break;
                            }

                            case "changeEstadoCuartel": {
                                int idCuartel = leer.nextInt();
                                String nomHuerto = leer.next().trim();
                                EstadoFonologico nuevo = EstadoFonologico.valueOf(leer.next().trim().toUpperCase());
                                changeEstadoCuartel(nomHuerto, idCuartel, nuevo);
                                break;
                            }

                            case "addPesaje": {
                                int id = leer.nextInt();
                                String rutCos = leer.next().trim();
                                int idPlan = leer.nextInt();
                                int idCuad = leer.nextInt();
                                float cantidad = leer.nextFloat();
                                Calidad calidad = Calidad.valueOf(leer.next().trim().toUpperCase());
                                addPesaje(id, Rut.of(rutCos), idPlan, idCuad, cantidad, calidad);
                                break;
                            }
                        }

                    } catch (GestionHuertosException e) {
                       throw new GestionHuertosException(e.getMessage());
                    }
                }
            }

            leer.close();
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e);
        }
    }


    private Optional<Cosechador> findCosechadorByRut(Rut rut){
        for(Cosechador c : cosechadores){
            Rut r = c.getRut();
            if(r.getNumero() == rut.getNumero() && r.getDv() == rut.getDv()){
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
            Rut r =  p.getRut();
            if(r.getNumero() == rut.getNumero() && r.getDv() == rut.getDv()) {
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
            Rut r = sup.getRut();
            if(r.getNumero() == rut.getNumero() && r.getDv() == rut.getDv()) {
                return Optional.of(sup);
            }
        }
        return Optional.empty();
    }

    private Optional<Pesaje> findPesajeById(int id){
        for(Pesaje p : pesajes){
            if(p.getId() == id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    private Optional<PagoPesaje>  findPagoPesajeById(int id){
        for(PagoPesaje p : pagosPesajes){
            if(p.getId() == id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

}