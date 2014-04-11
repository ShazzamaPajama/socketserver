/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socketserver;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul.koroski
 */
public class ServerThread extends Thread implements ActionListener{
    private final Socket                    UserSocket;
    private PrintWriter                     UserOut;
    private BufferedReader                  UserIn;
    private ArrayList<ServerThread>         Listeners;
    
    public ServerThread(Socket test, ArrayList<ServerThread> list){
        UserSocket = test;
        
        try {
            UserOut = new PrintWriter(UserSocket.getOutputStream());
            UserIn = new BufferedReader(new InputStreamReader(UserSocket.getInputStream()));
            
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not connect to Socket stream");
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserOut.println("Someone Says: " + e.getActionCommand());
    }

    @Override
    public void run(){
        UserOut.println("Welcome to the Chatroom. Please Chat.");
        String UserInput;
        
        try {
            while((UserInput = UserIn.readLine()) != null){
                this.NotifyListeners(UserInput);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void NotifyListeners(String Message){
        for (ServerThread i : Listeners){
            i.actionPerformed(new ActionEvent(this,1,Message));
        }
    }
}
