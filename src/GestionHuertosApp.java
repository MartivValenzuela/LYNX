
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestionHuertosApp {
    private Scanner tcld = new Scanner(System.in);
    private DateTimeFormatter F = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ControlProduccion control = new ControlProduccion();

    public static void main(String[] args) {
        new GestionHuertosApp().menu();
    }
    private void menu(){
        System.out.println("*** Sistema de Gestión de Huertos***");
        System.out.println("\n MENÚ DE OPCIONES");
        System.out.println("1. Crear Persona");
        System.out.println("2. Crear Cultivo");
        System.out.println("3. Crear Huerto");
        System.out.println("4. Crear Plan de Cosecha");
        System.out.println("5. Asignar Cosechadores a Plan");
        System.out.println("6. Listar Cultivos");
        System.out.println("7. Listar Huertos");
        System.out.println("8. Listar Personas");
        System.out.println("9. Listar Planes de Cosecha");
        System.out.println("10. Salir");
        int opcion = tcld.nextInt();
        switch (opcion){
            case 1:
                creaPersona();
            case 2:
                creaCultivo();
            case 3:
                creaHuerto();
            case 4:
                creaPlanDeCosecha();
            case 5:
                asignaCosechadoresAPlan();
            case 6:
                listaCultivos();
            case 7:
                listaHuertos();
            case 8:
                listasPersonas();
            case 9:
                listaPlanesCosecha();
            case 10:

        }
    }
    private void creaCultivo(){
        System.out.println("Creando un cultivo...");
        System.out.println("Identificación: ");
        System.out.println("Especie: " + );
        System.out.println("Variedad: " + );
        System.out.println("Rendimiento: " + );
        System.out.println("\nCultivo creado exitosamente");
    }

    private void creaHuerto(){
        System.out.println("Creando un huerto...");
        System.out.println("Nombre: " + );
        System.out.println("Superficie: " + );
        System.out.println("Ubicacion: " + );
        System.out.println("Rut propietario: " + );

        System.out.println("Huerto creado exitosamente");

        System.out.println("Agregar cuarteles al huerto" );
        System.out.println("Nro. de cuarteles: " + );
        for (int i = 0; i < ; i++) {
            System.out.println("\nId cuartel: " + );
            System.out.println("Superficie cuartel: " + );
            System.out.println("Id cultivo del cuartel: " +);
            System.out.println("Cuartel agregado exitosamente al huerto");
        }

    }

    private void creaPlanDeCosecha(){
        System.out.println("Creando un plan de cosecha....");
        System.out.println("Id del plan: ");
        int id = Integer.parseInt(tcld.nextLine().trim());
        System.out.println("Nombre del plan: ");
        String nombre = tcld.nextLine().trim();
        System.out.println("Fecha de inicio (dd/mm/aaaa): ");
        LocalDate inicio = LocalDate.parse(tcld.nextLine().trim(), F);
        System.out.println("Fecha de termino (dd/mm/aaaa): ");
        LocalDate termino = LocalDate.parse(tcld.nextLine().trim(), F);
        System.out.println("Meta (kilos): ");
        double metas = Double.parseDouble(tcld.nextLine().trim());
        System.out.println("Precio base por kilos: ");
        float precioBase = Float.parseFloat(tcld.nextLine().trim());
        System.out.println("Nombre Huerto: ");
        String nombreHuerto = tcld.nextLine().trim();
        System.out.println("Id cuartel: ");
        int cuartel = Integer.parseInt(tcld.nextLine().trim());

        boolean okPlan = control.createPlanCosecha(id, nombre, inicio, termino, metas, precioBase, nombreHuerto, cuartel);
        if(!okPlan){
            System.out.println("No se pudo crear el plan de cosecha(id duplicado, fechas invalidas, o huerto/cuartel inexistentes)");
        }

        System.out.println("Plan de cosecha creado exitosamente");

        System.out.println("Agregando cuadrillas al plan de cosecha");
        System.out.println("Nro. de cuadrillas: ");
        int nroCuadrillas = Integer.parseInt(tcld.nextLine().trim());
        for (int i = 0; i < nroCuadrillas; i++) {
            System.out.println("------Cuadrilla #" +(i+1));
            System.out.println("Id cuadrilla: ");
            int idCuadrilla = Integer.parseInt(tcld.nextLine().trim());
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
        int id = Integer.parseInt(tcld.nextLine().trim());
        System.out.println("Id cuadrilla: ");
        int idCuadrilla = Integer.parseInt(tcld.nextLine().trim());
        System.out.println("Nro. cosechadores a asignar: ");
        int nroCosechadores = Integer.parseInt(tcld.nextLine().trim());

        for (int i = 0; i < nroCosechadores; i++) {
            System.out.println("Fecha de inicio asignación (dd/mm/aaaa)");
            LocalDate inicio = LocalDate.parse(tcld.nextLine().trim(), F);
            System.out.println("Fecha de termino asignación (dd/mm/aaaa)");
            LocalDate termino = LocalDate.parse(tcld.nextLine().trim(), F);
            System.out.println("Meta (Kilos): ");
            double metas = Double.parseDouble(tcld.nextLine().trim());
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

        for (int i = 0; i < ; i++) {
            System.out.println("Id        Especie        Variedad       Rendimiento        Nro. cuarteles");
            System.out.println(  + "    " + + "    " + + "    " + );
        }
    }

    private void listaHuertos(){
        System.out.println("LISTADO DE HUERTOS");
        System.out.println("------------------");
        System.out.println("Nombre        Superficie Ubicación        Rut Propietario    Nombre propietario        Nro. cuarteles");

        for (int i = 0; i < ; i++) {
            System.out.println(+ "    " + + "    " + + "    " + + "     " + );
        }
    }

    private void listasPersonas(){
        System.out.println("LISTADO DE PROPIETARIOS");
        System.out.println("-----------------------");
        for (int i = 0; i < ; i++) {
            System.out.println("Rut        Nombre        Dirección        email        Dirección comercial        Nro. huertos");
            System.out.println(   "    " +  "    " +  "    " +  "    " +  "    " );
        }
        System.out.println("LISTADO DE SUPERVISORES");
        System.out.println("-----------------------");
        for (int i = 0; i < ; i++) {
            System.out.println("Rut        Nombre        Dirección        email        Profesión        Nombre cuadrilla");
            System.out.println( "    " + "    " +  "    " +  "    " +  "    ");
        }
        System.out.println("LISTADO DE COSECHADORES");
        System.out.println("-----------------------");
        for (int i = 0; i < ; i++) {
            System.out.println("Rut        Nombre        Dirección        email        Fecha nacimiento        Nro. caudrillas");
            System.out.println( "    " +  "    " + "    " + "    " + "    " + );
        }
    }

    private void listaPlanesCosecha(){
        System.out.println("LISTADO DE PLANES DE COSECHA");
        System.out.println("----------------------------");
        for (int i = 0; i < ; i++) {
            System.out.println("Id        Nombre        Fecha inicio    Fecha término    Meta (kg)    Precio base (kg)    Estado    Id cuartel    Nombre huerto    Nro. cuadrillas");
            System.out.println(  "    " +  "    " + "    " +  "    " + "    " +  "    " +  "    " +  "    " +  "    " + );
        }
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
