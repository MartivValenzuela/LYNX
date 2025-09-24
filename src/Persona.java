public class Persona {
    private Rut rut;
    private String nombre;
    private String email;
    private String dirrecion;

    public Persona(Rut rut, String nom, String email, String dir) {
        this.rut = rut;
        this.nombre = nom;
        this.email = email;
        this.dirrecion = dir;
    }

    public Rut getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDirrecion() {
        return dirrecion;
    }

    public void setDirrecion(String dirrecion) {
        this.dirrecion = dirrecion;
    }

}
