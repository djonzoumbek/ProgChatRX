package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guepe
 */
public class Serveur extends Thread{
    private int nombreClient = 1;
    private boolean isActive = true;
    
    public static void main(String[] args){
        new Serveur().start();
    }
    @Override
    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(4321);
            System.out.println("Initialisation du serveur...");
            while(isActive){
                Socket socket = serverSocket.accept();
                new Conversation(socket, nombreClient).start();
                ++nombreClient;
            }
        } catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    class Conversation extends Thread{
        private Socket socketClient;
        private int numeroClient;
        public Conversation(Socket s, int num){
            this.socketClient = s;
            this.numeroClient = num;
        }
        @Override
        public void run(){
            try {
                InputStream inputStream = socketClient.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);
                
                PrintWriter pw = new PrintWriter(socketClient.getOutputStream(), true);
                String ipClient = socketClient.getRemoteSocketAddress().toString();
                System.out.println("Connexion de client ..." + numeroClient + "@IP " + ipClient);
                pw.println("Bienvenue ! vous êtes le client : " + numeroClient);
                
                while (true) {
                    String req = br.readLine();
                    pw.println(req.length());
                    
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
    }
    
    
}
