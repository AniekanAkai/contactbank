package contactbank;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * @author anearcan
 */
public class Contact {
    private String name;
    private String company;
    private String position;
    private String email;
    private long phoneNo;
    private String address;
    private String country;
    private ArrayList <String> project;
    private ArrayList <String> groups;
    private long id;
    
    Contact(){
        name = "";
        company = "";
        position = "";
        email = "";
        phoneNo = 0;
        address = "";
        country = "";
        project = new ArrayList<String>();
        groups = new ArrayList<String>();
        id = 0;
    }
    
    Contact(long id, String name, String position, String company, String address, String country, String email, long phone){
        this.name = name;
        this.company = company;
        this.position = position;
        this.email = email;
        phoneNo = phone;
        this.address = address;
        this.country = country;
        project = new ArrayList<String>();
        groups = new ArrayList<String>();
        this.id = id;
    }
    
    public void setID(long id){
        this.id = id;
    }
    
    public long getID(){
        return id;
    }
    
    void addGroup(String groupName){
        groups.add(groupName); 
    }
    
    void removeGroup(String groupName){
        groups.remove(groupName);
    }
    
    void addProject(String projectName){
        project.add(projectName); 
    }
    
    void removeProject(String projectName){
        project.remove(projectName);
    }
    
    void setName(String name){
        this.name = name;
    }
    
    String getName(){return this.name;}
    
    void setCompany(String company){
        this.company = company;
    }
    String getCompany(){return this.company;}
    
    void setPosition(String position){
        this.position = position;
    }
    String getPosition(){return this.position;}

    void setEmail(String email){
            this.email = email;
    }
    String getEmail(){return this.email;}

    void setAddress(String address){
        this.address = address;
    }
    String getAddress(){return this.address;}
    
    void setCountry(String country){
        this.country = country;
    }
    String getCountry(){return this.country;}
    
    void setPhoneNo(long phone){
        phoneNo = phone;
    }
    Long getPhoneNo(){return phoneNo;}

    ArrayList<String> getProjectList(){return project;}

    ArrayList<String> getGroupList(){return groups;}

    String stringToDB() {
        String pString="", gString="";
        Iterator<String> i = project.iterator();
        while(i.hasNext()){
            pString+=(i.next() +",");
        }
        i = groups.iterator();
        while(i.hasNext()){
            gString+=(i.next() +",");
        }
        
        String string = Long.toString(id)+";"+name+";"+position+";"+company
                +";"+address+";"+country+";"+email+";"+Long.toString(phoneNo)
                +";"+pString+";"+gString;        
        return string;
    }

    @Override
    public String toString() {
        String pString="", gString="";
        Iterator<String> i = project.iterator();
        while(i.hasNext()){
            pString+=(i.next() +",");
        }
        i = groups.iterator();
        while(i.hasNext()){
            gString+=(i.next() +",");
        }
        
        String string = Long.toString(id)+";"+name+";"+position+";"+company
                +";"+address+";"+country+";"+email+";"+Long.toString(phoneNo)
                +";"+pString+";"+gString;        
        return string;
    }
}
