package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class CosechadorAsignado implements Serializable {
    private static final long serialVersionUID = 1L;
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
        double totalKg = pesajes.stream()
                .mapToDouble(p-> (double) p.getCantidadKg())
                .sum();

        if(metaKilos <= 0){
            return 0f;
        }
        return (float)(totalKg / metaKilos);
    }
    public int getNroPesajesImpagos(){
        return (int) pesajes.stream()
                .filter(p-> !p.isPagado())
                .count();
    }
    public double getMontoPesajesImpagos(){
       return (double) pesajes.stream()
               .filter(p-> !p.isPagado())
               .mapToDouble(Pesaje::getMonto)
               .sum();
    }
    public int getNroPesajesPagados(){
        return (int) pesajes.stream()
                .filter(Pesaje::isPagado)
                .count();
    }
    public double getMontoPesajesPagados(){
        return pesajes.stream()
                .filter(Pesaje::isPagado)
                .mapToDouble(Pesaje::getMonto)
                .sum();
    }
    public void addPesaje(Pesaje pesaje){
        if(pesajes == null){
            return;
        }
        boolean yaExiste = pesajes.stream().anyMatch(p->p.equals(pesaje));
        if(yaExiste){
            pesajes.add(pesaje);
        }
    }
    public Pesaje[] getPesajes(){
        return pesajes.toArray(new Pesaje[0]);
    }
}
