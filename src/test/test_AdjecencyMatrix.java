package test;

import exceptions.VertexInexistentException;
import exceptions.WrongSizeException;
import model.AdjacencyMatrix;

import java.util.ArrayList;

public class test_AdjecencyMatrix {

    private AdjacencyMatrix matriceTest;

    public static void main (String[] args) throws WrongSizeException, VertexInexistentException {

        AdjacencyMatrix matriceTest;
        matriceTest = new AdjacencyMatrix();
        System.out.println("getSize of matriceTest : " +  matriceTest.getSize());

        ArrayList<Integer> edge1 = new ArrayList<Integer>();
        edge1.add(65);
        matriceTest.addVertexNonOriented(edge1,0);
        System.out.println("After add 1 edge of weight 65");
        System.out.println(matriceTest.toString());

        ArrayList<Integer> edge2 = new ArrayList<Integer>();
        edge2.add(43);
        edge2.add(56);
        matriceTest.addVertexNonOriented(edge2,1);
        System.out.println(matriceTest.toString());

        ArrayList<Integer> edge3 = new ArrayList<Integer>();
        edge3.add(1);
        edge3.add(2);
        edge3.add(3);
        System.out.println("vertex3.getVertex(2)" + edge3.get(2));
        matriceTest.addVertexNonOriented(edge3,1);
        System.out.println(matriceTest.toString());

        matriceTest.delVertex(1);
        System.out.println("After remove the 2nd edge :");
        System.out.println(matriceTest.toString());

        matriceTest.delVertex(0);
        System.out.println("After remove the 1st edge :");
        System.out.println(matriceTest.toString());

    }

}
