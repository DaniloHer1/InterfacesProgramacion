package ProfesoresAlumnos.Persistencia;

import ProfesoresAlumnos.Logica.Persona;
import ProfesoresAlumnos.Presentacion.Ventanas.VentanaPrincipal;

import java.sql.*;
import java.util.List;

public class ConexionDB {

    public void BorrarPersona(String sql,String dni){

        String url = "jdbc:mysql://localhost:3306/colegio";

        try {
            Connection connection = DriverManager.getConnection(url, "root", "");

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,dni);

            stmt.executeUpdate();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void InsertarPersona(String sql,String dni,String nombre,String apellido,String Telefono){

        String url = "jdbc:mysql://localhost:3306/colegio";

        try {
            Connection connection = DriverManager.getConnection(url, "root", "");

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,dni);
            stmt.setString(2,nombre);
            stmt.setString(3,apellido);
            stmt.setString(4,Telefono);

            stmt.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void InsertarAP(String sql,String dni){
        String url = "jdbc:mysql://localhost:3306/colegio";

        try {
            Connection connection = DriverManager.getConnection(url, "root", "");

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,dni);

            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
