package modelo;

public class Usuario {
    // Atributos protegidos para que las clases hijas (como Administrador) puedan heredarlos
    protected String username;
    protected String password;
    protected String rol;

    // Constructor: El molde para inicializar un usuario con sus datos
    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    // Métodos Getter: Permiten a otras capas (como el Controlador) leer los datos de forma segura
    public String getUsername() { 
        return username; 
    }
    
    public String getRol() { 
        return rol; 
    }
}