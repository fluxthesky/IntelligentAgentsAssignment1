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

public class Main {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 
		
		//starts value iteration
		ValueIteration vi = new ValueIteration();
		vi.setup();
		vi.startValueIteration();
	
		
		
		//starts policy evalution
		PolicyEvaluation pe = new PolicyEvaluation();
		pe.setLocation(vi.getLocation().x += 50 , vi.getLocation().y += 50);
		pe.setup();
		pe.startPolicyEvalution();
		
 		
		
	}


	

}
