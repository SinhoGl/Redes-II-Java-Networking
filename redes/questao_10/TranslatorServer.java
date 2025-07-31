package redes.questao_10;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class TranslatorServer {

    private static final int PORT = 12345;

    public static void main(String[] args) {
        //HashMap com alguns exemplos e seus correspondentes
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("hello", "oi");
        dictionary.put("world", "mundo");
        dictionary.put("dog", "cachorro");
        dictionary.put("cat", "gato");
        dictionary.put("house", "casa");
        dictionary.put("car", "carro");
        dictionary.put("juice", "suco");
        dictionary.put("sun", "sol");
        dictionary.put("moon", "lua");
        dictionary.put("computer", "computador");
        dictionary.put("champion", "Bahia");

        try(ServerSocket serverSocket = new ServerSocket(PORT)){

            System.out.println("Servidor de traducao iniciando na porta: "+PORT);

            while(true){

                try (Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    while(true){
                        String englishWord = in.readLine();
                        System.out.println("Recebido do cliente: " + englishWord);

                        String translation = dictionary.getOrDefault(englishWord.toLowerCase(), "Traducao nao encontrada.");
                        out.println(translation);
                    }
                } catch (IOException e) {  //Excessao caso nao haja comunicacao com cliente
                    System.err.println("Erro ao comunicar com o cliente: " + e.getMessage());
                }

            }

        } catch(IOException e){  //Excessao caso nao haja conexao estabelecida
            System.out.println("Erro ao iniciar o servidor.");
        }

    }
    
}
