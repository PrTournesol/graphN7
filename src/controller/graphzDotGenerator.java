package controller;

import model.AdjacencyMatrix;

import java.io.*;


public class graphzDotGenerator {
    private final static String osName = System.getProperty("os.name").replaceAll("\\s","");
    private String tempDir;
    private String GraphizExecutable;


    /*public static void main (String args[]){
        graphzDotGenerator obj = new graphzDotGenerator();

        AdjacencyMatrix matrix;
        String gpz;
        matrix = matrixGenerator.GenerateGraphNonOriented(5);
        System.out.println("\n"+matrix.toString());
        gpz = NonOrientedToGpzString(matrix);
        System.out.println(gpz);
        File fic = genrateFile(gpz);
        obj.genratePng(fic);

        GraphViz gz = new GraphViz();
        gz.get_img_stream(fic,"PNG","dot");

    }*/

    public static void main (String args[]){
        graphzDotGenerator obj = new graphzDotGenerator();
        AdjacencyMatrix matrix;
        String gpz;
        matrix = matrixGenerator.GenerateCompleteGraphOriented(5);
        System.out.println("\n"+matrix.toString());
        gpz = OrientedToGpzString(matrix);
        System.out.println(gpz);

    }

    public static String OrientedToGpzString (AdjacencyMatrix matrix){
        //TODO
        String stg = new String("");
        int size = matrix.size();
        int weight;
        stg+="digraph {\n";
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++) {
                weight=matrix.getWeight(i,j);
                if (weight!=0){
                    stg+=matrix.getName(i)+" -> "+matrix.getName(j)+"[label=\""+weight+"\",weight=\""+weight+"\"];\n";
                }
            }
        }
        stg+="}";
        return stg;
    }

    public static String NonOrientedToGpzString (AdjacencyMatrix matrix){
        //TODO
        String stg = new String("");
        int size = matrix.size();
        int weight;
        stg+="graph {\n";
        for (int i=0; i<size; i++){
            for (int j=i; j<size; j++) {
                weight=matrix.getWeight(i,j);
                if (weight!=0){
                    stg+=matrix.getName(i)+" -- "+matrix.getName(j)+"[label=\""+weight+"\",weight=\""+weight+"\"];\n";
                }
            }
        }
        stg+="}";
        return stg;
    }

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
