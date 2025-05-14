package ProfesoresAlumnos.Presentacion.Ventanas;

import ProfesoresAlumnos.Persistencia.ConexionDB;
import ProfesoresAlumnos.Persistencia.DatosPersona;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrincipal extends  JFrame {
    private JPanel panelPrincipal;
    private JPanel PanelTabla;
    private JTable table1;
    private JButton añadirButton;
    private JButton borrarButton;
    private JComboBox comboBox1;
    private JButton ordenarButton;
    private  DefaultTableModel tablaModelo;

    public VentanaPrincipal() {
        init();
    }

    public void init(){

        setTitle("Ventana Principal");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

        añadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaAñadir ventanaAñadir=new VentanaAñadir(VentanaPrincipal.this);
                ventanaAñadir.setVisible(true);
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ConexionDB conexionDB=new ConexionDB();

                int fila = table1.getSelectedRow();

                if(fila==-1){
                    System.out.println("");
                }

                int filaModelo = table1.convertRowIndexToModel(fila);

                Object dni = null;
                try {
                    dni = tablaModelo.getValueAt(filaModelo, 0);
                } catch (Exception ex) {
                   JOptionPane.showMessageDialog(null,"No has Selecionado ninguna persona","Error",JOptionPane.ERROR_MESSAGE);
                }
                String sql= """
                            delete from persona where dni=?
                            """;

                conexionDB.BorrarPersona(sql, (String) dni);
                listarPersonas();
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila=table1.getSelectedRow();
                int filaModelo = table1.convertRowIndexToModel(fila);

                if(e.getClickCount()==2){
                    completarDatos((String) tablaModelo.getValueAt(filaModelo,0), (String) tablaModelo.getValueAt(filaModelo,1),
                            (String) tablaModelo.getValueAt(filaModelo,2), (String) tablaModelo.getValueAt(filaModelo,3));
                }

            }
        });
        ordenarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                DatosPersona datosPersona=new DatosPersona();

                if(comboBox1.getSelectedItem().equals("DNI")) {

                    tablaModelo.setRowCount(0);
                    datosPersona.OrdenarDatosPersonas("dni");

                } else if (comboBox1.getSelectedItem().equals("NOMBRE")) {

                    tablaModelo.setRowCount(0);
                    datosPersona.OrdenarDatosPersonas("nombre");

                }else if(comboBox1.getSelectedItem().equals("APELLIDO")){

                    tablaModelo.setRowCount(0);
                    datosPersona.OrdenarDatosPersonas("apellido");

                }else if(comboBox1.getSelectedItem().equals("TELEFONO")){

                    tablaModelo.setRowCount(0);
                    datosPersona.OrdenarDatosPersonas("telefono");
                }

                datosPersona.getLisaPersona().forEach(persona -> {
                    tablaModelo.addRow(new Object[]{persona.getDNI(),persona.getNombre(),persona.getApellido(),persona.getTelefono(),
                            persona.getAlumno(),persona.getProfesor()});
                });

            }

        });

    }

    public void completarDatos(String DNI,String Nombre,String Apellido,String Telefono){

        VentanaModificar ventanaModificar=new VentanaModificar(VentanaPrincipal.this);
        setVisible(true);
        ventanaModificar.setTextFieldDni(DNI);
        ventanaModificar.setTextFieldNombre(Nombre);
        ventanaModificar.setTextFieldApellido(Apellido);
        ventanaModificar.setTextFieldTelefono(Telefono);

        int fila=table1.getSelectedRow();
        int filaModelo = table1.convertRowIndexToModel(fila);

        if (tablaModelo.getValueAt(filaModelo,4).equals("-")){
            ventanaModificar.setProfesorCheckBox(true);
        }else if(tablaModelo.getValueAt(filaModelo,5).equals("-")){
            ventanaModificar.setAlumnoCheckBox(true);
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        String titulos[] = {"DNI", "Nombre", "Apellido", "Telefono", "Alumno", "Profesor"};
        tablaModelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaModelo.setColumnIdentifiers(titulos);
        table1 = new JTable(tablaModelo);

        TableRowSorter<DefaultTableModel> ordenacion = new TableRowSorter<>(tablaModelo);

        table1.setRowSorter(ordenacion);

        listarPersonas();


    }

    public void listarPersonas(){

        DatosPersona datosPersona=new DatosPersona();
        tablaModelo.setRowCount(0);
        datosPersona.listarDatosPersonas();

        datosPersona.getLisaPersona().forEach(persona -> {
            tablaModelo.addRow(new Object[]{persona.getDNI(),persona.getNombre(),persona.getApellido(),persona.getTelefono(),
                    persona.getAlumno(),persona.getProfesor()});
        });

    }



}
