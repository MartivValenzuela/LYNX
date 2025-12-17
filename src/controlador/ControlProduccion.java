package controlador;

import modelo.*;
import persistencia.GestionHuertosIO;
import utilidades.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ControlProduccion {

    private static ControlProduccion instance;

    private List<Persona> personas = new ArrayList<>();
    private List<PlanCosecha> planes = new ArrayList<>();
    private List<Cultivo> cultivos = new ArrayList<>();
    private List<Huerto> huertos = new ArrayList<>();
    private List<Pesaje> pesajes = new ArrayList<>();
    private List<PagoPesaje> pagosPesajes = new ArrayList<>();
    private DateTimeFormatter F = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final GestionHuertosIO io  = new GestionHuertosIO();

    public ControlProduccion() {
        try {
            readDataFromTextFile("archivoGestion.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ControlProduccion getInstance(){
        if(instance==null){
            instance = new ControlProduccion();
        }
        return instance;
    }

    public void readSystemData() throws GestionHuertosException {

        // limpiar colecciones
        this.personas.clear();
        this.cultivos.clear();
        this.planes.clear();
        this.huertos.clear();
        this.pesajes.clear();
        this.pagosPesajes.clear();

        // 1) Personas
        try {
            Persona[] personasLeidas = io.readPersonas();
            if (personasLeidas != null) {
                for (Persona p : personasLeidas) {
                    if (p != null) this.personas.add(p);
                }
            }
        } catch (GestionHuertosException e) {
            if (!e.getMessage().contains("no encontrado")) throw e;
        }

        // 2) Cultivos
        try {
            Cultivo[] cultivosLeidos = io.readCultivos();
            if (cultivosLeidos != null) {
                for (Cultivo c : cultivosLeidos) {
                    if (c != null) this.cultivos.add(c);
                }
            }
        } catch (GestionHuertosException e) {
            if (!e.getMessage().contains("no encontrado")) throw e;
        }

        // 3) Planes
        try {
            PlanCosecha[] planesLeidos = io.readPlanesCosecha();
            if (planesLeidos != null) {
                for (PlanCosecha p : planesLeidos) {
                    if (p != null) this.planes.add(p);
                }
            }
        } catch (GestionHuertosException e) {
            if (!e.getMessage().contains("no encontrado")) throw e;
        }

        // 4) Reconstruir huertos desde las personas (solo propietarios tienen huertos)
        for (Persona per : this.personas) {
            if (per instanceof Propietario) {
                Propietario prop = (Propietario) per;

                if (prop.getHuertos() != null) {
                    for (Huerto h : prop.getHuertos()) {
                        if (h != null) {
                            h.setPropietario(prop);
                            if (!this.huertos.contains(h)) this.huertos.add(h);
                        }
                    }
                }
            }
        }

        // 5) Reconstruir pesajes y pagos desde planes
        for (PlanCosecha plan : this.planes) {
            if (plan.getCuadrillas() != null) {
                for (Cuadrilla cuad : plan.getCuadrillas()) {
                    if (cuad != null && cuad.getAsignaciones() != null) {
                        for (CosechadorAsignado asig : cuad.getAsignaciones()) {
                            if (asig != null && asig.getPesajes() != null) {
                                for (Pesaje p : asig.getPesajes()) {
                                    if (p != null) {
                                        this.pesajes.add(p);

                                        if (p.getPagoPesaje() != null
                                                && !this.pagosPesajes.contains(p.getPagoPesaje())) {
                                            this.pagosPesajes.add(p.getPagoPesaje());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public void saveSystemData() throws GestionHuertosException {

        Persona[] arrPersonas = this.personas.toArray(new Persona[0]);
        Cultivo[] arrCultivos = this.cultivos.toArray(new Cultivo[0]);
        PlanCosecha[] arrPlanes = this.planes.toArray(new PlanCosecha[0]);

        io.savePersonas(arrPersonas);
        io.saveCultivos(arrCultivos);
        io.savePlanesCosecha(arrPlanes);
    }


    public void createPropietario(Rut rut, String nombre, String email, String dirParticular, String dirComercial)
            throws GestionHuertosException {
        if (findPersonaByRut(rut).isPresent()) {
            throw new GestionHuertosException("Ya existe una persona con el rut indicado");
        }
        personas.add(new Propietario(rut, nombre, email, dirParticular, dirComercial));
    }

    public void createSupervisor(Rut rut, String nombre, String email, String direccion, String profesion)
            throws GestionHuertosException {
        if (findPersonaByRut(rut).isPresent()) {
            throw new GestionHuertosException("Ya existe una persona con el rut indicado");
        }
        personas.add(new Supervisor(rut, nombre, email, direccion, profesion));
    }

    public void createCosechador(Rut rut, String nombre, String email, String direccion, LocalDate fechaNacimiento)
            throws GestionHuertosException {
        if (findPersonaByRut(rut).isPresent()) {
            throw new GestionHuertosException("Ya existe una persona con el rut indicado");
        }
        personas.add(new Cosechador(rut, nombre, email, direccion, fechaNacimiento));
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

        Cuadrilla c = Arrays.stream(plan.getCuadrillas())
                .filter(cuad -> cuad.getId() == idCuadrilla)
                .findFirst()
                .orElseThrow(() -> new GestionHuertosException("No existe una cuadrilla con el id indicado en este plan"));

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
        LocalDate hoy = asignar.getDesde();
        if (hoy.isBefore(asignar.getDesde()) || hoy.isAfter(asignar.getHasta())) {
            throw new GestionHuertosException("La fecha no está en el rango de la asignación del cosechador a la cuadrilla");
        }

        Pesaje nuevo = new Pesaje(id,cantidadKg,calidad,hoy.atStartOfDay(),asignar);
        this.pesajes.add(nuevo);
    }

    public double addPagoPesaje(int id, Rut rutCosechador) throws GestionHuertosException {
        if (findPagoPesajeById(id).isPresent()) {
            throw new GestionHuertosException("Ya existe un pago de pesaje con el id indicado");
        }

        if (findCosechadorByRut(rutCosechador).isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }

        List<Pesaje> pesajesAPagar = pesajes.stream()
                .filter(p -> p.getPagoPesaje() == null)
                .filter(p -> {
                    Rut r = p.getCosechadorAsignado().getCosechador().getRut();
                    return r.getNumero() == rutCosechador.getNumero() && r.getDv() == rutCosechador.getDv();
                })
                .collect(Collectors.toList());

        if (pesajesAPagar.isEmpty()) {
            throw new GestionHuertosException("El cosechador no tiene pesajes impagos");
        }

        PagoPesaje nuevoPago = new PagoPesaje(id, LocalDate.now(), pesajesAPagar);

        this.pagosPesajes.add(nuevoPago);
        return nuevoPago.getMonto();
    }

    public String[] getCuadrillasDeCosechadorDePlan(Rut rutCosechador) throws GestionHuertosException {
        Cosechador cosechador = findCosechadorByRut(rutCosechador)
                .orElseThrow(() -> new GestionHuertosException("No existe un cosechador con el rut indicado"));

        CosechadorAsignado[] asignaciones = cosechador.getAsignaciones();
        if (asignaciones == null || asignaciones.length == 0) {
            throw new GestionHuertosException("El cosechador no tiene cuadrillas disponibles para pesaje");
        }

        LocalDate hoy = LocalDate.now();

        String[] cuadrillasAptas = Arrays.stream(asignaciones)
                .filter(asign -> asign.getCuadrilla() != null &&
                        asign.getCuadrilla().getPlanCosecha() != null &&
                        asign.getCuadrilla().getPlanCosecha().getEstado() == EstadoPlan.EJECUTANDO)
                .filter(asign -> !hoy.isBefore(asign.getDesde()) && !hoy.isAfter(asign.getHasta()))
                .map(asign -> asign.getCuadrilla().getId() + ";" +
                        asign.getCuadrilla().getNombre() + ";" +
                        asign.getCuadrilla().getPlanCosecha().getId())
                .toArray(String[]::new);

        if (cuadrillasAptas.length == 0) {
            throw new GestionHuertosException("El cosechador no tiene cuadrillas disponibles para pesaje");
        }

        return cuadrillasAptas;
    }

    public String[] listCultivos() {
        return cultivos.stream()
                .sorted(Comparator.comparing(Cultivo::getEspecie, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Cultivo::getVariedad, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(cultivo -> {
                    long nCuarteles = huertos.stream()
                            .filter(h -> h.getCuarteles() != null)
                            .flatMap(h -> Arrays.stream(h.getCuarteles()))
                            .filter(c -> c.getCultivo() != null && c.getCultivo().getId() == cultivo.getId())
                            .count();

                    String especie = (cultivo.getEspecie() != null) ? cultivo.getEspecie() : "";
                    String variedad = (cultivo.getVariedad() != null) ? cultivo.getVariedad() : "";

                    return cultivo.getId() + "; " + especie + "; " + variedad + "; " +
                            String.format("%.1f", cultivo.getRendimiento()) + "; " + nCuarteles;
                })
                .toArray(String[]::new);
    }

    public String[] listHuertos(){
        if (huertos.isEmpty()) return new String[0];
        return huertos.stream()
                .map(h -> {
                    String propRut = (h.getPropietario() != null && h.getPropietario().getRut() != null) ?
                            h.getPropietario().getRut().toString() : "";
                    String nomProp = (h.getPropietario() != null && h.getPropietario().getNombre() != null) ?
                            h.getPropietario().getNombre() : "";
                    int nCuart = (h.getCuarteles() == null) ? 0 : h.getCuarteles().length;

                    return h.getNombre() + "; " + String.format("%.1f", h.getSuperficie()) + "; " +
                            h.getUbicacion() + "; " + propRut + "; " + nomProp + "; " + nCuart;
                })
                .toArray(String[]::new);
    }

    public String[] listPropietarios(){
        return personas.stream()
                .filter(p -> p instanceof Propietario)
                .map(p -> (Propietario) p)
                .sorted(Comparator.comparing(Propietario::getNombre, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(prop -> {
                    int nrHuertos = (prop.getHuertos() == null) ? 0 : prop.getHuertos().length;
                    return prop.getRut() + "; " + prop.getNombre() + "; " + prop.getDireccion() + "; " +
                            prop.getEmail() + "; " + prop.getDirComercial() + "; " + nrHuertos;
                })
                .toArray(String[]::new);
    }

    public String[] listCosechadores() {
        return personas.stream()
                .filter(p -> p instanceof Cosechador)
                .map(p -> (Cosechador) p)
                .sorted((c1, c2) -> {
                    double m1 = (c1.getAsignaciones() == null) ? 0.0 :
                            Arrays.stream(c1.getAsignaciones())
                                    .mapToDouble(CosechadorAsignado::getMontoPesajesImpagos).sum();
                    double m2 = (c2.getAsignaciones() == null) ? 0.0 :
                            Arrays.stream(c2.getAsignaciones())
                                    .mapToDouble(CosechadorAsignado::getMontoPesajesImpagos).sum();
                    return Double.compare(m2, m1);
                })
                .map(c -> {
                    int nCuadrillas = (c.getCuadrillas() == null) ? 0 : c.getCuadrillas().length;
                    String fNac = (c.getFechaNacimiento() == null) ? "" : c.getFechaNacimiento().toString();

                    double montoImpago = (c.getAsignaciones() == null) ? 0.0 :
                            Arrays.stream(c.getAsignaciones())
                                    .mapToDouble(CosechadorAsignado::getMontoPesajesImpagos).sum();

                    double montoPagado = (c.getAsignaciones() == null) ? 0.0 :
                            Arrays.stream(c.getAsignaciones())
                                    .mapToDouble(CosechadorAsignado::getMontoPesajesPagados).sum();

                    return c.getRut() + "; " + c.getNombre() + "; " + c.getDireccion() + "; " +
                            c.getEmail() + "; " + fNac + "; " + nCuadrillas + "; " +
                            String.format("%.1f", montoImpago) + "; " + String.format("%.1f", montoPagado);
                })
                .toArray(String[]::new);
    }

    public String [] listSupervisores(){
        return personas.stream()
                .filter(p -> p instanceof Supervisor)
                .map(p -> (Supervisor) p)
                .sorted((s1, s2) -> {
                    double k1 = (s1.getCuadrilla() != null) ? s1.getCuadrilla().getKilosPesados() : 0.0;
                    double k2 = (s2.getCuadrilla() != null) ? s2.getCuadrilla().getKilosPesados() : 0.0;
                    return Double.compare(k2, k1);
                })
                .map(s -> {
                    String cuadNom;
                    double kilosPesados = 0.0;
                    int nroPesajesImpagos = 0;
                    Cuadrilla cuad = s.getCuadrilla();
                    if(cuad == null){
                        cuadNom = "S/A";
                    } else{
                        cuadNom = cuad.getNombre();
                        kilosPesados = cuad.getKilosPesados();
                        if(cuad.getAsignaciones() != null){
                            nroPesajesImpagos = Arrays.stream(cuad.getAsignaciones())
                                    .mapToInt(CosechadorAsignado::getNroPesajesImpagos)
                                    .sum();
                        }
                    }
                    return s.getRut() + "; " + s.getNombre() + "; " + s.getDireccion() + "; " +
                            s.getEmail() + "; " + s.getProfesion() + "; " + cuadNom + "; " +
                            String.format("%.1f", kilosPesados) + "; " + nroPesajesImpagos;
                })
                .toArray(String[]::new);
    }

    public String[] listPlanesCosecha(){
        return planes.stream()
                .sorted(Comparator.comparing(PlanCosecha::getEstado)
                        .thenComparing(PlanCosecha::getMetaKilos))
                .map(p -> {
                    String huerto = (p.getCuartel() != null && p.getCuartel().getHuerto() != null) ?
                            p.getCuartel().getHuerto().getNombre() : "";
                    int idCuartel = (p.getCuartel() != null) ? p.getCuartel().getId() : 0;
                    int nCuadrillas = (p.getCuadrillas() == null) ? 0 : p.getCuadrillas().length;

                    return p.getId() + "; " + p.getNombre() + "; " + p.getInicio().format(F) + "; " +
                            p.getFinEstimado().format(F) + "; " + String.format("%.1f", p.getMetaKilos()) + "; " +
                            String.format("%.1f", p.getPrecioBaseKilo()) + "; " + p.getEstado() + "; " +
                            idCuartel + "; " + huerto + "; " + nCuadrillas;
                })
                .toArray(String[]::new);
    }

    public String[] listPesajes() {
        return pesajes.stream()
                .map(p -> {
                    String pagadoEl = (p.getPagoPesaje() != null) ? p.getPagoPesaje().getFecha().format(F) : "Impago";
                    return p.getId() + "; " + p.getFechaHora().toLocalDate().format(F) + "; " +
                            p.getCosechadorAsignado().getCosechador().getRut().toString() + "; " +
                            p.getCalidad() + "; " + String.format("%.1f", p.getCantidadKg()) + "; " +
                            String.format("%.1f", p.getPrecioKg()) + "; " + String.format("%.1f", p.getMonto()) + "; " + pagadoEl;
                })
                .toArray(String[]::new);
    }

    public String[] listPesajesCosechador(Rut rutCosechador) throws GestionHuertosException{
        Optional<Cosechador> optCosechador = findCosechadorByRut(rutCosechador);
        if (optCosechador.isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }

        return pesajes.stream()
                .filter(p -> {
                    Rut r = p.getCosechadorAsignado().getCosechador().getRut();
                    return r.getNumero() == rutCosechador.getNumero() && r.getDv() == rutCosechador.getDv();
                })
                .map(p -> {
                    String pagadoEl = (p.getPagoPesaje() != null) ? p.getPagoPesaje().getFecha().format(F) : "Impago";
                    return p.getId() + "; " + p.getFechaHora().toLocalDate().format(F) + "; " +
                            p.getCalidad() + "; " + String.format("%.1f", p.getCantidadKg()) + "; " +
                            String.format("%.1f", p.getPrecioKg()) + "; " + String.format("%.1f", p.getMonto()) + "; " + pagadoEl;
                })
                .toArray(String[]::new);
    }

    public String[] listPagosPesajes() {
        return pagosPesajes.stream()
                .map(pago -> {
                    int id = pago.getId();
                    String fecha = pago.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    double monto = pago.getMonto();
                    int numPesajes = pago.getPesajes().length;
                    String rut = "S/D";
                    if (numPesajes > 0 && pago.getPesajes()[0] != null &&
                            pago.getPesajes()[0].getCosechadorAsignado() != null &&
                            pago.getPesajes()[0].getCosechadorAsignado().getCosechador() != null) {
                        rut = pago.getPesajes()[0].getCosechadorAsignado().getCosechador().getRut().toString();
                    }
                    return id + "; " + fecha + "; " + String.format("%.1f", monto) + "; " + numPesajes + "; " + rut;
                })
                .toArray(String[]::new);
    }

    private void readDataFromTextFile(String path) throws FileNotFoundException, GestionHuertosException {
        DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner leer = new Scanner(new File(path));
        leer.useDelimiter("[;\r\n]+");
        leer.useLocale(Locale.US);

        while (leer.hasNext()) {
            String token = leer.next().trim();
            if (token.isEmpty()) continue;
            if(token.startsWith("#")){
                if(leer.hasNextLine()) leer.nextLine();
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

    private Optional<Persona> findPersonaByRut(Rut rut) {
        return personas.stream()
                .filter(p -> p.getRut().getNumero() == rut.getNumero() && p.getRut().getDv() == rut.getDv())
                .findFirst();
    }

    private Optional<Cosechador> findCosechadorByRut(Rut rut){
        return personas.stream()
                .filter(p -> p instanceof Cosechador)
                .map(p -> (Cosechador) p)
                .filter(c -> c.getRut().getNumero() == rut.getNumero() && c.getRut().getDv() == rut.getDv())
                .findFirst();
    }

    private Optional<Cultivo> findCultivoById(int id){
        return cultivos.stream().filter(c -> c.getId() == id).findFirst();
    }

    private Optional<Propietario> findPropietarioByRut(Rut rut){
        return personas.stream()
                .filter(p -> p instanceof Propietario)
                .map(p -> (Propietario) p)
                .filter(prop -> prop.getRut().getNumero() == rut.getNumero() && prop.getRut().getDv() == rut.getDv())
                .findFirst();
    }

    private Optional<PlanCosecha> findPlanById(int id){
        return planes.stream().filter(p -> p.getId() == id).findFirst();
    }

    private Optional<Huerto> findHuertoByName(String name){
        return huertos.stream().filter(h -> h.getNombre().equalsIgnoreCase(name)).findFirst();
    }

    private Optional<Supervisor> findSupervisorByRut(Rut rut){
        return personas.stream()
                .filter(p -> p instanceof Supervisor)
                .map(p -> (Supervisor) p)
                .filter(s -> s.getRut().getNumero() == rut.getNumero() && s.getRut().getDv() == rut.getDv())
                .findFirst();
    }

    private Optional<Pesaje> findPesajeById(int id){
        return pesajes.stream().filter(p -> p.getId() == id).findFirst();
    }

    private Optional<PagoPesaje> findPagoPesajeById(int id){
        return pagosPesajes.stream().filter(p -> p.getId() == id).findFirst();
    }
}