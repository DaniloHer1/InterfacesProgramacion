package ProfesoresAlumnos.Presentacion.Ventanas;

import ProfesoresAlumnos.Persistencia.ConexionDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class VentanaModificar extends JFrame {

    private JPanel panelPrincipal;
    private JTextField textFieldDni;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldTelefono;
    private JCheckBox profesorCheckBox;
    private JCheckBox alumnoCheckBox;
    private JButton modificarButton;
    private JPanel botopanel;
    private VentanaPrincipal ventanaPrincipal;

     public  VentanaModificar(VentanaPrincipal ventanaPrincipal) {

         this.ventanaPrincipal=ventanaPrincipal;
         init();


     }


    public void init(){
        setTitle("Modificar");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setLocation(1200,500);
        setVisible(true);
        setResizable(false);
        textFieldDni.setEditable(false);

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
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ConexionDB conexionDB=new ConexionDB();

                String sqlBorr = """
                           delete from persona where dni=?
                                """;
              conexionDB.BorrarPersona(sqlBorr,textFieldDni.getText());

              String sqlIN= """
                         insert into persona(dni, nombre, apellido, telefono) VALUES (?,?,?,?);                                                                            
                            """;
                conexionDB.InsertarPersona(sqlIN,textFieldDni.getText(),textFieldNombre.getText(),
                        textFieldApellido.getText(),textFieldTelefono.getText());



                if (profesorCheckBox.isSelected()){
                    String sql1= """
                                delete from alumno  where dni=?
                                """;
                    conexionDB.InsertarAP(sql1,textFieldDni.getText());
                    String sql2= """
                                insert into profesor(dni) values(?); 
                                """;
                    conexionDB.InsertarAP(sql2,textFieldDni.getText());
                }else if (alumnoCheckBox.isSelected()){
                    String sql1= """
                                delete from profesor  where dni=?
                                """;
                    conexionDB.InsertarAP(sql1,textFieldDni.getText());
                    String sql2= """
                                insert into alumno(dni) values(?); 
                                """;
                    conexionDB.InsertarAP(sql2,textFieldDni.getText());
                }
                ventanaPrincipal.listarPersonas();

                dispose();
            }

        });

    }


    public void setTextFieldDni(String Dni) {
        textFieldDni.setText(Dni);

    }

    public void setTextFieldNombre(String Nombre) {
        textFieldNombre.setText(Nombre);
    }

    public void setTextFieldApellido(String Apellido) {
        textFieldApellido.setText(Apellido);
    }

    public void setTextFieldTelefono(String Telefono) {
        textFieldTelefono.setText(Telefono);
    }

    public void setAlumnoCheckBox(boolean cierto) {
        alumnoCheckBox.setSelected(cierto);
    }

    public void setProfesorCheckBox(boolean cierto) {
        profesorCheckBox.setSelected(cierto);
    }
}


