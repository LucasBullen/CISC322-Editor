import javax.swing.JOptionPane;
import javax.swing.table.*;
import java.util.Vector;

class RemoveDuplicateRowsAction extends CSVAction {
	public RemoveDuplicateRowsAction() {
		super("Remove Duplicate Rows");
    } // end constructor UpCaseAction

    /**
     * Removes all duplicate rows from a csv file, keeping
     * the first occurnace of a row.
     * @param con CSV to change.
     * @param 0
     * @param 0.
     */
    protected void changeCSV(CSVContents con, int r, int c) {
        DefaultTableModel table = con.dftTbl;
        Vector<Vector<String>> content = table.getDataVector();
        for (int i = 0; i<content.size(); i++) {
            Vector<String> row = content.get(i);
            for (int j = i+1; j<content.size(); j++) {
                 if (row.equals(content.get(j))) {
                    table.removeRow(j);
                    j--;
                }
            }

        }
        con.dftTbl = table;
    } // end changeText
} // end class UpCaseAction
