public class Huerto {
    private String nombre;
    private float superficie;
    private String ubicacion;

    public Huerto(String nom, float sup, String ubi, Propietario prop) {
        this.nombre = nom;
        this.superficie = sup;
        this.ubicacion = ubi;
        this.propietario = prop;
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

}
