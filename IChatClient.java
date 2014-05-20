/*=========== // Interfaz Cliente Chat - para callback //======
*/
package example.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatClient extends java.rmi.Remote {
	void receiveEnter(String name) throws RemoteException;
	void receiveExit(String name)throws RemoteException;
	void receiveMessage(Message message) throws RemoteException;
}