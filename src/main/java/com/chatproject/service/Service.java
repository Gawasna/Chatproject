package com.chatproject.service;
import io.socket.client.Socket;
import io.socket.client.IO;
import javax.swing.JTextArea;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
public class Service {
   private static Service instance;
   private Socket client;
   private JTextArea textArea;
   private final int PORT_NUMBER = 3307;
   private final String IF="localhost";
   public static Service getInstance() {
	   if(instance==null) {
		   instance= new Service(textArea);
	   }
	   return instance;
   }
   private Service() {
   }
   public void startServer() {
	   try {
         client=IO.socket("http://"+IP+":"+PORT_NUMBER);
	     client.open();
	   }catch(URISyntaxException e) {
		   error(e);
	   }
	     
   }
   private void error(Exception e) {
	   System.err.println(e);
   }
}
