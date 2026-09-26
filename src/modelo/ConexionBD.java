package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // La dirección que apunta a la base de datos de Workbench 8.0
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto_login"; 
    private static final String USER = "root";
    
    // IMPORTANTE: Si al instalar MySQL le asignaste una contraseña al usuario root, 
    // escríbela aquí adentro. Si no le pusiste contraseña (como en XAMPP), déjalo vacío: ""
    private static final String PASSWORD = "root2026"; 

    public static Connection getConexion() {
        Connection conexion = null;
        try {
            // Registra el Driver que agregamos a Libraries (Paso obligatorio en Java)
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Intenta abrir el puente de comunicación
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: No se encontró el conector de MySQL en Libraries. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR: No se pudo conectar a la base de datos. ¿MySQL está encendido? " + e.getMessage());
        }
        return conexion;
    }
}