package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;

public class PagoPesaje extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField idpago;
    private JTextField rutcosechador;
    private JTable table1;

    public PagoPesaje(String[][] prueba, String[] columnas) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);
        getRootPane().setDefaultButton(buttonOK);

        TableModel tableModel = new DefaultTableModel(prueba,columnas);
        table1.setModel(tableModel);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        for (int i = 0; i < table1.getColumnCount(); i++) {
            table1.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        idpago.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && !Character.isSpaceChar(c)){
                    e.consume();
                }

            }
        });
        idpago.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    int idPago = Integer.parseInt(idpago.getText());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "El ID debe ser numérico",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private void onListarPagos(){
        String[][] prueba = {{"w","w","w","w","w","w","w"}};
        if (prueba.length>0){
            String[] columnas ={"ID","Fecha","Calidad","Kilos","Precio Kg.","Monto","Pagado"};
            PagoPesaje.display(prueba,columnas);
        }else {
            JOptionPane.showMessageDialog(this,"Este cosechador no tiene pagos que recivir", "",JOptionPane.ERROR_MESSAGE);
        }

    }

    private static void display(String[][] prueba, String[] columnas) {
        PagoPesaje dialog = new PagoPesaje(prueba,columnas);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
    }

    private void onOK() {
        // add your code here
        String idpago = this.idpago.getText().trim();
        String rutcosechador = this.rutcosechador.getText().trim();
        if (idpago.isEmpty() || rutcosechador.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar rellenados", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
