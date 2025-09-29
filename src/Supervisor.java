public class Supervisor extends Persona {
    private String profesion;
    private Cuadrilla cuadrilla;

    public Supervisor(Rut rut, String nombre, String email, String direccion, String profesion) {
        super(rut, nombre, email, direccion);
        this.profesion = profesion;
        this.cuadrilla = null;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(Cuadrilla cuad) {
        // Supervisor controla Cuadrilla
        this.cuadrilla = cuad;
        if (cuad != null) {

        }
    }
    public boolean tieneCuadrillaAsignada() {
        return cuadrilla != null;
    }
}