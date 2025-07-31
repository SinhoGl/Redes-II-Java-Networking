import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    public static void main(String[] args) {
        final String SERVER_IP = "127.0.0.1"; // localhost
        final int SERVER_PORT = 8080;

        try {
            // 1. O cliente tenta estabelecer uma conexão com o servidor no IP e porta especificados.
            //    Se o servidor não estiver rodando e escutando na porta 8080,
            //    a linha abaixo lançará uma exceção 'ConnectionRefusedException'.
            System.out.println("Tentando conectar ao servidor TCP...");
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Conectado ao servidor TCP!");

            // 2. Recebe a mensagem do servidor
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverMessage = reader.readLine();
            System.out.println("Mensagem do servidor: " + serverMessage);

            // 3. Fecha o socket
            socket.close();

        } catch (UnknownHostException ex) {
            System.out.println("Servidor nao encontrado: " + ex.getMessage());
        } catch (IOException ex) {
            // Este é o erro esperado se o cliente for executado antes do servidor
            System.out.println("Erro de I/O (Provavelmente o servidor nao esta no ar): " + ex.getMessage());
        }
    }
}
