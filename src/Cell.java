import java.awt.Color;

import javax.swing.JButton;

public class Cell extends JButton{
	
	
	private double reward = 0;
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
		
		switch(type) {
		
		
		case white:
			reward = -0.04;
			color = Color.WHITE;
			cell_type = white;
			this.setBackground(color);
			break;
		case green:
			reward = 1;
			color = Color.GREEN;
			cell_type = green;
			this.setBackground(color);
			break;
		case brown:
			reward = -1;
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
