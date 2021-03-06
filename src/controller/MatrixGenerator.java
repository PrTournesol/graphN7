package controller;

import exceptions.NonOrientedMatrixException;
import exceptions.WrongSizeException;
import model.AdjacencyMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MatrixGenerator {
    private boolean orientedMx; //true if the theMatrix to generate is oriented
    private int size; //getSize of the theMatrix
    private AdjacencyMatrix theMatrix;

    public static void main(String args[]) throws Exception {
        System.out.println("Matrix generator test :");
        AdjacencyMatrix matrix;
        MatrixGenerator mg = new MatrixGenerator(10);
        matrix = mg.generateRandGraph(false);
        System.out.println("Rand Non-oriented matrix generated :\n"+matrix.toString());
        mg.setOrientedMx(true);
        matrix = mg.generateRandGraph(false);
        System.out.println("Rand oriented matrix generated :\n"+matrix.toString());

        matrix = mg.generateCycleGraphOriented();
        System.out.println("Rand cycle oriented matrix generated :\n"+matrix.toString());


    }

    /** Make a generator for a 5 non-oriented theMatrix
     */
    public MatrixGenerator() {
        this(5,false);
    }

    /** Make a generator for a non-oriented theMatrix
     * @param size the getSize of the square theMatrix to generate
     */
    public MatrixGenerator(int size) {
        this(size,false);
    }

    /**
     * @param size the getSize of the square theMatrix to generate
     * @param orientedMatrix true if you want to generate an oriented theMatrix
     */
    public MatrixGenerator(int size, boolean orientedMatrix) {
        orientedMx=orientedMatrix;
        this.size=size;
        theMatrix=null;
    }

    public AdjacencyMatrix generateRandGraph(boolean random) {
        ArrayList<Integer> subMatrix;
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(size);
        int k;
        Random rand = new Random();
        for (int i = 1; i <= size; i++) {
            subMatrix = new ArrayList<Integer>(size);
            if (this.orientedMx) {
                k = 1;
            } else {
                k = i;
            }
            for (int j = k; j <= size; j++) {
                if (rand.nextInt(10)<7){
                    subMatrix.add(0);
                }
                else if (random){
                        subMatrix.add(rand.nextInt(10));
                    }
                    else{
                        subMatrix.add(rand.nextInt(2));

                }

            }

            matrix.add(subMatrix);
        }
        /*if (!this.orientedMx) {
            for (int i = 0; i < getSize; i++) {
                for (int j = 0; j < i; j++) {
                    matrix.get(i).add(j, matrix.get(j).get(i));
                }
            }
        }*/
        theMatrix = new AdjacencyMatrix(matrix, orientedMx);
        return theMatrix;
    }

    public AdjacencyMatrix generateCycleGraphOriented() throws NonOrientedMatrixException {
        int k;
        if (! orientedMx){
            throw new NonOrientedMatrixException();
        }
        ArrayList<Integer> subMatrix;
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(size);
        Random rand = new Random();
        int stop = rand.nextInt(size-2)+3;

        // generating vertexes of the cycle :
        for (int i=1; i<stop; i++){
            subMatrix = new ArrayList<Integer>(size);
            if (this.orientedMx) {
                k = 1;
            } else {
                k = i;
            }
            for (int j=k; j<=i; j++) {
                subMatrix.add(0);
            }
            // connecting the vertexes to another (part of the cycle)
            subMatrix.add(rand.nextInt(99)+1);

            // finishing the connextions of the vertex
            for (int j=i+1; j<size; j++) {
                subMatrix.add(0);
            }
            matrix.add(subMatrix);
        }

        // closing the cycle :
        subMatrix = new ArrayList<Integer>(size);
        subMatrix.add(rand.nextInt(99)+1);
        for (int j=2; j<=size; j++) {
            subMatrix.add(0);
        }
        matrix.add(subMatrix);


        //completion with empty vertexes :
        for (int i=stop+1; i<=size; i++) {
            subMatrix = new ArrayList<Integer>(size);
            for (int j = 1; j <= size; j++) {
                subMatrix.add(0);
            }
            matrix.add(subMatrix);
        }

        theMatrix = new AdjacencyMatrix(matrix,orientedMx);

        //sharing the vertexes randomly :
        ArrayList<String> names = new ArrayList<String>();
        for (int i=1; i<= size; i++){
            names.add(""+i);
        }
        Collections.shuffle(names);
        try {
            theMatrix.setNames(names);
        } catch (WrongSizeException e) {
            System.out.println("new names' getSize : "+names.size());
            System.out.println("am' getSize : "+theMatrix.getSize());

            e.printStackTrace();
        }

        return theMatrix;
    }

    public AdjacencyMatrix generateCompleteGraph() {
        int k;
        ArrayList<Integer> subMatrix;
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(size);
        Random rand = new Random();
        for (int i=1; i<=size; i++){
            subMatrix = new ArrayList<Integer>(size);
            if (this.orientedMx) {
                k = 1;
            } else {
                k = i;
            }
            for (int j=k; j<=size; j++) {
                subMatrix.add(rand.nextInt(10)+1);
            }
            matrix.add(subMatrix);
        }
        theMatrix = new AdjacencyMatrix(matrix,orientedMx);
        return theMatrix;
    }

    /**
     * @param orientedMx change the type of matrix to generate, true if it is oriented
     */
    public void setOrientedMx(boolean orientedMx) {
        this.orientedMx = orientedMx;
    }

    public boolean isOrientedMx() {
        return orientedMx;
    }

    /**
     * @param size set the getSize of the matrix to generate
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the ;qtrix generated, null if the matrix has not been generated
     */
    public AdjacencyMatrix getTheMatrix() {
        return theMatrix;
    }
}
