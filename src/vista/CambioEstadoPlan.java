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
    private JComboBox<EstadoPlan> comboBox1;
    private JTextField idplangui ;
    private JLabel nombregui;
    private JLabel estadoactual;
    private JLabel cumpmeta;

    private final ControlProduccion control = ControlProduccion.getInstance();


    public CambioEstadoPlan() {
        setContentPane(contentPane);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        EstadoPlan[] estadosposibles = EstadoPlan.values();
        for (EstadoPlan estadoPlan : estadosposibles){
            comboBox1.addItem(estadoPlan);
        }
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



        idplangui.addActionListener(e -> cargarPlandDesdeListado());
        idplangui.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                cargarPlandDesdeListado();
            }
        });
    }

    private void cargarPlandDesdeListado(){
        String txt = idplangui.getText().trim();
        if(txt.isEmpty()){
            return;
        }
        try {
            int idbuscado =  Integer.parseInt(txt);
            String[] planes = control.listPlanesCosecha();

            boolean encontrado = false;

            for(String linea : planes){
                String[] p = linea.split(";");

                int id = Integer.parseInt(p[0].trim());
                if(id == idbuscado){
                    encontrado = true;

                    String nombre = p[1].trim();
                    String meta = p[4].trim();
                    String estado = p[6].trim();

                    nombregui.setText(nombre);
                    cumpmeta.setText(meta);
                    estadoactual.setText(estado);

                    comboBox1.removeAllItems();
                    for(EstadoPlan e : EstadoPlan.values()){
                        if(!e.name().equalsIgnoreCase(estado)){
                            comboBox1.addItem(e);
                        }
                    }
                    break;
                }
            }
            if(!encontrado){
                JOptionPane.showMessageDialog(this, "No existe un plan con ese ID","Error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "ID invalido","Error",JOptionPane.ERROR_MESSAGE);
        }
    }




    private void onOK() {
        try {
            if(idplangui.getText().trim().isEmpty()){
                throw new GestionHuertosException("Debe ingresar ID del plan");
            }
            if(comboBox1.getSelectedItem() == null){
                throw new GestionHuertosException("Debe seleccionar un  nuevo estado");
            }
            int id = Integer.parseInt(idplangui.getText().trim());
            EstadoPlan nuevo = (EstadoPlan) comboBox1.getSelectedItem();

            control.changeEstadoPlan(id, nuevo);
            JOptionPane.showMessageDialog(this,"Se ha cambiado el estado correctamente");
            dispose();
        }catch (GestionHuertosException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado", "Error", JOptionPane.ERROR_MESSAGE);
        }



    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}