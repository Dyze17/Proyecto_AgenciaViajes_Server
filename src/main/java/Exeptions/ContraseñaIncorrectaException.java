package Exeptions;

public class ContraseñaIncorrectaException extends Exception{
    public ContraseñaIncorrectaException() {
        super("Contraseña incorrecta");
    }
}
