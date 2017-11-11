package contactbank;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 * UI  for the application
 * @author Teddy
 */
class ContactBankView extends JFrame {

    public CBModel cbm;
    public JScrollPane contactListPane;
    public JPanel actionPanel;
    public JPanel orientationPanel;
    public JButton searchButton, addContact, saveButton, showGroupMembers, 
                    showProjectMembers, changeViewButton, removeButton,
                    refreshButton;
    public JMenuBar menuBar;
    public JMenu helpMenu, settingsMenu, editMenu;
    public JMenuItem aboutItem, helpItem, viewReadmeItem, //help menu items
                      settingsItem, advancedSettingsItem,  //settings menu items
                      createNewGroup, createNewProject, removeGroup, removeProject; //edit menu items
    public JTextField searchField;
    public JFrame cbFrame;
    public JTable contactTable;
    private MyTableModel mtm;
    
    ContactBankView(CBModel cbm) throws IOException {
        System.out.println("View has begun");
        this.cbm = cbm;
        cbFrame = new JFrame("Contact Bank");
        cbFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Search and List view toggle buttons
        orientationPanel = new JPanel();
        searchField = new JTextField("Enter key....");
        searchField.setEditable(true);
        searchField.setSize(50, 50);
//        Image img = ImageIO.read(getClass().getResource("Search-icon.png"));        
        searchButton = new JButton();
//        Image newimg = img.getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH);
//        ImageIcon icon = new ImageIcon(newimg);
//        searchButton.setIcon(icon); 
        searchButton.setToolTipText("Find a contact");
        searchButton.setSize(5, 5);
        searchButton.setActionCommand("Search");
        changeViewButton = new JButton("Toggle View");
        changeViewButton.setActionCommand("Toggle");
        orientationPanel.add(searchField);
        orientationPanel.add(searchButton);
        orientationPanel.add(changeViewButton);
        cbFrame.getContentPane().add(orientationPanel,BorderLayout.NORTH);
        
        //Contacts list panel as table
        mtm = new MyTableModel();
        contactTable = new JTable(mtm);
        contactTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        contactTable.setFillsViewportHeight(true);       
        contactListPane = new JScrollPane(contactTable);
        cbFrame.getContentPane().add(contactListPane, BorderLayout.CENTER);
        
        //Action buttons
        actionPanel = new JPanel();
        addContact = new JButton("Add Contact");
        addContact.setActionCommand("Open Add Dialog");
        saveButton = new JButton("Save List");
        saveButton.setActionCommand("Save");
        refreshButton = new JButton("Refresh");
        refreshButton.setActionCommand("Refresh");
        removeButton = new JButton("Delete");
        removeButton.setActionCommand("Remove");
        showGroupMembers = new JButton("Show Group members");
        showGroupMembers.setActionCommand("Show Group");
        showProjectMembers = new JButton("Show Project members");
        showProjectMembers.setActionCommand("Show Project");
        actionPanel.add(addContact);
        actionPanel.add(saveButton);
        actionPanel.add(removeButton);
        actionPanel.add(showGroupMembers);
        actionPanel.add(showProjectMembers);
        actionPanel.add(refreshButton, BorderLayout.EAST);
        cbFrame.getContentPane().add(actionPanel,BorderLayout.SOUTH);

        //Menu will contain options for new groups and projects.
        menuBar = new JMenuBar();
        
        helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About");
        aboutItem.setActionCommand("About");
        helpItem = new JMenuItem("Help");
        helpItem.setActionCommand("Help");
        viewReadmeItem = new JMenuItem("View Readme.pdf");
        viewReadmeItem.setActionCommand("Open Readme file");

        helpMenu.add(helpItem); helpMenu.add(viewReadmeItem); 
        helpMenu.add(aboutItem);
        
        settingsMenu = new JMenu("Settings");
        advancedSettingsItem = new JMenuItem("Advanced Settings...");
        advancedSettingsItem.setActionCommand("Advanced Settings");
        settingsItem = new JMenuItem("Settings...");
        settingsItem.setActionCommand("Settings");
        settingsMenu.add(settingsItem);settingsMenu.add(advancedSettingsItem);
        
        editMenu = new JMenu("Edit");
        createNewGroup = new JMenuItem("Create New Group...");
        createNewGroup.setActionCommand("Create Group");
        createNewProject = new JMenuItem("Create New Project...");
        createNewProject.setActionCommand("Create Project");
        removeGroup = new JMenuItem("Remove Group...");
        removeGroup.setActionCommand("Remove Group");
        removeProject = new JMenuItem("Remove Project...");
        removeProject.setActionCommand("Remove Project");
        editMenu.add(createNewGroup); editMenu.add(createNewProject);
        editMenu.add(removeGroup);editMenu.add(removeProject);
        
        menuBar.add(editMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
        
        cbFrame.setJMenuBar(menuBar);
                
        cbFrame.pack();
        cbFrame.setVisible(true);
        
        cbm.getController().listen(this);
    }
    
    public void setNewTableModel(ArrayList<Contact> c){
    	cbFrame.getContentPane().remove(contactListPane);
    	mtm = new MyTableModel(c);
        contactTable = new JTable(mtm);
        contactTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        contactTable.setFillsViewportHeight(true);       
        contactListPane = new JScrollPane(contactTable);
        cbFrame.getContentPane().add(contactListPane, BorderLayout.CENTER);
        
        cbFrame.pack();
    }
    
    public MyTableModel getTableModel(){
        return mtm;
    }
    
    public void addListener(CBController cbc){
        
        //Listener for buttons
        saveButton.addActionListener(cbc);
        addContact.addActionListener(cbc);
        searchButton.addActionListener(cbc);
        changeViewButton.addActionListener(cbc);
        refreshButton.addActionListener(cbc);
        removeButton.addActionListener(cbc);
        showGroupMembers.addActionListener(cbc);
        showProjectMembers.addActionListener(cbc);

        //Listener for MenuItems
        advancedSettingsItem.addActionListener(cbc);
        viewReadmeItem.addActionListener(cbc);
        settingsItem.addActionListener(cbc);
        aboutItem.addActionListener(cbc);
        createNewGroup.addActionListener(cbc);        
        createNewProject.addActionListener(cbc);        
        removeGroup.addActionListener(cbc);
        removeProject.addActionListener(cbc);
        
    }

class MyTableModel extends AbstractTableModel {
        private String[] columnNames;
        private Contact[] contacts;
        private Object[][] data;

        MyTableModel() {
            columnNames = new String[cbm.getColumns().size()];
            columnNames = cbm.getColumns().toArray(columnNames);
            contacts = new Contact[cbm.getCurrentContacts().size()];
            contacts = cbm.getCurrentContacts().toArray(contacts);
            data = new Object[cbm.getCurrentContacts().size()][cbm.getColumns().size()];
            
            for(int i=0; i<=contacts.length/2;i++){
                if(i==data.length-1){
                    Arrays.copyOf(data, data.length+100);
                }
                data[i][0] = contacts[i].getID();
                data[i][1] = contacts[i].getName();
                data[i][2] = contacts[i].getPosition(); 
                data[i][3] = contacts[i].getCompany();
                data[i][4] = contacts[i].getAddress();
                data[i][5] = contacts[i].getCountry(); 
                data[i][6] = contacts[i].getEmail();
                data[i][7] = contacts[i].getPhoneNo(); 
                data[i][8] = contacts[i].getProjectList().toString(); 
                data[i][9] = contacts[i].getGroupList().toString();

                data[contacts.length-i-1][0] = contacts[contacts.length-i-1].getID();
                data[contacts.length-i-1][1] = contacts[contacts.length-i-1].getName();
                data[contacts.length-i-1][2] = contacts[contacts.length-i-1].getPosition(); 
                data[contacts.length-i-1][3] = contacts[contacts.length-i-1].getCompany();
                data[contacts.length-i-1][4] = contacts[contacts.length-i-1].getAddress();
                data[contacts.length-i-1][5] = contacts[contacts.length-i-1].getCountry(); 
                data[contacts.length-i-1][6] = contacts[contacts.length-i-1].getEmail();
                data[contacts.length-i-1][7] = contacts[contacts.length-i-1].getPhoneNo(); 
                data[contacts.length-i-1][8] = contacts[contacts.length-i-1].getProjectList().toString(); 
                data[contacts.length-i-1][9] = contacts[contacts.length-i-1].getGroupList().toString();                                           
            }   
        }
        
        MyTableModel(ArrayList<Contact> c){
            columnNames = new String[cbm.getColumns().size()];
            columnNames = cbm.getColumns().toArray(columnNames);
            contacts = new Contact[c.size()];
            contacts = c.toArray(contacts);
            data = new Object[c.size()][columnNames.length];
            if(contacts.length>=1){
                for(int i=0; i<=(int)contacts.length/2;i++){
                    if(i==data.length-1){
                        Arrays.copyOf(data, data.length+100);
                    }
                    data[i][0] = contacts[i].getID();
                    data[i][1] = contacts[i].getName();
                    data[i][2] = contacts[i].getPosition(); 
                    data[i][3] = contacts[i].getCompany();
                    data[i][4] = contacts[i].getAddress();
                    data[i][5] = contacts[i].getCountry(); 
                    data[i][6] = contacts[i].getEmail();
                    data[i][7] = contacts[i].getPhoneNo(); 
                    data[i][8] = contacts[i].getProjectList().toString(); 
                    data[i][9] = contacts[i].getGroupList().toString();

                    data[contacts.length-i-1][0] = contacts[contacts.length-i-1].getID();
                    data[contacts.length-i-1][1] = contacts[contacts.length-i-1].getName();
                    data[contacts.length-i-1][2] = contacts[contacts.length-i-1].getPosition(); 
                    data[contacts.length-i-1][3] = contacts[contacts.length-i-1].getCompany();
                    data[contacts.length-i-1][4] = contacts[contacts.length-i-1].getAddress();
                    data[contacts.length-i-1][5] = contacts[contacts.length-i-1].getCountry(); 
                    data[contacts.length-i-1][6] = contacts[contacts.length-i-1].getEmail();
                    data[contacts.length-i-1][7] = contacts[contacts.length-i-1].getPhoneNo(); 
                    data[contacts.length-i-1][8] = contacts[contacts.length-i-1].getProjectList().toString(); 
                    data[contacts.length-i-1][9] = contacts[contacts.length-i-1].getGroupList().toString();                                           
                }       
            }
        }
 
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
 
        @Override
        public int getRowCount() {
            return data.length;
        }
 
        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }
 
        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
 
        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
 
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }
 
        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        @Override
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
 
    }    
    
}
