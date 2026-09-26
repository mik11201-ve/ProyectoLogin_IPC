package modelo;

public class Administrador extends Usuario {
    
    // Constructor: Inicializa al administrador
    public Administrador(String username, String password) {
        // La instrucción 'super' invoca al constructor de la clase padre (Usuario)
        // y le asigna automáticamente el rol fijo de "Administrador"
        super(username, password, "Administrador");
    }
}