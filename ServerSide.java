
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerSide {
  public static void main(String[] args) throws IOException {
    
  try( ServerSocket serverSocket = new ServerSocket(12345)){
      System.out.println("Sever is running, wating for users...");

      Socket clientSocket = serverSocket.accept();
      String clientSocketIP = clientSocket.getInetAddress().toString();
      int clientSocketPort = clientSocket.getPort();
      System.out.println("[IP: " + clientSocketIP + " ,Port: " + clientSocketPort +"]  " + "Client Connection Successful!");


    try(clientSocket) {
        DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());

        String clientMessage = " ";

        while(true){
          clientMessage = dataIn.readUTF();
          System.out.println("Cliente: "+ clientMessage);

          if(clientMessage.equalsIgnoreCase("sair")){
            System.out.println("Cancelando conxão.");
            break;
          }

          String serverMessage = "Mensagem '" + clientMessage + "' recebida!";
          dataOut.writeUTF(serverMessage);
          dataOut.flush();
        }
        
    } catch (IOException e){
      System.err.println("Conexão perdida com o cliente: "+ e.getMessage());
    } 
  } catch (IOException e) {
    System.err.println("Não foi possivel inciar o server: "+ e.getMessage());
  }
}
}

