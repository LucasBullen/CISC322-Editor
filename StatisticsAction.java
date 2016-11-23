import javax.swing.JOptionPane;
import javax.swing.table.*;

class StatisticsAction extends CSVAction {
	public StatisticsAction() {
		super("Statistics");
    } // end constructor UpCaseAction

    /**
     * Convert the text in a given range of the document to upper case.
     * Does nothing if the start and end indices are equal.
     * @param con Text to change.
     * @param start Index of the first character to change (the one to be
     *  capitalized).
     * @param end Index one beyond the last character to change.
     */
    protected void changeCSV(CSVContents con, int row, int col) {
		System.out.println("changeText:"+row+","+col);
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

    private boolean isNumeric(String str)
    {
      return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

} // end class UpCaseAction
