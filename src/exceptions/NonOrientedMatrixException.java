package exceptions;

public class NonOrientedMatrixException extends Exception {

    public NonOrientedMatrixException() {
        super("This is incopatible with a non oriented matrix, make it oriented");
    }

}
