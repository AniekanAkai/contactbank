package contactbank;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class that reacts to the actions performed on the UI
 * No main logic performed here
 * Methods present include server requests,
 *           to query the DB for particular data, or
 *           to change the data in the DB.
 * @author Teddy
 */
public class CBController implements ActionListener{
    
    ContactBankView cbv;
    CBModel cbm;
    ContactBankApp cba;
    Socket socket;
    BufferedReader socketInput;
    PrintWriter socketOutput;
    AddView av;
    ArrayList<Contact> allContacts;
    ArrayList<String> columnNames;
    
    public CBController(ContactBankApp cba) throws IOException{
        this.cba = cba;
        socket = cba.getSocketStream();
        socketInput = cba.getInputStream();
        socketOutput = cba.getOuputStream();
        String input;

        //To populate the all contacts table on the GUI(first window)
        getAllColumns();
        getAllContacts();
        //Model would now collect the relevant data to be updated shown on 
        //the UI
        cbm = new CBModel(this, columnNames, allContacts, "All Contacts");            
    }
    
    public ArrayList<Contact> getUpdatedContactList() throws IOException{
        return getAllContacts();        
    }
    
    //To ready the controller to react to actions performed on the different views
    public void listen(Object o){
        if(o instanceof ContactBankView){
            cbv = (ContactBankView)o;
            cbv.addListener(this);            
        }else if(o instanceof AddView){
            av = (AddView)o;
            av.addListener(this);
        }
    }
        
