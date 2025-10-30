package modelo;

import utilidades.Persona;
import utilidades.Rut;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

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
        asignaciones.add(cosAs);
    }
    public Cuadrilla[] getCuadrillas(){
        Cuadrilla[] resultado = new Cuadrilla[asignaciones.size()];
        for (int i = 0; i < asignaciones.size(); i++){
            resultado[i] = asignaciones.get(i).getCuadrilla();
        }
        return resultado;
    }

    public Optional<CosechadorAsignado> getAsignacion(int idCud, int idPlan){
        for(CosechadorAsignado cos: asignaciones){
            Cuadrilla c = cos.getCuadrilla();
            PlanCosecha p = c.getPlanCosecha();
            if(c.getId() == idCud && p.getId() == idPlan){
                return Optional.of(cos);
            }
        }
        return Optional.empty();
    }
    public CosechadorAsignado[] getAsignaciones(){
        return  asignaciones.toArray(new CosechadorAsignado[0]);
    }
}
