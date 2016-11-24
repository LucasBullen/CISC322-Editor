import javax.swing.JOptionPane;
import javax.swing.table.*;

class StatisticsAction extends CSVAction {
	public StatisticsAction() {
		super("Statistics");
    } // end constructor UpCaseAction

    /**
     * Creates an Message Dialog Box showing statistics for
     * the selected column. Must be only numeric values
     * @param con CSV to analyze.
     * @param row clicked, unused.
     * @param column clicked, used as the column to be analyzed.
     */
    protected void changeCSV(CSVContents con, int row, int col) {
        DefaultTableModel table = con.dftTbl;
        int rows = table.getRowCount();
        float[] rowData = new float[rows];
        int sum = 0;
        int nonblank = 0;
        float max = 0;
        float min = 0;
        boolean maxMinSet = false;

        for (int i = 0; i < rows; i++) {
            String valString = (String)table.getValueAt(i,col);
            if(valString == null || valString.isEmpty()){
                continue;
            }else if(!isNumeric(valString)){
                JOptionPane.showMessageDialog(null, "Error: non-numeric value in row.");
                return;
            }
            float val = Float.parseFloat(valString);
            sum += val;
            nonblank++;
            if (!maxMinSet) {
                max = val;
                min = val;
                maxMinSet = true;
            }else if(max < val){
                max = val;
            }else if(min > val){
                min = val;
            }
        }
        float mean = sum / nonblank;
        JOptionPane.showMessageDialog(null, "Statistics:"+
            "\nSum: "+sum+
            "\nElements: "+nonblank+
            "\nMean: "+mean+
            "\nMax: "+max+
            "\nMin: "+min
        );
        con.dftTbl = table;
    } // end changeText

    /*
    * Analyzes a string to see if it is in a numeric format,
    * if so True is returned, otherwise False.
    * @param String to be analyzed
    */
    private boolean isNumeric(String str)
    {
      return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

} // end class UpCaseAction
