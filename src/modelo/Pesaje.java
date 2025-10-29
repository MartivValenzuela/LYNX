package modelo;

import java.time.LocalDateTime;
import utilidades.Calidad;
public class Pesaje {

    private int id;
    private double cantidadKg;
    private Calidad calidad;
    private LocalDateTime fechaHora;
    private double precioKg;
    private CosechadorAsignado cosechadorAsignado;
    private PagoPesaje pago;

    public Pesaje(int id, double cantidadKg, Calidad calidad, LocalDateTime fechaHora, CosechadorAsignado cosechadorAsignado) {
        this.id = id;
        this.cantidadKg = cantidadKg;
        this.calidad = calidad;
        this.fechaHora = fechaHora;
        this.cosechadorAsignado = cosechadorAsignado;
        this.precioKg = cosechadorAsignado.getCuadrilla().getPlanCosecha().getPrecioBaseKilo();
        this.pago = null;
    }

    public double getMonto() {
        double montoBase = this.cantidadKg * this.precioKg;
        switch (this.calidad) {
            case EXCELENTE:
                return montoBase;
            case SUFICIENTE:
                return montoBase * 0.80;
            case DEFICIENTE:
                return montoBase * 0.60;
            default:
                return 0.0;
        }
    }

    public boolean isPagado() {
        return this.pago != null;
    }

    public int getId() {
        return id;
    }

    public double getCantidadKg() {
        return cantidadKg;
    }

    public Calidad getCalidad() {
        return calidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getPrecioKg() {
        return precioKg;
    }

    public CosechadorAsignado getCosechadorAsignado() {
        return cosechadorAsignado;
    }

    public void setPago(PagoPesaje pago) {
        this.pago = pago;
    }

    public PagoPesaje getPagoPesaje() {
        return pago;
    }
}