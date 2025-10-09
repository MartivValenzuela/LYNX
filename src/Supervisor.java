public class Supervisor extends Persona {
    private String profesion;
    private Cuadrilla cuadrilla;

    public Supervisor(Rut rut, String nombre, String email, String direccion, String profesion, Cuadrilla cuadrilla) {
        super(rut, nombre, email, direccion);
        this.profesion = profesion;
        this.cuadrilla = cuadrilla;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public void setCuadrilla(Cuadrilla cuad) {
        this.cuadrilla = cuad;
    }
    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }
}