package exceptions;

public class OrientedMatrixException extends Exception {

    public OrientedMatrixException() {
        super("This is incopatible with a oriented matrix, make it non oriented or use another method");
    }

}
