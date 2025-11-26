package utilidades;

import java.io.Serializable;

public class Rut implements Serializable {
    private static final long serialVersionUID = 1L;
    private long numero;
    private char dv;

    private Rut(long numero, char dv) {
        this.numero = numero;
        this.dv = Character.toUpperCase(dv);
    }

    //Metodo priavdo para verificar que el dv sea correcto
    private static char calcularDV(long numero) {
        long rut = numero;
        int suma = 0;
        int multiplicador = 2;

        while (rut > 0) {
            suma += (rut % 10) * multiplicador;
            rut /= 10;
            multiplicador++;
            if (multiplicador == 8) {
                multiplicador = 2;
            }
        }

        int resto = suma % 11;
        int dvNum = 11 - resto;

        if (dvNum == 11) return '0';
        if (dvNum == 10) return 'K';
        return (char) (dvNum + '0');

    }

    public static Rut of(String rutstr) throws GestionHuertosException {
        if (rutstr == null || rutstr.trim().isEmpty()) {
            throw new GestionHuertosException("El RUT no puede estar vacío");
        }

        if (!rutstr.matches("^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9Kk]$")) {
            throw new GestionHuertosException(
                    "Formato de RUT inválido. Debe ser del tipo XX.XXX.XXX-Y"
            );
        }

        String rutlimpio = rutstr.replace(".", "").trim();
        String[] partes = rutlimpio.split("-");

        if (partes.length != 2) {
            throw new GestionHuertosException("Formato de RUT incorrecto. Debe ser (numero)-(dv)");
        }

        if (partes[1].length() != 1) {
            throw new GestionHuertosException("El dígito verificador debe ser un solo caracter");
        }

        try {
            long numero = Long.parseLong(partes[0]);
            char dvIngresado = partes[1].toUpperCase().charAt(0);

            char dvCalculado = calcularDV(numero);

            if (dvIngresado != dvCalculado) {
                throw new GestionHuertosException("El dígito verificador es incorrecto. Para " + numero + " el DV correcto es " + dvCalculado + ", pero se ingreso " + dvIngresado);
            }

            return new Rut(numero, dvIngresado);

        } catch (NumberFormatException e) {
            throw new GestionHuertosException("El numero del RUT no es válido (solo debe contener dígitos)");
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