// TCPServer.java

import java.net.*;

import javax.xml.crypto.Data;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerSide {
  public static void main(String[] args) throws IOException {
    // Here, we create a Socket instance named socket
    ServerSocket serverSocket = new ServerSocket(12345);
    System.out.println("Listening for clients...");
    Socket clientSocket = serverSocket.accept();
    String clientSocketIP = clientSocket.getInetAddress().toString();
    int clientSocketPort = clientSocket.getPort();
    System.out.println("[IP: " + clientSocketIP + " ,Port: " + clientSocketPort +"]  " + "Client Connection Successful!");


    DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());
    DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());

    String clientMessage = dataIn.readUTF();
    System.out.println(clientMessage);
    String serverMessage = "Opa, mensagem enviada do Server!";
    dataOut.writeUTF(serverMessage);


    dataIn.close();
    dataOut.close();
    serverSocket.close();
    clientSocket.close();
  }
}

