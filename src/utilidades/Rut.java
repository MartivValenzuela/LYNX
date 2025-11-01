package utilidades;

public class Rut {
    private long numero;
    private char dv;

    public Rut(long numero, char dv) {
        this.numero = numero;
        this.dv = Character.toUpperCase(dv);
    }


    public Rut of(String rutstr){
        String [] partes = rutstr.split("-");
        long numero = Long.parseLong(partes[0]);
        char dv = partes[1].toUpperCase().charAt(0);
        return new Rut(numero, dv);
    }

    @Override
    public String toString() {
        return numero + "-" + dv;
    }
}
