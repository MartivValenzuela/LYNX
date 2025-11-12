package vista;

import controlador.ControlProduccion;
import utilidades.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class GestionHuertosUI {
    private final Scanner tcld = new Scanner(System.in);
    private final DateTimeFormatter F = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final ControlProduccion control = new ControlProduccion();

    //Implementacion del singleton
    private static GestionHuertosUI instance;

    public static GestionHuertosUI getInstance() {
        if (instance == null) {
            return instance = new GestionHuertosUI();
        }
        return instance;
    }


    public void menu() {
        while (true) {
            try {
                System.out.println("::: MENU PRINCIPAL :::");
                System.out.println("1. Crear Personas");
                System.out.println("2. Menu Huertos");
                System.out.println("3. Menú Planes de Cosecha");
                System.out.println("4. Menú listados");
                System.out.println("5. Salir");
                System.out.print("Opcion:");
                int opcion = tcld.nextInt();
                if (opcion < 0 || opcion > 5) {
                    System.out.println("La opcion no esta en el rango valido");
                    break;
                }

                switch (opcion) {
                    case 1:
                        CreaPersona();
                        break;
                    case 2:
                        menuHuertos();
                        break;
                    case 3:
                        menuPlanesCosecha();
                        break;
                    case 4:
                        menuListados();
                        break;
                    case 5:
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("opcion no valida,intente de nuevo");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }

        }
    }

    private void menuHuertos() {
        while (true) {
            try {
                System.out.println(">>> SUBMENU HUERTOS <<<");
                System.out.println("1. Crear Cultivo");
                System.out.println("2. Crear Huerto");
                System.out.println("3. Agregar Cuarteles a Huerto");
                System.out.println("4. Cambiar Estado Cuartel");
                System.out.println("5. Volver");
                System.out.print("Opcion: ");
                int opcionSubmenus = tcld.nextInt();
                if (opcionSubmenus < 0 || opcionSubmenus > 6) {
                    System.out.println("La opcion no esta en el rango valido");
                    continue;
                }
                switch (opcionSubmenus) {
                    case 1:
                        creaCultivo();
                        break;
                    case 2:
                        creaHuerto();
                        break;
                    case 3:
                        agregaCuartelesAlHuerto();
                        break;
                    case 4:
                        CambiarEstadoCuartel();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("La opcion no esta en el rango valido");
                }
                opcionSubmenus = tcld.nextInt();
                if (opcionSubmenus < 0 || opcionSubmenus > 6) {
                    System.out.println("La opcion no esta en el rango valido");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Error!  " + e);
            }
        }
    }

    private void menuPlanesCosecha() {
        while (true) {
            System.out.println(">>> SUBMENU PLANES DE COSECHA <<<");
            System.out.println("1. Crear Plan de Cosecha");
            System.out.println("2. Cambiar Estado de Plan");
            System.out.println("3. Agregar Cuadrillas a Plan");
            System.out.println("4. Agregar Cosechadores a Cuadrilla");
            System.out.println("5. Agregar Pesaje a Cosechador");
            System.out.println("6. Pagar Pesajes Impagos de Cosechador");
            System.out.println("7. Volver");
            System.out.print("Opcion: ");
            int opcionSubmenus = tcld.nextInt();
            if (opcionSubmenus < 0 || opcionSubmenus > 7) {
                System.out.println("La opcion no esta en el rango valido");
                continue;
            }
            switch (opcionSubmenus) {
                case 1:
                    creaPlanDeCosecha();
                    break;
                case 2:
                    CambiarEstadoPlan();
                    break;
                case 3:
                    AgregaCuadrillasAPlan();
                    break;
                case 4:
                    asignaCosechadoresAPlan();
                    break;
                case 5:
                    AgregarPesaje();
                    break;
                case 6:
                    PagarPesajes();
                    break;
                case 7:
                    return;

                default:
                    System.out.println("La opcion no esta en el rango valido");
            }
        }

    }

    private void menuListados() {
        while (true) {
            System.out.println(">>> SUBMENU LISTADOS <<<");
            System.out.println("1. Listado de Propietarios");
            System.out.println("2. Listado de Supervisores");
            System.out.println("3. Listado de Cosechadores");
            System.out.println("4. Listado de Cultivos");
            System.out.println("5. Listado de Huertos");
            System.out.println("6. Listado de Planes de Cosecha");
            System.out.println("7. Listado Pesajes");
            System.out.println("8. Listado Pesajes de un Cosechador");
            System.out.println("9. Listado de Pagos");
            System.out.println("10. Volver");
            System.out.print("Opcion: ");
            int opcionSubmenus = tcld.nextInt();
            if (opcionSubmenus < 0 || opcionSubmenus > 10) {
                System.out.println("La opcion no esta en el rango valido");
                continue;
            }
            switch (opcionSubmenus) {
                case 1:
                    listapropietarios();
                    break;
                case 2:
                    listasupervisores();
                    break;
                case 3:
                    listacosechadores();
                    break;
                case 4:
                    listaCultivos();
                    break;
                case 5:
                    listaHuertos();
                    break;
                case 6:
                    listaPlanesCosecha();
                    break;
                case 7:
                    listaPersonas();
                    break;
                case 8:
                    uiListaPesajesCosechador();
                    break;
                case 9:
                    ListaPagos();
                    break;
                case 10:
                    return;
                default:
                    System.out.println("La opcion no esta en el rango valido");
            }

        }

    }

    private void CreaPersona() {
        try {
            System.out.println("Creando una persona...");
            System.out.println("Rol persona (1=Propietario, 2=Supervisor, 3=Cosechador):");
            String rol = tcld.next();
            System.out.print("rut:");
            Rut rut = Rut.of(tcld.next().trim());
            System.out.print("Nombre:");
            String nombre = tcld.next().trim();
            System.out.print("Email: ");
            String email = tcld.next().trim();
            System.out.print("Dirección: ");
            String direccion = tcld.nextLine().trim();
            if (rol.equals("1")) {
                System.out.print("Direccion Comercial:");
                String DireccionComer = tcld.next().trim();
                control.createPropietario(rut, nombre, email, direccion, DireccionComer);
                System.out.println("Propietario creado exitosamente");
            } else if (rol.equals("2")) {
                System.out.print("Profesion:");
                String profesion = tcld.next().trim();
                control.createSupervisor(rut, nombre, email, direccion, profesion);
                System.out.println("Supervisor creado exitosamente");

            } else if (rol.equals("3")) {
                System.out.print("Fecha Nacimiento (dd/mm/aaaa): ");
                LocalDate fechaNacimiento = LocalDate.parse(tcld.next().trim(), F);
                control.createCosechador(rut, nombre, email, direccion, fechaNacimiento);
                System.out.println("Cosechador creado exitosamente");
            } else {
                System.out.println("rol persona no valido");
            }
        } catch (Exception e) {
            System.out.println("Error!" + e);
        }
    }

    private void creaCultivo() {
        try {
            System.out.println("Creando un cultivo...");
            System.out.println("Identificación: ");
            int id = tcld.nextInt();
            tcld.nextLine();
            System.out.println("Especie: ");
            String especie = tcld.next().trim();
            System.out.println("Variedad: ");
            String variedad = tcld.next().trim();
            System.out.println("Rendimiento (Use coma para separador decimal): ");
            float rendimiento = tcld.nextFloat();
            tcld.nextLine();
            control.createCultivo(id, especie, variedad, rendimiento);
        } catch (GestionHuertosException e) {
            System.out.println("Error! " + e);
        }

    }

    private void creaHuerto() {
        try {
            System.out.println("Creando un huerto...");
            System.out.println("Nombre: ");
            String nombre = tcld.nextLine().trim();
            System.out.println("Superficie: ");
            float superficie = tcld.nextFloat();
            tcld.nextLine();
            System.out.println("Ubicacion: ");
            String ubicacion = tcld.next().trim();
            System.out.println("Rut propietario: ");

            Rut rut = Rut.of(tcld.next().trim());
            control.createHuerto(nombre, superficie, ubicacion, rut);
            System.out.println("Agregar cuarteles al huerto");
            System.out.println("Nro. de cuarteles: ");
            int n = tcld.nextInt();
            tcld.nextLine();
            for (int i = 0; i < n; i++) {
                System.out.println("----Cuartel #" + (i + 1));
                System.out.println("Id cuartel: ");
                int idCuartel = tcld.nextInt();
                tcld.nextLine();
                System.out.println("Superficie cuartel: ");
                float superficieCuartel = tcld.nextFloat();
                tcld.nextLine();
                System.out.println("Id cultivo del cuartel: ");
                int idCultivo = tcld.nextInt();
                tcld.nextLine();
                control.addCuartelToHuerto(nombre, idCuartel, superficieCuartel, idCultivo);
            }
        } catch (GestionHuertosException e) {
            System.out.println("Error! " + e);
        }
    }

    private void agregaCuartelesAlHuerto() {
        try {
            System.out.println("Agregando cuarteles a un huerto...");
            System.out.print("Nombre del huerto:");
            String nombreHuerto = tcld.nextLine().trim();
            System.out.println("Numeros de cuarteles a Agregar");
            int num = tcld.nextInt();
            tcld.nextLine();
            for (int i = 0; i < num; i++) {
                try {
                    System.out.println("----Cuartel #" + (i + 1));
                    System.out.print("Id cuartel: ");
                    int idCuartel = tcld.nextInt();
                    tcld.nextLine();
                    System.out.print("Superficie cuartel: ");
                    float superficieCuartel = tcld.nextFloat();
                    tcld.nextLine();
                    System.out.print("Id cultivo del cuartel: ");
                    int idCultivo = tcld.nextInt();
                    tcld.nextLine();
                    control.addCuartelToHuerto(nombreHuerto, idCuartel, superficieCuartel, idCultivo);
                } catch (GestionHuertosException e) {
                    System.out.println("Error al agregar cuartel" + i + "Error " + e);
                }

            }
        } catch (GestionHuertosException e) {
            System.out.println("Error!" + e);
        }
    }

    private void CambiarEstadoCuartel() {
        try {
            System.out.println("Cambiando estado de un cuartel...");
            System.out.print("Nombre del Huerto: ");
            String nombreHuerto = tcld.next().trim();
            System.out.print("Id cuartel: ");
            int idCuartel = tcld.nextInt();
            tcld.nextLine();
            System.out.println("Nuevo Estado de Cuartel: [1=Reposo invernal, 2=Floracion, 3=Cuaja, 4=Fructificacion, 5=Maduracion, 6=Cosecha, 7=Postcosecha]");
            System.out.print("Opcion:");
            int opestado = tcld.nextInt();
            tcld.nextLine();
            EstadoFonologico estado = EstadoFonologico.values()[opestado - 1];
            control.changeEstadoCuartel(nombreHuerto, idCuartel, estado);
            System.out.println("Estado del cuartel cambiado exitosamente");
        } catch (GestionHuertosException e) {
            System.out.println("Error!" + e);
        }
    }

    private void creaPlanDeCosecha() {
        try {
            System.out.println("Creando un plan de cosecha....");
            System.out.println("Id del plan: ");
            int id = tcld.nextInt();
            tcld.nextLine();
            System.out.println("Nombre del plan: ");
            String nombre = tcld.next().trim();
            System.out.println("Fecha de inicio (dd/mm/aaaa): ");
            LocalDate inicio = LocalDate.parse(tcld.next().trim(), F);
            System.out.println("Fecha de termino (dd/mm/aaaa): ");
            LocalDate termino = LocalDate.parse(tcld.next().trim(), F);
            System.out.println("Meta (kilos): ");
            double metas = tcld.nextDouble();
            tcld.nextLine();
            System.out.println("Precio base por kilos: ");
            float precioBase = tcld.nextFloat();
            tcld.nextLine();
            System.out.println("Nombre Huerto: ");
            String nombreHuerto = tcld.next().trim();
            System.out.println("Id cuartel: ");
            int cuartel = tcld.nextInt();
            tcld.nextLine();

            control.createPlanCosecha(id, nombre, inicio, termino, metas, precioBase, nombreHuerto, cuartel);
            System.out.println("Plan de cosecha creado exitosamente");

        } catch (GestionHuertosException e) {
            System.out.println("Error!" + e);
        }

    }

    private void AgregaCuadrillasAPlan() {
        try {
            System.out.println("Agregando cuadrillas al plan de cosecha");
            System.out.print("Id del plan:");
            int id = tcld.nextInt();
            System.out.println("Nro. de cuadrillas: ");
            int nroCuadrillas = tcld.nextInt();
            tcld.nextLine();
            for (int i = 0; i < nroCuadrillas; i++) {
                System.out.println("------Cuadrilla #" + (i + 1));
                System.out.println("Id cuadrilla: ");
                int idCuadrilla = tcld.nextInt();
                tcld.nextLine();
                System.out.println("Nombre cuadrilla: ");
                String nombreCuadrilla = tcld.next().trim();
                System.out.println("Rut supervisor: ");
                Rut rutSupervisor = Rut.of(tcld.next().trim());

                control.addCuadrillaToPlan(id, idCuadrilla, nombreCuadrilla, rutSupervisor);
            }
        } catch (GestionHuertosException e) {
            System.out.println("Error!" + e);
        }
    }

    private void asignaCosechadoresAPlan() {
        try {
            System.out.println("Asignando cosechadores a un plan de cosecha...");
            System.out.println("Id del plan: ");
            int id = tcld.nextInt();
            tcld.nextLine();
            System.out.println("Id cuadrilla: ");
            int idCuadrilla = tcld.nextInt();
            tcld.nextLine();
            System.out.println("Nro. cosechadores a asignar: ");
            int nroCosechadores = tcld.nextInt();
            tcld.nextLine();

            for (int i = 0; i < nroCosechadores; i++) {
                System.out.println("Fecha de inicio asignación (dd/mm/aaaa)");
                LocalDate inicio = LocalDate.parse(tcld.next().trim(), F);
                System.out.println("Fecha de termino asignación (dd/mm/aaaa)");
                LocalDate termino = LocalDate.parse(tcld.next().trim(), F);
                System.out.println("Meta (Kilos): ");
                double metas = tcld.nextDouble();
                tcld.nextLine();
                System.out.println("Rut cosechador: ");
                Rut rutCosechador = Rut.of(tcld.next().trim());
                control.addCosechadorToCuadrilla(id, idCuadrilla, inicio, termino, metas, rutCosechador);

            }
        } catch (GestionHuertosException e) {
            System.out.println("Error " + e);
        }


    }

    private void AgregarPesaje(){
        try{
            System.out.println("Agregando pesaje a un cosechador...");
            System.out.print("Id pesaje: ");
            int idPesaje = tcld.nextInt();
            tcld.nextLine();
            System.out.print("Rut Cosechador: ");
            Rut rutCosechador = Rut.of(tcld.next().trim());
            System.out.print("Id plan: ");
            int idPlan = tcld.nextInt();
            tcld.nextLine();
            System.out.print("Id cuadrilla: ");
            int idCuadrilla = tcld.nextInt();
            tcld.nextLine();
            System.out.print("Cantidad de kilos: ");
            float cantidadKg = tcld.nextFloat();
            tcld.nextLine();
            System.out.println("Calidad: [1=Excelente, 2=Suficiente, 3=Deficiente]");
            System.out.print("Opcion: ");
            int opCalidad = tcld.nextInt();
            tcld.nextLine();
            Calidad calidad = Calidad.values()[opCalidad - 1];
            control.addPesaje(idPesaje,rutCosechador,idPlan,idCuadrilla,cantidadKg,calidad);
            System.out.println("Pesaje agregado exitosamente al cosechador");
        }catch (GestionHuertosException e){
            System.out.println("Error!" + e);
        }
    }


    private void CambiarEstadoPlan() {
        try {
            System.out.println("Cambiando estado de un plan...");
            System.out.print("Id Plan:");
            int IdPlan = tcld.nextInt();
            tcld.nextLine();
            System.out.println("Nuevo Estado del plan: [1=Planificado, 2=Ejecutando, 3=Cerrado, 4=Cancelado]");
            System.out.print("Opcion:");
            int opEstado = tcld.nextInt();
            tcld.nextLine();
            EstadoPlan estado = EstadoPlan.values()[opEstado - 1];
            control.changeEstadoPlan(IdPlan, estado);
            System.out.println("Estado del plan cambiado exitosamente");

        } catch (GestionHuertosException e) {
            System.out.println("Error!" + e);
        }
    }

    private void PagarPesajes(){
        try{
            System.out.println("Pagando pesajes pendientes de un cosechador...");
            System.out.print("Id pago pesaje: ");
            int idPago = tcld.nextInt();
            tcld.nextLine();
            System.out.print("Rut cosechador: ");
            Rut rutCosechador = Rut.of(tcld.next().trim());

            double monto = control.addPagoPesaje(idPago, rutCosechador);
            System.out.printf("Monto pagado al cosechador: $%.1f\n", monto);
        }catch (GestionHuertosException e){
            System.out.println(e);
        }
    }

    private void listaCultivos () {
        System.out.println("LISTADO DE CULTIVOS");
        System.out.println("-------------------");
        System.out.printf("%-6s %-15s %-20s %-15s %-15s%n",
                "Id", "Especie", "Variedad", "Rendimiento", "Nro. cuarteles");

        String[] v = control.listCultivos();
        if (v.length == 0) {
            System.out.println("No existen cultivos registrados.");
        } else {
            for (String s : v) System.out.println(s);
        }
        System.out.println();
    }

    private void listaHuertos () {
        System.out.println("LISTADO DE HUERTOS");
        System.out.println("------------------");
        System.out.printf("%-20s %-12s %-30s %-18s %-25s %-15s%n",
                "Nombre", "Superficie", "Ubicación",
                "Rut propietario", "Nombre propietario", "Nro. cuarteles");

        String[] v = control.listHuertos();
        if (v.length == 0) {
            System.out.println("No existen huertos registrados.");
        } else {
            for (String s : v) System.out.println(s);
        }
        System.out.println();
    }

    private void listaPersonas () {
        // === PROPIETARIOS ===
        System.out.println("LISTADO DE PROPIETARIOS");
        System.out.println("-----------------------");
        System.out.printf("%-14s %-28s %-28s %-30s %-22s %-12s%n",
                "Rut", "Nombre", "Dirección", "email", "Dirección comercial", "Nro. huertos");

        String[] vp = control.listPropietarios();
        if (vp.length == 0) {
            System.out.println("No existen propietarios registrados.");
        } else {
            for (String s : vp) System.out.println(s);
        }
        System.out.println();

        // === SUPERVISORES ===
        System.out.println("LISTADO DE SUPERVISORES");
        System.out.println("-----------------------");
        System.out.printf("%-14s %-28s %-28s %-30s %-14s %-18s%n",
                "Rut", "Nombre", "Dirección", "email", "Profesión", "Nombre cuadrilla");

        String[] vs = control.listSupervisores();
        if (vs.length == 0) {
            System.out.println("No existen supervisores registrados.");
        } else {
            for (String s : vs) System.out.println(s);
        }
        System.out.println();

        // === COSECHADORES ===
        System.out.println("LISTADO DE COSECHADORES");
        System.out.println("-----------------------");
        System.out.printf("%-14s %-28s %-28s %-30s %-16s %-16s%n",
                "Rut", "Nombre", "Dirección", "email", "Fecha nacimiento", "Nro. cuadrillas");

        String[] vc = control.listCosechadores();
        if (vc.length == 0) {
            System.out.println("No existen cosechadores registrados.");
        } else {
            for (String s : vc) System.out.println(s);
        }
        System.out.println();
    }

    private void listaPlanesCosecha () {
        System.out.println("LISTADO DE PLANES DE COSECHA");
        System.out.println("-----------------------------");
        System.out.printf("%-8s %-20s %-14s %-14s %-12s %-16s %-14s %-10s %-20s %-12s%n",
                "Id", "Nombre", "Fecha inicio", "Fecha término",
                "Meta (kg)", "Precio base (kg)", "Estado",
                "Id cuartel", "Nombre huerto", "Nro. cuadrillas");

        String[] planes = control.listPlanesCosecha();
        if (planes.length == 0) {
            System.out.println("No existen planes de cosecha registrados.");
        } else {
            for (String p : planes) System.out.println(p);
        }
        System.out.println();
    }

    private void listaPesajes(){
        System.out.println("\n LISTADO DE PESAJES");
        System.out.println("----------------------");
        System.out.printf("%-5s %-12s %-15s %-12s %-12s %-10s %-10s %-12s%n", "Id", "Fecha", "Rut Cosechador", "Calidad", "Cantidad Kg", "Precio $", "Monto $", "Pagado el");
        String[] v = control.listPesajes();
        if (v.length == 0) {
            System.out.println("No existen pesajes registrados.");
        } else {
            for (String s : v) System.out.println(s);
        }
    }

    private void uiListaPesajesCosechador() {
        try {
            System.out.println("\nLISTADO DE PESAJES DEL COSECHADOR");
            System.out.print("Rut cosechador: ");
            Rut rutCosechador = Rut.of(tcld.next().trim());

            System.out.println("---------------------------------");
            System.out.printf("%-5s %-12s %-12s %-12s %-10s %-10s %-12s%n", "Id", "Fecha", "Calidad", "Cantidad Kg", "Precio $", "Monto $", "Pagado el");

            String[] v = control.listPesajesCosechador(rutCosechador);
            if (v.length == 0) {
                System.out.println("El cosechador no tiene pesajes registrados.");
            } else {
                for (String s : v) System.out.println(s);
            }
        } catch (Exception e){
            System.out.println("ERROR: Datos de entrada no válidos. " + e.getMessage());
        }
    }
    private void ListaPagos(){
        System.out.println("\nLISTADO DE PAGOS DE PESAJES");
        System.out.println("---------------------------");
        System.out.printf("%-5s %-12s %-12s %-10s %-15s%n", "Id", "Fecha", "Monto $", "Nro. Pesajes", "Rut Cosechador");

        String[] v = control.listPagosPesajes();
        if (v.length == 0) {
            System.out.println("No existen pagos registrados.");
        } else {
            for (String s : v) System.out.println(s);
        }
    }
    private void listapropietarios() {
        System.out.println("\nLISTADO DE PROPIETARIOS");
        System.out.println("-----------------------");
        System.out.printf("%-14s %-28s %-30s %-30s %-22s %-12s%n", "Rut", "Nombre", "Dirección", "email", "Dirección comercial", "Nro. huertos");

        String[] vp = control.listPropietarios();
        if (vp.length == 0) {
            System.out.println("No existen propietarios registrados.");
        } else {
            for (String s : vp) System.out.println(s);
        }
        System.out.println();
    }

    private void listasupervisores() {
        System.out.println("\nLISTADO DE SUPERVISORES");
        System.out.println("-----------------------");

        System.out.printf("%-14s %-28s %-30s %-28s %-16s %-18s %-12s %-12s%n", "Rut", "Nombre", "Dirección", "email", "Profesión", "Nom.cuadrilla", "Kg pesados", "#Pjes.impagos");

        String[] vs = control.listSupervisores();
        if (vs.length == 0) {
            System.out.println("No existen supervisores registrados.");
        } else {
            for (String s : vs) System.out.println(s);
        }
        System.out.println();
    }

    private void listacosechadores() {
        System.out.println("\nLISTADO DE COSECHADORES");
        System.out.println("-----------------------");
        // Formato actualizado según Fig. 20
        System.out.printf("%-14s %-28s %-30s %-30s %-16s %-16s %-16s %-16s%n", "Rut", "Nombre", "Dirección", "email", "Fecha Nac.", "Nro. Cuadrillas", "Monto impago $", "Monto pagado $");

        String[] vc = control.listCosechadores();
        if (vc.length == 0) {
            System.out.println("No existen cosechadores registrados.");
        } else {
            for (String s : vc) System.out.println(s);
        }
        System.out.println();
    }

}
