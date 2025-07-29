import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateServer {

    public static void main(String[] args) {
        
        int port = 12345;
        System.out.println("Server inciado na porta: " + port);

        try(ServerSocket serverSocket = new ServerSocket(port)){

            while(true){
                
            try {Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                Date currentDate = new Date();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formattedDate = formatter.format(currentDate);

                out.println(formattedDate);
                System.out.println("Data e hora enviada para o cliente: " + formattedDate);
                
                clientSocket.close();
                System.out.println("Conexão com o cliente fechado.");
                
            } catch(IOException e) {
                System.err.println("Não foi possível iniciar o servidor na porta " + port);
            }
                
                
            }

        } catch (IOException e) {
            System.out.println("Não foi possível inciar o servidor na porta "+ port);
            e.printStackTrace();
        }
    }
    
}
