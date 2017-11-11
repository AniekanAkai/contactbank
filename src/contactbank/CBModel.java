
package contactbank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Contact Bank system model class which updates the view depending on the 
 * user's input or selection in the UI/
 * @author Teddy
 */
public class CBModel {
    
    private String currentViewGroup; //The Group of contacts to be displayed
    private ArrayList<Contact> currentContacts; //List of contacts that belong to the group displayed
    private ArrayList<String> columns;
    private ContactBankView cbv;
    private CBController controller;
    
    public CBModel(CBController con, ArrayList<String> columnNames,
            ArrayList<Contact> allContacts, String viewedGroup) throws IOException{
        System.out.println("Model has begun");
        columns = columnNames;
        currentContacts = allContacts;
        currentViewGroup = viewedGroup;
        controller = con;
        cbv = new ContactBankView(this);
    }
    
    public CBController getController(){
        return controller;
    }

    /**
     * @return the currentContactGroup
     */
    public String getCurrentViewGroup() {
        return currentViewGroup;
    }

    /**
     * @return the currentContacts
     */
    public ArrayList<Contact> getCurrentContacts() {
        return currentContacts;
    }

    /**
     * Set the new list of contacts to have on display.
     * When the list of contacts is changed, the new list name/label(titled viewGroup) 
     * should be defined as well. One should not be done without the other.
     */
    public void setCurrentContacts(ArrayList<Contact> currentContacts, String listLabel) throws IOException {
        this.currentContacts = currentContacts;
        currentViewGroup = listLabel;
        refresh();
    }

    public void addContact(Contact c) throws IOException{
        currentContacts.add(c);
        refresh();
    }
    
    public void updateContact(Contact c) throws IOException{
        ListIterator<Contact> i = currentContacts.listIterator();
        while(i.hasNext()){
            Contact temp = i.next();
            //Match with contact ID since the ID unique and unchangeable
            if(temp.getID() == c.getID()){
                i.set(c);                
            }                 
        }
        refresh();        
    }
    
    public void removeContact(Contact c) throws IOException{
        Iterator<Contact> i = currentContacts.iterator();
        while(i.hasNext()){
            Contact temp = i.next();
            if(temp.equals(c)){
                System.out.println(temp.getName()+" has been removed.");
                i.remove();                
            }                 
        }
        refresh();
    }
    
    /**
     * @return the currentColumns
     */
    public ArrayList<String> getColumns() {
        return columns;
    }

    
    public void search(ArrayList<Contact> results) throws IOException{
        setCurrentContacts(results, "Search Results");
        refresh();    
    }
    
    public void refresh() throws IOException{
        //Refreshes table with new latest contact list
        cbv.setNewTableModel(currentContacts);
   
    }
}
