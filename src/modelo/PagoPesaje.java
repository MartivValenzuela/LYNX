package modelo;

import java.time.LocalDate;
import java.util.List;

public class PagoPesaje {

    private int id;
    private LocalDate fecha;
    private List<Pesaje> pesajes;

    public PagoPesaje(int id, LocalDate fecha, List<Pesaje> pesajesAPagar) {
        this.id = id;
        this.fecha = fecha;
        this.pesajes = pesajesAPagar;

        for (Pesaje pesaje : this.pesajes) {
            pesaje.setPago(this);
        }
    }

    public double getMonto() {
        double montoTotal = 0.0;
        for (Pesaje pesaje : this.pesajes) {
            montoTotal += pesaje.getMonto();
        }
        return montoTotal;
    }

    public int getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Pesaje[] getPesajes() {
        return this.pesajes.toArray(new Pesaje[0]);
    }
}
