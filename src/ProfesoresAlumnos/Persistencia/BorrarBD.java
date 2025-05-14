package ProfesoresAlumnos.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BorrarBD {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/colegio";
        try{
            Connection connection= DriverManager.getConnection(url,"root","");
            Statement stmt=connection.createStatement();

            String createDataBase= """
                                 drop database colegio
                    """;
            stmt.execute(createDataBase);




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
