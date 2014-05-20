/* Interfaz Servidor Chat */

package example.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatServer extends java.rmi.Remote {
	void login(String name, IChatClient newClient) throws RemoteException; 
	void logout(String name)throws RemoteException;
	void send(Message message) throws RemoteException;
}