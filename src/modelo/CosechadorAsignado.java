package modelo;

import java.time.LocalDate;

public class CosechadorAsignado {
    private LocalDate desde;
    private LocalDate hasta;
    private double metaKilos;
    private Cosechador cosechador;
    private Cuadrilla cuadrilla;

    public CosechadorAsignado(LocalDate fIni, LocalDate fFin, double meta, Cuadrilla cuad, Cosechador cos) {
        this.desde = fIni;
        this.hasta = fFin;
        this.metaKilos = meta;
        this.cosechador = cos;
        this.cuadrilla = cuad;


        if (cos != null){
            cos.addCuadrilla(this);
        }
    }

    public LocalDate getDesde() {
        return desde;
    }
    public LocalDate getHasta() {
        return hasta;
    }
    public double getMetaKilos() {
        return metaKilos;
    }
    public Cuadrilla getCuadrilla(){
        return cuadrilla;
    }
    public Cosechador getCosechador(){
        return cosechador;
    }
    public float getCumplimientoMeta(){
        return cumplimientoMeta;
    }
    public int getNroPesajesImpagos(){
        return nropesajesImpagos;
    }
    public double getMontoPesajesImpagos(){
        return montoPesajesImpagos;
    }
    public int getNroPesajesPagados(){
        return nroPesajesPagados;
    }
    public double getMontoPesajesPagados(){
        return montoPesajesPagados;
    }
    public void addPesaje(Pesaje pesaje){

    }
    public Pesaje[] getPesajes(){
        return pesajes;
    }

    public void setDesde(LocalDate desde) {
        this.desde = desde;
    }
    public void setHasta(LocalDate hasta) {
        this.hasta = hasta;
    }
    public void setMetaKilos(double metaKilos) {
        this.metaKilos = metaKilos;
    }

}
