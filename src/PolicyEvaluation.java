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

public class PolicyEvaluation extends JFrame {
	
 	 JMenu menu;
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
 
	 ArrayList<Integer> oldCellDirectionTaken = new ArrayList<Integer>();
	 ArrayList<Integer> cellDirectionTaken = new ArrayList<Integer>();

	
	
	
	 int nextCell = 0;
	 int iterationsLeft = 20;
	 double discount = 0.99;
	
	 int directionTaken = 0;
	 int oldDirectionTaken = 0;

	
	
	 double episilon = 80;
	
	 boolean action_changed = true;
	
	
	
	 double convergence = episilon*(1.0 - discount)/discount;
	
	 int row = 6;
	 int col = 6;
 	 int gridSize;
	
	 public PolicyEvaluation() {}
	 
	 
	 public void setup() {

			// TODO Auto-generated constructor stub
			 
				this.setSize(1024, 768);
				this.setTitle("Policy Evaluation");
				this.setVisible(true);
				this.setLayout(new BorderLayout());
				this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		
				
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
				map.setLayout(new GridLayout(row,col));
				gridSize = row * col;
				
				
				for (int i = 0 ;i < gridSize ; i++) {
					
					int k = i;
					grid.add(new Cell(Cell.white));	
					
					grid.get(i).addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							 grid.get(k).toggleCellType();
							 
							  clearGridData();
							 startPolicyEvalution();
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
				
				
				for (int i = 0 ;i < gridSize ; i++) {
					
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
	 
	 
	 public void clearGridData() {
		 
		 counter = 0;
		 for(int i = 0 ; i < grid.size(); i++) {
			 grid.get(i).setUtility(grid.get(i).getReward());
			 grid.get(i).setText("");
		 }
		 
		 oldCellDirectionTaken = new ArrayList<>();
		 directionTaken = 0;
		 
		 
		 
	 }
	 
	 
	 public void startPolicyEvalution() {

			
			do {
		
			
			cellUtility = new ArrayList<>();
			oldCellDirectionTaken = cellDirectionTaken;
			cellDirectionTaken = new ArrayList<>();

 			
		for (int i = 0 ;i < gridSize ; i++) {
			
			
			double newUtility;
			double oldUtility = 0;
			
			visitedCells = new ArrayList<Integer>();
			calculatedUtility =0;
		
			
			
			newUtility = findUtility(i);
			
			cellDirectionTaken.add(directionTaken);
			
			
			
			cellUtility.add(newUtility);
			
			
			
			
			
			if(grid.get(i).getType()!=Cell.wall) {
				
			
				System.out.println("Direction taken is " + directionTaken);
				
			switch (directionTaken) {
			
			
			
			
			case (NORTH):
				grid.get(i).setText(String.valueOf("cell no " + i + " " + cellUtility.get(i)) + " \n " + " ^ ");
				break;
			
			case (LEFT):
				grid.get(i).setText(String.valueOf("cell no " + i + " " + cellUtility.get(i)) + " \n " + " < ");
				break;
			case (RIGHT):
				grid.get(i).setText(String.valueOf("cell no " + i + " " + cellUtility.get(i)) + " \n " + " > ");
				break;
			
			case (SOUTH):
				grid.get(i).setText(String.valueOf("cell no " + i + " " + cellUtility.get(i)) + " \n " + " dwn ");
				break;
			
			
			
			}
		}
			
			

		}
		
		for(int i = 0 ; i < gridSize ; i++) {
			grid.get(i).setUtility(cellUtility.get(i));

		}
		
		
		for (int i = 0 ;i < gridSize ; i++) {
							
			
			if(oldCellDirectionTaken.isEmpty() != true) {
				
				if(grid.get(i).getType()!=Cell.wall) {
					
			if(oldCellDirectionTaken.get(i).equals(cellDirectionTaken.get(i)) != true) {
				
				
				//System.out.println("this is TRUE" + action_changed + counter);

				action_changed = true;
				break;
			}
			else {
				
				//System.out.println("this is FALSE" + action_changed + counter);

				action_changed = false;
			}
			
				
				}
			
			}
			
			

		}
				
		
		counter++;
 		
		
		
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
			}
		
			while(action_changed);
			//while(counter<0);

			
			
			
			
			System.out.println("Took " + counter + " iterations");
			
			
			
			
			
			
			
			
			
	 }
	 
	 
	 
	 
	 
	 
	 
	 private  double findUtility(int i) {
			
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
			if(i - col >= 0) {
				
				neighbourCell = grid.get(i-col);
				
				if(neighbourCell.getType() != Cell.wall) {
					
				northUtility = neighbourCell.getUtility();
				System.out.println("North utility is " + northUtility);
			
				}
				
				
			}
			
			//look left
			if(i - 1 >= 0 && i%col !=0) {
				
				
				neighbourCell = grid.get(i-1);
				
				if(neighbourCell.getType() != Cell.wall) {
					
					
				leftUtility = neighbourCell.getUtility();
				System.out.println("Left utility is " + leftUtility);
			
			
			
				}
				
			}
			
			
			//look right
			if(i + 1 <= gridSize-1 && (i+1)%col !=0) {
				
				
				neighbourCell = grid.get(i+1);
				
				if(neighbourCell.getType() != Cell.wall) {
					
					
				rightUtility = neighbourCell.getUtility();
				System.out.println("Right utility is " + rightUtility);
				
				
				}
			}
			
			//look south
			if(i +col<= gridSize-1) {
				
				neighbourCell = grid.get(i+col);
				
				if(neighbourCell.getType() != Cell.wall) {
					
					
				southUtility = neighbourCell.getUtility();
			    System.out.println("South utility is " + southUtility);
			    
			    
			    
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

		    	
		    	
		    }
*/
		    
		    System.out.println("nextCell is " + nextCell);
		    
		    
		    
		    
		    
		    //calculate the best action to take
		   double calculatedUtilityUp = currentReward + discount * (0.8 * northUtility + 0.1 * leftUtility + 0.1 * rightUtility) ;
		   double calculatedUtilityleft = currentReward + discount * (0.8 * leftUtility + 0.1 * northUtility + 0.1 * southUtility) ;
		   double calculatedUtilityright = currentReward + discount * (0.8 * rightUtility + 0.1 * northUtility + 0.1 * southUtility) ;
		   double calculatedUtilitydown = currentReward + discount * (0.8 * southUtility + 0.1 * leftUtility + 0.1 * rightUtility) ;

		   
 		   
		   
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
		   
		   System.out.println("highest utility is " + calculatedUtility + " direction is " + directionTaken);

		   
		   return calculatedUtility;
		   /*
		   System.out.println(calculatedUtilityUp); 
		   System.out.println(calculatedUtilityleft); 
		   System.out.println(calculatedUtilityright); 
		   System.out.println(calculatedUtilitydown); 

		   */
		   /*
		   if(calculatedUtilityUp >= calculatedUtilityleft && calculatedUtilityUp >= calculatedUtilityright && calculatedUtilityUp >= calculatedUtilitydown) {
			   
			   
			   System.out.println("return up");
			   directionTaken = NORTH;
			   return calculatedUtilityUp;
			   
		   }
		   else if(calculatedUtilityleft >= calculatedUtilityUp && calculatedUtilityleft >= calculatedUtilityright && calculatedUtilityUp >= calculatedUtilitydown) {
			   
			   System.out.println("return left");

			   
			   directionTaken = LEFT;

			   return calculatedUtilityleft;
			   
		   }
		   else if(calculatedUtilityright >= calculatedUtilityleft && calculatedUtilityright >= calculatedUtilityUp && calculatedUtilityright >= calculatedUtilitydown) {
			   System.out.println("return right");

			   
			   directionTaken = RIGHT;

			   return  calculatedUtilityright;
			   
		   }
		   else if(calculatedUtilitydown >= calculatedUtilityleft && calculatedUtilitydown >= calculatedUtilityright && calculatedUtilitydown >- calculatedUtilityUp) {
			   
			   System.out.println("return dw");

			   
			   directionTaken = SOUTH;

			   return calculatedUtilitydown;		    

			   
		   }
		   
		   return calUtility;
		   
		   */
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
			    
			    return calculatedUtility;
			*/
			
			
	 }
	 
	 
	 
	 
	 
	 
		public int editRowCol () {
			
			
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
						
					PolicyEvaluation pi = new PolicyEvaluation();
					pi.setRow(row);
					pi.setCol(col);
					pi.setup();
					pi.startPolicyEvalution();
					frame.setVisible(false);
					
					
					
					
				}
			});
			
			
			
			
			
			

			return 3;
			
		}
	
	
	
	
	
	
	
	
	

}
