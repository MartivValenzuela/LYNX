package vista;

import controlador.ControlProduccion;
import utilidades.GestionHuertosException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreaCultivo extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textEspecie;
    private JTextField textVariedad;
    private JTextField textRendimiento;
    private JTextField textId;

    private final ControlProduccion controlProduccion = ControlProduccion.getInstance();

    public CreaCultivo() {
        setContentPane(contentPane);
        setModal(true);

        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        //Esto sirve para cerrar con la X de la ventana
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        //2Este para cerrar con el esc del teclado
        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    private void onOK() {

        String idStr = textId.getText().trim();
        String especie = textEspecie.getText().trim();
        String variedad = textVariedad.getText().trim();
        String rendimientoStr = textRendimiento.getText().trim();

        if (idStr.isEmpty() || especie.isEmpty() || variedad.isEmpty() || rendimientoStr.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Todos los campos son obligatorios.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            float rendimiento = Float.parseFloat(rendimientoStr); // con punto

            controlProduccion.createCultivo(id, especie, variedad, rendimiento);

            JOptionPane.showMessageDialog(
                    this,
                    "Cultivo creado correctamente.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "ID y rendimiento deben ser números válidos (usa punto como separador decimal).",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (GestionHuertosException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error al crear cultivo",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error inesperado: " + ex.getClass().getSimpleName() + "\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void onCancel() {
        dispose();
    }
}
