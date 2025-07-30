package redes.questao_7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class GuessServer {

    private static final int PORT = 12346; // Usando uma porta diferente para não conflitar

    public static void main(String[] args) {
        System.out.println("Servidor de 'Adivinhe o Número' iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Espera um cliente se conectar
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo jogador conectado: " + clientSocket.getInetAddress().getHostAddress());

                // Cria e inicia uma nova thread para cuidar do jogo deste cliente
                GameHandler gameHandler = new GameHandler(clientSocket);
                new Thread(gameHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    /**
     * GameHandler gerencia um jogo completo para um único cliente.
     * Implementa Runnable para ser executada em uma thread separada.
     */
    private static class GameHandler implements Runnable {
        private final Socket clientSocket;
        private final int numeroSecreto;
        private int tentativas;

        public GameHandler(Socket socket) {
            this.clientSocket = socket;
            // Sorteia um número entre 1 e 100 
            this.numeroSecreto = new Random().nextInt(100) + 1;
            this.tentativas = 0;
        }

        @Override
        public void run() {
            try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                out.println("Bem-vindo ao jogo! Adivinhe o número entre 1 e 100.");

                String palpiteDoCliente;
                while ((palpiteDoCliente = in.readLine()) != null) {
                    try {
                        int palpite = Integer.parseInt(palpiteDoCliente);
                        tentativas++;

                        if (palpite < numeroSecreto) {
                            out.println("maior");
                        } else if (palpite > numeroSecreto) {
                            out.println("menor");
                        } else {
                            out.println("acertou");
                            out.println("Você acertou em " + tentativas + " tentativas! Fim de jogo.");
                            break; // Encerra o loop após o jogo ser concluido
                        }
                    } catch (NumberFormatException e) {
                        out.println("Entrada inválida. Por favor, envie apenas números inteiros.");
                    }
                }

            } catch (IOException e) {
                System.err.println("Comunicação com o jogador perdida: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Jogador desconectado: " + clientSocket.getInetAddress().getHostAddress());
                } catch (IOException e) {
                    // Ignora
                }
            }
        }
    }
}