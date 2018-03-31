import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import org.jfree.ui.RefineryUtilities;

public class ValueIteration extends JFrame {
	
 	 JMenu menu;
  	 boolean changeGrid = false;
 	 
 	 

 	 JMenuBar menuBar;
 	 JPanel map;
 	 ArrayList<Cell> grid = new ArrayList<Cell>();
 	 final int NORTH = 1;
 	 final int LEFT = 2;
 	 final int RIGHT = 3;
 	 final int SOUTH = 4;
 	 double absoluteDiff;
 	 double tempAbsoluteDiff;
 	
 	 int counter = 0;
 	
 	
 	 double calculatedUtility = 0;
	 ArrayList<Integer> visitedCells = new ArrayList<Integer>();
	 ArrayList<Double> cellUtility = new ArrayList<Double>();
	 ArrayList<Double> oldCellUtility = new ArrayList<Double>();
	 
	 ArrayList<CellUtilityHistory> cellUtilityHistory = new ArrayList<CellUtilityHistory>();

	 int nextCell = 0;
	 int iterationsLeft = 20;
	 double discount = 0.99;
	
	 int directionTaken = 0;
	
	// double episilon = 80;
	
	 double episilon = 50;
	
	 int row = 6;
	 int col = 6;
	
	
	
	 double convergence = episilon*(1.0 - discount)/discount;
 	
	
	 public ValueIteration() { }
	 
	 
	 
	 
	 public void setup() {

			// TODO Auto-generated constructor stub
		 
		 
		 
			 
				this.setSize(1024, 768);
				this.setTitle("Value Iteration"
						+ "");
				this.setVisible(true);
				this.setLayout(new BorderLayout());
				this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		
				
				menu = new JMenu("Settings");
				 
				
				
				
				
				JMenuItem setupGridSize = new JMenuItem("New Grid");
				setupGridSize.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						editRowCol();
					}
				});
				
				JMenuItem toggleChangeGrid = new JMenuItem("Change grid type");
				toggleChangeGrid.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						
						if(changeGrid == true) {
							
							System.out.println("TRUE");
						changeGrid = false;
						}
						else {
							
							changeGrid = true;
							
						}
						
					}
				});
				
				
				
				
			 
				menu.add(setupGridSize);
				menu.add(toggleChangeGrid);

				
				
				menuBar = new JMenuBar();
				menuBar.add(menu);
 				
				//setup grid
				
				map = new JPanel();
				map.setLayout(new GridLayout(6,6));

				
				
				for (int i = 0 ;i < 36 ; i++) {
					
					int k = i;
					
					grid.add(new Cell(Cell.white));	
					cellUtilityHistory.add(new CellUtilityHistory());
					
				 
					
					
					grid.get(i).addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							
							if(changeGrid == false) {
							plotGraph(k);
							}
						}
					});
					
					
					
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
				
				
				for (int i = 0 ;i < 36 ; i++) {
					
					map.add(grid.get(i));
					
				}
			 
			 
			 

				this.add(menuBar,BorderLayout.PAGE_START);
				this.add(map, BorderLayout.CENTER);
			 
			 
			 
			 
			 
			 
			 
		
	 }
	 
	 
	 public void setRow(int row) {
		this.row = row;
	}
	 
	 public void setCol(int col) {
		this.col = col;
	}
	 
	 
	public void plotGraph(int i) {
		
		
		final Graph demo = new Graph("Plot" , "Plot For Value Iteration");
		demo.setup(cellUtilityHistory.get(i).getCellUtilityHistory());
	    demo.pack();
	    
	    RefineryUtilities.centerFrameOnScreen(demo);
	    demo.setVisible(true);
		
		
		
		
		
		
		
	}
	 
	 
	 
	 
	 public void startValueIteration() {

			
			do {
			absoluteDiff = 0;
		
			
			
			
			oldCellUtility = cellUtility;
			cellUtility = new ArrayList<>();
			
		for (int i = 0 ;i < 36 ; i++) {
			
			
			double newUtility;
			double oldUtility = 0;
			
			visitedCells = new ArrayList<Integer>();
			calculatedUtility =0;
			iterationsLeft = 20;

			
			
			newUtility = findUtility(i);
			cellUtilityHistory.get(i).addCellUtilityHistory(newUtility);
			
			
			
			
			if(!oldCellUtility.isEmpty())
			oldUtility = oldCellUtility.get(i);
			
			
			tempAbsoluteDiff = Math.abs(newUtility - oldUtility);
			
			
			if(tempAbsoluteDiff >= absoluteDiff) {
				
				
				absoluteDiff = tempAbsoluteDiff;
				
			}
			
			
			
			System.out.println("absolute" + absoluteDiff);

			
			
			
			cellUtility.add(newUtility);
			
			
			
			
			if(grid.get(i).getType()!=Cell.wall) {
				
			
			switch (directionTaken) {
			
			
			case (NORTH):
				grid.get(i).setText(String.valueOf(cellUtility.get(i)) + " \n " + " ^ ");
				break;
			
			case (LEFT):
				grid.get(i).setText(String.valueOf(cellUtility.get(i)) + " \n " + " < ");
				break;
			case (RIGHT):
				grid.get(i).setText(String.valueOf(cellUtility.get(i)) + " \n " + " > ");
				break;
			
			case (SOUTH):
				grid.get(i).setText(String.valueOf(cellUtility.get(i)) + " \n " + " dwn ");
				break;
			
			
			
			}
		}
			
			

		}
		
	
		
		for (int i = 0 ;i < 36 ; i++) {
				
			grid.get(i).setUtility(cellUtility.get(i));

		}
		
		
		
	//	System.out.println("absolute" + absoluteDiff);
		//System.out.println("abssssssssssssssssolute" + convergence);
		counter++;
		
		
			}
	while(absoluteDiff >= convergence);
 

		System.out.println("took " + counter + " iterations");
			
			
	 }
	 
	 
	 
	 
	 
	 
	 
	 private double findUtility(int i) {
			
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
			
			double currentUtility = startCell.getUtility();
			double currentReward = startCell.getReward();

			
			
			
			//System.out.println(String.valueOf(startCell.getReward()));


			
			Cell neighbourCell;
			
			
			
			
			//look north
			if(i -6 >= 0) {
				
				neighbourCell = grid.get(i-6);
				
				if(neighbourCell.getType() != Cell.wall) {
					
				northUtility = neighbourCell.getUtility();
			//	System.out.println("North utility is " + northUtility);
			
				}
				
				
			}
			
			//look left
			if(i - 1 >= 0 && i%6 !=0) {
				
				
				neighbourCell = grid.get(i-1);
				
				if(neighbourCell.getType() != Cell.wall) {
					
					
				leftUtility = neighbourCell.getUtility();
			//	System.out.println("Left utility is " + leftUtility);
			
			
			
				}
				
			}
			
			
			//look right
			if(i + 1 <= 35 && (i+1)%6 !=0) {
				
				
				neighbourCell = grid.get(i+1);
				
				if(neighbourCell.getType() != Cell.wall) {
					
					
				rightUtility = neighbourCell.getUtility();
			//	System.out.println("Right utility is " + rightUtility);
				
				
				}
			}
			
			//look south
			if(i +6 <= 35) {
				
				neighbourCell = grid.get(i+6);
				
				if(neighbourCell.getType() != Cell.wall) {
					
					
				southUtility = neighbourCell.getUtility();
			//    System.out.println("South utility is " + southUtility);
			    
			    
			    
				}
				
			}
			
			if(northUtility == -100) {
				northUtility = currentUtility;
			}
			if(leftUtility == -100) {
				leftUtility = currentUtility;
				
			}
			if(rightUtility == -100) {
				rightUtility = currentUtility;
			}
			if(southUtility == -100) {
				southUtility = currentUtility;
			}
			
			
			
			
			
			
			
			//get max utility from state
		    //double max =  Math.max(Math.max(northUtility,leftUtility),rightUtility);
			
		  /*  if(northUtility >= leftUtility && northUtility >= rightUtility && northUtility >= southUtility ) {
		    	
		    	highestUtility = northUtility;
		    	lowerUtility1 = leftUtility;
		    	lowerUtility2 = rightUtility;
				nextCell = i - 6;
				direction = NORTH;

		    	
		    }
		    
		    else if(leftUtility >= northUtility && leftUtility >= rightUtility && leftUtility >= southUtility ) {
		    	
		    	highestUtility = leftUtility;
		    	lowerUtility1 = northUtility;
		    	lowerUtility2 = southUtility;
				nextCell = i - 1;
				direction = LEFT;

		    	
		    }
		    
		    else if(rightUtility >= northUtility && rightUtility >= leftUtility && rightUtility >= southUtility  ) {
		    	
		    	highestUtility = rightUtility;
		    	lowerUtility1 = northUtility;
		    	lowerUtility2 = southUtility;
				nextCell = i+1;
				direction = RIGHT;

		    	
		    	
		    }
		    
		    else if(southUtility >= northUtility && southUtility >= leftUtility && southUtility >= rightUtility  ) {
		    	
		    	highestUtility = southUtility;
		    	lowerUtility1 = rightUtility;
		    	lowerUtility2 = leftUtility;
				nextCell = i+6;
				direction = SOUTH;

		    	
		    	
		    }*/

		    
		    System.out.println("nextCell is " + nextCell);
		    
		    
		    
		    
		   double calculatedUtilityUp = currentReward + discount * (0.8 * northUtility + 0.1 * leftUtility + 0.1 * rightUtility) ;
		   double calculatedUtilityleft = currentReward + discount * (0.8 * leftUtility + 0.1 * northUtility + 0.1 * southUtility) ;
		   double calculatedUtilityright = currentReward + discount * (0.8 * rightUtility + 0.1 * northUtility + 0.1 * southUtility) ;
		   double calculatedUtilitydown = currentReward + discount * (0.8 * southUtility + 0.1 * leftUtility + 0.1 * rightUtility) ;

		//   System.out.println(calculatedUtilityUp); 
		//   System.out.println(calculatedUtilityleft); 
		//   System.out.println(calculatedUtilityright); 
		//   System.out.println(calculatedUtilitydown); 

		   
		   double calculatedUtility = Math.max(Math.max(calculatedUtilityUp,calculatedUtilityleft),Math.max(calculatedUtilityright,calculatedUtilitydown));
		   
		   
		   if(calculatedUtility == calculatedUtilityUp) {
			   directionTaken = NORTH;
		   }
		   else if(calculatedUtility == calculatedUtilityleft) {
			   directionTaken = LEFT;
		   }
		   else if(calculatedUtility == calculatedUtilityright) {
			   directionTaken = RIGHT;
		   }
		   else if(calculatedUtility == calculatedUtilitydown) {
			   directionTaken = SOUTH;
		   }
		   
		   
		   
		   
		   
		   /*
		   
		   if(calculatedUtilityUp >= calculatedUtilityleft && calculatedUtilityUp >= calculatedUtilityright && calculatedUtilityUp >= calculatedUtilitydown) {
			   
			   
			   System.out.println("return up");
			   directionTaken = NORTH;
			   return calculatedUtilityUp;
			   
		   }
		    if(calculatedUtilityleft >= calculatedUtilityUp && calculatedUtilityleft >= calculatedUtilityright && calculatedUtilityUp >= calculatedUtilitydown) {
			   
			  System.out.println("return left");

			   
			   directionTaken = LEFT;

			   return calculatedUtilityleft;
			   
		   }
		    if(calculatedUtilityright >= calculatedUtilityleft && calculatedUtilityright >= calculatedUtilityUp && calculatedUtilityright >= calculatedUtilitydown) {
			  System.out.println("return right");

			   
			   directionTaken = RIGHT;

			   return calculatedUtilityright;
			   
		   }
		    if(calculatedUtilitydown >= calculatedUtilityleft && calculatedUtilitydown >= calculatedUtilityright && calculatedUtilitydown >= calculatedUtilityUp) {
			   
			   System.out.println("return dw");

			   
			   directionTaken = SOUTH;

			   return calculatedUtilitydown;		    

			   
		   }*/
		   
		   
		   
		   
		    
		    return calculatedUtility;
		   
		   
		   
		   
		   /*
		    
		    
		    if(highestUtility == -100) {
		    	highestUtility = 0;
		    }
		    if(lowerUtility1 == -100) {
		    	lowerUtility1 = 0;
		    }
		    if(lowerUtility2 == -100) {
		    	lowerUtility2 = 0;
		    }
		    
		    
		    
			    System.out.println(String.valueOf("highest is " + highestUtility));
			    System.out.println(String.valueOf("lowest 1 is " +lowerUtility1));
			    System.out.println(String.valueOf("lowest 2 is " + lowerUtility2));
			    calculatedUtility = currentReward + discount * (0.8 * highestUtility + 0.1 * lowerUtility1 + 0.1 * lowerUtility1) ;
			    
			    //grid.get(i).setUtility(calculatedUtility);
			    
			    return calculatedUtility;*/
			
			
			
	 }
	 
	 
	 
	 
	 
	 
		public  int editRowCol () {
			
			
			JFrame frame = new JFrame();
			JPanel pane = new JPanel(new BorderLayout());
			pane.add(new JTextArea("Enter row and col") , BorderLayout.PAGE_START);
			
			JTextField rowField = new JTextField();
			JTextField colField = new JTextField();
			JButton ok = new JButton("Enter");
			JPanel pane2 = new JPanel();
			pane2.setLayout(new BoxLayout(pane2, BoxLayout.PAGE_AXIS));
			pane2.add(rowField  );
			pane2.add(colField);
			pane2.add(ok);
			pane.add(pane2);
			
			frame.add(pane);
			frame.setVisible(true);
			frame.setSize(300, 300);
			
			
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					
					row = Integer.valueOf(rowField.getText());
					col = Integer.valueOf(colField.getText());
						
					ValueIteration vi = new ValueIteration();
					vi.setRow(row);
					vi.setCol(col);
					
					
					
					
					
				}
			});
			
			
			
			
			
			

			return 3;
			
		}
	
	
	
	
	
	
	
	
	

}
