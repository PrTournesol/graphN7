package model;

public class AlgoVertex implements Cloneable{
    public int distToSource;
    public GraphColors color;

    public AlgoVertex(int distToSource, GraphColors color) {
        this.distToSource = distToSource;
        this.color = color;
    }

    @Override
    public String toString() {
        return "\nvertex{" +
                "distToSource=" + distToSource +
                ", color=" + color +
                '}';
    }

    public AlgoVertex copy() {
        AlgoVertex newVertex = new AlgoVertex(distToSource,color);
        return newVertex;
    }
}
