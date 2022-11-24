package com.example.accesoadatos2;

import javax.swing.*;
import java.awt.event.*;

public class JLogin extends JDialog implements ActionListener {

    private JPanel Panelgrande;
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JTextField tfUsuario;
    private JPasswordField tfContraseña;

    private String usuario;
    private String contrasena;

    public JLogin() {
        super();
        setTitle("Login");
        getContentPane().add(Panelgrande);
        pack();
        setLocationRelativeTo(null);
        setModal(true);

        aceptarButton.addActionListener(this);
        cancelarButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == aceptarButton) {
            aceptar();
        }
        else if (e.getSource() == cancelarButton) {
            cancelar();
        }
    }

    private void aceptar() {
        if ((tfUsuario.getText().equals(""))
                || (tfContraseña.getPassword().toString().equals(""))) {
            JOptionPane.showMessageDialog(null,
                    "Introduce usuario y contraseña",
                    "Login", JOptionPane.ERROR_MESSAGE);
            return;
        }

        usuario = tfUsuario.getText();
        contrasena = tfUsuario.getText();
        setVisible(false);
    }

    private void cancelar() {
        setVisible(false);
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }
}