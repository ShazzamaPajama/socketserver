/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socketserver;


import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul.koroski
 */
public class ServerThread extends Thread{
    private final Socket                    UserSocket;
    private BufferedReader                  SocketReader;
    private Socketserver                    Chatserver;
    
    
    public ServerThread(Socket user, Socketserver server){
        UserSocket = user;
        Chatserver = server;
        
        
        try {
            SocketReader = new BufferedReader(new InputStreamReader(UserSocket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    @Override
    public void run(){
        System.out.println("Server Thread Started");
        this.SendMessage("Welcome to the Chat Server");
        
        String SocketString;
        try{
            while((SocketString = SocketReader.readLine()) != null){
            this.SendMessage("Someone Says: " + SocketString);
            } 
        } catch (IOException ex){
            System.out.println("Could not send message");
        }
        
        
        
    }
    
    public void SendMessage(String Message){
        Chatserver.actionPerformed(new ActionEvent(this,1,Message));
        System.out.println("Message sent");
    }
}
