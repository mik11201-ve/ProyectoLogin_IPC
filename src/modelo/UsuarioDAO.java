package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    
    // Este método recibe los datos de la pantalla y busca en la Base de Datos
    public Usuario autenticar(String username, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario usuarioResult = null; // Aquí se aplicará el Polimorfismo

        // Sentencia SQL parametrizada para evitar inyecciones maliciosas
        String sql = "SELECT username, password, rol FROM usuarios WHERE username = ? AND password = ?";

        try {
            // 1. Abre el puente pidiendo la conexión (Pasos 8 y 9 del diagrama de secuencia)
            con = ConexionBD.getConexion(); 
            
            if (con != null) {
                // 2. Prepara la consulta inyectando el usuario y la clave de forma segura
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                
                // 3. Ejecuta la consulta en MySQL Workbench (Pasos 10 y 11)
                rs = ps.executeQuery(); 

                // 4. Si MySQL encuentra una coincidencia, procesamos el resultado
                if (rs.next()) {
                    String dbUser = rs.getString("username");
                    String dbRol = rs.getString("rol");

                    // APLICACIÓN DE POLIMORFISMO:
                    // Evaluamos el rol. Si es Administrador, instanciamos la clase hija.
                    if (dbRol.equalsIgnoreCase("Administrador")) {
                        // Pasos 12 y 14: Un objeto Administrador se almacena en una variable de tipo Usuario
                        usuarioResult = new Administrador(dbUser, password);
                    } else {
                        usuarioResult = new Usuario(dbUser, password, dbRol);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR DAO: Fallo en la consulta SQL: " + e.getMessage());
        } finally {
            // Cierre obligatorio de los canales de comunicación para liberar memoria en el servidor
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("ERROR DAO: No se pudieron cerrar los recursos: " + e.getMessage());
            }
        }
        
        // Paso 15: Retorna el objeto encontrado (o null si las credenciales fallaron)
        return usuarioResult; 
    }
}