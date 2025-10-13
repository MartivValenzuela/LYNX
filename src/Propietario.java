
public class Propietario extends Persona {
    private String direccionComercial;
    public Propietario(Rut rut, String nombre, String email, String direccion, String direccionComercial) {
        super(rut, nombre, email, direccion);
        this.direccionComercial = direccionComercial;
    }
    public String getDirComercial() {
        return direccionComercial;
    }
    public void setDirComercial(String direccion) {
        this.direccionComercial = direccion;
    }
    public boolean addHuerto(Huerto huerto) {

        return true;
    }
    public Huerto[] getHuertos() {
        return new Huerto[0];

    }
}
