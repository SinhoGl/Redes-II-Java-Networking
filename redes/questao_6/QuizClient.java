package redes.questao_6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class QuizClient {

    private static final String SERVER_ADDRESS = "localhost"; // Endereço do servidor
    private static final int SERVER_PORT = 12345; // Porta do servidor

    public static void main(String[] args) {
        // Usa try-with-resources para garantir que o socket e os leitores/escritores sejam fechados
        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Conectado ao servidor de Quiz. Aguardando perguntas...");

            String serverMessage;
            // Lê mensagens do servidor
            while ((serverMessage = in.readLine()) != null) {
                // Se a mensagem for o nosso sinal "PROMPT", é hora do usuário digitar
                if ("PROMPT".equals(serverMessage)) {
                    System.out.print("Sua resposta: ");
                    String userInput = stdIn.readLine();
                    if (userInput != null) {
                        out.println(userInput);
                    }
                } else {
                    // Se não, apenas imprime a mensagem do servidor (pergunta, feedback, etc.)
                    System.out.println(serverMessage);
                }
            }
            System.out.println("Conexão com o servidor foi encerrada.");

        } catch (UnknownHostException e) {
            System.err.println("Host desconhecido: " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("Não foi possível conectar ao servidor: " + e.getMessage());
        }
    }
}