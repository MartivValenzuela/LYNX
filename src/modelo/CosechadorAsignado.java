package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class CosechadorAsignado {
    private LocalDate desde;
    private LocalDate hasta;
    private double metaKilos;
    private Cosechador cosechador;
    private Cuadrilla cuadrilla;
    private ArrayList<Pesaje> pesajes;

    public CosechadorAsignado(LocalDate fIni, LocalDate fFin, double meta, Cuadrilla cuad, Cosechador cos) {
        this.desde = fIni;
        this.hasta = fFin;
        this.metaKilos = meta;
        this.cosechador = cos;
        this.cuadrilla = cuad;
        this.pesajes = new ArrayList<>();


        if (cos != null){
            cos.addCuadrilla(this);
        }
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
    public float getCumplimientoMeta(){
        double totalKg = 0;
        for(Pesaje p: pesajes){
            totalKg += p.getCantidadKg();
        }
        if(metaKilos <= 0){
            return 0f;
        }
        return (float)(totalKg / metaKilos);
    }
    public int getNroPesajesImpagos(){
        int n = 0;
        for (Pesaje p : pesajes){
            if(!p.isPagado()){
                n++;
            }
        }
        return n;
    }
    public double getMontoPesajesImpagos(){
        double m = 0;
        for (Pesaje p : pesajes){
            if(!p.isPagado()){
                m += p.getMonto();
            }
        }
        return m;
    }
    public int getNroPesajesPagados(){
        int n = 0;
        for (Pesaje p : pesajes){
            if(p.isPagado()){
                n++;
            }
        }
        return n;
    }
    public double getMontoPesajesPagados(){
        double m = 0;
        for(Pesaje p: pesajes){
            if(p.isPagado()){
                m += p.getMonto();
            }
        }
        return m;
    }
    public void addPesaje(Pesaje pesaje){
        if(pesajes == null){
            return;
        }
        if(!pesajes.contains(pesaje)){
            pesajes.add(pesaje);
        }
    }
    public Pesaje[] getPesajes(){
        return pesajes.toArray(new Pesaje[0]);
    }
}
