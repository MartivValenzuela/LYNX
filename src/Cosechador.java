import java.util.ArrayList;
import java.util.Date;

public class Cosechador extends Persona {
    private Date fechaNacimiento;
    private int nrAsignaciones;
    private CosechadorAsignado[] asignaciones;

    public Cosechador(Rut rut, String nom, String email, String dir, int maxAsignaciones) {
        super(rut, nom, email, dir);
        this.asignaciones = new CosechadorAsignado[maxAsignaciones];
        this.nrAsignaciones =
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fNac) {
        this.fechaNacimiento = fNac;
    }
    public void addCuadrilla (CosechadorAsignado cosAs){
        if(nrAsignaciones < asignaciones.length){
            asignaciones[nrAsignaciones++] = cosAs;
        }
    }
    public Cuadrilla[] getCuadrillas(){
        Cuadrilla[] resultado = new Cuadrilla[nrAsignaciones];
        for (int i = 0; i < nrAsignaciones; i++){
            resultado[i] = asignaciones[i].getCuadrilla();
        }
        return resultado;
    }
}
