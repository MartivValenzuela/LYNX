import java.util.Scanner;

public class GestionHuertosApp {
    private ControlProduccion control;

    public GestionHuertosApp() {
        control = new ControlProduccion();
    }

    public static void main(String[] args) {
        Scanner tcld = new Scanner(System.in);
    }
        private void menu () {

        }
        private void creaPersona () {

        }
        private void creaCultivo () {

        }
        private void creaHuerto () {

        }
        private void creaPlanDeCosecha () {

        }
        private void asignaCosechadoresAPlan () {

        }
        private void listaCultivos () {

        }
        private void listaHuertos () {
                String[] huertos = control.listHuertos();

                System.out.println("listado de huertos");
                System.out.println("---");

                if (huertos.length == 0) {
                    System.out.println("No hay huertos registrados en el sistema.");
                    return;
                }
                System.out.println("Nombre, Superficie, Ubicacion, Rut propietario, Nombre propietario, Nro. cuarteles");
                for (String huerto : huertos) {
                    System.out.println(huerto);
                }
            }


            private void listaPersonas () {
            System.out.println("listado de personas");
            System.out.println("---");

            // Propietario
            String[] propietarios = control.listPropietarios();
            System.out.println("PROPIETARIOS:");
            if (propietarios.length == 0) {
                System.out.println("No hay propietarios registrados");
            } else {
                for (String p : propietarios) {
                    System.out.println(p);
                }
            }

            // Supervisorees
            String[] supervisores = control.listSupervisores();
            System.out.println("\nSUPERVISORES:");
            if (supervisores.length == 0) {
                System.out.println("No hay supervisores registrados");
            } else {
                for (String s : supervisores) {
                    System.out.println(s);
                }
            }

            // cosechadore
            String[] cosechadores = control.listCosechadores();
            System.out.println("\nCOSECHADORES:");
            if (cosechadores.length == 0) {
                System.out.println("No hay cosechadores registrados");
            } else {
                for (String c : cosechadores) {
                    System.out.println(c);
                }
            }
        }
        private void listaPlanesCosecha () {
            String[] planes = control.listPlanesCosecha();

            System.out.println("listado de planes de cosecha ");
            System.out.println("---");

            if (planes.length == 0) {
                System.out.println("No hay planes de cosecha registrados en el sistema.");
                return;
            }

            System.out.println("Id, Nombre, Inicio, Termino, Meta, Precio base, Nombre huerto, Id cuartel, Nro. cuadrillas");
            for (String plan : planes) {
                System.out.println(plan);
            }
        }