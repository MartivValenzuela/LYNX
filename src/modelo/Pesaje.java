package modelo;

import utilidades.Calidad;

import java.time.LocalDateTime;

public class Pesaje {
    private int id;
    private double cantidadKg;
    private Calidad calidad;
    private LocalDateTime fechaHora;
    private double precioKg;

    public Pesaje(int id, double cant, Calidad cal, LocalDateTime fecha, CosechadorAsignado cosechadorAsignado) {
        this.id = id;
        this.cantidadKg = cant;
        this.calidad = cal;
        this.fechaHora = fecha;
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
        return;
    }
    public double getMonto(){
        return
    }
    public boolean isPagado() {
        return this.calidad == Calidad.SUFICIENTE;
    }
}
