package redes.questao_5;

import redes.util.Client;

public class Clientaadsad {
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connectToServer(12345);

        client.writeMessageToServer("hello");
        client.close();
    }
}