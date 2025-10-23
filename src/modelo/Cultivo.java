package modelo;

import java.util.ArrayList;
public class Cultivo {
    private int id;
    private String especie;
    private String variedad;
    private float rendimiento;
    private ArrayList<Cuartel> cuarteles;

    public Cultivo(int id, String esp, String var, float rend) {
        this.id = id;
        this.especie = esp;
        this.variedad = var;
        this.rendimiento = rend;
        this.cuarteles = new ArrayList<>();
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
    public boolean addCuartel (Cuartel cuertel){
        if (cuertel == null || cuarteles.contains(cuertel)) {
           return false;
        }
        cuarteles.add(cuertel);
        return true;
    }
    public Cuartel[] getCuerteles(){
        return cuarteles.toArray(new Cuartel[0]);
    }
}