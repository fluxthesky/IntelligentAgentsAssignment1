import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Main {
	
	
	
	
	static JFrame frame;
 	static JMenu menu;
 	static JMenuBar menuBar;
 	static JPanel map;
 	static ArrayList<Cell> grid = new ArrayList<Cell>();
 	static final int NORTH = 1;
 	static final int LEFT = 2;
 	static final int RIGHT = 3;
 	static double calculatedUtility = 0;
	static ArrayList<Integer> visitedCells = new ArrayList<Integer>();
	static ArrayList<Integer> cellUtility = new ArrayList<Integer>();
	static int nextCell = 0;
	static int iterationsLeft = 20;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		int row = 0;
		int col = 0;
		
		
		//setup frame
		frame = new JFrame();
		frame.setSize(1024, 768);
		frame.setTitle("Hello World");
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		
 		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		
		
		
		
		
		//setup menu
		menu = new JMenu("Settings");
		JMenuItem setupGridSize = new JMenuItem("Set up grid size");
		setupGridSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				editRowCol();
			}
		});
		
		
		menu.add(setupGridSize);
		menuBar = new JMenuBar();
		menuBar.add(menu);
		
		//setup grid
		
		map = new JPanel();
		map.setLayout(new GridLayout(3,4));
		//map.setLayout(new GridLayout(6,6));

		
	/*	for (int i = 0 ;i < 36 ; i++) {
			
			grid.add(new Cell(Cell.white));
			
		}
		
		grid.get(0).setType(Cell.green);
		grid.get(2).setType(Cell.green);
		grid.get(5).setType(Cell.green);
		grid.get(9).setType(Cell.green);
		grid.get(16).setType(Cell.green);
		grid.get(23).setType(Cell.green);
		
		grid.get(1).setType(Cell.wall);
		grid.get(10).setType(Cell.wall);
		grid.get(25).setType(Cell.wall);
		grid.get(26).setType(Cell.wall);
		grid.get(27).setType(Cell.wall);

		
		grid.get(7).setType(Cell.brown);
		grid.get(11).setType(Cell.brown);
		grid.get(14).setType(Cell.brown);
		grid.get(21).setType(Cell.brown);
		grid.get(28).setType(Cell.brown);*/
		
		for (int i = 0 ;i < 12 ; i++) {
			
			final int k = i;
			grid.add(new Cell(Cell.white));			
			grid.get(i).setText(String.valueOf(i));
			grid.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					calculatedUtility =0;
					visitedCells = new ArrayList<Integer>();
					iterationsLeft = 20;
					findUtility(k);
					
					System.out.println("end");
					
				}
			});
			
			
		}
		grid.get(3).setType(Cell.green);
		grid.get(5).setType(Cell.wall);
		grid.get(7).setType(Cell.brown);
		
		

		for (int i = 0 ;i < 12 ; i++) {

			map.add(grid.get(i));

			
 			
		}
		

		
		
		for (int i = 0 ;i < 12 ; i++) {
 			visitedCells = new ArrayList<Integer>();
			calculatedUtility =0;
			iterationsLeft = 20;
 			findUtility(i);
 			
 			
 			if(grid.get(i).getType()!=Cell.wall)
			grid.get(i).setText(String.valueOf(calculatedUtility));else {
				grid.get(i).setText(" ");
			}
 
		}

		
		System.out.println("end");

		
 
		
		
		
		/*for (int i = 0 ;i < 36 ; i++) {
			
			grid.add(new Cell(Cell.white));	
			
		}
		
		grid.get(0).setType(Cell.green);
		grid.get(2).setType(Cell.green);
		grid.get(5).setType(Cell.green);
		grid.get(9).setType(Cell.green);
		grid.get(16).setType(Cell.green);
		grid.get(23).setType(Cell.green);
		
		grid.get(1).setType(Cell.wall);
		grid.get(10).setType(Cell.wall);
		grid.get(25).setType(Cell.wall);
		grid.get(26).setType(Cell.wall);
		grid.get(27).setType(Cell.wall);

		
		grid.get(7).setType(Cell.brown);
		grid.get(11).setType(Cell.brown);
		grid.get(14).setType(Cell.brown);
		grid.get(21).setType(Cell.brown);
		grid.get(28).setType(Cell.brown);
		
		
		
		grid.get(coordinateToLocation(0, 0)).setText(String.valueOf(18.538042));
		grid.get(coordinateToLocation(0, 1)).setText(String.valueOf(16.973925));
		grid.get(coordinateToLocation(0, 2)).setText(String.valueOf(15.645911));
		grid.get(coordinateToLocation(0, 3)).setText(String.valueOf(14.432489));
		grid.get(coordinateToLocation(0, 4)).setText(String.valueOf(13.401969));
		grid.get(coordinateToLocation(0, 5)).setText(String.valueOf(12.329421));
		grid.get(coordinateToLocation(1, 1)).setText(String.valueOf(14.585138));
		grid.get(coordinateToLocation(1, 2)).setText(String.valueOf(14.446991));
		grid.get(coordinateToLocation(1, 3)).setText(String.valueOf(13.511668));
		grid.get(coordinateToLocation(1, 5)).setText(String.valueOf(11.42872));
		grid.get(coordinateToLocation(2, 0)).setText(String.valueOf(16.593647));
		grid.get(coordinateToLocation(2, 1)).setText(String.valueOf(15.394961));
		grid.get(coordinateToLocation(2, 2)).setText(String.valueOf(13.399139));
		grid.get(coordinateToLocation(2, 3)).setText(String.valueOf(12.629236));
		grid.get(coordinateToLocation(2, 5)).setText(String.valueOf(10.583616));
		grid.get(coordinateToLocation(3, 0)).setText(String.valueOf(15.480907));
		grid.get(coordinateToLocation(3, 1)).setText(String.valueOf(15.827977));
		grid.get(coordinateToLocation(3, 2)).setText(String.valueOf(14.659492));
		grid.get(coordinateToLocation(3, 3)).setText(String.valueOf(12.626667));
		grid.get(coordinateToLocation(3, 5)).setText(String.valueOf(11.000803));
		grid.get(coordinateToLocation(4, 0)).setText(String.valueOf(15.37473));

		



		for (int i = 0 ;i < 36 ; i++) {
			
			map.add(grid.get(i));
			
		}
		*/
		 
		
		
		frame.add(menuBar,BorderLayout.PAGE_START);
		frame.add(map, BorderLayout.CENTER);
		
		
		
         
 		
		
	}
	
	
	public static int coordinateToLocation(int col, int row) {
		
		
		
		return row * 6 + col;
		
		
		
		
		
		
		
		
		
	}
	
	
	
	public static void findUtility(int i) {
		
		Cell startCell = grid.get(i);
	 //   System.out.println(String.valueOf(startCell.getType()));

		
		System.out.println("At cell " + i);
		
		double northUtility = -100;
		double leftUtility = -100;
		double rightUtility = -100;
		double southUtility = -100;
		
		double highestUtility = -100;
		double lowerUtility1 = -100;
		double lowerUtility2 = -100;
		
		int direction = -1;
		
		double currentUtility = startCell.getReward();
		if(currentUtility == -100) {
			currentUtility = 0;
		}
		
		System.out.println(String.valueOf(startCell.getReward()));


		
		Cell neighbourCell;
		
		
		
		
		//look north
		if(i -4 >= 0) {
			
			neighbourCell = grid.get(i-4);
			northUtility = neighbourCell.getReward();
		//	System.out.println("North utility is " + northUtility);
			
			
		}
		
		//look left
		if(i - 1 >= 0 && i%4 !=0) {
			
			
			neighbourCell = grid.get(i-1);
			leftUtility = neighbourCell.getReward();
		//	System.out.println("Left utility is " + leftUtility);

			
		}
		
		
		//look right
		if(i + 1 <= 11 && (i+1)%4 !=0) {
			
			
			neighbourCell = grid.get(i+1);
			rightUtility = neighbourCell.getReward();
		//	System.out.println("Right utility is " + rightUtility);

		}
		
		
		
		//get max utility from state
	    //double max =  Math.max(Math.max(northUtility,leftUtility),rightUtility);
		
	    if(northUtility >= leftUtility && northUtility >= rightUtility ) {
	    	
	    	highestUtility = northUtility;
	    	lowerUtility1 = leftUtility;
	    	lowerUtility2 = rightUtility;
			nextCell = i - 4;
			direction = NORTH;
				
	    	
	    	
	    	
	    }
	    
	    else if(leftUtility >= northUtility && leftUtility >= rightUtility ) {
	    	
	    	highestUtility = leftUtility;
	    	lowerUtility1 = northUtility;
	    	lowerUtility2 = rightUtility;
			nextCell = i - 1;
			direction = LEFT;

	    	
	    }
	    
	    else if(rightUtility >= northUtility && rightUtility >= leftUtility ) {
	    	
	    	highestUtility = rightUtility;
	    	lowerUtility1 = northUtility;
	    	lowerUtility2 = leftUtility;
			nextCell = i+1;
			direction = RIGHT;

	    	
	    	
	    }
	    
	    
	    if(direction == NORTH) {
	    	
	    	
	    	if (visitedCells.contains(i-4) == true) {
	    		if(leftUtility > rightUtility) {
	    			nextCell = i-1;
	    		}else {
	    			nextCell = i+1;
	    		}
	    		
	    		
	    	}
	    	
	    }
	    else if (direction == LEFT) {
	    	
	    	
	    	if (visitedCells.contains(i-1) == true) {
	    		if(northUtility > rightUtility) {
	    			nextCell = i-4;
	    		}else {
	    			nextCell = i+1;
	    		}
	    		
	    		
	    	}
	    	
	    }
	    
	    else if (direction == RIGHT) {
	    	
	    	
	    	if (visitedCells.contains(i+1) == true) {
	    		if(northUtility > leftUtility) {
	    			nextCell = i-4;
	    		}else {
	    			nextCell = i-1;
	    		}
	    		
	    		
	    	}
	    	
	    }
	    
	    
	    if(nextCell < 0) {
	    	nextCell ++;
	    	nextCell ++;

	    }
	    if(nextCell > 11) {
	    	nextCell--;
	    	nextCell--;

	    }
	    
	    
	    
	    
	    
	    
	  //  System.out.println(String.valueOf(highestUtility));
	  //  System.out.println(String.valueOf(lowerUtility1));
	  //  System.out.println(String.valueOf(lowerUtility2));
	    
	    
	    if(highestUtility == -100) {
	    	highestUtility = 0;
	    }
	    if(lowerUtility1 == -100) {
	    	lowerUtility1 = 0;
	    }
	    if(lowerUtility2 == -100) {
	    	lowerUtility2 = 0;
	    }
	    
		calculatedUtility = currentUtility + 0.8 * highestUtility + 0.1 * lowerUtility1 + 0.1 * lowerUtility1 + calculatedUtility;
	//	System.out.println("adding to utility");
		   
		    
		    
	    
	    visitedCells.add(nextCell);
	    
	    
	     
	    //System.out.println(String.valueOf(startCell.getType()));
	    
	    if(iterationsLeft > 0) {
	    	
	    	
	    	iterationsLeft = iterationsLeft - 1;
	    	System.out.println("Iterations left are " + iterationsLeft);

	    	findUtility(nextCell);
 	    	
	    }
	    
 
	    
	//    System.out.println("calcultaed utility is " + calculatedUtility);
		
		
		
		
	}
	
	
	
 	
	
	public static int editRowCol () {
		
		
		JFrame frame = new JFrame();
		JPanel pane = new JPanel(new BorderLayout());
		pane.add(new JTextArea("Enter row and col") , BorderLayout.PAGE_START);
		
		JTextField row = new JTextField();
		JTextField col = new JTextField();
		JButton ok = new JButton("Enter");
		JPanel pane2 = new JPanel();
		pane2.setLayout(new BoxLayout(pane2, BoxLayout.PAGE_AXIS));
		pane2.add(row  );
		pane2.add(col);
		pane2.add(ok);
		pane.add(pane2);
		
		frame.add(pane);
		frame.setVisible(true);
		frame.setSize(300, 300);

		return 3;
		
	}
	
	
	

}
