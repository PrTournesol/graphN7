package model;

import java.util.ArrayList;

public class BFSstep {
    ArrayList<BFSvertex> data;
    private int size;

    public BFSstep(int size) {
        this.size=size;
        this.data = new ArrayList<>(size);
    }

    public BFSvertex get(int index) {
        return data.get(index);
    }

    public BFSvertex set(int index, BFSvertex element) {
        return data.set(index, element);
    }

    public boolean add(BFSvertex vertex) {
        return data.add(vertex);
    }

    public ArrayList<BFSvertex> copy(){
        ArrayList<BFSvertex> theCopy = new ArrayList<BFSvertex>(size);
        for (BFSvertex vertex : data) {
            theCopy.add(vertex.copy());
        }
        return theCopy;
    }
}
