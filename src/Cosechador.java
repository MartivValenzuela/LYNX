import java.util.ArrayList;
import java.util.Date;

public class Cosechador extends Persona {
    private Date fechaNacimiento;
    private int nrCuadrillas;
    private Cuadrilla[] cuadrillas;

    public Cosechador(Rut rut, String nom, String email, String dir, int maxCuadrillas) {
        super(rut, nom, email, dir);
        this.cuadrillas = new Cuadrilla[maxCuadrillas];
        this.nrCuadrillas = 0;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fNac) {
        this.fechaNacimiento = fNac;
    }
    public void addCuadrilla (CosechadorAsignado cosAs){
        if(nrCuadrillas < cuadrillas.length){
            cuadrillas[nrCuadrillas++] = cosAs;
        }
    }
    public Cuadrilla[] getCuadrillas(){
        Cuadrilla[] resultado = new Cuadrilla[nrCuadrillas];
        for (int i = 0; i < nrCuadrillas; i++){
            resultado[i] = cuadrillas[i];
        }
        return resultado;
    }
}
