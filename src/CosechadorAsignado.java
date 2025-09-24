import java.util.Date;

public class CosechadorAsignado {
    private Date desde;
    private Date hasta;
    private double metaKilos;

    public CosechadorAsignado(Date fIni, Date fFin, double meta, Cuadrilla cuad, Cosechador cos) {
        this.fIni = fIni;
        this.fFin = fFin;
        this.meta = meta;
        this.cuadrilla = cuad;
        this.cosechador = cos;
    }

    public LocalDate getDesde() {
        return desde;
    }

    public void setDesde(LocalDate desde) {
        this.desde = desde;
    }

    public LocalDate getHasta() {
        return hasta;
    }

    public void setHasta(LocalDate hasta) {
        this.hasta = hasta;
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
