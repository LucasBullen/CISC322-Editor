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
        con.dftTbl.setValueAt("cheese", row, col);
    } // end changeText
} // end class UpCaseAction
