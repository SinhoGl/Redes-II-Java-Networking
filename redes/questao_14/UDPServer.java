import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        final int PORT = 9090;
        byte[] buffer = new byte[256]; // Buffer para receber os dados

        // 1. O servidor cria um DatagramSocket para escutar em uma porta.
        //    Ele fica aguardando pacotes chegarem.
        DatagramSocket socket = new DatagramSocket(PORT);
        System.out.println("Servidor UDP iniciado. Aguardando pacotes na porta " + PORT + "...");

        // 2. Cria um DatagramPacket para receber os dados do cliente.
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        // 3. O método receive() bloqueia até que um pacote seja recebido.
        socket.receive(packet);

        // 4. Extrai e exibe a mensagem recebida.
        String receivedMessage = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Pacote recebido do cliente: " + receivedMessage);

        // 5. Fecha o socket.
        socket.close();
        System.out.println("Servidor UDP encerrado.");
    }
}
