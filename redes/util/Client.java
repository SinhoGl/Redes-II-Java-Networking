package redes.util;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements AutoCloseable {

    private Socket socket;

    public Client() {
        socket = new Socket();
    }

    /**
     * Conecta com o servidor da porta indicada
     * 
     * @param port Porta do servidor
     * @throws IOException Exceção padrão de entrada e saida
     */
    public void connectToServer(int port) 
        throws IOException {
        socket.connect(new InetSocketAddress("127.0.0.1", port), 1000);
        System.out.println("Conexao com o servidor realizada com sucesso!");
    }

    /**
     * Escreve uma mensagem para o servidor
     * 
     * @param message Mensagem para o servidor
     * @throws IOException Exceção padrão de entrada e saida
     */
    public void writeMessageToServer(String message) 
        throws IOException {
        DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
        dataOut.writeUTF(message);
    }

    
    /**
     * Le a mensagem do servidor conectado
     * 
     * @return {@code String} Mensagem do servidor
     * @throws IOException Exceção padrão de entrada e saida
     */
    public String getServerMessage() 
        throws IOException {
        DataInputStream dataIn = new DataInputStream(socket.getInputStream());
        return dataIn.readUTF();
    }

    @Override
    public void close() 
        throws IOException {
        socket.close();
    }
}
