package vista;

import javax.swing.*;
import java.awt.event.*;

public class GUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public GUI() {

    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        GUI dialog = new GUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
