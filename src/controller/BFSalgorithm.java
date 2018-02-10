package controller;

import exceptions.VertexInexistentException;
import model.AdjacencyMatrix;
import model.BFSstep;
import model.BFSvertex;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import static model.BFScolors.*;

public class BFSalgorithm {
    private AdjacencyMatrix matrix;
    private ArrayList<ArrayList<BFSvertex>> algorithmSteps;

  public static void main (String[] Args) throws VertexInexistentException{
      //génération de la matrice
      AdjacencyMatrix matrix;
      MatrixGenerator mg = new MatrixGenerator(4,false);
      matrix = mg.generateCompleteGraph();
      System.out.println("\n"+matrix.toString());

      BFSalgorithm bfs = new BFSalgorithm(matrix);
      bfs.runBFS(0);

      GraphzDotConverter gpzGenerator = new GraphzDotConverter();
      int i=1;
      for (ArrayList<BFSvertex> step : bfs.algorithmSteps) {
          gpzGenerator.bfsToGpzString(matrix,step);
          gpzGenerator.generateFile("./graphs/graph"+i+".dot");
          gpzGenerator.generateSVG("./graphs/graph"+i+".dot");
          i+=1;
      }

  }

    public BFSalgorithm(AdjacencyMatrix matrix) {
        this.matrix = matrix;
    }

    public void runBFS(int source) throws VertexInexistentException {
        // par convention -1 = infini

        if (source>matrix.getSize()){
            throw new VertexInexistentException();
        }

        //Variables :
        int size = matrix.getSize();
        int vertex, distance, pred;
        BFSstep oneStep = new BFSstep(size+1);
        LinkedBlockingQueue<Integer> fifo;

        algorithmSteps = new ArrayList<ArrayList<BFSvertex>>(size+1);

        // initialisation du BFS
        for (int i=0; i<size;i++){
            oneStep.add(new BFSvertex(-1, WHITE));
        }
        oneStep.set(source,new BFSvertex(0,GREY));

        //initialisation de la FIFO
        fifo = new LinkedBlockingQueue<>(size);
        fifo.add(source);

        // parcours des sommets suivants
        while (fifo.size()>0) {
            vertex=fifo.remove();
            // distance de la source de vertex
            pred=oneStep.get(vertex).distToSource;

            for (int i =0; i<size;i++) {
                distance = matrix.getData().get(vertex).get(i);
                if (distance!=0 && oneStep.get(i).color==WHITE){
                    // il y a un arc non null entre vertex et i
                    oneStep.set(i,new BFSvertex(distance+pred,GREY));
                    fifo.add(i);
                }
            }
            oneStep.get(vertex).color=BLACK;
            algorithmSteps.add(oneStep.copy());
        }
    }

}
