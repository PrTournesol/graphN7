package model;

public class BFSvertex implements Cloneable{
    public int distToSource;
    public BFScolors color;

    public BFSvertex(int distToSource, BFScolors color) {
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

    public BFSvertex copy() {
        BFSvertex newVertex = new BFSvertex(distToSource,color);
        return newVertex;
    }
}
