package ProfesoresAlumnos.Presentacion.Ventanas;

import ProfesoresAlumnos.Persistencia.ConexionDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class VentanaAñadir extends JFrame {
    private JPanel PanelPrincipal;
    private JTextField textField1DNI;
    private JTextField textField2Apellido;
    private JTextField textField3Nombre;
    private JTextField textField4Telefono;
    private JCheckBox profesorCheckBox;
    private JCheckBox alumnoCheckBox;
    private JButton confirmarButton;
    private VentanaPrincipal ventanaPrincipal;


    public VentanaAñadir(VentanaPrincipal ventanaPrincipal) {

        this.ventanaPrincipal=ventanaPrincipal;
        init();

    }

    public void init(){
        setTitle("Añadir");
        setContentPane(PanelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setLocation(500,500);
        setVisible(true);
        setResizable(false);

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               ConexionDB  conexionDB=new ConexionDB();

                String sql= """
                                insert into persona(dni, nombre, apellido, telefono) VALUES (?,?,?,?)
                                """;
                conexionDB.InsertarPersona(sql,textField1DNI.getText(),textField3Nombre.getText(),
                        textField2Apellido.getText(),textField4Telefono.getText());

                if (profesorCheckBox.isSelected()){
                    String sql1= """
                                insert into profesor(dni) values(?); 
                                """;
                    conexionDB.InsertarAP(sql1,textField1DNI.getText());

                }else if (alumnoCheckBox.isSelected()){
                    String sql1= """
                                insert into alumno(dni) values(?); 
                                """;
                    conexionDB.InsertarAP(sql1,textField1DNI.getText());
                }
                ventanaPrincipal.listarPersonas();
                dispose();

            }
        });
        profesorCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (alumnoCheckBox.isSelected()){
                    profesorCheckBox.setSelected(false);
                }
            }
        });
        alumnoCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (profesorCheckBox.isSelected()){
                    alumnoCheckBox.setSelected(false);
                }
            }
        });
    }


}
