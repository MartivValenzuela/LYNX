import java.time.LocalDate;
import java.util.Date;

public class PlanCosecha {
    private int id;
    private String nombre;
    private Date inicio;
    private Date finEstimado;
    private Date finReal;
    private double metaKilos;
    private double precioBaseKilo;
    private EstadoPlan estado;
    private Cuartel cuartel;

    public PlanCosecha(int id, String nom, Date ini, Date finEst, double meta, double precio, Cuartel cuartel) {
        this.id = id;
        this.nombre = nom;
        this.inicio = ini;
        this.finEstimado = finEst;
        this.metaKilos = meta;
        this.precioBaseKilo = precio;
        this.cuartel = cuartel;
        if(cuartel != null){
            cuartel.plan = this;
        }
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFinEstimado() {
        return finEstimado;
    }

    public LocalDate getFinReal() {
        return finReal;
    }
    public void setFinReal(Date finReal) {
        this.finReal = finReal;
    }
    public double getMetaKilos() {
        return metaKilos;
    }

    public void setMetaKilos(double metaKilos) {
        this.metaKilos = metaKilos;
    }

    public double getPrecioBaseKilo() {
        return precioBaseKilo;
    }

    public void setPrecioBaseKilo(double precioBaseKilo) {
        this.precioBaseKilo = precioBaseKilo;
    }

    public EstadoPlan getEstado() {
        return estado;
    }

    public void setEstado(EstadoPlan estado) {
        this.estado = estado;
    }
    public Cuartel getCuartel(){
        return cuartel;
    }
    public boolean addCuadrilla (int idCuad, String nomCuad, Supervisor supervisor){

    }
    public boolean addCosechadorToCuadrilla(int idCuad, LocalDate fIni, LocalDate fFin, double meta, Cosechador cos){

    }
    public Cuadrilla[] getCuadrillas(){

    }
}
