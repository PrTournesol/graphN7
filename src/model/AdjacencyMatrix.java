package model;

import java.util.ArrayList;

import exceptions.NonOrientedMatrixException;
import exceptions.OrientedMatrixException;
import exceptions.VertexInexistentException;
import exceptions.WrongSizeException;

public class AdjacencyMatrix {
    private ArrayList<ArrayList<Integer>> data;
    private ArrayList<String> names;
    private boolean orientedMx; //true if the theMatrix to generate is oriented
    private int size;


    public AdjacencyMatrix(ArrayList<ArrayList<Integer>> data, boolean oriented) {
        this.data = data;
        this.orientedMx=oriented;
        this.names = new ArrayList<String>();
        this.size=data.size();
        for (int i = 0; i< getSize(); i++){
            names.add(""+i);
        }
        // complete the vertexes with the good one
        if (!oriented) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < i; j++) {
                    data.get(i).add(j, data.get(j).get(i));
                }
            }
        }
    }

    public AdjacencyMatrix(int size, boolean oriented) {
        this.data = new ArrayList<>(size);
        this.orientedMx=oriented;
        this.names = new ArrayList<>();
        this.size=size;
        // complete the vertexes with the good one
            for (int i = 0; i < size; i++) {
                names.add(""+i);
                data.add(new ArrayList<>(size));
                for (int j = 0; j < size; j++) {
                    data.get(i).add(0);
                }
            }
    }

    public AdjacencyMatrix() {
        this.data= new ArrayList< ArrayList<Integer>>();
    }

    public void addVertexNonOriented (ArrayList<Integer> newEdgesWeight, int index, String name) throws WrongSizeException, OrientedMatrixException {
        if ((data.size()+1) != newEdgesWeight.size()) {
            throw new WrongSizeException();
        }
        if (orientedMx){
            throw new OrientedMatrixException();
        }
        for (int i=0; i<index;i++) {
            this.set(i,index,newEdgesWeight.get(i));
        }
        for (int i=index; i<data.size();i++) {
            this.set(i,index,newEdgesWeight.get(i+1));
        }
        data.add(index, newEdgesWeight);
        names.add(index,name);
        size+=1;
    }

    public void addVertexnOriented (ArrayList<Integer> outgoingEdgesWeight, ArrayList<Integer> enteringEdgesWeight, int index, String name) throws WrongSizeException, NonOrientedMatrixException {
        if ((data.size()+1) != outgoingEdgesWeight.size() || (data.size()+1) != enteringEdgesWeight.size()) {
            throw new WrongSizeException();
        }
        if (!orientedMx){
            throw new NonOrientedMatrixException();
        }
        for (int i=0; i<index;i++) {
            this.set(i,index,enteringEdgesWeight.get(i));
        }
        for (int i=index; i<data.size();i++) {
            this.set(i,index,enteringEdgesWeight.get(i+1));
        }
        data.add(index, outgoingEdgesWeight);
        names.add(index,name);
        size+=1;
    }

    public void delVertex (int vertexNumber) throws VertexInexistentException {
        if (vertexNumber > data.size()) {
            throw new VertexInexistentException();
        }
        data.remove(vertexNumber);
        names.remove(vertexNumber);
        for (ArrayList<Integer> vertex: data) {
            vertex.remove(vertexNumber);
        }
    }

    public String toString(){
        String rep="";
        int i;
        if (size==0){
            return "Empty matrix []";
        }
        rep+="\t";
        // ajout des noms sur les colonnes
        for (String stg: names){
            rep+="\t"+stg;
        }
        rep+="\n\t";

        // ajout des | pour les colonnes :
        for (int j = 0; j< getSize(); j++){
            rep+="\t|";
        }
        rep+="\n";
        i=0;
        for (ArrayList<Integer> vertex: data) {
            // ajout du nom sur les lignes
            rep+=names.get(i) + "\t-";
            i++;
            // parcours de la sous liste (ArrayList<Integer> dans l'ArrayList data)
            for (Integer nb:vertex) {
                rep+=" \t"+nb;
            }
            rep+="\n";
        }
        return rep;
    }

    public int getSize() {
        return data.size();
    }

    public int getVertex(int index, int subIndex) {
        return data.get(index).get(subIndex);
    }

    public String getName(int index){
        return names.get(index);
    }
    
    

    public Object[] namesToArray() {
		return names.toArray();
	}

	public void setNames(ArrayList<String> names) throws WrongSizeException {
        if (names.size() != this.getSize()){
            throw new WrongSizeException();
        }
        this.names = names;
    }

    public boolean isOrientedMx() {
        return orientedMx;
    }

    public void set(int index, int subIndex, int element) {
        try {
            data.get(index).add(subIndex,element);
        }
        catch (IndexOutOfBoundsException e){
            data.get(index).add(element);
        }
    }

    public ArrayList<Integer> getAdjecents(int index) {
        return data.get(index);
    }
}
