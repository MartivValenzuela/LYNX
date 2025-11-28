package modelo;

import vista.Persona;
import utilidades.Rut;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class Cosechador extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDate fechaNacimiento;
    private ArrayList<CosechadorAsignado> asignaciones;

    public Cosechador(Rut rut, String nom, String email, String dir, LocalDate fechaNacimiento) {
        super(rut, nom, email, dir);
        this.asignaciones = new ArrayList<>();
        this.fechaNacimiento = fechaNacimiento;
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
        return asignaciones
                .stream()
                .map(CosechadorAsignado::getCuadrilla)
                .toArray(Cuadrilla[]::new);
    }

    public Optional<CosechadorAsignado> getAsignacion(int idCud, int idPlan){
        return asignaciones
                .stream()
                .filter(cos -> cos.getCuadrilla().getId() == idCud
                            && cos.getCuadrilla().getPlanCosecha().getId() == idPlan)
                .findFirst();
    }
    public CosechadorAsignado[] getAsignaciones(){
        return  asignaciones.toArray(new CosechadorAsignado[0]);
    }
}
