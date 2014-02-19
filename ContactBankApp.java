package contactbank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ContactBankApp {
        
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader socketInput;
        
    public ContactBankApp(){        
         try {
                System.out.println("Connecting to server...");
                //Requesting connection to server at port 4444 
                socket = new Socket("localhost", 4444);
                //Used to write to the socket
                out = new PrintWriter(socket.getOutputStream(), true);
                //used to read from the socket
                socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                //While the socket is empty do nothing
                while(!socketInput.ready()){}
                //Reading from socket and storing value in 'input'
                String input = socketInput.readLine();

                // If ACK msg received. Client may request of server
                if(input.equals("Server connected to client")){
                   System.out.println("Socket Connection successful. Wait for client request");
                }
                
              } catch(Exception e) {
                System.out.print("Whoops! It didn't work!\n");
                e.printStackTrace();
              }  
    }
        
        
    public Socket getSocketStream(){
        return socket;
    }
    
    public PrintWriter getOuputStream(){
        return out;
    }

    public BufferedReader getInputStream(){
        return socketInput;
    }

    public static void main(String[] args) throws IOException{
        
        ContactBankApp cba = new ContactBankApp();
        //Start the controller which request all contacts list
        //List is received by Model which calls the UI(view) and updates the view
        CBController cbc;
        cbc = new CBController(cba); 
    }
}
