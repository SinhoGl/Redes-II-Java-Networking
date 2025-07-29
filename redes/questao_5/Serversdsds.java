package redes.questao_5;

import redes.util.Server;

public class Serversdsds {
    public static void main(String[] args) throws Exception {
        Server server = new Server(12345);
        server.openServer();
        System.out.println(server.readClientMessage());
        server.close();
    }
}
