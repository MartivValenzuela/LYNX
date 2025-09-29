public class Persona {
    private Rut rut;
    private String nombre;
    private String email;
    private String direction;


    public Persona(Rut rut, String nom, String email, String dir) {
        this.rut = rut;
        this.nombre = nom;
        this.email = email;
        this.direction = dir;
    }


    public Rut getRut() {
        return this.rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getDirection() {
        return direction;
    }

    // no validé nada por ahora
    public void setEmail(String email) {
        this.email = email;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}