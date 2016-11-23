import ca.queensu.cs.dal.edfmwk.menu.MenuElement;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.Action;

class RightClickMenu extends JPopupMenu {

    public void addElement(CSVAction element){
    	JMenuItem newItem = new JMenuItem(element);
    	System.out.println(newItem);
    	add(newItem);
    }
}