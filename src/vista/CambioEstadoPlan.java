package vista;

import controlador.ControlProduccion;
import utilidades.GestionHuertosException;

import javax.swing.*;
import java.awt.event.*;

public class CambioEstadoPlan extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> comboBox1;
    private JTextField idplangui;
    private JLabel nombregui;


    public CambioEstadoPlan() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


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


    }

    private void mostrarPlanCosecha() {
        // PlanCosecha plan = new PlanCosecha();
         // nombregui = plan

    }


    private void onOK() {

        Integer idplangui = null;
        try {
            String idplan = this.idplangui.getText().trim();

            if (idplan.isEmpty()){
                JOptionPane.showMessageDialog(this, "El campo ID Plan está vacío", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            idplangui = Integer.valueOf(idplan);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        CambioEstadoPlan dialog = new CambioEstadoPlan();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }
}
