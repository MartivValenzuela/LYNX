import java.util.Scanner;

public class GestionHuertosApp {
    public static void main (String []args){
        Scanner tcld = new Scanner(System.in);

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
        }
        switch (opcion){
            case 1:
                private void creaPersona(){
                    System.out.println("Creando una persona...");
                    System.out.println("Rol persona (1=Propietario, 2=Supervisor, 3=Cosechador)");
                    System.out.println("Rut: " + );
                    System.out.println("Nombre: " + );
                    System.out.println("Email: " +);
                    System.out.println("Dirección: " + );
                    System.out.println("Fecha Nacimiento (dd/mm/aaaa): " +);
                    System.out.println("\nCosechador creado exitosamente");

                }
            case 2:
                private void creaCultivo(){
                    System.out.println("Creando un cultivo...");
                    System.out.println("Identificación: ");
                    System.out.println("Especie: " + );
                    System.out.println("Variedad: " + );
                    System.out.println("Rendimiento: " + );
                    System.out.println("\nCultivo creado exitosamente");
                }
            case 3:
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
            case 4:
                private void creaPlanDeCosecha(){
                    System.out.println("Creando un plan de cosecha");
                    System.out.println("Id del plan: " + );
                    System.out.println("Nombre del plan: " + );
                    System.out.println("Fecha de inicio (dd/mm/aaaa): " + );
                    System.out.println("Fecha de termino (dd/mm/aaaa): " + );
                    System.out.println("Meta (kilos): " + );
                    System.out.println("Precio base por kilos: " + );
                    System.out.println("Nombre Huerto: " + );
                    System.out.println("Id cuartel: " + );
                    System.out.println("\nPlan de cosecha creado exitosamente\n");

                    System.out.println("Agregando cuadrillas al plan de cosecha");
                    System.out.println("Nro. de cuadrillas: " + );

                    for (int i = 0; i < ; i++) {
                        System.out.println("Id cuadrilla: " + );
                        System.out.println("Nombre cuadrilla: " + );
                        System.out.println("Rut supervisor: " + );
                        System.out.println("Cuadrilla agragada correctamente al plan de cosecha");
                    }
                }
            case 5:
                private void asignaCosechadoresAPlan(){
                    System.out.println("Asignando cosechadores a un plan de cosecha...");
                    System.out.println("Id del plan: " + ) ;
                    System.out.println("Id cuadrilla: " + );
                    System.out.println("Nro. cosechadores a asignar: " + );
                for (int i = 0; i < ; i++) {
                    System.out.println("Fecha de inicio asignación (dd/mm/aaaa)" + );
                    System.out.println("Fecha de termino asignación (dd/mm/aaaa)" + );
                    System.out.println("Meta (Kilos): " + );
                    System.out.println("Rut cosechador: " + );
                    System.out.println("Cosechador asignado exitosamente a una cuadrilla del plan de cosecha");
                }

                }
            case 6:
                private void listaCultivos(){
                    System.out.println("LISTADO DE CULTIVOS");
                    System.out.println("-------------------");

                    for (int i = 0; i < ; i++) {
                        System.out.println("Id        Especie        Variedad       Rendimiento        Nro. cuarteles");
                    }
                }
            case 7:
                private void listaHuertos(){
                    for (int i = 0; i < ; i++) {

                    }
                }
            case 8:
                private void listasPersonas(){
                    for (int i = 0; i < ; i++) {

                    }
                }
            case 9:
                private void listaPlanesCosecha(){
                    for (int i = 0; i < ; i++) {

                    }
                }
            case 10:

            default:
        }
    }
}