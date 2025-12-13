package vista;

import controlador.ControlProduccion;
import utilidades.GestionHuertosException;
import utilidades.Rut;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Locale;

public class PagoPesaje extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField idpago;
    private JTextField rutcosechador;
    private JTable table1;
    private JLabel lblTotal;

    private final ControlProduccion control = ControlProduccion.getInstance();
    private DefaultTableModel modelo;

    public PagoPesaje() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);

        //Es para forzar el tamaño del Scroll, es porque se ve raro sin esto, hagan la prueba si gustan
        JScrollPane sp = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, table1);
        if (sp != null) {
            sp.setPreferredSize(new java.awt.Dimension(650, 120));
            sp.setMinimumSize(new java.awt.Dimension(650, 120));
            sp.setMaximumSize(new java.awt.Dimension(9999, 120));
        }
        pack();
        setLocationRelativeTo(null);


        modelo = new DefaultTableModel(
                new Object[]{"ID", "Fecha", "Calidad", "Kilos", "Precio Kg.", "Monto", "Pagado"},
                0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table1.setModel(modelo);
        if (lblTotal != null) lblTotal.setText("Total a pagar: $ 0,0");

        rutcosechador.addActionListener(e -> cargarPesajesPendientes()); // Enter en el rut
        rutcosechador.addFocusListener(new FocusAdapter() {
            @Override public void focusLost(FocusEvent e) {
                cargarPesajesPendientes();
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setLocationRelativeTo(null);
    }

    private void cargarPesajesPendientes() {
        modelo.setRowCount(0);
        if (lblTotal != null) lblTotal.setText("Total a pagar: $ 0,0");

        String rutStr = rutcosechador.getText().trim();
        if (rutStr.isEmpty()) return;

        try {
            Rut rut = Rut.of(rutStr);
            String[] filas = control.listPesajesCosechador(rut);

            double total = 0.0;

            for (String linea : filas) {
                String[] p = linea.split(";");
                if (p.length < 7) continue;

                String id = p[0].trim();
                String fecha = p[1].trim();
                String calidad = p[2].trim();
                String kilos = p[3].trim();
                String precio = p[4].trim();
                String monto = p[5].trim();
                String pagado = p[6].trim();

                if (!pagado.equalsIgnoreCase("Impago")) continue;

                modelo.addRow(new Object[]{id, fecha, calidad, kilos, precio, monto, pagado});

                String x = monto.replace(".", "").replace(",", ".");
                try { total += Double.parseDouble(x); } catch (Exception ignored) {}
            }

            if (lblTotal != null) {
                lblTotal.setText("Total a pagar: $ " + String.format(Locale.getDefault(), "%,.1f", total));
            }

        } catch (GestionHuertosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar pesajes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onOK() {
        try {
            String idStr = this.idpago.getText().trim();
            String rutStr = this.rutcosechador.getText().trim();

            if (idStr.isEmpty() || rutStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar rellenados", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (modelo.getRowCount() == 0) {
                cargarPesajesPendientes();
            }

            int idPago = Integer.parseInt(idStr);
            Rut rut = Rut.of(rutStr);

            double montoPagado = control.addPagoPesaje(idPago, rut);

            JOptionPane.showMessageDialog(this,
                    "Pago registrado. Monto pagado: $ " + String.format(Locale.getDefault(), "%,.1f", montoPagado),
                    "OK", JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID pago debe ser numérico", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (GestionHuertosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }
    public static void main(String[] args) {
        PagoPesaje dialog = new PagoPesaje();
        dialog.setVisible(true);
        System.exit(0);
    }
}
