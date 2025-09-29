import java.util.ArrayList;

public class Propietario extends Persona {
    private String direccionComercial;
    private ArrayList<Huerto> huertos;

    public Propietario(Rut rut, String nombre, String email, String direccion, String direccionComercial) {
        super(rut, nombre, email, direccion);
        this.direccionComercial = direccionComercial;
        this.huertos = new ArrayList<>();
    }

    public String getDirComercial() {
        return direccionComercial;
    }

    public void setDirComercial(String direccion) {
        this.direccionComercial = direccion;
    }

    public boolean addHuerto(Huerto huerto) {
        // hay relacion con Huerto
        if (!huertos.contains(huerto)) {
            huertos.add(huerto);
            return true;
        }
        return false;
    }

    public Huerto[] getHuertos() {
        return huertos.toArray(new Huerto[0]);
    }
    // meteodo agregado todavia sin uso podriia llegar a servir (opcional)
    public int getCantidadHuertos() {
        return huertos.size();
    }
}