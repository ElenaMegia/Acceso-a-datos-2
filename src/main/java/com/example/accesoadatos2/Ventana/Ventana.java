package com.example.accesoadatos2.Ventana;

import com.example.accesoadatos2.JLogin;
import com.example.accesoadatos2.util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Ventana implements Ventanaact, ActionListener {


    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton nuevoButton;
    private JButton guardarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton cancelarButton;
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfNacionalidad;
    private JFormattedTextField fechatf;
    private JTable Partidos;
    private JList list1;
    private JTextField Busqueda;
    private JTable tablatenistas;
    private JLabel apellidostf;
    private JLabel tfnombre;
    private JLabel tffecha;
    private JLabel tfnacionalidad;
    private DefaultTableModel mtTenistas;

    private Connection conexion;
    public Ventana() {

        initVentana();
        inittablatenistas();

        try {
            conectar();
            login();
            listarTenistas();
        } catch (ClassNotFoundException cnfe) {
            JOptionPane.showMessageDialog(null,
                    "No se ha podido cargar",
                    "Conectar", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null,
                    "No se ha podido conectar con el servidor. Comprueba " +
                            "que está arrancado",
                    "Conectar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initVentana() {
        JFrame frame = new JFrame("Spotify");
        frame.getContentPane().add(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        addListeners();
    }

    private void inittablatenistas() {

        mtTenistas = new DefaultTableModel();
        mtTenistas.addColumn("Nombre");
        mtTenistas.addColumn("Apellidos");
        mtTenistas.addColumn("F.Nacimiento");
        mtTenistas.addColumn("Nacionalidad");

        tablatenistas.setModel(mtTenistas);
    }

    private void listarTenistas() {

        String sql = "SELECT * FROM tenistas";
        try {
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String apellidos = resultado.getString("apellidos");
                Date fechaNacimiento = resultado.getDate("fecha_nacimiento");
                String nacionalidad = resultado.getString("nacionalidad");

                Object[] fila = new Object[]{nombre, apellidos,
                        fechaNacimiento, nacionalidad};

                mtTenistas.addRow(fila);
            }
        } catch (SQLException sqle) {
            Util.mensajeError("Error al listar", "Tenistas");
            sqle.printStackTrace();
        }
    }

    private void conectar() throws ClassNotFoundException,
            SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        // FIXME coger la información de conexion
        conexion = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/Tenistas", "root", "root");
    }

    private void login() {

        JLogin login = new JLogin();
        login.setVisible(true);

        String usuario = login.getUsuario();
        String contrasena = login.getContrasena();

        String sql = "SELECT nombre FROM usuarios WHERE " +
                "nombre = ? AND contrasena = SHA1(?)";

        try {
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, usuario);
            sentencia.setString(2, contrasena);
            ResultSet resultado = sentencia.executeQuery();

            if (!resultado.next()) {
                JOptionPane.showMessageDialog(null,
                        "Usuario/Contraseña incorrectos", "Login",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "", "",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void addListeners() {

        nuevoButton.addActionListener(this);
        guardarButton.addActionListener( this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == nuevoButton) {
            // TODO Limpiar cajas de texto y hacerlas editables
        }
        else if (e.getSource() == guardarButton) {

            String sql = "INSERT INTO tenistas (nombre, apellidos, " +
                    "fecha_nacimiento, nacionalidad) " +
                    "VALUES (?, ?, ?, ?)";

            try {
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                sentencia.setString(1, tfNombre.getText());
                sentencia.setString(2, tfApellido.getText());
                sentencia.setDate(3, Date.valueOf(fechatf.getText()));
                sentencia.setString(4, tfNacionalidad.getText());

                sentencia.executeUpdate();
            } catch (SQLException sqle) {
                Util.mensajeError("Error al dar de alta", "error");
            }

        }
    }
} 
