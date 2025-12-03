package vista.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import controlador.ControlProduccion;
import utilidades.GestionHuertosException;

public class ListaCosechadoresGUI extends JDialog {
    ControlProduccion control = ControlProduccion.getInstance();
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTable TablaCosechadores;

    public static void display(){
        ListaCosechadoresGUI dialog = new ListaCosechadoresGUI();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

    public ListaCosechadoresGUI() {
        setContentPane(contentPane);
        setModal(true);

        TableModel tablemodel = new DefaultTableModel();
        TablaCosechadores.setModel(tablemodel);
        DefaultTableCellRenderer rightrendered = new DefaultTableCellRenderer();
        rightrendered.setHorizontalAlignment(JLabel.RIGHT);
        try{
            String[] nomColumnas = {"Rut","Nombre","Direccíon","email","Fecha Nac.","Nro. Cuadrillas","Monto Impago $","Monto Pagado $"};
            String[][] datosCosechadores = procesarDatos();
            if (datosCosechadores.length>0){
                TablaCosechadores.setModel(new DefaultTableModel(datosCosechadores,nomColumnas));
            }else {
                JOptionPane.showMessageDialog(null,"Error, no hay datos!","Error",JOptionPane.ERROR_MESSAGE);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al procesar datos","Error grave",JOptionPane.ERROR_MESSAGE);
        }





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


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private String[][] procesarDatos(){
        String[] datos = control.listCosechadores();

        if (datos == null || datos.length == 0){
            return new String[0][8];
        }
        //Como el stream regresa un String[] en vez de una matriz y no podemos agregar metodos
        //Esta funcion los Parsea y separa,estoy seguro de que podemos reutilizarla
        String[][] matriz = new String[datos.length][8];
        for (int i=0; i< datos.length;i++){
            String[] separador = datos[i].split(";\\s*");
            for (int j=0;j< separador.length;j++){
                matriz[i][j] = separador[j];
            }
        }
        return matriz;
    }

}
