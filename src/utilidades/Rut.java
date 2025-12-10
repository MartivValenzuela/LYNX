package utilidades;

public class Rut {
    private long numero;
    private char dv;

    private Rut(long numero, char dv) {
        this.numero = numero;
        this.dv = Character.toUpperCase(dv);
    }

    //Método privado para calcular DV
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

    //PARA CREAR UN RUT DESDE STRING
    public static Rut of(String rutstr) throws GestionHuertosException {
        if (rutstr == null || rutstr.trim().isEmpty()) {
            throw new GestionHuertosException("El RUT no puede estar vacío");
        }

        if (!rutstr.matches("^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9Kk]$")) {
            throw new GestionHuertosException(
                    "Formato de RUT inválido. Debe ser del tipo XX.XXX.XXX-Y"
            );
        }

        String rutLimpio = rutstr.replace(".", "").trim();
        String[] partes = rutLimpio.split("-");

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
                throw new GestionHuertosException(
                        "El dígito verificador es incorrecto. Para " + numero +
                                " el DV correcto es " + dvCalculado +
                                ", pero se ingresó " + dvIngresado
                );
            }

            return new Rut(numero, dvIngresado);

        } catch (NumberFormatException e) {
            throw new GestionHuertosException("El número del RUT no es válido (solo dígitos)");
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
        return formatearConPuntos() + "-" + dv;
    }

    private String formatearConPuntos() {
        String numStr = Long.toString(numero);


        if (numStr.length() == 7) {
            return numStr.substring(0, 1) + "." +
                    numStr.substring(1, 4) + "." +
                    numStr.substring(4);
        } else if (numStr.length() == 8) {
            return numStr.substring(0, 2) + "." +
                    numStr.substring(2, 5) + "." +
                    numStr.substring(5);
        }

        return numStr;
    }
}
