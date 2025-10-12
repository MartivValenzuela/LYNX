import java.util.ArrayList;

public class Huerto {
    private String nombre;
    private float superficie;
    private String ubicacion;
    private ArrayList<Cuartel>cuarteles;

    public Huerto(String nom, float sup, String ubi, Propietario prop) {
        this.nombre = nom;
        this.superficie = sup;
        this.ubicacion = ubi;
        this.propietario = prop;
        this.cuarteles= new ArrayList<>();
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

    public Persona getPropietario(){
        return propietario;
    }

    public void setPropietario(Persona propietario){
        this.propietario = propietario;
    }
    public boolean addCuartel(int id, float sup,Cultivo cul){
        if (id == 0) {
            cuarteles.add(cuartel);
            return false;
        }else {
            return true;
        }
    }
    public Cuartel getCuartel(int id){
        return cuartel;
    }
    public Cuartel[] getCuarteles(){
        Cuartel[] resultado = new Cuartel[cuarteles.size()];
        for (int i = 0; i < cuarteles.size(); i++) {
            resultado[i] =cuarteles.get(i).getCuartel();
        }
        return resultado;
    }

}
