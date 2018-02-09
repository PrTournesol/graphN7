package controller;

import model.AdjacencyMatrix;

import java.io.*;


public class graphzDotConverter {
    private final static String osName = System.getProperty("os.name").replaceAll("\\s","");
    private String tempDir;
    private String GraphizExecutable;
    private File dot;
    private File png;
    private String dotContent;


    public static void main (String args[]) {
        graphzDotConverter obj = new graphzDotConverter();

        //génération de la matrice
        AdjacencyMatrix matrix;
        matrixGenerator mg = new matrixGenerator(15,false);
        matrix = mg.generateRandGraph();
        System.out.println("\n"+matrix.toString());

        //test du convertisseur :
        graphzDotConverter gpzGenerator = new graphzDotConverter();
        gpzGenerator.MatrixToGpzString(matrix);
        System.out.println(gpzGenerator.getDotContent());

        gpzGenerator.generateFile("./graphs/graph.dot");
        gpzGenerator.generateSVG("./graphs/graph.dot");


    }

    public void MatrixToGpzString (AdjacencyMatrix matrix){
        int size = matrix.size();
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
                weight=matrix.getWeight(i,j);
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

    public void prepareEnv() {
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
        if (osName.contains("Windows")) {
            String command = "graphviz/windows_bin/dot ";
        } else {
            String command = "graphviz/linux_bin/dot ";
        }


        String command = "graphviz/windows_bin/dot ";
        command += " -Tsvg -o ";
        command += name.substring(0, name.length() - 3);
        command += "svg ";
        command += name;
        System.out.println(command);
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
    }


    public String getDotContent() {
        return dotContent;
    }
}
