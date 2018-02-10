package controller;

import exceptions.VertexInexistentException;
import model.AdjacencyMatrix;
import model.AlgoStep;
import model.AlgoVertex;
import view.GraphzDotConverter;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import static model.GraphColors.*;

public class BFSalgorithm {
    private AdjacencyMatrix matrix;
    private ArrayList<ArrayList<AlgoVertex>> algorithmSteps;
    private AdjacencyMatrix tree;
    private int size;

  public static void main (String[] Args) throws VertexInexistentException{
      //génération de la matrice
      AdjacencyMatrix matrix;
      MatrixGenerator mg = new MatrixGenerator(10,false);
      matrix = mg.generateCompleteGraph();
      System.out.println("\n"+matrix.toString());

      BFSalgorithm bfs = new BFSalgorithm(matrix);
      bfs.runBFS(0);

      GraphzDotConverter gpzGenerator = new GraphzDotConverter();
      int i=1;
      for (ArrayList<AlgoVertex> step : bfs.algorithmSteps) {
          gpzGenerator.ColoredVerticesToGpzString(matrix,step,false);
          gpzGenerator.generateFile("./graphs/graph"+i+".dot");
          gpzGenerator.generateSVG("./graphs/graph"+i+".dot");
          i+=1;
      }
      System.out.println("tree :");
      gpzGenerator.ColoredVerticesToGpzString(bfs.tree,bfs.algorithmSteps.get(bfs.algorithmSteps.size()-1),false);
      gpzGenerator.generateFile("./graphs/graphTree.dot");
      gpzGenerator.generateSVG("./graphs/graphTree.dot");
      System.out.println("fin :");


  }

    public BFSalgorithm(AdjacencyMatrix matrix) {
        this.matrix = matrix;
        this.size = matrix.getSize();
        this.tree = new AdjacencyMatrix(size,matrix.isOrientedMx());
    }

    public void runBFS(int source) throws VertexInexistentException {
        // par convention -1 = infini

        if (source>matrix.getSize()){
            throw new VertexInexistentException();
        }

        //Variables :
        int vertex, distance, pred;
        AlgoStep oneStep = new AlgoStep(size+1);
        LinkedBlockingQueue<Integer> fifo;

        algorithmSteps = new ArrayList<ArrayList<AlgoVertex>>(size+1);

        // initialisation du BFS
        for (int i=0; i<size;i++){
            oneStep.add(new AlgoVertex(Integer.MAX_VALUE, WHITE));
        }
        oneStep.set(source,new AlgoVertex(0,GREY));

        //initialisation de la FIFO
        fifo = new LinkedBlockingQueue<>(size);
        fifo.add(source);

        // parcours des sommets suivants
        while (fifo.size()>0) {
            vertex=fifo.remove();
            // distance de la source de vertex
            pred=oneStep.get(vertex).distToSource;

            for (int i =0; i<size;i++) {
                distance = matrix.getVertex(vertex,i);
                if (distance!=0 && oneStep.get(i).color==WHITE){
                    // il y a un arc non null entre vertex et i
                    oneStep.set(i,new AlgoVertex(distance+pred,GREY));
                    tree.set(vertex,i,distance);
                    fifo.add(i);
                }
            }
            oneStep.get(vertex).color=BLACK;
            algorithmSteps.add(oneStep.copy());
        }
    }

}
