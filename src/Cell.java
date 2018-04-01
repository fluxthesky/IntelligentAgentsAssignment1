import java.awt.Color;

import javax.swing.JButton;

public class Cell extends JButton{
	
	
	//the reward given by the grid
	private double reward = 0;
	
	//the current utility of the grid
	public double utility = 0;
	
	
	//the color of the grid
	private Color color = Color.WHITE;
	
	
	public final static int white = 0;
	public final static int green = 1;
	public final static int brown = 2;
	public final static int wall = 3;
	public int cell_type = 0;
	
	
	
	
	
	public Cell(int type) {
		// TODO Auto-generated constructor stub
		
 
		setType(type);
		
		
		
		
	}
	
	
	public void setType(int type) {
		
		//set what type of indiviual grid this will be
		
		
		switch(type) {
		
		
		case white:
			reward = -0.04;
			utility = reward;	
			color = Color.WHITE;
			cell_type = white;
			this.setBackground(color);
			break;
		case green:
			reward = 1;
			utility = reward;
			color = Color.GREEN;
			cell_type = green;
			this.setBackground(color);
			break;
		case brown:
			reward = -1;
			utility = reward;
			color = Color.ORANGE;
			cell_type = brown;
			this.setBackground(color);
			break;
		case wall:
			reward = -100;
			color = Color.BLACK;
			cell_type = wall;
			this.setBackground(color);
			break;
		
		
		
		
		
		
		
		}
		
	}
	
	
	public void toggleCellType() {
		
		//toggle between different types
		
		if(cell_type==white) {
			
			setType(brown);
		}
		else if(cell_type==brown) {
			setType(green);
		}
		else if(cell_type==green) {
			setType(wall);
		}
		else if(cell_type==wall) {
			setType(white);
		}
		
		
		
		
	}
	
	
	
	public void setUtility(double utility) {
		this.utility = utility;
	}
	
	public double getUtility() {
		return utility;
	}
	
	
	
	public int getType() {
		return cell_type;
	}
	
 	 public double getReward() {
		return reward;
	}
	
	
	public void setReward(double reward) {
		this.reward = reward;
	}
	
	public void setColor(Color color) {
		this.color = color;
		this.setBackground(color);
	}
	
	
	
	
	

}
