package model;

import java.util.ArrayList;

import exceptions.VertexInexistentException;
import exceptions.WrongSizeException;

public class AdjacencyMatrix {
    private ArrayList<ArrayList<Integer>> data;

    public AdjacencyMatrix(ArrayList<ArrayList<Integer>> data) {
        this.data = data;
    }

    public AdjacencyMatrix() {
        this.data= new ArrayList< ArrayList<Integer>>();
    }

    public void addVertexNonOriented (ArrayList<Integer> newVertex, int index) throws WrongSizeException {
        if ((data.size()+1) != newVertex.size()) {
            throw new WrongSizeException();
        }
        for (int i=0; i<index;i++) {
            data.get(i).add(index,newVertex.get(i));
        }
        for (int i=index; i<data.size();i++) {
            data.get(i).add(index,newVertex.get(i+1));
        }
        data.add(index, newVertex);
    }

    public void addVertexnOriented (ArrayList<Integer> vertex, int index) throws WrongSizeException {
        if ((data.size()+1) != vertex.size()) {
            throw new WrongSizeException();
        }
        for (int i=1; i<=data.size();i++) {
            //TODO
            System.out.println("TODO");
            // existingVertex.add(index,0);
        }
        data.add(index, vertex);
    }

    public void delVertex (int vertexNumber) throws VertexInexistentException {
        if (vertexNumber > data.size()) {
            throw new VertexInexistentException();
        }
        data.remove(vertexNumber);
        for (ArrayList<Integer> vertex: data) {
            vertex.remove(vertexNumber);
        }
    }

    public ArrayList<ArrayList<Integer>> getData() {
        return data;
    }
}
