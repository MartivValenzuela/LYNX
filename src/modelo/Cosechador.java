package modelo;

import utilidades.Rut;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cosechador extends Persona {
    private LocalDate fechaNacimiento;
    private ArrayList<CosechadorAsignado> asignaciones;

    public Cosechador(Rut rut, String nom, String email, String dir) {
        super(rut, nom, email, dir);
        this.asignaciones = new ArrayList<>();
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fNac) {
        this.fechaNacimiento = fNac;
    }
    public void addCuadrilla (CosechadorAsignado cosAs){
        if(cosAs!=null && !asignaciones.contains(cosAs)){
            asignaciones.add(cosAs);
        }
    }
    public Cuadrilla[] getCuadrillas(){
        Cuadrilla[] resultado = new Cuadrilla[asignaciones.size()];
        for (int i = 0; i < asignaciones.size(); i++){
            resultado[i] = asignaciones.get(i).getCuadrilla();
        }
        return resultado;
    }
}
