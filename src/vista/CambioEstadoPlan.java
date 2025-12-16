package vista;

import controlador.ControlProduccion;
import utilidades.EstadoPlan;
import utilidades.GestionHuertosException;

import javax.swing.*;
import java.awt.event.*;

public class CambioEstadoPlan extends JFrame {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField textId;
    private JLabel lbNombre;
    private JLabel lbCumplimiento;
    private JLabel lbEstado;
    private JComboBox<String> comboBoxNvEstado;

    private final ControlProduccion control = ControlProduccion.getInstance();

    public CambioEstadoPlan() {
        setContentPane(contentPane);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);

        lbNombre.setText("-");
        lbCumplimiento.setText("-");
        lbEstado.setText("-");
        comboBoxNvEstado.removeAllItems();

        textId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) e.consume();
            }
        });

        // Buscar plan al salir del campo o Enter
        textId.addActionListener(e -> cargarPlanDesdeListado());
        textId.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                cargarPlanDesdeListado();
            }
        });

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(null);
    }

    private void cargarPlanDesdeListado() {
        String txt = textId.getText().trim();
        if (txt.isEmpty()) return;

        try {
            int idBuscado = Integer.parseInt(txt);
            String[] planes = control.listPlanesCosecha();

            boolean encontrado = false;

            for (String linea : planes) {
                String[] p = linea.split(";");

                int id = Integer.parseInt(p[0].trim());
                if (id == idBuscado) {
                    encontrado = true;

                    String nombre = p[1].trim();
                    String meta = p[4].trim();
                    String estado = p[6].trim();

                    lbNombre.setText(nombre);
                    lbCumplimiento.setText(meta);
                    lbEstado.setText(estado);

                    comboBoxNvEstado.removeAllItems();
                    for (EstadoPlan e : EstadoPlan.values()) {
                        if (!e.name().equalsIgnoreCase(estado)) {
                            comboBoxNvEstado.addItem(e.name());
                        }
                    }
                    break;
                }
            }

            if (!encontrado) {
                limpiar();
                JOptionPane.showMessageDialog(this,
                        "No existe un plan con ese ID",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            limpiar();
            JOptionPane.showMessageDialog(this,
                    "ID inválido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onOK() {
        try {
            if (textId.getText().trim().isEmpty())
                throw new GestionHuertosException("Debe ingresar ID del plan");

            if (comboBoxNvEstado.getSelectedItem() == null)
                throw new GestionHuertosException("Debe seleccionar un nuevo estado");

            int id = Integer.parseInt(textId.getText().trim());
            EstadoPlan nuevo = EstadoPlan.valueOf(
                    comboBoxNvEstado.getSelectedItem().toString()
            );

            control.changeEstadoPlan(id, nuevo);

            JOptionPane.showMessageDialog(this,
                    "Estado del plan actualizado correctamente");
            dispose();

        } catch (GestionHuertosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        lbNombre.setText("-");
        lbCumplimiento.setText("-");
        lbEstado.setText("-");
        comboBoxNvEstado.removeAllItems();
    }
}
