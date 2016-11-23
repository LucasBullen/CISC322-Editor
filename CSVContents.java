// $Id: CSVContents.java,v 1.0 2012/10/04 13:57:18 dalamb Exp $
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

import java.util.ArrayList;
import javax.swing.table.*;

//import java.util.*;
import ca.queensu.cs.dal.edfmwk.doc.DocumentException;
import ca.queensu.cs.dal.edfmwk.doc.StringSequence;
import ca.queensu.cs.dal.edfmwk.doc.StringSequenceInputStream;

/**
 * Internal representation of a csv document.
 *<p>
 * Copyright 2010-2011 David Alex Lamb.
 * See the <a href="../doc-files/copyright.html">copyright notice</a> for details.
 */
public class CSVContents
    extends javax.swing.text.PlainDocument
    implements StringSequence
{
    //Array of the header strings for each column of the table
    public String[] headers;
    //2D array of all the strings stored in the table
    public String[][] rowData;
    private int bufferSize;
    private char[] buffer;
    public DefaultTableModel dftTbl;

    /**
     * Constructs an empty csv file contents.
     */
    public CSVContents() {
    	super();
        headers = new String[1];
        rowData = new String[1][1];
        //Placeholder information for new csv files
        headers[0]="new";
        rowData[0][0]="csv";

        dftTbl = new DefaultTableModel(rowData, headers);

        bufferSize = 100;
        buffer = new char[bufferSize];
    } // end constructor

    /**
     * Reads the entire document, and closes the stream from which it is read.
     * @param in Where to read the document from.
     * @throws IOException if any I/O errors occur, in which case it will have
     * closed the stream.
     */
    public void open(InputStream in)
	throws IOException
    {
    	CSVReader reader = new CSVReader(new InputStreamReader(in));
        String[] nextLine;
    	// Assuming first line of the csv contains headers
        if ((nextLine = reader.readNext()) != null) {
            headers = nextLine;
        } else {
            headers = new String[1];
        }
        // Will store the length of the longest line, in case a line is longer than headers
        int longestLength = headers.length;
        ArrayList<String[]> tempData = new ArrayList<String[]>();

        //each iteration of this loop assigns nextLine to a new line taken from the 		input file
        while ((nextLine = reader.readNext()) != null) {
            tempData.add(nextLine);
            // Updating the longest length
			if (nextLine.length > longestLength) {
                longestLength = nextLine.length;
            }
        }

		// We initially set longestLength to header.length, checking if it has changed
		// If so, we update it
        if (longestLength != headers.length) {
            // Stores the final headers
			String[] tempHeaders = new String[longestLength];
            // Adds the existing headers to the new temp, extended tail will be null
			for (int i=0; i<headers.length; i++) {
                tempHeaders[i] = headers[i];
            }
            headers = tempHeaders;
        }

		// Creates the final set of row data in String[][] format, such that
		// the length of the 2D array is the size of the file
        rowData = tempData.toArray(new String[tempData.size()][]);
        dftTbl = new DefaultTableModel(rowData, headers);
    } // end method open

    /**
     * Writes the entire document.
     * @param out Where to write the document
     * @throws IOException if any I/O errors occur.
     */
    public void write(Writer out) throws IOException
    {
        CSVWriter writer = new CSVWriter(out,',',CSVWriter.NO_QUOTE_CHARACTER);
        for (int x = -1; x < dftTbl.getRowCount(); x++){
            String[] holdData = new String[dftTbl.getColumnCount()];

            for (int y = 0; y < dftTbl.getColumnCount(); y++){
                if( x == -1 ){
                    holdData[y] = dftTbl.getColumnName(y).toString();
                }else{
                    holdData[y] = dftTbl.getValueAt(x, y).toString();
                }
            }
            writer.writeNext(holdData);
        }
        writer.close();
    } // end method write

    /**
     * Saves the entire document.
     * @param out Where to write the document.
     * @throws IOException if any I/O errors occur, in which case it will have
     * closed the stream.
     */
    public void save(OutputStream out) throws IOException {
	try {
	    write(new PrintWriter(out));
	} catch (Exception e) {
	    out.close();
	    //	    throw new IOException(e);
	    throw new IOException(e.getLocalizedMessage());
	}
    } // end save

    /**
     * Gets an input stream from which the document contents can be read as a
     *  stream of bytes.  This is required when running in a sandbox, where
     *  {@link javax.jnlp.FileSaveService#saveAsFileDialog} does not provide a
     *  means of supplying an output stream to which to write the internal
     *  representation. Document managers should avoid using this method
     *    wherever possible, preferring {@link #save} instead.
     * @throws DocumentException if such a stream cannot be created.
     */
    public InputStream getContentsStream() throws DocumentException
    {
	try {
	    // return new StringBytesInputStream(this);
	    return new StringSequenceInputStream(this);
	} catch (Exception e) {
	    throw new DocumentException(e);
	}
    } // end getContentStream

    /**
     * Gets a substring of the text of the document.
     * A variant on {@link javax.swing.text.Document#getText} that raises no
     *  exceptions.
     * @param start Index of the first character in the substring.
     * @param length Length of the substring
     * @return the substring, or null if the supposed substring extends beyond
     *   the bounds of the text.
     */
    public String safelyGetText(int start, int length) {
	try {
	    return getText(start,length);
	} catch (Exception e) {
	    return null;
	}
    } // end safelyGetText

} // end TextContents
