import java.time.LocalDate;
import java.util.Date;

public class CosechadorAsignado {
    private Date desde;
    private Date hasta;
    private double metaKilos;

    public CosechadorAsignado(Date fIni, Date fFin, double meta, Cuadrilla cuad, Cosechador cos) {
        this.desde = fIni;
        this.hasta = fFin;
        this.metaKilos = meta;
    }

    public LocalDate getDesde() {
        return desde;
    }

    public void setDesde(LocalDate desde) {

    }

    public LocalDate getHasta() {
    }

    public void setHasta(LocalDate hasta) {

    }

    public double getMetaKilos() {
        return metaKilos;
    }

    public void setMetaKilos(double metaKilos) {
        this.metaKilos = metaKilos;
    }
    public Cuadrilla getCuadrilla(){
        return cuadrilla;
    }
    public Cosechador getCosechador(){
        return cosechador;
    }
}
