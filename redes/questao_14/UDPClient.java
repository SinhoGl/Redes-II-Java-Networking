import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        final String SERVER_IP = "127.0.0.1";
        final int SERVER_PORT = 9090;

        // 1. O cliente cria um DatagramSocket. Não é necessário se conectar.
        DatagramSocket socket = new DatagramSocket();
        System.out.println("Cliente UDP iniciado.");

        String message = "Olá, servidor! Este é um pacote UDP.";
        byte[] buffer = message.getBytes();

        // 2. O cliente cria um pacote (DatagramPacket) com a mensagem,
        //    o IP e a porta do servidor de destino.
        InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, SERVER_PORT);

        // 3. O cliente simplesmente envia o pacote.
        //    Esta operação NÃO lança um erro se o servidor estiver offline.
        //    O cliente não sabe se o pacote foi recebido ou não.
        socket.send(packet);
        System.out.println("Pacote UDP enviado para " + SERVER_IP + ":" + SERVER_PORT);

        // 4. Fecha o socket.
        socket.close();
        System.out.println("Cliente UDP encerrado.");
    }
}
