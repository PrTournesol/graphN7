package exceptions;

public class VertexInexistentException extends Exception {

    public VertexInexistentException() {
        super("You are trying to delete an inexistent Vertex.");
    }
}
