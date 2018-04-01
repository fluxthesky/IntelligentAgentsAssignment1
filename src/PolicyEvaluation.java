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

import org.jfree.ui.RefineryUtilities;

public class PolicyEvaluation extends JFrame {

	JMenu menu;
	boolean changeGrid = false;

	JMenuBar menuBar;
	JPanel map;
	ArrayList<Cell> grid = new ArrayList<Cell>();
	
	
	//the integer values assiocated with the directions
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

	ArrayList<CellUtilityHistory> cellUtilityHistory = new ArrayList<CellUtilityHistory>();

	int nextCell = 0;
	int iterationsLeft = 20;
	double discount = 0.99;

	int directionTaken = 0;
	int oldDirectionTaken = 0;

	double episilon = 80;

	boolean action_changed = true;

	double convergence = episilon * (1.0 - discount) / discount;

	int row = 6;
	int col = 6;
	int gridSize;

	boolean doNotSetup = false;

	public PolicyEvaluation() {
	}

	public void setup() {

		// TODO Auto-generated constructor stub

		// configure the jframe for execution

		this.setSize(1024, 768);
		this.setTitle("Policy Evaluation");
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// configure the setting menu
		menu = new JMenu("Settings");
		JMenuItem setupGridSize = new JMenuItem("New Grid");
		setupGridSize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				newGrid();
			}
		});

		JMenuItem toggleChangeGrid = new JMenuItem("Change grid type");
		toggleChangeGrid.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (changeGrid == true) {

					System.out.println("TRUE");
					changeGrid = false;
				} else {

					changeGrid = true;

				}

			}
		});

		menu.add(setupGridSize);
		menu.add(toggleChangeGrid);

		menuBar = new JMenuBar();
		menuBar.add(menu);

		// setup grid

		map = new JPanel();
		map.setLayout(new GridLayout(row, col));
		gridSize = row * col;

		for (int i = 0; i < gridSize; i++) {

			cellUtilityHistory.add(new CellUtilityHistory());

			int k = i;
			grid.add(new Cell(Cell.white));

			// add functionality associated with clicking a grid
			grid.get(i).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					if (changeGrid == false) {
						plotGraph(k);
					}

					else {
						grid.get(k).toggleCellType();
						clearGridData();
						startPolicyEvalution();
					}
				}
			});

		}

		if (doNotSetup == false) {

			// setup the maze according to the given configuration

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

		}

		for (int i = 0; i < gridSize; i++) {

			map.add(grid.get(i));

		}

		this.add(menuBar, BorderLayout.PAGE_START);
		this.add(map, BorderLayout.CENTER);

	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void clearGridData() {

		// clear grid data for re running again

		cellUtilityHistory = new ArrayList<>();

		counter = 0;
		for (int i = 0; i < grid.size(); i++) {
			cellUtilityHistory.add(new CellUtilityHistory());
			grid.get(i).setUtility(grid.get(i).getReward());
			grid.get(i).setText("");
		}

		oldCellDirectionTaken = new ArrayList<>();
		directionTaken = 0;

	}

	public void plotGraph(int i) {

		// plot a graph of utility vs iterations
		
		final Graph demo = new Graph("Plot", "Plot For Policy Evaluation, Grid no " + i);
		demo.setup(cellUtilityHistory.get(i).getCellUtilityHistory());
		demo.pack();

		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
		
		
		final Graph demo2 = new Graph("Plot", "Plot For Policy Taken, Grid no " + i);
		demo2.setupGraph(cellUtilityHistory.get(i).getCellDirectionHistory());
		demo2.pack();

		RefineryUtilities.centerFrameOnScreen(demo);
		demo2.setVisible(true);

	}

	public void startPolicyEvalution() {

		do {

			// first start by setting up the required data everytime we perform one
			// iteration

			cellUtility = new ArrayList<>();

			// the previous grid direction taken is recorded in oldCellDirectionTaken
			oldCellDirectionTaken = cellDirectionTaken;
			cellDirectionTaken = new ArrayList<>();

			// find utilities of all the grids
			for (int i = 0; i < gridSize; i++) {

				double newUtility;

				
				
				
				
				// find the utility of the current grid
				newUtility = findUtility(i);

				//record the history of utilities assiocatied with each grid, to be used for plotting graphs later
				cellUtilityHistory.get(i).addCellUtilityHistory(newUtility);
				cellUtilityHistory.get(i).addCellDirectionHistory(directionTaken);

				
				// record the taken grid policy
				cellDirectionTaken.add(directionTaken);

				// add the newly computed cell utility to this list, which will be updated later
				cellUtility.add(newUtility);

				// display the utility if the cell is not a wall
				if (grid.get(i).getType() != Cell.wall) {


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
						grid.get(i)
								.setText(String.valueOf("cell no " + i + " " + cellUtility.get(i)) + " \n " + " dwn ");
						break;

					}
				}

			}

			// for all the grids, update their utility with the newly computed ones.
			for (int i = 0; i < gridSize; i++) {
				grid.get(i).setUtility(cellUtility.get(i));

			}

			// compare the old policy with the new one
			for (int i = 0; i < gridSize; i++) {

				if (oldCellDirectionTaken.isEmpty() != true) {

					if (grid.get(i).getType() != Cell.wall) {

						if (oldCellDirectionTaken.get(i).equals(cellDirectionTaken.get(i)) != true) {

							action_changed = true;
							break;
						} else {

							action_changed = false;
						}

					}

				}

			}

			counter++;

			

		}

		//action_changed will be true if a new policy is made, else it becomes false and the loop terminates
		while (action_changed);

		
		//prints the number of iterations taken
		System.out.println("policy evalutation Took " + counter + " iterations");

	}

	private double findUtility(int i) {

		//this is the current grid
		Cell startCell = grid.get(i);


		
		//initialize the utilies for all directions from the grid to -100 first, we will compute their utility later
		double northUtility = -100;
		double leftUtility = -100;
		double rightUtility = -100;
		double southUtility = -100;


		//get's this grid's utility
		double currentUtility = startCell.getUtility();
		
		
		//get the reward value of this grid
		double currentReward = startCell.getReward();


		//the grid neighbouring to the current grid
		Cell neighbourCell;

		// look north and attempt to find the utility of the grid, if it is not a wall
		if (i - col >= 0) {

			neighbourCell = grid.get(i - col);

			if (neighbourCell.getType() != Cell.wall) {

				northUtility = neighbourCell.getUtility();

			}

		}

		// look left and attempt to find the utility of the grid, if it is not a wall
		if (i - 1 >= 0 && i % col != 0) {

			neighbourCell = grid.get(i - 1);

			if (neighbourCell.getType() != Cell.wall) {

				leftUtility = neighbourCell.getUtility();

			}

		}

		// look right and attempt to find the utility of the grid, if it is not a wall
		if (i + 1 <= gridSize - 1 && (i + 1) % col != 0) {

			neighbourCell = grid.get(i + 1);

			if (neighbourCell.getType() != Cell.wall) {

				rightUtility = neighbourCell.getUtility();

			}
		}

		// look south and attempt to find the utility of the grid, if it is not a wall
		if (i + col <= gridSize - 1) {

			neighbourCell = grid.get(i + col);

			if (neighbourCell.getType() != Cell.wall) {

				southUtility = neighbourCell.getUtility();

			}

		}

		
		//over here, if the corresponding grids appear to be a wall, set their 'utility' to be that of the current utility
		
		if (northUtility == -100) {
			northUtility = currentUtility;
		}
		if (leftUtility == -100) {
			leftUtility = currentUtility;

		}
		if (rightUtility == -100) {
			rightUtility = currentUtility;
		}
		if (southUtility == -100) {
			southUtility = currentUtility;
		}


		
		
		// calculate the best policy to take
		double calculatedUtilityUp = currentReward
				+ discount * (0.8 * northUtility + 0.1 * leftUtility + 0.1 * rightUtility);
		double calculatedUtilityleft = currentReward
				+ discount * (0.8 * leftUtility + 0.1 * northUtility + 0.1 * southUtility);
		double calculatedUtilityright = currentReward
				+ discount * (0.8 * rightUtility + 0.1 * northUtility + 0.1 * southUtility);
		double calculatedUtilitydown = currentReward
				+ discount * (0.8 * southUtility + 0.1 * leftUtility + 0.1 * rightUtility);

		
		//calculate the largest utility among all directions
		double calculatedUtility = Math.max(Math.max(calculatedUtilityUp, calculatedUtilityleft),
				Math.max(calculatedUtilityright, calculatedUtilitydown));
		

		//set the new policy to be taken based on which direction has the largest utility
		if (calculatedUtility == calculatedUtilityUp) {
			directionTaken = NORTH;
		} else if (calculatedUtility == calculatedUtilityleft) {
			directionTaken = LEFT;
		} else if (calculatedUtility == calculatedUtilityright) {
			directionTaken = RIGHT;
		} else if (calculatedUtility == calculatedUtilitydown) {
			directionTaken = SOUTH;
		}


		//return the largest utility
		return calculatedUtility;

	}

	public int newGrid() {
		
		
		//this method is used for creating a new grid


		JFrame frame = new JFrame();
		JPanel pane = new JPanel(new BorderLayout());
		pane.add(new JTextArea("Enter row and col"), BorderLayout.PAGE_START);

		JTextField rowField = new JTextField();
		JTextField colField = new JTextField();
		JButton ok = new JButton("Enter");
		JPanel pane2 = new JPanel();
		pane2.setLayout(new BoxLayout(pane2, BoxLayout.PAGE_AXIS));
		pane2.add(rowField);
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
				pi.doNotSetup = true;

				pi.setup();
				pi.startPolicyEvalution();
				frame.setVisible(false);

			}
		});

		return 3;

	}

}
