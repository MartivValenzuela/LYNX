public class Cultivo {
    private int id;
    private String especie;
    private String variedad;
    private float rendimiento;

    public Cultivo(int id, String esp, String var, float rend) {
        this.id = id;
        this.especie = esp;
        this.variedad = var;
        this.rendimiento = rend;
    }

    public int getId() {
        return id;
    }

    public String getEspecie() {
        return especie;
    }

    public String getVariedad() {
        return variedad;
    }

    public float getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(float rendimiento) {
        this.rendimiento = rendimiento;
    }
    public boolean addCuertel (Cuertel cuertel){

    }
    public Cuartel[] getCuerteles(){

    }
}
