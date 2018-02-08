package model;

import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;

import exceptions.VertexInexistentException;
import exceptions.WrongSizeException;

public class AdjacencyMatrix {
    private ArrayList<ArrayList<Integer>> data;
    private ArrayList<String> names;
    private boolean orientedMx; //true if the theMatrix to generate is oriented

    public AdjacencyMatrix(ArrayList<ArrayList<Integer>> data, boolean oriented) {
        this.data = data;
        this.orientedMx=oriented;
        this.names = new ArrayList<String>();
        for (int i=1; i<= size(); i++){
            names.add(""+i);
        }
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

    public String toString(){
        String rep="";
        int i;
        rep+="\t";
        for (String stg: names){
            rep+="\t"+stg;
        }
        rep+="\n\t";
        for (int j=0; j<size();j++){
            rep+="\t|";
        }
        rep+="\n";
        i=0;
        for (ArrayList<Integer> vertex: data) {
            rep+=names.get(i) + "\t-";
            i++;
            for (Integer nb:vertex) {
                rep+=" \t"+nb;
            }
            rep+="\n";
        }
        return rep;
    }

    public int size() {
        return data.size();
    }

    public int getWeight(int index, int subIndex) {
        return data.get(index).get(subIndex);
    }

    public String getName(int index){
        return names.get(index);
    }

    public void setNames(ArrayList<String> names) throws WrongSizeException {
        if (names.size() != this.size()){
            throw new WrongSizeException();
        }
        this.names = names;
    }

    public boolean isOrientedMx() {
        return orientedMx;
    }
}
