package redes.questao_6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QuizServer {

    private static final int PORT = 12345;
    private static final List<Pergunta> perguntas = new ArrayList<>();

    // Bloco que carrega as perguntas ao iniciar o servidor
    static {
        perguntas.add(new Pergunta(
                "Quem e o pai da computacao?",
                new String[]{"1) Alan Turing", "2) Charles Babbage", "3) Ada Lovelace", "4) John von Neumann"}, 1
        ));
        perguntas.add(new Pergunta(
                "Quem ganhou o jogo do ano em 2015",
                 new String[]{"1) The Witcher 3", "2) Dark Souls II", "3) Bloodborne", "4) Fallout 4"}, 1
        ));
        perguntas.add(new Pergunta(
                "Em que dia se comemora o Halloween?",
                new String[]{"1) 2 de Julho", "2) 31 de Outubro", "3) 25 de Dezembro", "4) 14 de Fevereiro"}, 2
        ));
        perguntas.add(new Pergunta(
                "Qual o nome do primeiro satelite artificial?",
                new String[]{"1) Sputnik 1", "2) Explorer 1", "3) Luna 1", "4) Voyager 1"}, 1
        ));
    }

    public static void main(String[] args) {
        System.out.println("Servidor de Quiz iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Espera um cliente se conectar e cria um socket para ele
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                // Cria e inicia uma nova thread para cuidar deste cliente
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    // ClienteHandler é uma classe interna que implementa Runnable
    // para lidar com a comunicação com o cliente em diferentes threads

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                out.println("Bem-vindo ao Quiz! Responda com o número da opção correta.");
                int pontuacao = 0;

                // Envia cada pergunta para o cliente
                for (Pergunta pergunta : perguntas) {
                    out.println("-----------------------------------------");
                    out.println(pergunta.getEnunciado());
                    for (String opcao : pergunta.getOpcoes()) {
                        out.println(opcao);
                    }
                    // Sinaliza para o cliente que é hora de responder
                    out.println("PROMPT");

                    String clientResponse = in.readLine();
                    if (clientResponse != null) {
                        try {
                            int resposta = Integer.parseInt(clientResponse);
                            if (pergunta.isRespostaCorreta(resposta)) {
                                out.println("Feedback: Correto!");
                                pontuacao++;
                            } else {
                                out.println("Feedback: Incorreto!");
                            }
                        } catch (NumberFormatException e) {
                            out.println("Feedback: Resposta inválida. Por favor, envie apenas o número.");
                        }
                    } else {
                        // Cliente desconectou
                        break;
                    }
                }

                out.println("=========================================");
                out.println("Quiz finalizado! Sua pontuação final foi: " + pontuacao + " de " + perguntas.size());
                out.println("Obrigado por jogar!");

            } catch (IOException e) {
                System.err.println("Erro no manipulador de cliente: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Cliente desconectado: " + clientSocket.getInetAddress().getHostAddress());
                } catch (IOException e) {
                    // Ignora
                }
            }
        }
    }
}
