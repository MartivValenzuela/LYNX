import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlanCosecha {
    private int id;
    private String nombre;
    private LocalDate inicio;
    private LocalDate finEstimado;
    private LocalDate finReal;
    private double metaKilos;
    private double precioBaseKilo;
    private EstadoPlan estado;
    private Cuartel cuartel;
    private List<Cuadrilla> cuadrillas = new ArrayList<>();

    public PlanCosecha(int id, String nom, LocalDate ini, LocalDate finEst, double meta, double precio, Cuartel cuartel) {
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
    public void setFinReal(LocalDate finReal) {
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
        if(findCuadrillaById(idCuad) != null){
            return false;
        }
        if(supervisor == null){
            return false;
        }
        if(supervisor.getCuadrilla() == null){
            return false;
        }
        Cuadrilla nueva = new Cuadrilla(idCuad, nomCuad, supervisor, this);
        cuadrillas.add(nueva);
        return true;
    }
    public boolean addCosechadorToCuadrilla(int idCuad, LocalDate fIni, LocalDate fFin, double meta, Cosechador cos){
        Cuadrilla cuad = findCuadrillaById(idCuad);
        if(cuad == null){
            return false;
        }
        return cuad.addCosechador(fIni, fFin, meta, cos);
    }
    public Cuadrilla[] getCuadrillas(){
        return cuadrillas.toArray(new Cuadrilla[0]);
    }

    private Cuadrilla findCuadrillaById(int idCuad){
        for(Cuadrilla c : cuadrillas){
            if(c.getId() == idCuad)
                return c;
        }
        return null;
    }
}
