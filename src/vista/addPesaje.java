package vista;

import controlador.ControlProduccion;
import utilidades.Calidad;
import utilidades.GestionHuertosException;
import utilidades.Rut;

import javax.swing.*;

public class addPesaje extends JFrame {

    private JPanel contentPane;
    private JTextField txtIdPesaje;
    private JTextField txtCantidadKg;
    private JComboBox<String> cmbCosechador;
    private JComboBox<String> cmbCuadrilla;
    private JComboBox<Calidad> cmbCalidad;
    private JButton btnAceptar;
    private JButton btnCancelar;

    private final ControlProduccion control = ControlProduccion.getInstance();

    public addPesaje() {
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cargarListas();

        btnCancelar.addActionListener(e -> dispose());
        btnAceptar.addActionListener(e -> accionAceptar());
        cmbCosechador.addActionListener(e -> cargarCuadrillas());
    }

    private void cargarListas() {
        cmbCosechador.removeAllItems();

        String[] cosechadores = control.listCosechadores();
        for (String linea : cosechadores) {
            String[] partes = linea.split(";");
            if (partes.length < 2) {
                continue;
            }
            String rut = partes[0].trim();
            String nombre = partes[1].trim();

            cmbCosechador.addItem(rut + "; " + nombre);
        }

        cmbCalidad.removeAllItems();
        for (Calidad c : Calidad.values()) {
            cmbCalidad.addItem(c);
        }
    }

    private void cargarCuadrillas() {
        cmbCuadrilla.removeAllItems();

        Object sel = cmbCosechador.getSelectedItem();
        if (sel == null) {
            return;
        }

        try {
            String seleccion = (String) sel;
            String[] partesSel = seleccion.split(";");
            if (partesSel.length == 0) {
                return;
            }
            String rutStr = partesSel[0].trim();

            Rut rut = Rut.of(rutStr);

            String[] cuadrillas = control.getCuadrillasDeCosechadorDePlan(rut);

            for (String linea : cuadrillas) {
                cmbCuadrilla.addItem(linea);
            }

        } catch (GestionHuertosException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Cuadrillas no disponibles",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar cuadrillas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void accionAceptar() {
        try {
            if (txtIdPesaje.getText().trim().isEmpty()) {
                throw new GestionHuertosException("Debe ingresar ID Pesaje");
            }
            if (txtCantidadKg.getText().trim().isEmpty()) {
                throw new GestionHuertosException("Debe ingresar Cantidad Kg");
            }
            if (cmbCosechador.getSelectedItem() == null) {
                throw new GestionHuertosException("Debe seleccionar un Cosechador");
            }
            if (cmbCuadrilla.getSelectedItem() == null) {
                throw new GestionHuertosException("Debe seleccionar una Cuadrilla");
            }

            int idPesaje = Integer.parseInt(txtIdPesaje.getText().trim());
            float kilos = Float.parseFloat(txtCantidadKg.getText().trim());
            Calidad calidad = (Calidad) cmbCalidad.getSelectedItem();

            String selCosechador = (String) cmbCosechador.getSelectedItem();
            String[] partesC = selCosechador.split(";");
            String rutStr = partesC[0].trim();
            Rut rutCosechador = Rut.of(rutStr);

            String selCuadrilla = (String) cmbCuadrilla.getSelectedItem();
            String[] partes = selCuadrilla.split(";");
            if (partes.length < 3) {
                throw new GestionHuertosException("Error al leer datos de la cuadrilla");
            }

            int idCuadrilla = Integer.parseInt(partes[0].trim());
            int idPlan = Integer.parseInt(partes[2].trim());

            control.addPesaje(idPesaje, rutCosechador, idPlan, idCuadrilla, kilos, calidad);

            JOptionPane.showMessageDialog(this, "Pesaje agregado exitosamente");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error: los campos numéricos deben ser válidos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (GestionHuertosException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
