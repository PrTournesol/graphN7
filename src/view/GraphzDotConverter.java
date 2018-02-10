package view;

import controller.MatrixGenerator;
import exceptions.NonOrientedMatrixException;
import model.AdjacencyMatrix;
import model.AlgoVertex;
import static model.GraphColors.*;
import java.io.*;
import java.util.ArrayList;


public class GraphzDotConverter {
    private final static String osName = System.getProperty("os.name").replaceAll("\\s","");
    private String tempDir;
    private String GraphizExecutable;
    private File dot;
    private File png;
    private String dotContent;


    public static void main (String args[]) throws NonOrientedMatrixException {
        GraphzDotConverter obj = new GraphzDotConverter();

        //génération de la matrice
        AdjacencyMatrix matrix;
        MatrixGenerator mg = new MatrixGenerator(4,false);
        matrix = mg.generateCompleteGraph();
        System.out.println("\n"+matrix.toString());

        //test du convertisseur :
        GraphzDotConverter gpzGenerator = new GraphzDotConverter();
        gpzGenerator.matrixToGpzString(matrix);
        System.out.println(gpzGenerator.getDotContent());

        gpzGenerator.generateFile("./graphs/graph.dot");
        gpzGenerator.generateSVG("./graphs/graph.dot");


    }

    public void matrixToGpzString(AdjacencyMatrix matrix){
        int size = matrix.getSize();
        int k,weight;
        boolean insert;
        String cha;
        if (matrix.isOrientedMx()) {
            dotContent="digraph {\n";
        }
        else {
            dotContent= "graph {\n";
        }
        for (int i=0; i<size; i++){
            insert=false;
            if (matrix.isOrientedMx()) {
                k = 0;
                cha=" -> ";
            } else {
                k = i;
                cha=" -- ";
            }
            for (int j=k; j<size; j++) {
                weight=matrix.getVertex(i,j);
                if (weight!=0){
                    insert=true;
                    dotContent+=matrix.getName(i)+cha+matrix.getName(j)+"[label=\""+weight+"\",weight=\""+weight+"\"];\n";
                }
            }
            if (!insert) {
                dotContent += matrix.getName(i) + ";\n";
            }
        }
        dotContent+="}";
    }

    public void ColoredVerticesToGpzString(AdjacencyMatrix matrix, ArrayList<AlgoVertex> bfsStep, boolean dijkstra){
        int size = matrix.getSize();
        int k,weight;
        boolean insert;
        String cha;
        if (matrix.isOrientedMx()) {
            dotContent="digraph {\n";
        }
        else {
            dotContent= "graph {\n";
        }
        for (int i=0; i<size; i++){
            if (bfsStep.get(i).color==BLACK){
                dotContent += matrix.getName(i) + " [label=<"+i+"<BR/>\n<FONT POINT-SIZE=\"12\">d="+bfsStep.get(i).distToSource+"</FONT>>,style=filled,color=BLACK,fontcolor=white];\n";
            }
            else if (bfsStep.get(i).color==GREY){
                dotContent += matrix.getName(i) + " [style=filled,color=grey] ;\n";
            }
            else {
                dotContent += matrix.getName(i) + ";\n";
            }
            if (matrix.isOrientedMx() || dijkstra) {
                k = 0;
                cha=" -> ";
            } else {
                k = i;
                cha=" -- ";
            }
            for (int j=k; j<size; j++) {
                weight=matrix.getVertex(i,j);
                if (weight!=0){
                    dotContent+=matrix.getName(i)+cha+matrix.getName(j)+"[label=\""+weight+"\",weight=\""+weight+"\"];\n";
                }
            }

        }
        dotContent+="}";
    }

    public void prepareEnv() {
        //System.out.println("Nom de l'os : "+osName);
        File fic = new File("./graphs");
        if (!fic.exists()) {
            fic.mkdir();
        }
        fic = new File("./graphviz");
        if (!fic.exists()) {
            try {
                Process p = Runtime.getRuntime().exec("jar xf Graph_TP_option1.jar graphviz");
                java.io.BufferedReader out = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
                String line;
                while((line=out.readLine())!= null){
                    System.out.println(line);
                    System.out.flush();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void generateFile (String name) {
        this.prepareEnv();
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(name), "utf-8"));
            writer.write(dotContent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
        dot = new File(name);
    }

    public void generateSVG (String name) {
        String command;
        if (osName.contains("Windows")) {
            // TODO : remove lib/ for export jar
            command = "lib/graphviz/windows_bin/dot ";
        } else {
            command = "graphviz/linux_bin/dot ";
        }

        command += " -Tsvg -o ";
        command += name.substring(0, name.length() - 3);
        command += "svg ";
        command += name;
        //System.out.println(command);
        try {
            Process p = Runtime.getRuntime().exec(command);
            java.io.BufferedReader out = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
            String line;
            while((line=out.readLine())!= null){
                System.out.println(line);
                System.out.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //TODO remove try catch (pour lancer firefox
        try {
            Process p = Runtime.getRuntime().exec("cmd /c start firefox -new-tab "+ name.substring(0, name.length() - 3)+"svg");
            java.io.BufferedReader out = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
            String line;
            while((line=out.readLine())!= null){
                System.out.println(line);
                System.out.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    public String getDotContent() {
        return dotContent;
    }
}
