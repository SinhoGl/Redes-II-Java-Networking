package redes.questao_10;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TranslatorClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)) {
            while(true){
                System.out.print("Digite uma palavra em ingles para traduzir: ");
                String word = scanner.nextLine();

                out.println(word);
                String response = in.readLine();

                System.out.println("Traducao: " + response);
            }
        } catch (IOException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        }
    }
}