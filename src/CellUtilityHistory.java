import java.util.ArrayList;

public class CellUtilityHistory {
	
	
	public ArrayList<Double> cellUtilityHistory = new ArrayList<Double>();

	
	public void addCellUtilityHistory(double cellUtilityHistory) {
		this.cellUtilityHistory.add(cellUtilityHistory);
	}
	
	public ArrayList<Double> getCellUtilityHistory() {
		return cellUtilityHistory;
	}
	
	
	

}
