import java.util.ArrayList;

public class Huerto {
    private String nombre;
    private float superficie;
    private String ubicacion;
    private ArrayList<Cuartel> cuarteles;
    private Propietario propietario;

    public Huerto(String nom, float sup, String ubi, Propietario prop) {
        this.nombre = nom;
        this.superficie = sup;
        this.ubicacion = ubi;
        this.propietario = prop;
        this.cuarteles = new ArrayList<>();

        if (prop != null) {
            prop.addHuerto(this);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public boolean addCuartel(int id, float sup, Cultivo cul) {
        if (cul == null) {
            return false;
        }
        if (getCuartelById(id) != null) {
            return false;
        }
        Cuartel c = new Cuartel(id, sup, cul, this);
        cuarteles.add(c);
        return true;
    }

    public Cuartel getCuartel(int id) {
        return getCuartelById(id);
    }

    public Cuartel[] getCuarteles() {
        Cuartel[] resultado = new Cuartel[cuarteles.size()];
        for (int i = 0; i < cuarteles.size(); i++) {
            resultado[i] = cuarteles.get(i);
        }
        return resultado;
    }

    public Cuartel getCuartelById(int id) {
        for (Cuartel c : cuarteles) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
}