package controller;

import exceptions.VertexInexistentException;
import model.AdjacencyMatrix;
import model.AlgoStep;
import model.AlgoVertex;
import view.GraphzDotConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

import static model.GraphColors.BLACK;
import static model.GraphColors.GREY;
import static model.GraphColors.WHITE;

public class DisjkstraAlgorithm {
    private AdjacencyMatrix matrix;
    private ArrayList<ArrayList<AlgoVertex>> algorithmSteps;
    private int size;
    private AdjacencyMatrix predecessors;


    public static void main (String[] Args) throws VertexInexistentException {
        //génération de la matrice
        AdjacencyMatrix matrix;
        MatrixGenerator mg = new MatrixGenerator(2,false);
        matrix = mg.generateCompleteGraph();
        System.out.println("\n"+matrix.toString());

        DisjkstraAlgorithm djk = new DisjkstraAlgorithm(matrix);
        djk.runDijkstra(0);

        GraphzDotConverter gpzGenerator = new GraphzDotConverter();
        int i=1;
        for (ArrayList<AlgoVertex> step : djk.algorithmSteps) {
            gpzGenerator.ColoredVerticesToGpzString(matrix,step,true);
            gpzGenerator.generateFile("./graphs/graph"+i+".dot");
            gpzGenerator.generateSVG("./graphs/graph"+i+".dot");
            i+=1;
        }
        gpzGenerator.ColoredVerticesToGpzString(djk.predecessors,djk.algorithmSteps.get(djk.algorithmSteps.size()-1),true);
        gpzGenerator.generateFile("./graphs/graphTree.dot");
        gpzGenerator.generateSVG("./graphs/graphTree.dot");


    }

    public DisjkstraAlgorithm(AdjacencyMatrix matrix) {
        this.matrix = matrix;
        this.size = matrix.getSize();
        this.predecessors = new AdjacencyMatrix(size,matrix.isOrientedMx());
    }

    public void runDijkstra(int source) throws VertexInexistentException {
        // par convention Integer.MAX_VALUE = infini
        if (source>matrix.getSize()){
            throw new VertexInexistentException();
        }

//Variables :
        boolean relaxTest;
        int distance, pred,minVertex, currentVertex;
        AlgoStep oneStep = new AlgoStep(size+1);
        HashSet<Integer> treated;
        HashSet<Integer> remained;

        algorithmSteps = new ArrayList<ArrayList<AlgoVertex>>(size+1);

        //initialisation de l'ensemble Q
        treated = new HashSet<>(size);
        remained = new HashSet<>(size);
        minVertex = source;

        // initialisation avec des degrés infi pour tous les sommets
        for (int i=0; i<size;i++){
            oneStep.add(new AlgoVertex(Integer.MAX_VALUE, WHITE));
            remained.add(i);

        }
        // changer la valeur de la source avec distance 0
        oneStep.set(source,new AlgoVertex(0,GREY));

        // parcours des sommets suivants
        while (!remained.isEmpty()) {
            minVertex=oneStep.getMinDistToSource(remained);
            remained.remove(minVertex);
            treated.add(minVertex);

            // distance de la source de vertex
            pred=oneStep.get(minVertex).distToSource;

            //relaxer tous les sommets adjacents :
            for (int i =0; i<size;i++) {
                distance = matrix.getVertex(minVertex,i);
                relaxTest=oneStep.get(i).distToSource > (pred+distance);
                if (relaxTest){
                    oneStep.get(i).distToSource=pred+distance;
                    //System.out.println("relax "+i+" pred"+minVertex);
                    //System.out.println(predecessors.toString());
                    // supprimer le précédent prédecesseur
                    for (int j=0;j<size; j++){
                        predecessors.set(j,i,0);
                    }
                    predecessors.set(minVertex,i,distance);
                    //System.out.println(predecessors.toString());

                }
            }
            oneStep.get(minVertex).color=BLACK;
            algorithmSteps.add(oneStep.copy());
        }
        //System.out.println("END : \n");
        //System.out.println(predecessors.toString());




    }

}
