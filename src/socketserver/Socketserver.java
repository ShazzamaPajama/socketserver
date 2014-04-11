/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul.koroski
 */
public class Socketserver {

    /**
     * @param args the command line arguments
     */
   
    
    public static void main(String[] args) {
        ArrayList<ServerThread> listeners = new ArrayList<>();
        
        try {
            ServerSocket test = new ServerSocket(1337);
            
            while(true){
                Socket NewClient = test.accept();
                ServerThread NewThread = new ServerThread(NewClient, listeners);
                listeners.add(NewThread);
                
                NewThread.start();
            } 
        } catch (IOException ex) {
            Logger.getLogger(Socketserver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
