import java.util.ArrayList;

public class CellUtilityHistory {
	
	
	//this class is responsible for storing history of cell utilities and cell directions
	
	public ArrayList<Double> cellUtilityHistory = new ArrayList<Double>();
	public ArrayList<Integer> cellDirectionHistory = new ArrayList<Integer>();


	
	
	
	
	public void addCellUtilityHistory(double cellUtilityHistory) {
		this.cellUtilityHistory.add(cellUtilityHistory);
	}
	
	public ArrayList<Double> getCellUtilityHistory() {
		return cellUtilityHistory;
	}
	
	
	public void addCellDirectionHistory(int cellDirectionHistory) {
		this.cellDirectionHistory.add(cellDirectionHistory);
	}
	
	
	public ArrayList<Integer> getCellDirectionHistory() {
		return cellDirectionHistory;
	}
	

}
