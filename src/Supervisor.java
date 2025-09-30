public class Supervisor extends Persona {
    private String profesion;

    public Supervisor(Rut rut, String nombre, String email, String direccion, String profesion) {
        super(rut, nombre, email, direccion);
        this.profesion = profesion;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public void setCuadrilla(Cuadrilla cuad) {
    }
    public Cuadrilla getCuadrilla() {
        return null;
        p
    }
}