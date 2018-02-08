package controller;

import model.AdjacencyMatrix;

import java.io.*;


public class graphzDotGenerator {
    private final static String osName = System.getProperty("os.name").replaceAll("\\s","");
    private String tempDir;
    private String GraphizExecutable;


    public static void main (String args[]) throws Exception{
        graphzDotGenerator obj = new graphzDotGenerator();

        AdjacencyMatrix matrix;
        String gpz;
        matrixGenerator mg = new matrixGenerator(5,false);
        matrix = mg.generateRandGraph();
        System.out.println("\n"+matrix.toString());
        gpz = MatrixToGpzString(matrix);
        System.out.println(gpz);


    }

    public static String MatrixToGpzString (AdjacencyMatrix matrix){
        //TODO
        String stg;
        int size = matrix.size();
        int k,weight;
        boolean insert;
        String cha;
        if (matrix.isOrientedMx()) {
            stg="digraph {\n";
        }
        else {
            stg= "graph {\n";
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
                weight=matrix.getWeight(i,j);
                if (weight!=0){
                    insert=true;
                    stg+=matrix.getName(i)+cha+matrix.getName(j)+"[label=\""+weight+"\",weight=\""+weight+"\"];\n";
                }
            }
            if (!insert) {
                stg += matrix.getName(i) + ";\n";
            }
        }
        stg+="}";
        return stg;
    }

    /*public static String NonOrientedToGpzString (AdjacencyMatrix matrix){
        //TODO
        String stg = new String("");
        int size = matrix.size();
        int weight;
        boolean insert;
        stg+="graph {\n";
        for (int i=0; i<size; i++){
            insert=false;
            for (int j=i; j<size; j++) {
                weight=matrix.getWeight(i,j);
                if (weight!=0){
                    insert=true;
                }
            }
            if (!insert) {
                stg += matrix.getName(i) + ";\n";
            }
        }
        stg+="}";
        return stg;
    }*/

    public static File genrateFile (String content){
        File fic = new File("graph.dot");
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("graph.dot"), "utf-8"));
            writer.write(content);
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
        return fic;
    }

    public File genratePng (File fic) {
        if (osName.contains("Windows")) {
            tempDir = "c:/temp";
            GraphizExecutable = "c:/Program Files (x86)/Graphviz 2.28/bin/dot.exe";
        } else {
            tempDir = "/tmp";
            GraphizExecutable = "/usr/bin/dot";
        }
        fic.getAbsolutePath();
        return fic;
    }

}
