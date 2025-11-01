package vista;
import controlador.ControlProduccion;
import utilidades.Rut;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestionHuertosUI {
    private final Scanner tcld = new Scanner(System.in);
    private final DateTimeFormatter F = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final ControlProduccion control = new ControlProduccion();

    //Implementacion del singleton
    private static GestionHuertosUI instance;

    public static GestionHuertosUI getInstance(){
        if (instance == null){
            return instance = new GestionHuertosUI();
        }
        return instance;
    }



    public void menu(){
        while(true){
            try {
                System.out.println("::: MENU PRINCIPAL :::");
                System.out.println("1. Crear Personas");
                System.out.println("2. Menu Huertos");
                System.out.println("3. Menú Planes de Cosecha");
                System.out.println("4. Menú listados");
                System.out.println("5. Salir");
                System.out.print("Opcion:");
                int opcion = tcld.nextInt();

                if (opcion < 0 || opcion>5){
                    System.out.println("La opcion no esta en el rango valido");
                    break;
                }

                switch (opcion){
                    case 1: uiCreaPersona(); break;
                    case 2: menuHuertos(); break;
                    case 3: menuPlanesCosecha();
                    case 4: menuListados();
                    case 5: System.out.println("Saliendo del programa..."); break;
                    default:
                        System.out.println("opcion no valida,intente de nuevo");
                }
            }catch (Exception e){
                System.out.println("ERROR: " + e.getMessage());
            }

        }
    }

    private void menuHuertos(){
        while (true){
            try{
                System.out.println(">>> SUBMENU HUERTOS <<<");
                System.out.println("1. Crear Cultivo");
                System.out.println("2. Crear Huerto");
                System.out.println("3. Agregar Cuarteles a Huerto");
                System.out.println("4. Cambiar Estado Cuartel");
                System.out.println("5. Volver");
                System.out.print("Opcion: ");
                int opcionSubmenus = tcld.nextInt();
                if (opcionSubmenus < 0 || opcionSubmenus > 6){
                    System.out.println("La opcion no esta en el rango valido");
                    continue;
                }
                switch (opcionSubmenus){
                    case 1: creaCultivo();break;
                    case 2: creaHuerto();break;
                    case 3: break;
                    case 4: break;
                    case 5: menu();
                    default:System.out.println("La opcion no esta en el rango valido");
                }
                opcionSubmenus = tcld.nextInt();
                if (opcionSubmenus < 0 || opcionSubmenus > 6){
                    System.out.println("La opcion no esta en el rango valido");
                    continue;
                }
            }catch (Exception e){
                System.out.println("Error!  " +e);
            }
        }
    }

    private void menuPlanesCosecha(){
        while (true){
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
            if (opcionSubmenus < 0 || opcionSubmenus > 7){
                System.out.println("La opcion no esta en el rango valido");
                continue;
            }
            switch (opcionSubmenus){
                case 1: creaPlanDeCosecha();break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6: break;
                case 7: menu(); break;
                default:System.out.println("La opcion no esta en el rango valido");
            }
        }

    }

    private void menuListados(){
        while (true){
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
            if (opcionSubmenus < 0 || opcionSubmenus > 10){
                System.out.println("La opcion no esta en el rango valido");
                continue;
            }
            switch (opcionSubmenus){
                case 1: break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6: break;
                case 7: menu(); break;
                case 8: break;
                case 9: break;
                case 10: menu();
                default:System.out.println("La opcion no esta en el rango valido");
            }

        }

    }
    private void uiCreaPersona(){
        try{
            System.out.println("Creando una persona...");
            System.out.println("Rol persona (1=Propietario, 2=Supervisor, 3=Cosechador):");
            String rol = tcld.next();
            System.out.print("rut:");
            Rut rut = new Rut(tcld.next());
        }
    }
    private void creaCultivo(){
        System.out.println("Creando un cultivo...");
        System.out.println("Identificación: " );
        int id = tcld.nextInt();
        tcld.nextLine();
        System.out.println("Especie: " );
        String especie = tcld.next().trim();
        System.out.println("Variedad: "  );
        String variedad = tcld.next().trim();
        System.out.println("Rendimiento (Use coma para separador decimal): ");
        float rendimiento = tcld.nextFloat();
        tcld.nextLine();
        control.createCultivo(id, especie, variedad, rendimiento);
        if(ok){
            System.out.println("Cultivo creado exitosamente");
        } else {
            System.out.println("No se pudo crear: ya existe un cultivo con ese identificador.");
        }
    }

    private void creaHuerto(){
        System.out.println("Creando un huerto...");
        System.out.println("Nombre: ");
        String nombre = tcld.nextLine().trim();
        System.out.println("Superficie: ");
        float superficie = tcld.nextFloat();
        tcld.nextLine();
        System.out.println("Ubicacion: ");
        String ubicacion = tcld.nextLine().trim();
        System.out.println("Rut propietario: ");
        //Rut rut = new Rut(tcld.nextLine().trim());
        Rut rut = new Rut(Long.parseLong(tcld.nextLine().trim());
        boolean ok = control.createHuerto(nombre, superficie, ubicacion, rut);
        if(!ok){
            System.out.println("No ha sido posible crear el huerto (nombre duplicado o propietario inexistente).");
            return;
        }

        System.out.println("Agregar cuarteles al huerto" );
        System.out.println("Nro. de cuarteles: ");
        int n =  tcld.nextInt();
        tcld.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("----Cuartel #"+(i+1));
            System.out.println("Id cuartel: ");
            int idCuartel = tcld.nextInt();
            tcld.nextLine();
            System.out.println("Superficie cuartel: ");
            float superficieCuartel = tcld.nextFloat();
            tcld.nextLine();
            System.out.println("Id cultivo del cuartel: ");
            int idCultivo = tcld.nextInt();
            tcld.nextLine();
            boolean okC = control.addCuartelToHuerto(nombre, idCuartel, superficieCuartel, idCultivo);
            if(okC){
                System.out.println("Cuartel agregado exitosamente al huerto");
            } else {
                System.out.println("No se pudo agregar cuartel al huerto(id duplicado o cultivo inexistente)");
            }
        }

    }

    private void creaPlanDeCosecha(){
        System.out.println("Creando un plan de cosecha....");
        System.out.println("Id del plan: ");
        int id = tcld.nextInt();
        tcld.nextLine();
        System.out.println("Nombre del plan: ");
        String nombre = tcld.nextLine().trim();
        System.out.println("Fecha de inicio (dd/mm/aaaa): ");
        LocalDate inicio = LocalDate.parse(tcld.nextLine().trim(), F);
        System.out.println("Fecha de termino (dd/mm/aaaa): ");
        LocalDate termino = LocalDate.parse(tcld.nextLine().trim(), F);
        System.out.println("Meta (kilos): ");
        double metas = tcld.nextDouble();
        tcld.nextLine();
        System.out.println("Precio base por kilos: ");
        float precioBase = tcld.nextFloat();
        tcld.nextLine();
        System.out.println("Nombre Huerto: ");
        String nombreHuerto = tcld.nextLine().trim();
        System.out.println("Id cuartel: ");
        int cuartel = tcld.nextInt();
        tcld.nextLine();

        boolean okPlan = control.createPlanCosecha(id, nombre, inicio, termino, metas, precioBase, nombreHuerto, cuartel);
        if(!okPlan){
            System.out.println("No se pudo crear el plan de cosecha(id duplicado, fechas invalidas, o huerto/cuartel inexistentes)");
        }

        System.out.println("Plan de cosecha creado exitosamente");

        System.out.println("Agregando cuadrillas al plan de cosecha");
        System.out.println("Nro. de cuadrillas: ");
        int nroCuadrillas = tcld.nextInt();
        tcld.nextLine();
        for (int i = 0; i < nroCuadrillas; i++) {
            System.out.println("------Cuadrilla #" +(i+1));
            System.out.println("Id cuadrilla: ");
            int idCuadrilla = tcld.nextInt();
            tcld.nextLine();
            System.out.println("Nombre cuadrilla: ");
            String nombreCuadrilla = tcld.nextLine().trim();
            System.out.println("Rut supervisor: ");
            Rut rutSupervisor = new Rut(tcld.nextLine().trim());

            boolean okCuad = control.addCuadrillaToPlan(id, idCuadrilla, nombreCuadrilla, rutSupervisor);
            if(okCuad){
                System.out.println("Cuadrilla agragada correctamente al plan de cosecha");
            } else {
                System.out.println("No se pudo crear/asociar (id cuadrilla duplicado en el plan, supervisor inexistente o ya asignado).");
            }
        }
    }

    private void asignaCosechadoresAPlan(){
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
            LocalDate inicio = LocalDate.parse(tcld.nextLine().trim(), F);
            System.out.println("Fecha de termino asignación (dd/mm/aaaa)");
            LocalDate termino = LocalDate.parse(tcld.nextLine().trim(), F);
            System.out.println("Meta (Kilos): ");
            double metas = tcld.nextDouble();
            tcld.nextLine();
            System.out.println("Rut cosechador: ");
            Rut rutCosechador = new Rut(tcld.nextLine().trim());

            boolean ok = control.addCosechadorToCuadrilla(id, idCuadrilla, inicio, termino, metas, rutCosechador);
            if(ok){
                System.out.println("Cosechador asignado exitosamente a una cuadrilla del plan de cosecha");
            } else {
                System.out.println("No se pudo asignar (plan/cuadrilla/cosechador inexistente o fechas fuera del rango del plan o inválidas).");
            }
        }

    }

    private void listaCultivos(){
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


    private void creaPersona(){
        System.out.println("Creando una persona...");
        System.out.println("Rol persona (1=Propietario, 2=Supervisor, 3=Cosechador)");
        String rol = tcld.nextLine().trim();
        System.out.println("Rut: ");
        Rut rut = new Rut(tcld.nextLine().trim());
        System.out.println("Nombre: ");
        String nombre = tcld.nextLine().trim();
        System.out.println("Email: ");
        String email = tcld.nextLine().trim();
        System.out.println("Dirección: ");
        String direccion = tcld.nextLine().trim();

        boolean ok;
        if(rol.equals("1")){
            System.out.println("Direccion Particular: ");
            String direccionParticular = tcld.nextLine().trim();
            System.out.println("Direccion Comercial: ");
            String direccionComercial = tcld.nextLine().trim();
            ok = control.createPropietario(rut, nombre, email, direccionParticular, direccionComercial);
        } else if(rol.equals("2")){
            System.out.println("Profesion: ");
            String profesion = tcld.nextLine().trim();
            ok = control.createSupervisor(rut, nombre, email, direccion, profesion);
        } else {
            System.out.println("Fecha Nacimiento (dd/mm/aaaa): ");
            LocalDate fechaNacimiento = LocalDate.parse(tcld.nextLine().trim(), F);
            ok = control.createCosechador(rut, nombre, email, direccion, fechaNacimiento);
        }
        if(ok){
            System.out.println("Persona creada exitosamente");
        } else {
            System.out.println("Error al crear persona.");
        }
    }
}