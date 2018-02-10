package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class AlgoStep {
    ArrayList<AlgoVertex> data;
    private int size;

    public AlgoStep(int size) {
        this.size=size;
        this.data = new ArrayList<>(size);
    }

    public AlgoVertex get(int index) {
        return data.get(index);
    }

    public AlgoVertex set(int index, AlgoVertex element) {
        return data.set(index, element);
    }

    public boolean add(AlgoVertex vertex) {
        return data.add(vertex);
    }

    public ArrayList<AlgoVertex> copy(){
        ArrayList<AlgoVertex> theCopy = new ArrayList<AlgoVertex>(size);
        for (AlgoVertex vertex : data) {
            theCopy.add(vertex.copy());
        }
        return theCopy;
    }

    public int getMinDistToSource(HashSet<Integer> remained) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i : remained){
            if (data.get(i).distToSource<min) {
                min = data.get(i).distToSource;
                index=i;
            }
        }
        return index;
    }
}
