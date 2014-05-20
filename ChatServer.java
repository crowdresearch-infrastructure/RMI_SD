/* Servidor Chat */

package example.chat;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class ChatServer extends UnicastRemoteObject implements IChatServer { 
	Hashtable<String, IChatClient> chatters = new Hashtable<String, IChatClient>(); 
	
	public ChatServer() throws RemoteException {}

	public synchronized void login(String name, IChatClient nc) throws RemoteException { 
		chatters.put(name,nc);
		Enumeration entChater = chatters.elements(); 
		while( entChater.hasMoreElements() ){
			((IChatClient) entChater.nextElement()).receiveEnter(name);
		}
			System.out.println("Client " + name + " has logged in");
	}

	public synchronized void logout(String name) throws RemoteException { 
		System.out.println("Client " + name + " has logged out"); 
		Enumeration entChater = chatters.elements();
		while( entChater.hasMoreElements()) {
			((IChatClient) entChater.nextElement()).receiveExit(name); 
		}
		chatters.remove(name);
	}

	public synchronized void send(Message message) throws RemoteException { 
		Enumeration entChater = chatters.elements();
		while( entChater.hasMoreElements() ) {
			((IChatClient) entChater.nextElement()).receiveMessage(message);
		} 
		System.out.println("Message from client "+message.name+":\n"+message.text);
	}

	public static void main( String[] args ){
            String downloadLocation = new String("file:/Users/IBAGNOG/Desktop/RMI/exaple/chat/"); 
            String serverURL = new String("///ChatServer");
		
		    try {
            	System.setProperty("java.rmi.server.codebase", downloadLocation); 
            	ChatServer server = new ChatServer(); 
            	//Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
            
            	Naming.rebind(serverURL, server); 
            	System.out.println("Chat server ready");
            }
            catch(Exception ex){
            	ex.printStackTrace();
            }
    }
}
