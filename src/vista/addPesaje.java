
package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controlador.ControlProduccion;
import utilidades.Calidad;
import utilidades.GestionHuertosException;
import utilidades.Rut;

public class addPesaje extends JFrame {

    private JPanel contentPane;
    private JTextField txtIdPesaje;
    private JTextField txtCantidadKg;
    private JComboBox<String> cmbCosechador;
    private JComboBox<String> cmbCuadrilla;
    private JComboBox<Calidad> cmbCalidad;
    private JButton btnAceptar;
    private JButton btnCancelar;

    private ControlProduccion control = ControlProduccion.getInstance();

    public addPesaje() {
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setResizable(false);


        cargarListas();

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionAceptar();
            }
        });

        cmbCosechador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCuadrillas();
            }
        });
    }

    private void cargarListas() {
        cmbCosechador.removeAllItems();
        String[] cosechadores = control.listCosechadores();
        for (String c : cosechadores) {
            cmbCosechador.addItem(c);
        }

        cmbCalidad.removeAllItems();
        for (Calidad c : Calidad.values()) {
            cmbCalidad.addItem(c);
        }
    }

    private void cargarCuadrillas() {
        cmbCuadrilla.removeAllItems();

        if (cmbCosechador.getSelectedItem() == null) return;

        try {
            String seleccion = (String) cmbCosechador.getSelectedItem();
            String rutStr = seleccion.split(" ")[0];
            Rut rut = Rut.of(rutStr);

            String[] cuadrillas = control.getCuadrillasDeCosechadorDePlan(rut);

            for (String c : cuadrillas) {
                cmbCuadrilla.addItem(c);
            }
        } catch (GestionHuertosException e) {
        } catch (Exception e) {
        }
    }

    private void accionAceptar() {
        try {
            if (txtIdPesaje.getText().isEmpty()) throw new GestionHuertosException("Debe ingresar ID Pesaje");
            if (txtCantidadKg.getText().isEmpty()) throw new GestionHuertosException("Debe ingresar Cantidad Kg");
            if (cmbCosechador.getSelectedItem() == null) throw new GestionHuertosException("Debe seleccionar un Cosechador");
            if (cmbCuadrilla.getSelectedItem() == null) throw new GestionHuertosException("Debe seleccionar una Cuadrilla");

            int idPesaje = Integer.parseInt(txtIdPesaje.getText());
            float kilos = Float.parseFloat(txtCantidadKg.getText());
            Calidad calidad = (Calidad) cmbCalidad.getSelectedItem();

            String selCosechador = (String) cmbCosechador.getSelectedItem();
            Rut rutCosechador = Rut.of(selCosechador.split(" ")[0]);

            String selCuadrilla = (String) cmbCuadrilla.getSelectedItem();
            String[] partes = selCuadrilla.split(";");

            if (partes.length < 3) throw new GestionHuertosException("Error al leer datos de la cuadrilla");

            int idCuadrilla = Integer.parseInt(partes[0]);
            int idPlan = Integer.parseInt(partes[2]);

            control.addPesaje(idPesaje, rutCosechador, idPlan, idCuadrilla, kilos, calidad);

            JOptionPane.showMessageDialog(this, "Pesaje agregado exitosamente");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Los campos numéricos deben ser válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (GestionHuertosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
