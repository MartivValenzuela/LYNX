package utilidades;

import java.util.Objects;

public class Rut {
    private String numero;

    public Rut(String numero) {
        this.numero = numero;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Rut)){
            return false;
        }
        Rut r = (Rut)obj;
        return numero != null && numero.equals(r.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }

    @Override
    public String toString() {
        return numero;
    }
}
