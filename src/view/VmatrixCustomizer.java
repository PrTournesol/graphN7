package view;

import javax.swing.*;

import controller.MatrixGenerator;
import javafx.scene.image.Image;
import model.AdjacencyMatrix;

import java.awt.*;
import java.util.ArrayList;

public class VmatrixCustomizer extends JFrame {
	private JTextField textField_7;
	private JTable table;
	private AdjacencyMatrix matrix;
	private JPanel matrixContainer;
	private int size;
	private ArrayList<JButton> plus;
	private ArrayList<ArrayList<JTextField>> data;
	private ArrayList<JLabel> names;

	private JPanel panel_data;
	private JPanel panel_names;
	private JPanel panel_buttons;
	private JPanel panel_image;

	
	
    public static void main (String[] args){
        AdjacencyMatrix matrix;
        MatrixGenerator mg = new MatrixGenerator(5);
        matrix = mg.generateRandGraph(true);
        
        VmatrixCustomizer vm = new VmatrixCustomizer(matrix);

    }

    public VmatrixCustomizer(AdjacencyMatrix matrix) {
    	setResizable(false);
        this.setTitle("Matrix generator");
        this.matrix=matrix;
        this.size=matrix.getSize();
        getContentPane().setLayout(new BorderLayout());

        panel_data = new JPanel(new GridLayout(size, size));
        //panel = new JPanel(new GridLayout(1, 1));
        
		panel_names = new JPanel(new GridLayout(1, size));
		//panel_names = new JPanel(new GridLayout(1, 1));

		
        // initialisation des JTextField
        data = new ArrayList<>(size);
        names = new ArrayList<>(size);
        for (int i=0; i<size;i++) {
			data.add(new ArrayList<>(size));
			names.add(new JLabel(matrix.getName(i),JLabel.CENTER));
			panel_names.add(names.get(i));
			for(int j=0; j<size;j++) {
				data.get(i).add(new JTextField(""+matrix.getVertex(i, j)));
		        panel_data.add(data.get(i).get(j));
			}
		}
        
        getContentPane().add(panel_names,BorderLayout.NORTH);
        getContentPane().add(panel_data,BorderLayout.CENTER);

        
        panel_buttons = new JPanel();
        
        plus = new ArrayList<>(size);       
        for(int j=0; j<size;j++) {
        	plus.add(new JButton("+"));
            panel_buttons.add(plus.get(j));//System.out.println(data[i][j]);
		}
        getContentPane().add(panel_buttons, BorderLayout.SOUTH);

        panel_image = new JPanel();
        panel_image.add(new Image("graphs/graph.svg"));
        getContentPane().add(panel_image, BorderLayout.WEST);

        
        
        pack();
        this.setVisible(true);
        
    }
}
