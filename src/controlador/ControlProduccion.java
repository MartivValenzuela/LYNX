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

    public ControlProduccion() {
        try {
            readDataFromTextFile("archivoGestion.txt");
            System.out.println(">> Datos cargados correctamente desde archivoGestion.txt");
        }catch (GestionHuertosException e) {
            System.out.println(">> Error al cargar datos de archivoGestion.txt: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(">> Error inesperado: " + e.getClass().getName());
            e.printStackTrace();
        }
    }


    public static ControlProduccion getInstance(){
        if(instance==null){
            instance = new ControlProduccion();
        }
        return instance;
    }

    public void createPropietario(Rut rut, String nombre, String email, String dirParticular, String dirComercial)
            throws GestionHuertosException {

        if (findPropietarioByRut(rut).isPresent()) {
            throw new GestionHuertosException("Ya existe un propietario con el rut indicado");
        }
        if (existePersonaConRut(rut)) {
            throw new GestionHuertosException("Ya existe una persona (propietario, supervisor o cosechador) con el rut indicado");
        }
        propietarios.add(new Propietario(rut, nombre, email, dirParticular, dirComercial));
    }

    public void createSupervisor(Rut rut, String nombre, String email, String direccion, String profesion)
            throws GestionHuertosException {

        if (findSupervisorByRut(rut).isPresent()) {
            throw new GestionHuertosException("Ya existe un supervisor con el rut indicado");
        }
        if (existePersonaConRut(rut)) {
            throw new GestionHuertosException("Ya existe una persona (propietario, supervisor o cosechador) con el rut indicado");
        }
        supervisores.add(new Supervisor(rut, nombre, email, direccion, profesion));
    }
    public void createCosechador(Rut rut, String nombre, String email, String direccion, LocalDate fechaNacimiento)
            throws GestionHuertosException {

        if (findCosechadorByRut(rut).isPresent()) {
            throw new GestionHuertosException("Ya existe un cosechador con el rut indicado");
        }
        if (existePersonaConRut(rut)) {
            throw new GestionHuertosException("Ya existe una persona (propietario, supervisor o cosechador) con el rut indicado");
        }
        cosechadores.add(new Cosechador(rut, nombre, email, direccion, fechaNacimiento));
    }
    private boolean existePersonaConRut(Rut rut) {
        // verificar  propietarios
        for (Propietario p : propietarios) {
            if (p.getRut().getNumero() == rut.getNumero() &&
                    p.getRut().getDv() == rut.getDv()) {
                return true;
            }
        }

        // verificar supervisores
        for (Supervisor s : supervisores) {
            if (s.getRut().getNumero() == rut.getNumero() &&
                    s.getRut().getDv() == rut.getDv()) {
                return true;
            }
        }

        // Verificar en cosechadores
        for (Cosechador c : cosechadores) {
            if (c.getRut().getNumero() == rut.getNumero() &&
                    c.getRut().getDv() == rut.getDv()) {
                return true;
            }
        }

        return false;
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
        if(finEstim.isBefore(inicio)){
            throw new GestionHuertosException("La fecha de término no puede ser anterior a la fecha de inicio.");
        }
        PlanCosecha plan = new PlanCosecha(idPlan, nom, inicio, finEstim, meta, precioBase, oc.get());
        planes.add(plan);
        oc.get().addPlanCosecha(plan);
    }

    public void changeEstadoPlan(int idPlan, EstadoPlan nuevo) throws GestionHuertosException {
        PlanCosecha plan = findPlanById(idPlan)
                .orElseThrow(() -> new GestionHuertosException("Plan de cosecha no existe"));

        boolean cambioExitoso = plan.setEstado(nuevo);

        if (!cambioExitoso) {
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

        if(c == null){
            throw new GestionHuertosException("No existe una cuadrilla con el id indicado en este plan");
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
        return cultivos.stream()
                .map(cultivo -> {
                    // contar cuántos cuarteles usan este cultivo
                    long nCuarteles = huertos.stream()
                            .filter(h -> h.getCuarteles() != null)
                            .flatMap(h -> Arrays.stream(h.getCuarteles()))
                            .filter(cuartel -> cuartel.getCultivo() != null
                                    && cuartel.getCultivo().getId() == cultivo.getId())
                            .count();

                    String especie = Optional.ofNullable(cultivo.getEspecie()).orElse("");
                    String variedad = Optional.ofNullable(cultivo.getVariedad()).orElse("");

                    return String.format(
                            "%-6d %-15s %-20s %-15.2f %-15d",
                            cultivo.getId(),
                            especie,
                            variedad,
                            cultivo.getRendimiento(),
                            (int) nCuarteles
                    );
                })
                .toArray(String[]::new);
    }

    public String[] listHuertos(){
        return huertos.stream()
                .map(h -> {
                    String propRut = "";
                    String nomProp = "";
                    if (h.getPropietario() != null) {
                        if (h.getPropietario().getRut() != null) {
                            propRut = h.getPropietario().getRut().toString();
                        }
                        if (h.getPropietario().getNombre() != null) {
                            nomProp = h.getPropietario().getNombre();
                        }
                    }
                    int nCuart;
                    if(h.getCuarteles() == null){
                        nCuart = 0;
                    } else{
                        nCuart = h.getCuarteles().length;
                    }
                    return String.format("%-20s %-12.1f %-30s %-18s %-25s %-15d",
                            h.getNombre(), h.getSuperficie(), h.getUbicacion(), propRut, nomProp, nCuart);

                })
                    .toArray(String[]::new);
    }
    public String[] listPropietarios(){
        return propietarios.stream()
                .map(prop -> {
                    int nrHuertos;
                    if(prop.getHuertos() == null){
                        nrHuertos = 0;
                    } else {
                        nrHuertos = prop.getHuertos().length;
                    }
                    return String.format(
                            "%-14s %-28s %-30s %-30s %-22s %2d",
                            prop.getRut(),
                            prop.getNombre(),
                            prop.getDireccion(),
                            prop.getEmail(),
                            prop.getDirComercial(),
                            nrHuertos
                    );
                })
                .toArray(String[]::new);
    }
    public String[] listCosechadores() {
        if(cosechadores.isEmpty()){
            return new String[0];
        }
        return cosechadores.stream()
                .map(c ->{
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

                    double montoImpago = Arrays.stream(c.getAsignaciones())
                            .mapToDouble(CosechadorAsignado::getMontoPesajesImpagos)
                            .sum();

                    double montoPagado = Arrays.stream(c.getAsignaciones())
                            .mapToDouble(CosechadorAsignado::getMontoPesajesPagados)
                            .sum();

                    return String.format(
                            "%-14s %-28s %-30s %-30s %-16s %-16d %-16.1f %-16.1f",
                            c.getRut(),
                            c.getNombre(),
                            c.getDireccion(),
                            c.getEmail(),
                            fNac,
                            nCuadrillas,
                            montoImpago,
                            montoPagado
                    );
                })
                .toArray(String[]::new);
    }

    public String [] listSupervisores(){
        if(supervisores.isEmpty()){
            return new String[0];
        }
        return supervisores.stream()
                .map(s -> {
                    String cuadNom = "S/A";
                    double kilosPesados = 0.0;
                    int nroPesajesImpagos = 0;

                    Cuadrilla cuad = s.getCuadrilla();

                    if(cuad != null) {
                        cuadNom = cuad.getNombre();
                        kilosPesados = cuad.getKilosPesados();

                        if(cuad.getAsignaciones() != null) {
                            nroPesajesImpagos = Arrays.stream(cuad.getAsignaciones())
                                    .mapToInt(CosechadorAsignado:: getNroPesajesImpagos)
                                    .sum();
                        }
                    }

                    return  String.format(
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
                })
                .toArray(String[]::new);
    }
    public String[] listPlanesCosecha(){
        if(planes.isEmpty()){
            return new String[0];
        }
        return planes.stream()
                .map(p -> {
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
                    return String.format("%-8d %-20s %-14s %-14s %-12.1f %-16.1f %-14s %-10d %-20s %-12d",
                            p.getId(),
                            p.getNombre(),
                            p.getInicio().format(F),
                            p.getFinEstimado().format(F),
                            p.getMetaKilos(),
                            p.getPrecioBaseKilo(),
                            p.getEstado(),
                            idCuartel,
                            huerto,
                            nCuadrillas
                    );
                })
                .toArray(String[]::new);
    }

    public String[] listPesajes() {
        if (pesajes.isEmpty()) {
            return new String[0];
        }
        return pesajes.stream()
                .map(p -> {
                    String pagadoEl = "Impago";
                    if (p.getPagoPesaje() != null) {
                        pagadoEl = p.getPagoPesaje().getFecha().format(F);
                    }

                    return String.format("%-5d %-12s %-15s %-12s %-12.1f %-10.1f %-10.1f %-12s",
                            p.getId(),
                            p.getFechaHora().toLocalDate().format(F),
                            p.getCosechadorAsignado().getCosechador().getRut().toString(),
                            p.getCalidad(),
                            p.getCantidadKg(),
                            p.getPrecioKg(),
                            p.getMonto(),
                            pagadoEl
                    );
                }).toArray(String[]::new);
    }

    public String[] listPesajesCosechador(Rut rutCosechador)
        throws GestionHuertosException{
        Cosechador cosechador = findCosechadorByRut(rutCosechador)
                .orElseThrow(() -> new GestionHuertosException("No existe el Cosechador con el rut indicado"));

        List<Pesaje> pesajesCosechador = pesajes.stream()
                .filter(p -> {
                    Rut r = p.getCosechadorAsignado().getCosechador().getRut();
                    return r.getNumero() == rutCosechador.getNumero()
                            && r.getDv() == rutCosechador.getDv();
                })
                .toList();
        if (pesajesCosechador.isEmpty()) {
            return new String[0];
        }

        return pesajesCosechador.stream()
                .map(p -> {
                    String pagadoEl = "Impago";
                    if (p.getPagoPesaje() != null) {
                        pagadoEl = p.getPagoPesaje().getFecha().format(F);
                    }

                    return String.format("%-5d %-12s %-12s %-12.1f %-10.1f %-10.1f %-12s",
                            p.getId(),
                            p.getFechaHora().toLocalDate().format(F),
                            p.getCalidad(),
                            p.getCantidadKg(),
                            p.getPrecioKg(),
                            p.getMonto(),
                            pagadoEl
                    );
                })
                .toArray(String[]::new);

    }

    public String[] listPagosPesajes() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return pagosPesajes.stream()
                .map(pago -> {
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

                    return String.format("%d;%s;%.1f;%d;%s",
                            id, fecha, monto, numPesajes, rut
                    );
                })
                .toArray(String[]::new);
    }

    private void readDataFromTextFile(String path)
        throws FileNotFoundException, GestionHuertosException {
        DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Scanner leer = new Scanner(new File(path));
            leer.useDelimiter("[;\r\n]+");
            leer.useLocale(Locale.US);

            while (leer.hasNext()) {
                String token = leer.next().trim();
                if (token.isEmpty()) continue;

                if(token.startsWith("#")){
                    if(leer.hasNextLine()){
                        leer.nextLine();
                    }
                    continue;
                }


                String operacion = token;
                if(!leer.hasNextInt()){
                    throw new GestionHuertosException("Falta la cantidad de registros para la operación: " + operacion);
                }
                int n = leer.nextInt();

                for (int i = 0; i < n; i++) {
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
                }
            }

            leer.close();
    }


    private Optional<Cosechador> findCosechadorByRut(Rut rut){
        return cosechadores
                .stream()
                .filter(c -> c.getRut().getNumero() == rut.getNumero()
                        && c.getRut().getDv() == rut.getDv())
                .findFirst();
    }
    private Optional<Cultivo> findCultivoById(int id){
        return cultivos
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }
    private Optional<Propietario> findPropietarioByRut(Rut rut) {
        return propietarios
                .stream()
                .filter(p -> p.getRut().getNumero() == rut.getNumero()
                        && p.getRut().getDv() == rut.getDv())
                .findFirst();
    }
    private Optional<PlanCosecha> findPlanById(int id){
        return planes
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    private Optional<Huerto> findHuertoByName(String name){
        return huertos
                .stream()
                .filter(h -> h.getNombre().equalsIgnoreCase(name))
                .findFirst();
    }
    private Optional<Supervisor> findSupervisorByRut(Rut rut){
        return supervisores
                .stream()
                .filter(s -> s.getRut().getNumero() == rut.getNumero()
                        && s.getRut().getDv() == rut.getDv())
                .findFirst();
    }

    private Optional<Pesaje> findPesajeById(int id){
        return pesajes
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    private Optional<PagoPesaje>  findPagoPesajeById(int id){
        return pagosPesajes
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

}