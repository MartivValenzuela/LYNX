package vista.GUI;

import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import controlador.ControlProduccion;
import utilidades.Rut;
// Importa tu controlador aquí
// import controlador.ControladorProduccion;

public class CreaPersona extends JFrame {
    private JPanel panelPrincipal;

    private JTextField txtDir;
    private JTextField txRut;
    private JTextField txEmail;
    private JTextField txNom;
    private JTextField txtDatoVar;
    private JTextField TxtDirComer;
    private JRadioButton BtCos;
    private JRadioButton BtSup;
    private JRadioButton BtProp;
    private JButton BtAceptar;
    private  JButton BtCancelar;
    private JLabel lbDatoVariable;
    private ControlProduccion control = ControlProduccion.getInstance();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public CreaPersona(ControlProduccion control) {
        this.control = control;
        setTitle("Creación de persona");
        setContentPane(panelPrincipal); // <--- ESTO ES CRUCIAL: Carga el diseño visual
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        //Esto es como un struct de botones donde solo puede haber 1 , si se presiona otro se cambia
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(BtProp);
        grupo.add(BtCos);
        grupo.add(BtSup);
        BtProp.setSelected(true);

        ActionListener cambioRol = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDatoVar.setText("");
                if (BtProp.isSelected()){
                    lbDatoVariable.setText("Direccion Comercial");
                    txtDatoVar.setToolTipText("Ingrese la Direccion Comercial");
                } else if (BtSup.isSelected()) {
                    lbDatoVariable.setText("Profesion:");
                    txtDatoVar.setToolTipText("Ingrese la Profesion");

                } else if (BtCos.isSelected()) {
                    lbDatoVariable.setText("Fecha Nacimiento");
                    txtDatoVar.setToolTipText("dd/MM/aaaa");

                }
            }
        };
        BtProp.addActionListener(cambioRol);
        BtCos.addActionListener(cambioRol);
        BtSup.addActionListener(cambioRol);
        // Acción del botón Cancelar
        BtCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        BtAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos();
            }
        });
    }

    private void guardarDatos() {
        if (txRut.getText().isEmpty() || txNom.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Existen datos incorrectos o faltantes",
                    "Error", JOptionPane.ERROR_MESSAGE); // [cite: 194]
            return;
        }

        try {
            // AQUÍ LLAMAS A TU CONTROLADOR
            Rut rut = Rut.of(txRut.getText());
            String nom = txNom.getText();
            String email = txEmail.getText();
            String dir = txtDir.getText();
            String datoExtra = txtDatoVar.getText();

            if (BtProp.isSelected()){
                control.createPropietario(rut,nom,email,dir,datoExtra);

            } else if (BtSup.isSelected()) {
                control.createSupervisor(rut,nom,email,dir,datoExtra);
            } else if (BtCos.isSelected()) {
                LocalDate fecha = LocalDate.parse(datoExtra,formatter);
                control.createCosechador(rut,nom,email,dir,fecha);
            }


            JOptionPane.showMessageDialog(this,
                    "Persona creada exitosamente",
                    "Información", JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}