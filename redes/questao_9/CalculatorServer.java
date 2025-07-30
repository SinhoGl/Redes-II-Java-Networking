import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class CalculatorServer {

    private static final int PORT = 12345;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor de Calculadora iniciado na porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ScriptEngineManager manager;
        private ScriptEngine engine;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.manager = new ScriptEngineManager();
            this.engine = manager.getEngineByName("js"); // Usar o motor JavaScript para avaliação de expressões
        }

        @Override
        public void run() {
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String expression;
                while ((expression = in.readLine()) != null) {
                    System.out.println("Recebido do cliente " + clientSocket.getInetAddress().getHostAddress() + ": " + expression);
                    try {
                        Object result = engine.eval(expression);
                        out.println(result.toString());
                        System.out.println("Enviado para o cliente: " + result.toString());
                    } catch (Exception e) {
                        out.println("Erro: Expressão inválida");
                        System.err.println("Erro ao avaliar expressão: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro de comunicação com o cliente " + clientSocket.getInetAddress().getHostAddress() + ": " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Cliente desconectado: " + clientSocket.getInetAddress().getHostAddress());
                } catch (IOException e) {
                    System.err.println("Erro ao fechar o socket do cliente: " + e.getMessage());
                }
            }
        }
    }
}