    /*
     * Here the client sends requests to the server depending on the action
     * selected in the UI. Client request is done in the methods
     */    
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e){
        System.out.println("Action performed");
        //Button Action listeners
        if(e.getActionCommand().equals("Search")){
            //Search full contact list
            try {
                System.out.println("Searching for " + cbv.searchField.getText());            
                search(cbv.searchField.getText());
            } catch (IOException ex) {
                Logger.getLogger(CBController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getActionCommand().equals("Remove")){
            //remove the currently selected contact
            System.out.println("Remove selected contact");
            try {
                int row = cbv.contactTable.getSelectedRow();
                Contact c = new Contact((Long)cbv.contactTable.getValueAt(row, 0),
                                        (String)cbv.contactTable.getValueAt(row, 1),
                                        (String)cbv.contactTable.getValueAt(row, 2), 
                                        (String)cbv.contactTable.getValueAt(row, 3),
                                        (String)cbv.contactTable.getValueAt(row, 4), 
                                        (String)cbv.contactTable.getValueAt(row, 5),
                                        (String)cbv.contactTable.getValueAt(row, 6),
                                        (Long)cbv.contactTable.getValueAt(row, 7));
                removeContact(c);
            } catch (IOException ex) {
                Logger.getLogger(CBController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getActionCommand().equals("Save")){
            //Save current table and update db
            //save table in excel format or xml or .sql
            System.out.println("Saving changes");            
        }else if(e.getActionCommand().equals("Refresh")){
            try {
                //Show the current table in the DB on the UI
                System.out.println("Refresh");
                getAllContacts();
                cbm.setCurrentContacts(allContacts, "All Contacts");
                cbm.refresh();
            } catch (IOException ex) {
                Logger.getLogger(CBController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getActionCommand().equals("Open Add Dialog")){
            //Open dialog to enter new contact details 
            
        }else if(e.getActionCommand().equals("Add")){
            //store contact in  DB
           
        }else if(e.getActionCommand().equals("Toggle")){
            //Toggle current contact list views
                    
        }else if(e.getActionCommand().equals("Show Group")){
         //Open a dialog to specify group to view and list contacts in this group
         
            
        }else if(e.getActionCommand().equals("Show Project")){
         //Open a dialog to specify project to view and list contacts in this project
            
        }
        //Menu Item Action listeners
        else if(e.getActionCommand().equals("About")){
            //Open Dialog displaying details of the program
            
        }else if(e.getActionCommand().equals("Help")){
            //Open Webpage with details on program
            
        }else if(e.getActionCommand().equals("Open Readme File")){
            //Open Readme.pdf
            
        }else if(e.getActionCommand().equals("Advanced Settings")){
            //Open window with advanced settings options
        
        }else if(e.getActionCommand().equals("Settings")){
            //Open window with basic settings options
        
        }else if(e.getActionCommand().equals("Create Group")){
            //Open dialog specifying group to create
        
        }else if(e.getActionCommand().equals("Create Project")){
            //Open dialog specifying project to create
            
        }else if(e.getActionCommand().equals("Remove Group")){
            //Open dialog specifying group to remove
            
        }else if(e.getActionCommand().equals("Remove Project")){
            //Open dialog specifying project to remove
            
        }
    }
    
    public void addContact(Contact c) throws IOException{
        socketOutput.println("Add Contact");
        while(!socketInput.ready()){}
        if(socketInput.readLine().equalsIgnoreCase("Send Details")){
            String cString = c.stringToDB();
            socketOutput.println(cString);
        }
        cbm.addContact(c);
    }
    
    public void addContactToGroup(Contact c, String groupName) throws IOException{
        c.addGroup(groupName);
        socketOutput.println("Update contact");
        while(!socketInput.ready()){}
        if(socketInput.readLine().equalsIgnoreCase("Send Details")){
            String cString = c.stringToDB();
            socketOutput.println(cString);
        }
        cbm.updateContact(c);
    }
    
    //Editing is done in the UI, then the latest edit is then updated in backend
    public void updateContactDetails(Contact newContact) throws IOException{
        
        socketOutput.println("Update contact");
        while(!socketInput.ready()){}
        if(socketInput.readLine().equalsIgnoreCase("Send Details")){
            String cString = newContact.stringToDB();
            socketOutput.println(cString);
        }    
                
        cbm.updateContact(newContact);
    }
    
    public void createNewGroup(String groupName, ArrayList<Contact> groupMembers) throws IOException{
    
        Iterator<Contact> it = groupMembers.iterator();
        while(it.hasNext()){
            Contact c = it.next();
            c.addGroup(groupName);
            socketOutput.println("Update contact");
            while(!socketInput.ready()){}
            if(socketInput.readLine().equalsIgnoreCase("Send Details")){
                String cString = c.stringToDB();
                socketOutput.println(cString);
            }
            cbm.updateContact(c);
        }
    }
    
    public void createNewProject(String projectName, ArrayList<Contact> projectParticipants) throws IOException{
    
            Iterator<Contact> it = projectParticipants.iterator();
            while(it.hasNext()){
                Contact c = it.next();
                c.addProject(projectName);
                socketOutput.println("Update contact");
                while(!socketInput.ready()){}
                if(socketInput.readLine().equalsIgnoreCase("Send Details")){
                    String cString = c.stringToDB();
                    socketOutput.println(cString);
                }
                cbm.updateContact(c);
            }
    }    
    
    public void removeGroup(String groupName, ArrayList<Contact> groupMembers) throws IOException{
        Iterator<Contact> it = groupMembers.iterator();
        while(it.hasNext()){
            Contact c = it.next();
            c.removeGroup(groupName);
            socketOutput.println("Update contact");
            while(!socketInput.ready()){}
            if(socketInput.readLine().equalsIgnoreCase("Send Details")){
                String cString = c.stringToDB();
                socketOutput.println(cString);
            }
            cbm.updateContact(c);
        }
    }
    
    public void removeProject(String projectName, ArrayList<Contact> projectParticipants) throws IOException{
            Iterator<Contact> it = projectParticipants.iterator();
            while(it.hasNext()){
                Contact c = it.next();
                c.removeProject(projectName);
                socketOutput.println("Update contact");
                while(!socketInput.ready()){}
                if(socketInput.readLine().equalsIgnoreCase("Send Details")){
                    String cString = c.stringToDB();
                    socketOutput.println(cString);
                }
                cbm.updateContact(c);
            }
    }    
    
    public void search(String key) throws IOException{
        //Search done on server side with MySQL query
        //Search results are returned and created in a list which is then
        //sent to the model.
        ArrayList<Contact> searchResults = new ArrayList<>();
        socketOutput.println("Search");
        while(!socketInput.ready()){}
        if(socketInput.readLine().equalsIgnoreCase("Send key")){
            socketOutput.println(key);
        }
        while(true){
            //Server is sending all contacts in the specified group
            while(!socketInput.ready()){}
            String contactData = socketInput.readLine();
            if(contactData.equalsIgnoreCase("Done")){
                System.out.println("All member of search results received");
                break;
            }else{
                 //All the data is stored as a long string, with each particular
                //contact information seperated by ';'
                String[] data = contactData.split(";");
                // Each row represents a contact 
                Contact c = new Contact(Long.parseLong(data[0]),data[1], data[2], data[3], data[4], data[5], data[6], Long.parseLong(data[7]));
                String[] projects = data[8].split(",");
                for(int i=0; i<projects.length; i++){
                    c.addProject(projects[i]);
                }
                String[] groups = data[9].split(",");
                for(int i=0; i<groups.length; i++){
                    c.addProject(groups[i]);
                }   
                searchResults.add(c);
            }
        }
        cbm.search(searchResults);
    }
  
    public void sendEmail(String to, String from, String body){
        //to be implemented
    }
    
    public void removeContact(Contact c) throws IOException{
        socketOutput.println("Remove Contact");
        while(!socketInput.ready()){}
        if(socketInput.readLine().equalsIgnoreCase("Send ID")){            
            socketOutput.println(Long.toString(c.getID()));
        }
        cbm.removeContact(c);            
    }
    
    public void removeContactFromGroup(Contact c, String groupName) throws IOException{
        c.addGroup(groupName);
        socketOutput.println("Update contact");
        while(!socketInput.ready()){}
        if(socketInput.readLine().equalsIgnoreCase("Send Details")){
            String cString = c.stringToDB();
            socketOutput.println(cString);
        }
        cbm.updateContact(c);        
    }
    
    public void viewGroup(String groupName) throws IOException{
        
        ArrayList<Contact> groupMembers = new ArrayList<>();
        socketOutput.println("Send Group Members");
        while(!socketInput.ready()){}
        if(socketInput.readLine().equalsIgnoreCase("Send Group Name")){
            socketOutput.println(groupName);
        }
        while(true){
            //Server is sending all contacts in the specified group
            while(!socketInput.ready()){}
            String contactData = socketInput.readLine();
            if(contactData.equalsIgnoreCase("done")){
                System.out.println("All member of "+groupName+" group received");
                break;
            }else{
                 //All the data is stored as a long string, with each particular
                //contact information seperated by ';'
                String[] data = contactData.split(";");
                // Each row represents a contact 
                Contact c = new Contact(Long.parseLong(data[0]),data[1], data[2], data[3], data[4], data[5], data[6], Long.parseLong(data[7]));
                String[] projects = data[8].split(",");
                for(int i=0; i<projects.length; i++){
                    c.addProject(projects[i]);
                }
                String[] groups = data[9].split(",");
                for(int i=0; i<groups.length; i++){
                    c.addProject(groups[i]);
                }   
                groupMembers.add(c);
            }
        }
        cbm.setCurrentContacts(groupMembers, groupName);
    }
    
    public void viewProject(String projectName) throws IOException{
        
        ArrayList<Contact> projectParticipants = new ArrayList<>();
        socketOutput.println("Send Project Participants");
        while(!socketInput.ready()){}
        if(socketInput.readLine().equalsIgnoreCase("Send Project Name")){
            socketOutput.println(projectName);
        }
        while(true){
            //Server is sending all contacts in the specified group
            while(!socketInput.ready()){}
            String contactData = socketInput.readLine();
            if(contactData.equalsIgnoreCase("Done")){
                System.out.println("All member of "+projectName+" group received");
                break;
            }else{
                 //All the data is stored as a long string, with each particular
                //contact information seperated by ';'
                String[] data = contactData.split(";");
                // Each row represents a contact 
                Contact c = new Contact(Long.parseLong(data[0]),data[1], data[2], data[3], data[4], data[5], data[6], Long.parseLong(data[7]));
                String[] projects = data[8].split(",");
                for(int i=0; i<projects.length; i++){
                    c.addProject(projects[i]);
                }
                String[] groups = data[9].split(",");
                for(int i=0; i<groups.length; i++){
                    c.addProject(groups[i]);
                }   
                projectParticipants.add(c);
            }
        }
        cbm.setCurrentContacts(projectParticipants, projectName);    
    }    

    private ArrayList<Contact> getAllContacts() throws IOException{
        
        String rowData; 
        String input;
        allContacts = new ArrayList<>();
        socketOutput.println("Fill Rows");//sends a request to the server
        while(true){
            //Do nothing when the socket input stream is empty
            while(!socketInput.ready()){}
            //When the Socket input stream contains data from the server, read it 
            //and act accordingly
            input = socketInput.readLine();
            if(input.equalsIgnoreCase("Done")){
                break;
            }else{
                //Receives one row of data, which is one person's details
                rowData = input;
                //All the data is stored as a long string, with each particular
                //contact information seperated by ';'
                String[] data = rowData.split(";");
                // Each row represents a contact 
                Contact c = new Contact(Long.parseLong(data[0]),data[1], data[2], data[3], data[4], data[5], data[6], Long.parseLong(data[7]));
                String[] projects = data[8].split(",");
                for(int i=0; i<projects.length; i++){
                    c.addProject(projects[i]);
                }
                String[] groups = data[9].split(",");
                for(int i=0; i<groups.length; i++){
                    c.addGroup(groups[i]);
                }   
                allContacts.add(c);
            } 
            //All Contacts from the Database have been retrieved.           
        }    
        return allContacts;
    }
    
    private void getAllColumns() throws IOException{
        String input;
        socketOutput.println("Send Column names");
        columnNames = new ArrayList<>();
        while(true){
            //Do nothing when the socket input stream is empty
            while(!socketInput.ready()){}
            //When the Socket input stream contains data from the server, read it 
            //and act accordingly
            input = socketInput.readLine();
            if(input.equalsIgnoreCase("Done")){
                break;
            }else{
                //Add the column name from the server to the list of column names                
                columnNames.add(input); 
            }                        
        }    
    }
}
