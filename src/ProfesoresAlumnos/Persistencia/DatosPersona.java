package ProfesoresAlumnos.Persistencia;

import ProfesoresAlumnos.Logica.Persona;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatosPersona {

    private List<Persona> lisaPersona=new ArrayList<>();

    public  void OrdenarDatosPersonas(String orden){
        lisaPersona.clear();
        String url = "jdbc:mysql://localhost:3306/colegio";

        try {
            Connection connection = DriverManager.getConnection(url, "root", "");

            Statement stmt=connection.createStatement();

            String sql= """
                    SELECT p.*,
                                CASE WHEN a.dni IS NOT NULL THEN 'X' ELSE '-' END AS es_alumno,
                                CASE WHEN pr.dni IS NOT NULL THEN 'X' ELSE '-' END AS es_profesor
                                FROM persona p
                                LEFT JOIN alumno a ON p.dni = a.dni
                                LEFT JOIN profesor pr ON p.dni = pr.dni
                                order by p. """+orden;


            ResultSet resultSet= stmt.executeQuery(sql);

            while (resultSet.next()){
                lisaPersona.add(new Persona(resultSet.getString(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
                        ,resultSet.getString(6)));
            }
        } catch (SQLException e) {

                  JOptionPane.showMessageDialog(null,"Error DB"," ",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarDatosPersonas(){
        lisaPersona.clear();
        String url = "jdbc:mysql://localhost:3306/colegio";

        try {
            Connection connection = DriverManager.getConnection(url, "root", "");
            Statement stmt = connection.createStatement();
            String sql= """
                    SELECT p.*,
                                CASE WHEN a.dni IS NOT NULL THEN 'X' ELSE '-' END AS es_alumno,
                                CASE WHEN pr.dni IS NOT NULL THEN 'X' ELSE '-' END AS es_profesor
                                FROM persona p
                                LEFT JOIN alumno a ON p.dni = a.dni
                                LEFT JOIN profesor pr ON p.dni = pr.dni;
                         """;

            ResultSet resultSet= stmt.executeQuery(sql);

            while (resultSet.next()){
               lisaPersona.add(new Persona(resultSet.getString(1),resultSet.getString(2),
                       resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
                        ,resultSet.getString(6)));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error DB"," ",JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Persona> getLisaPersona() {
        return lisaPersona;
    }

}
