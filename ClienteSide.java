// TCPClient.java

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClienteSide {
  public static void main(String[] args) throws IOException {
   
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("127.0.0.1", 12345), 1000);
    System.out.println("Connection Successful!");

    DataInputStream dataIn = new DataInputStream(socket.getInputStream());
    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
    
    dataOut.writeUTF("Olá, mensagem vinda do Cliente! 👌");

    String serverMessage = dataIn.readUTF();
    System.out.println(serverMessage);


    dataIn.close();
    dataOut.close();
    socket.close();
  }
}