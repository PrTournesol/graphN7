package controller;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.NonOrientedMatrixException;
import exceptions.VertexInexistentException;
import model.AdjacencyMatrix;
import model.AlgoVertex;
import view.GraphzDotConverter;

public class MainController {
    private Scanner input = new Scanner(System.in);
	private static MainController mn;
	private static MatrixGenerator mg;
	private static AdjacencyMatrix matrix;
	private static GraphzDotConverter gpzGenerator;
	private static DisjkstraAlgorithm djk;
	private static BFSalgorithm bfs;
	private static int i;

    
	public static void main(String[] args) throws NonOrientedMatrixException, VertexInexistentException {
		mn = new MainController();
		int GeneratorChoice, GraphChoice, GraphSize, run, source;
		boolean stop, GraphType;
		gpzGenerator = new GraphzDotConverter();
		gpzGenerator.prepareEnv();
		
		GeneratorChoice = mn.menuGeneration();
		if (GeneratorChoice==0) {
			stop=true;
		}
		else {
			GraphChoice =mn.menuSelectionGraph();
			GraphType = true;
			if (GeneratorChoice==0) {
				stop=true;
			}
			else if (GraphChoice==2) {
				GraphType = false;
			}
			
			GraphSize =mn.getNumber();
			
			mg = new MatrixGenerator(GraphSize,GraphType);
	        switch (GeneratorChoice) {
	        case 1:
		        matrix = mg.generateCycleGraphOriented();
				break;
			case 2:
		        matrix = mg.generateCompleteGraph();
				break;
			case 3:
		        matrix = mg.generateRandGraph(true);
				break;
			case 4:
		        matrix = mg.generateRandGraph(false);
				break;
			default:
		        matrix = mg.generateRandGraph(false);
				break;
			}			        
	        matrix = mg.generateCompleteGraph();
	        System.out.println("\n"+matrix.toString());	
	        
	        generateGpz("graph");
	        run= mn.menuRun();
	        switch (run) {
			case 1:
				System.out.println("Enter the source vertex for Dijkstra");
				source = mn.getVertex(matrix.getSize());
				djk = new DisjkstraAlgorithm(matrix);
		        djk.runDijkstra(source);
		        
		        i=1;
		        for (ArrayList<AlgoVertex> step : djk.algorithmSteps) {
		            gpzGenerator.ColoredVerticesToGpzString(matrix,step,true);
		            gpzGenerator.generateFile("./graphs/graph"+i+".dot");
		            gpzGenerator.dotConvertTo("./graphs/graph"+i+".dot","svg");
		            gpzGenerator.dotConvertTo("./graphs/graph"+i+".dot","png");
		            i+=1;
		        }
		        System.out.println(djk.predecessors.toString());
		        gpzGenerator.ColoredVerticesToGpzString(djk.predecessors,djk.algorithmSteps.get(djk.algorithmSteps.size()-1),true);
		        gpzGenerator.generateFile("./graphs/graphTree.dot");
		        gpzGenerator.dotConvertTo("./graphs/graphTree.dot","svg");
		        gpzGenerator.dotConvertTo("./graphs/graphTree.dot","png");
				break;
			case 2:
				System.out.println("Enter the source vertex for BFS");
				source = mn.getVertex(matrix.getSize());
				
				bfs = new BFSalgorithm(matrix);
				bfs.runBFS(0);
				
				GraphzDotConverter gpzGenerator = new GraphzDotConverter();
				i = 1;
				for (ArrayList<AlgoVertex> step : bfs.algorithmSteps) {
				      gpzGenerator.ColoredVerticesToGpzString(matrix,step,false);
				      gpzGenerator.generateFile("./graphs/graph"+i+".dot");
				      gpzGenerator.dotConvertTo("./graphs/graph"+i+".dot","svg");
				      gpzGenerator.dotConvertTo("./graphs/graph"+i+".dot","png");
				      i+=1;
				}
				gpzGenerator.ColoredVerticesToGpzString(bfs.tree,bfs.algorithmSteps.get(bfs.algorithmSteps.size()-1),false);
				gpzGenerator.generateFile("./graphs/graphTree.dot");
				gpzGenerator.dotConvertTo("./graphs/graphTree.dot","svg");
				gpzGenerator.dotConvertTo("./graphs/graphTree.dot","png");


			default:
				break;
			}
				
			
		}
	}


	private static void generateGpz(String string) {

		gpzGenerator.matrixToGpzString(matrix);
		gpzGenerator.generateFile("./graphs/"+string+".dot");
		gpzGenerator.dotConvertTo("./graphs/"+string+".dot","svg");
		gpzGenerator.dotConvertTo("./graphs/"+string+".dot","png");
	}
	
	
	public int menuGeneration() {
        int selection;
        do {
        	System.out.println("Choose from these choices");
            System.out.println("-------------------------\n");
            System.out.println("1 - Generate a cycle Graph");
            System.out.println("2 - Generate a completed Graph");
            System.out.println("3 - Generate a Random graph");
            System.out.println("4 - Generate a simple Random graph (with weight 1 max)");
            System.out.println("0 - Quit");

            selection = input.nextInt();
		} while (selection<0 || selection >4);
        return selection;    
    }
	
	public int menuSelectionGraph() {
        int selection;
 
        do {        
        	System.out.println("Choose from these choices");
	        System.out.println("-------------------------\n");
	        System.out.println("1 - Oriented");
	        System.out.println("2 - Non Oriented");
	        System.out.println("0 - Quit");
	        selection = input.nextInt();
		} while (selection<0 || selection >2);
        return selection;    
    }
	
	public int menuRun() throws VertexInexistentException {
        int selection;
        do {
        	System.out.println("Choose from these choices");
	        System.out.println("-------------------------\n");
	        System.out.println("1 - Dijkstra");
	        System.out.println("2 - BFS");
	        System.out.println("3 - delete a veertex");
	        System.out.println("0 - Quit");
	        selection = input.nextInt();
	        if (selection==3) {
	        	int j=matrix.getSize()-1;
				System.out.println("Enter the vertex to delete from 0 to "+ j);
	        	matrix.delVertex(this.getVertex(matrix.getSize()));
		
	        	System.out.println(matrix.toString());
	        	generateGpz("graph");
	        }
	        
        } while (selection<0 || selection >2);

        return selection;    
    }
	
	public int getNumber() {
        int selection;
        do {
			System.out.println("Enter a number of vertices >2 and <16");
	        selection = input.nextInt();

		} while (selection<3 || selection >15);

        return selection;    
    }
	
	public int getVertex(int i) {
        int selection;
    	int j=i-1;
        do {
	        selection = input.nextInt();
		} while (selection<0 || selection >j);

        return selection;    
    }
	
}
