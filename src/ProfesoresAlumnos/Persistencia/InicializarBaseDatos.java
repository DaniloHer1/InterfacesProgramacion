package ProfesoresAlumnos.Persistencia;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializarBaseDatos {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";

        try{
            Connection connection= DriverManager.getConnection(url,"root","");
            Statement stmt=connection.createStatement();

        stmt.addBatch("""
                CREATE DATABASE IF NOT EXISTS Colegio
            """);
            stmt.addBatch("USE Colegio");

            stmt.addBatch("""
                CREATE TABLE IF NOT EXISTS persona (
                    dni VARCHAR(100) PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    apellido VARCHAR(100) NOT NULL,
                    telefono VARCHAR(100)
                );
            """);

            stmt.addBatch("""
                CREATE TABLE IF NOT EXISTS profesor (
                    id_prof INT PRIMARY KEY AUTO_INCREMENT,
                    dni VARCHAR(100) NOT NULL,
                    FOREIGN KEY (dni) REFERENCES persona(dni) ON DELETE CASCADE
                );
            """);

            stmt.addBatch("""
                CREATE TABLE IF NOT EXISTS alumno (
                    id_alumn INT PRIMARY KEY AUTO_INCREMENT,
                    dni VARCHAR(100) NOT NULL,
                    FOREIGN KEY (dni) REFERENCES persona(dni) ON DELETE CASCADE
                );
            """);

            // Insertar datos en persona
            stmt.addBatch("""
                INSERT INTO persona (dni, nombre, apellido, telefono) VALUES
                ('12345678A', 'Juan', 'Pérez', '600123456'),
                ('23456789B', 'María', 'López', '600234567'),
                ('34567890C', 'Carlos', 'García', '600345678'),
                ('45678901D', 'Lucía', 'Martínez', '600456789'),
                ('56789012E', 'Ana', 'Sánchez', '600567890');
            """);

            // Insertar en profesor
            stmt.addBatch("""
                INSERT INTO profesor (dni) VALUES
                ('12345678A'),
                ('23456789B');
            """);

            // Insertar en alumno
            stmt.addBatch("""
                INSERT INTO alumno (dni) VALUES
                ('34567890C'),
                ('45678901D'),
                ('56789012E');
            """);

     
            stmt.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
