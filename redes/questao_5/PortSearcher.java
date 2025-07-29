package redes.questao_5;

import java.io.IOException;
import java.util.Scanner;

import redes.util.Client;

public class PortSearcher {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Digite o hostname: ");
        String hostname = in.nextLine();

        System.out.print("Digite o limite inferior: ");
        int lowerLimit = in.nextInt();

        System.out.print("Digite o limite superior: ");
        int higherLimit = in.nextInt();

        for (int port = lowerLimit; port <= higherLimit; port++) {
            try(Client client = new Client()) {
                client.connectToServer(port, hostname);
                if (client.isConnected())
                    System.out.println(String.format("Porta aberta [ %s ] no Host [ %s ]", port, hostname));
            }
            catch (IOException ignore) {}
        }

        in.close();
    }
}