import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        final int PORT = 8080;

        // 1. O servidor cria um ServerSocket para escutar em uma porta específica.
        //    Isso DEVE acontecer antes que qualquer cliente tente se conectar.
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor TCP iniciado. Aguardando cliente na porta " + PORT + "...");

        // 2. O método accept() bloqueia a execução até que um cliente se conecte.
        //    Se o servidor não chegar a este ponto, a conexão do cliente será recusada.
        Socket clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

        // 3. Comunicação com o cliente
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("Ola, cliente! Conexao TCP estabelecida com sucesso.");

        // 4. Fecha os recursos
        clientSocket.close();
        serverSocket.close();
        System.out.println("Servidor TCP encerrado.");
    }
}
