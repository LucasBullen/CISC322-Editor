import javax.swing.JOptionPane;
import javax.swing.table.*;
import java.util.Vector;

class RemoveDuplicateRowsAction extends CSVAction {
	public RemoveDuplicateRowsAction() {
		super("Remove Duplicate Rows");
    } // end constructor UpCaseAction

    /**
     * Convert the text in a given range of the document to upper case.
     * Does nothing if the start and end indices are equal.
     * @param con Text to change.
     * @param start Index of the first character to change (the one to be
     *  capitalized).
     * @param end Index one beyond the last character to change.
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
