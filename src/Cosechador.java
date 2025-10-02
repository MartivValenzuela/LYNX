import java.util.ArrayList;
import java.util.Date;

public class Cosechador extends Persona {
    private Date fechaNacimiento;
    private ArrayList<CosechadorAsignado> asignaciones;

    public Cosechador(Rut rut, String nom, String email, String dir, int maxAsignaciones) {
        super(rut, nom, email, dir);
        this.asignaciones = new ArrayList<>();
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fNac) {
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
