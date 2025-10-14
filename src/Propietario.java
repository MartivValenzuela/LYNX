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
        if (huerto == null) {
            return false;
        }
        for (Huerto h : huertos) {
            if (h.getNombre().equals(huerto.getNombre())) {
                return false;
            }
        }
        huerto.setPropietario(this);
        huertos.add(huerto);
        return true;
    }

    public Huerto[] getHuertos() {
        Huerto[] arrayHuertos = new Huerto[huertos.size()];
        return huertos.toArray(arrayHuertos);
    }
}