package main;

import controlador.LoginControlador;
import modelo.UsuarioDAO;
import vista.LoginVista;
import vista.MenuAdminVista;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciamos las Vistas (Las pantallas que están apagadas en memoria)
        LoginVista vistaLogin = new LoginVista();
        MenuAdminVista vistaMenu = new MenuAdminVista();
        
        // 2. Instanciamos el Modelo (El gestor de acceso a datos)
        UsuarioDAO modeloDAO = new UsuarioDAO();
        
        // 3. Instanciamos al Controlador e inyectamos las dependencias
        // Al nacer, el controlador une los botones de las vistas con la lógica del modelo
        LoginControlador controlador = new LoginControlador(vistaLogin, vistaMenu, modeloDAO);
        
        // 4. Centramos la pantalla de Login en el monitor del usuario
        vistaLogin.setLocationRelativeTo(null);
        
        // 5. ¡Encendemos el sistema! Hacemos visible la pantalla de Login
        vistaLogin.setVisible(true);
    }

}