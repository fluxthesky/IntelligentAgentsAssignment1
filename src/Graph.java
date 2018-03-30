import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class Graph extends ApplicationFrame  {

	String name = "";
	
	public Graph(String title, String name) {
		super(title);
		this.name = name;
 
	}

	
	public void setup(ArrayList<Double> cellUtility) {
		
		
		
		
		final XYSeries series = new XYSeries("");
		
		
		for(int i = 0 ; i < cellUtility.size(); i++) {
			
			
			series.add(i , cellUtility.get(i));
			
			
			
			
			
		}
		
		
		
		
		
		
		
	    final XYSeriesCollection data = new XYSeriesCollection(series);
	    final JFreeChart chart = ChartFactory.createXYLineChart(
	    		name,
	        "Number of iterations", 
	        "Utilies", 
	        data,
	        PlotOrientation.VERTICAL,
	        true,
	        true,
	        false);
	    
	    
	    
	    
	    final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	    setContentPane(chartPanel);
		
		
		
		
	}
	
	
	
	
	
	/**
	 * 
	 */
 	
	
	
	
	
	
	
	

}
