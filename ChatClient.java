package example.chat;

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class ChatClient extends UnicastRemoteObject implements IChatClient {
      public String name;
      IChatServer server;
      String serverURL;

      public ChatClient( String name, String url ) throws RemoteException { this.name = name;
            serverURL = url;
            connect();
      }

      private void connect() {
            try {
            server=(IChatServer) Naming.lookup("rmi://"+serverURL+"/ChatServer"); server.login(name, this); // callback object
            
            }catch( Exception e ) {
                  e.printStackTrace();
            } 
      }
      private void disconnect() {
            try {
                  server.logout(name);

            }catch( Exception e ) {
                  e.printStackTrace();
            } 
      }

      private void sendTextToChat(String text) { 
            try {
                  server.send(new Message(name,text));

            }catch( RemoteException e ) {
                  e.printStackTrace();
            } 
      }

      public void receiveEnter( String name ) {
            System.out.println("\nLog in "+name+"\n"+this.name+" -- Cadena a enviar: ");
      }

      public void receiveExit( String name ) { 
            System.out.println("\nLog out " + name + "\n"); 
            if ( name.equals(this.name) )
                  System.exit(0);
            else
                  System.out.println(this.name + " -- Cadena a enviar: " );
      }

      public void receiveMessage( Message message ) { 
            System.out.println('\n'+message.name+":\n"+message.text+"\n"+name+" -- Cadena a enviar: "); 
      }

      public static String pideCadena(String letrero) { 
            StringBuffer strDato = new StringBuffer(); 
            String strCadena = "";
            try {
                  System.out.print(letrero);
                  BufferedInputStream bin = new BufferedInputStream(System.in); 
                  byte bArray[] = new byte[256];
                  int numCaracteres = bin.read(bArray);

                  while (numCaracteres==1 && bArray[0]<32)
                        numCaracteres = bin.read(bArray);
                  for(int i=0;bArray[i]!=13 && bArray[i]!=10 && bArray[i]!=0; i++) {
                        strDato.append((char) bArray[i]);
                  }
                  strCadena = new String( strDato );
            }
            catch( IOException ioe ) {
                  System.out.println(ioe.toString()); 
            }
            return strCadena;
      }

      public static void main( String[] args ) {
            String strCad;
            try {
                  ChatClient clte = new ChatClient( args[0],args[1] ); 
                  strCad = pideCadena(args[0] + " -- Cadena a enviar: "); 
                  while( !strCad.equals("quit") ) {
                        clte.sendTextToChat(strCad);
                        strCad = pideCadena(args[0]+" -- Cadena a enviar: ");
                  }
                  System.out.println("Local console "+clte.name+", going down"); 
                  clte.disconnect();
            }
            catch( RemoteException e ) {
                  e.printStackTrace();
            } 
      }
}