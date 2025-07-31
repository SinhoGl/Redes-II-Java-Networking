package redes.questao_11;

import java.io.*;
import java.net.*;

public class DocumentClient {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;
    private static final String DIRETORIO_DESTINO = "redes/questao_11/saves";

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(HOST, PORTA);
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        ) {
            System.out.print("Digite o nome do arquivo a ser solicitado: ");
            String nomeArquivo = teclado.readLine().trim();
            saida.writeUTF(nomeArquivo);

            boolean existe = entrada.readBoolean();

            if (existe) {
                long tamanho = entrada.readLong();
                File pastaDestino = new File(DIRETORIO_DESTINO);
                if (!pastaDestino.exists()) pastaDestino.mkdir();

                File arquivoDestino = new File(pastaDestino, nomeArquivo);
                try (FileOutputStream fos = new FileOutputStream(arquivoDestino)) {
                    byte[] buffer = new byte[4096];
                    int bytesLidos;
                    long totalLido = 0;

                    while (totalLido < tamanho && (bytesLidos = entrada.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesLidos);
                        totalLido += bytesLidos;
                    }
                }

                System.out.println("Arquivo recebido com sucesso e salvo em: " + arquivoDestino.getPath());
            } else {
                String mensagemErro = entrada.readUTF();
                System.out.println("Erro: " + mensagemErro);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

