package contactbank;

import java.net.*;
import java.io.*;

public class ServerStartup {
    private static ServerSocket srvr ;
    private static Socket skt;
    
    public void startServer() throws IOException{
          boolean listening = true;
          try { 
            //Server is created on Port 4444  
            srvr = new ServerSocket(4444);
            System.out.print("Server is waiting for client...\n");
            //Start a thread to handle client connection request
            skt = srvr.accept();
            ServerThread st = new ServerThread(skt);
            st.start();
          }catch(Exception e) {
             System.out.print("Whoops! It didn't work!\n");
             e.printStackTrace();
          }
          // A loop to have the server continually listening for clients
          while(listening){
                new ServerThread(srvr.accept()).start();
          }
          srvr.close();
          System.out.print("Server closed...\n");  
    
    }
    
    public static void main(String args[]) throws IOException {
        ServerStartup ss = new ServerStartup();
        ss.startServer();
    }   
}
