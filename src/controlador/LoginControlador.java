package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import modelo.UsuarioDAO;
import vista.LoginVista;
import vista.MenuAdminVista;

public class LoginControlador {
    // El controlador necesita conocer y tener acceso a las otras capas
    private LoginVista vistaLogin;
    private MenuAdminVista vistaMenu;
    private UsuarioDAO modeloDAO;

    // Constructor: Recibe las pantallas y el modelo para unirlos (Inyección de dependencias)
    public LoginControlador(LoginVista vistaLogin, MenuAdminVista vistaMenu, UsuarioDAO modeloDAO) {
        this.vistaLogin = vistaLogin;
        this.vistaMenu = vistaMenu;
        this.modeloDAO = modeloDAO;

        // Le ordenamos a las pantallas que le entreguen el control de sus botones a este controlador
        this.vistaLogin.escucharBotonIngresar(new EncargadoBotonIngresar());
        this.vistaMenu.escucharBotonCerrar(new EncargadoBotonCerrar());
    }

    // CLASE INTERNA: El "oído" que escucha cuando haces clic en el botón "Ingresar" con el mouse
    private class EncargadoBotonIngresar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Paso 3 y 5: Extrae las credenciales que el usuario escribió en la pantalla
            String userTXT = vistaLogin.getUsuario();
            String passTXT = vistaLogin.getPassword();

            // Validación simple: que los campos no estén vacíos en la interfaz
            if (userTXT.trim().isEmpty() || passTXT.trim().isEmpty()) {
                JOptionPane.showMessageDialog(vistaLogin, "Por favor, llene todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Paso 7: El controlador va al Modelo (DAO) a consultar la Base de Datos
            Usuario usuarioAutenticado = modeloDAO.autenticar(userTXT, passTXT);

            // Paso 16 y 17: El controlador evalúa la respuesta del modelo
            if (usuarioAutenticado != null) {
                // Si el objeto no es nulo, las credenciales son correctas
                if (usuarioAutenticado.getRol().equalsIgnoreCase("Administrador")) {
                    // Paso 20: Transición de pantallas si es Administrador
                    vistaLogin.setVisible(false); // Oculta el Login
                    vistaMenu.setVisible(true);   // Enciende el Menú Principal
                } else {
                    // En caso de que en un futuro crees otros roles (ej. Operador, Consultor)
                    JOptionPane.showMessageDialog(vistaLogin, "Acceso concedido. Rol: " + usuarioAutenticado.getRol(), "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // Paso 17 (Alternativa de fallo): Si el modelo devolvió null, las credenciales no existen
                JOptionPane.showMessageDialog(vistaLogin, "Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // CLASE INTERNA: El "oído" que escucha cuando haces clic en "Cerrar Sesión" en el Menú
    private class EncargadoBotonCerrar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Flujo inverso seguro: oculta el menú, limpia las cajitas de texto y reabre el Login
            vistaMenu.setVisible(false);
            vistaLogin.limpiarCampos();
            vistaLogin.setVisible(true);
        }
    }

}
