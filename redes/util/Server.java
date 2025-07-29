package redes.util;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements AutoCloseable {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    /**
     * Construtor do servidor
     * 
     * @param port Porta local na qual o servidor sera criado
     */
    public Server(int port) 
        throws IOException {
        serverSocket = new ServerSocket(port);
    }

    /**
     * Inicializa o servidor.
     * 
     * @throws IOException Exceção padrão de entrada e saida
     */
    public void openServer() 
        throws IOException {
        clientSocket = serverSocket.accept();
    }

    /**
     * Le a mensagem do cliente conectado
     * 
     * @return {@code String} Mensagem do cliente
     * @throws IOException Exceção padrão de entrada e saida
     */
    public String readClientMessage() 
        throws IOException {
        DataInputStream clientData = new DataInputStream(clientSocket.getInputStream());
        return clientData.readUTF();
    }

    /**
     * Escreve uma mensagem para o cliente
     * 
     * @param message Mensagem para o cliente
     * @throws IOException Exceção padrão de entrada e saida
     */
    public void writeMessageToClient(String message) 
        throws IOException {
        DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
        dataOut.writeUTF(message);
    }

    @Override
    public void close() 
        throws IOException {
        serverSocket.close();
    }
}
