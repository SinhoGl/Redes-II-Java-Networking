package redes.questao_11;

import java.io.*;
import java.net.*;

public class DocumentServer {
    private static final int PORTA = 12345;
    private static final String DIRETORIO = "redes/questao_11/docs";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor aguardando conexões...");

            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                new Thread(() -> tratarCliente(cliente)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tratarCliente(Socket socket) {
        try (
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        ) {
            while(true){
                String nomeArquivo = entrada.readUTF().trim();
                File arquivo = new File(DIRETORIO + "/" + nomeArquivo);

                if (arquivo.exists() && !arquivo.isDirectory()) {
                    saida.writeBoolean(true);
                    saida.writeLong(arquivo.length());

                    try (FileInputStream fis = new FileInputStream(arquivo)) {
                        byte[] buffer = new byte[4096];
                        int bytesLidos;
                        while ((bytesLidos = fis.read(buffer)) != -1) {
                            saida.write(buffer, 0, bytesLidos);
                        }
                    }

                    System.out.println("Arquivo enviado: " + nomeArquivo);
                } else {
                    saida.writeBoolean(false);
                    saida.writeUTF("Arquivo não encontrado.");
                    System.out.println("Arquivo não encontrado: " + nomeArquivo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}
