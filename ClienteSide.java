// TCPClient.java

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSide {
  public static void main(String[] args) throws IOException {
   
    try(Socket socket = new Socket();
        Scanner scanner = new Scanner(System.in)){
   
   
    socket.connect(new InetSocketAddress("127.0.0.1", 12345), 1000);
    System.out.println("Connection Successful!, type 'sair' to stop conncetion.");

    DataInputStream dataIn = new DataInputStream(socket.getInputStream());
    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

    String messageToSend = " ";

    while(!messageToSend.equals("sair")){ 
      System.out.println("Você: ");   
      messageToSend = scanner.nextLine();
      dataOut.writeUTF(messageToSend);
      dataOut.flush();

  
      if(!messageToSend.equalsIgnoreCase("sair")) {
        String serverMessage = dataIn.readUTF();
        System.out.println(serverMessage);
      }
    }

    System.out.println("Encerrando conexão.");
    
  } catch (IOException e){
    System.err.println("Error de conexão com o servidor: " + e.getMessage());
  }
}
}