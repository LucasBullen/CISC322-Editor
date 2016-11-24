// $Id: CSVDocument.java,v 1.0 2012/10/04 13:57:18 dalamb Exp $
import java.awt.Container;
import java.io.*;
//import java.util.*;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.*;
import java.awt.BorderLayout;
// Import only those classes from edfmwk that are essential, for documentation purposes
import ca.queensu.cs.dal.edfmwk.doc.AbstractDocument;
import ca.queensu.cs.dal.edfmwk.doc.DocumentType;
import ca.queensu.cs.dal.edfmwk.doc.DocumentEvent;
import ca.queensu.cs.dal.edfmwk.doc.DocumentException;
import ca.queensu.cs.dal.edfmwk.doc.DocumentListener;

/**
 * Implementation of a csv document, which is (indirectly) defined in
 * terms of a Swing {@link javax.swing.Document}.
 *<p>
 * Copyright 2010 David Alex Lamb.
 * See the <a href="../doc-files/copyright.html">copyright notice</a> for details.
 */
public class CSVDocument
    extends AbstractDocument
    implements javax.swing.event.DocumentListener
{
    private CSVContents contents;
    private RightClickMenu menu;
    public int mouseRowLocation;
    public int mouseColLocation;

    /**
     * Constructs a document representation.
     * @param type The type of the document.
     */
    public CSVDocument(CSVType type) {
	super(type);
    menu = type.getRightClickMenu();
	contents = new CSVContents();
	contents.addDocumentListener(this);
    //Adds the JTable to the window with current table information
    loadWindowWithContent();
    } // end CSVDocument

    /**
    * Function to load the window with the content of the
    * DefaultTableModel within the CSVContent.
    */
    public void loadWindowWithContent(){
        JTable csvTable = new JTable(contents.dftTbl);
        csvTable.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            mouseRowLocation = csvTable.rowAtPoint(evt.getPoint());
            mouseColLocation = csvTable.columnAtPoint(evt.getPoint());
            if (mouseRowLocation >= 0 && mouseColLocation >= 0) {
                //we clicked a square
                menu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
         }
        });
        window = new JScrollPane(csvTable);
    }

    // CSV document change listeners: all invoke the framework's own document
    // change listeners.

    /**
     * Gives notification that an attribute or set of attributes changed.
     */
    public void changedUpdate(javax.swing.event.DocumentEvent e) {
	setChanged();
    } // end changedUpdate


    /**
     * Gives notification that there was an insert into the document.
     */
    public void insertUpdate(javax.swing.event.DocumentEvent e) {
	setChanged();
    } // end insertUpdate

    /**
     * Gives notification that a portion of the document has been removed.
     */
    public void removeUpdate(javax.swing.event.DocumentEvent e) {
	setChanged();
    } // end removeUpdate

    /**
     * Saves the entire document.  After this operation completes
     * successfully, {@link #isChanged} returns <b>false</b>
     * @param out Where to write the document.
     * @throws IOException if any I/O errors occur, in which case it will have
     * closed the stream; isChanged() is unchanged.
     */
    public void save(OutputStream out) throws IOException {
	contents.save(out);
	setChanged(false);
    } // save

    /**
     * Gets an input stream from which the document contents can be read as a
     *  stream of bytes.  This is required when running in a sandbox, where
     *  {@link javax.jnlp.FileSaveService#saveAsFileDialog} does not provide a
     *  means of supplying an output stream to which to write the internal
     *  representation. Document managers should avoid using this method
     *    wherever possible, preferring {@link #save} instead.
     * @throws IOException if such a stream cannot be created.
     */
    public InputStream getContentsStream() throws DocumentException {
	return contents.getContentsStream();
    } // getContentsStream

    /**
     * Reads the entire document, and closes the stream from which it is read.
     * @param in Where to read the document from.
     * @throws IOException if any I/O errors occur, in which case it will have
     * closed the stream.
     */
    public void open(InputStream in)
	throws IOException
    {
	    contents.open(in);
        //Adds the JTable to the window with current table information
        loadWindowWithContent();
	    setChanged(false);
    } // open

    /**
     * Gets the contents of the CSV document, for those few methods within
     *    this package that need direct access (such as actions).
     */
    CSVContents getContents() { return contents; }
} // end class CSVDocument

