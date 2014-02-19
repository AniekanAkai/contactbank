package contactbank;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    private DbControl dbController;
    private Socket skt;
    private PrintWriter out;
    private String request;
    private Statement stmt;

    ServerThread(Socket skt) {
        super("ServerThread");
        this.skt = skt;
//        dbController = new DbControl();
    }

    @Override
    public void run() {
        request = "";
        try {
            //Connecting to Contact Bank Database and prepare Statement object 
            //to use to query the DB
            dbController = new DbControl();
            dbController.connect();
            try {
                stmt = dbController.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

            } catch (SQLException ex) {
                System.err.println("Problem making Statement object");
                ex.printStackTrace();
            }

            System.out.print("Server has connected!\n ");

            //Establish communication Socket streams
            out = new PrintWriter(skt.getOutputStream(),true);
            InputStreamReader serverInput = new InputStreamReader(skt.getInputStream());
            BufferedReader srvrSktInput = new BufferedReader(serverInput);

            //Send connection success acknowledgement 
            out.println("Server connected to client");

            while (true) {
                System.out.print("Received request: ");
                while (!srvrSktInput.ready()){}
                request = srvrSktInput.readLine();
                System.out.print(request + "\n"); // Read one line and output it

                //Handling requests from the clients
                switch(request){
                    case "shut down server":
                        out.close();
                        skt.close();
                        break;
                        
                    case "Send Column names":
                        ResultSet rs = stmt.executeQuery("select column_name from information_schema.`COLUMNS` where table_name like 'all_contacts' order by ordinal_position");
                        while (rs.next()) {
                            out.println(rs.getString("column_name"));
                            System.out.println("Sending " + rs.getString("column_name"));
                        }
                        System.out.println("Sending: Done");
                        out.println("Done");
                        break;

                    case "Fill Rows":
                        sendSpecificContacts("all", "");
                        break;

                    case "Add Contact":
                        out.println("Send Details");
                        while (!srvrSktInput.ready()) {
                        }
                        addContact(srvrSktInput.readLine());
                        out.println("Done");
                        break;

                    case "Update contact":
                        out.println("Send Details");
                        while (!srvrSktInput.ready()) {
                        }
                        updateContact(srvrSktInput.readLine());
                        out.println("Done");
                        break;

                    case "Send Group Members":
                        out.println("Send Group Name");
                        while(!srvrSktInput.ready()) {}
                        sendSpecificContacts(srvrSktInput.readLine(), "groups");
                        break;

                    case "Send Project Participants":
                        out.println("Send Project Name");
                        while(!srvrSktInput.ready()) {}
                        sendSpecificContacts(srvrSktInput.readLine(), "projects");
                        System.out.println("Sending Done message");
                        break;

                    case "Remove Contact":
                        out.println("Send ID");
                        while(!srvrSktInput.ready()) {}
                        removeContact(srvrSktInput.readLine());
                        out.println("Done");
                        break;
                    
                    case "Search":
                        out.println("Send key");
                        while(!srvrSktInput.ready()) {}
                        searchTable(srvrSktInput.readLine());
                        break;
                        
                    default:
                        break;
                }
            }
        }   catch (IOException | SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchTable(String key)throws SQLException{
        String query = MessageFormat.format("SELECT * FROM contact_bank.all_contacts "
                + "where name like {0} "
                + "or position like {0} "
                + "or organization like {0} "
                + "or address like {0} "
                + "or country like {0} "
                + "or email like {0} "
                + "or phone like {0} "
                + "or projects like {0} "
                + "or groups like {0}",  new Object[]{"'%"+key+"%'"});
        //System.out.println(query);
        int i=0;
        ResultSet rowData = stmt.executeQuery(query);
        while(rowData.next()){
            //Send retreived row data to client.
            out.println(Long.toString(rowData.getLong(1)) + ";"//id
                    + rowData.getString(2) + ";"//name
                    + rowData.getString(3) + ";"//position
                    + rowData.getString(4) + ";"//organization
                    + rowData.getString(5) + ";"//address
                    + rowData.getString(6) + ";"//country
                    + rowData.getString(7) + ";"//email
                    + Long.toString(rowData.getLong(8)) + ";"//phone
                    + rowData.getString(9) + ";"//projects
                    + rowData.getString(10));//groups
            System.out.println("Sent row " + Integer.toString(i));
            i++;
        }
        out.println("Done");
    }
    
    public void addContact(String contactDetails) throws SQLException {
        String[] detail = contactDetails.split(";");
        int rs = stmt.executeUpdate("INSERT INTO contact_bank.all_contacts "
                + "(id, name, position, organization, address, country, email, phone"
                + ", projects, groups) "
                + "VALUES(" + detail[0] + ",'" + detail[1] + "','" + detail[2] + "','" + detail[3] + "','" + detail[4]
                + "','" + detail[5] + "','" + detail[6] + "'," + detail[7] + ",'" + detail[8] + "','" + detail[9] + "')");

        if (rs == 1) {
            System.out.println(detail[1] + " has been added");
        } else {
            System.err.println("Contact not added. Error!");
        }


    }

    public void updateContact(String contactDetails) throws SQLException {
        String[] detail = contactDetails.split(";");
        System.out.println("UPDATE contact_bank.all_contacts SET "
                + "name = '" + detail[1]
                + "', position = '" + detail[2]
                + "', organization = '" + detail[3]
                + "', address = '" + detail[4]
                + "', country = '" + detail[5]
                + "', email = '" + detail[6]
                + "', phone = " + detail[7]
                + ", projects = '" + detail[8]
                + "', groups = '" + detail[9]
                + "' WHERE id = " + detail[0]);
        int rs = stmt.executeUpdate("UPDATE contact_bank.all_contacts SET "
                + "name = '" + detail[1]
                + "', position = '" + detail[2]
                + "', organization = '" + detail[3]
                + "', address = '" + detail[4]
                + "', country = '" + detail[5]
                + "', email = '" + detail[6]
                + "', phone = " + detail[7]
                + ", projects = '" + detail[8]
                + "', groups = '" + detail[9]
                + "' WHERE id = " + detail[0]);
        if (rs == 1) {
            System.out.println(detail[1] + " has been updated");
        } else {
            System.err.println("Contact not updated. Error!");
        }
    }

    public void sendSpecificContacts(String key, String field) throws SQLException {

        ResultSet rowData;
        if (key.equalsIgnoreCase("all")) {
            rowData = stmt.executeQuery("SELECT * FROM all_contacts");
        } else {
            rowData = stmt.executeQuery("SELECT * FROM all_contacts"
                    + " WHERE " + field + " LIKE '%" + key + "%'");
        }

        int i = 0;
        while (rowData.next()) {
            //Send retreived row data to client.
            out.println(Long.toString(rowData.getLong(1)) + ";"//id
                    + rowData.getString(2) + ";"//name
                    + rowData.getString(3) + ";"//position
                    + rowData.getString(4) + ";"//organization
                    + rowData.getString(5) + ";"//address
                    + rowData.getString(6) + ";"//country
                    + rowData.getString(7) + ";"//email
                    + Long.toString(rowData.getLong(8)) + ";"//phone
                    + rowData.getString(9) + ";"//projects
                    + rowData.getString(10));//groups
            System.out.println("Sent row " + Integer.toString(i));
            i++;
        }
        out.println("Done");
    }

    private void removeContact(String id) throws SQLException {
        int rs = stmt.executeUpdate("DELETE FROM contact_bank.all_contacts WHERE id = " + id);
        if (rs == 1) {
            System.out.println("Contact at id =" + id + " has been removed");
        } else {
            System.err.println("Contact not removed. Error!");
        }
    }
}
