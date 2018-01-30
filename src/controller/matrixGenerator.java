package controller;

import model.AdjacencyMatrix;

import java.util.ArrayList;
import java.util.Random;

public class matrixGenerator {

    public static void main (String args[]){
        AdjacencyMatrix matrix;
        matrix = GenerateGraphNonOriented(5);
        System.out.println("\n"+matrix.toString());

    }

    public static AdjacencyMatrix GenerateGraphOriented(int size) {
        ArrayList<Integer> subMatrix;
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(size);
        Random rand = new Random();
        for (int i=1; i<=size; i++){
            subMatrix = new ArrayList<Integer>(size);
            for (int j=0; j<=size; j++) {
                subMatrix.add(rand.nextInt(2)*rand.nextInt(50));
            }
            matrix.add(subMatrix);
        }
        AdjacencyMatrix adMatric = new AdjacencyMatrix(matrix);
        return adMatric;
    }

    public static AdjacencyMatrix GenerateCompleteGraphOriented(int size) {
        ArrayList<Integer> subMatrix;
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(size);
        Random rand = new Random();
        for (int i=1; i<=size; i++){
            subMatrix = new ArrayList<Integer>(size);
            for (int j=0; j<=size; j++) {
                if (rand.nextInt(10)<7){
                    subMatrix.add(0);
                }
                else{
                    subMatrix.add(rand.nextInt(100));
                }
            }
            matrix.add(subMatrix);
        }
        AdjacencyMatrix adMatric = new AdjacencyMatrix(matrix);
        return adMatric;
    }

    public static AdjacencyMatrix GenerateCycleGraphOriented(int size) {
        ArrayList<Integer> subMatrix;
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(size);
        Random rand = new Random();
        for (int i=1; i<=(rand.nextInt(size-2)+2); i++){
            //TODO
            subMatrix = new ArrayList<Integer>(size);
            for (int j=0; j<=size; j++) {
                if (rand.nextInt(10)<7){
                    subMatrix.add(0);
                }
                else{
                    subMatrix.add(rand.nextInt(100));
                }
            }
            matrix.add(subMatrix);
        }
        AdjacencyMatrix adMatric = new AdjacencyMatrix(matrix);
        return adMatric;
    }


    public static AdjacencyMatrix GenerateGraphNonOriented(int size) {
        ArrayList<Integer> subMatrix;
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(size);
        Random rand = new Random();
        for (int i=1; i<=size; i++){
            subMatrix = new ArrayList<Integer>(size);
            for (int j=i; j<=size; j++) {
                subMatrix.add(rand.nextInt(100));
            }
            matrix.add(subMatrix);
        }
        for (int i=0; i<size; i++){
            for (int j=0; j<i; j++) {
                matrix.get(i).add(j,matrix.get(j).get(i));
            }
        }
        AdjacencyMatrix adMatric = new AdjacencyMatrix(matrix);
        return adMatric;
    }
}
