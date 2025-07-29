package redes.questao_8;

import java.io.IOException;
import java.util.Scanner;

import redes.util.Client;

public class AccountClient {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String request;

        do {
            request = in.nextLine();
            try (Client client = new Client()) {
                client.connectToServer(12345);
                client.writeMessageToServer(request);
                System.out.println(client.getServerMessage());
            }
            catch(IOException exception) {
                System.out.println("Erro de conexao");
            }

        } while (!request.equalsIgnoreCase("X"));

        in.close();
    }
}
