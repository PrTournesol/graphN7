package exceptions;

public class WrongSizeException extends Throwable {

    public WrongSizeException() {
        super("The Vertex you are trying to add has not the good size for the matrix.");
    }

}
