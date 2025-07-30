package redes.questao_7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GuessClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12346;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            // Thread para ler mensagens do servidor e exibi-las
            Thread serverListener = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Servidor: " + serverMessage);
                        if (serverMessage.startsWith("Você acertou")) {
                            // Encerra o cliente quando o jogo termina
                            System.exit(0);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Conexão com o servidor perdida.");
                }
            });
            serverListener.start();

            // Loop principal para ler o palpite do usuário e enviar ao servidor
            System.out.println("Conectado! Digite seus palpites e pressione Enter.");
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
            }

        } catch (UnknownHostException e) {
            System.err.println("Host desconhecido: " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("Não foi possível conectar ao servidor: " + e.getMessage());
        }
    }
}