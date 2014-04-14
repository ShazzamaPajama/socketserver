/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socketserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul.koroski
 */
public class Socketserver implements ActionListener{
    private final ArrayList<PrintWriter>  writers = new ArrayList<>();
    ServerSocket chatserver;
    public Socketserver(){
        try {
            chatserver = new ServerSocket(1337);
            System.out.println("Server Started");
 
        } catch (IOException ex) {
            Logger.getLogger(Socketserver.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not start server");
        }
    }    
        
    public void acceptClients(){
        try{
            while(true){
                Socket NewUser = chatserver.accept();
                
                PrintWriter NewWriter = new PrintWriter(NewUser.getOutputStream(), true);
                writers.add(NewWriter);
                
                new ServerThread(NewUser, this).start();
                System.out.println("New User Connected");
            }
        } catch(IOException ex){
            System.out.println("Could not accept new client");
        }  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (PrintWriter i : writers){
            i.println(e.getActionCommand());
            System.out.println("User sent: " + e.getActionCommand());
        }
    }
    
}
