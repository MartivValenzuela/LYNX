package utilidades;

public class Rut {
    private long numero;
    private char dv;

    public Rut(long numero, char dv) {
        this.numero = numero;
        this.dv = Character.toUpperCase(dv);
    }


    public static Rut of(String rutstr){
        String rutlimpio = rutstr.replace(".","");
        String [] partes = rutstr.split("-");
        try{
            long numero = Long.parseLong(partes[0]);
            char dv = partes[1].toUpperCase().charAt(0);
            return new Rut(numero, dv);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Error" + e);
        }

    }

    public long getNumero() {
        return numero;
    }

    public char getDv() {
        return dv;
    }

    @Override
    public String toString() {
        return numero + "-" + dv;
    }
}
