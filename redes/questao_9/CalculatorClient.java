import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CalculatorClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado ao servidor de calculadora em " + SERVER_ADDRESS + ":" + SERVER_PORT);
            System.out.println("Digite expressões aritméticas (ex: 10+5, 7*3, 20/4). Digite 'sair' para encerrar.");

            String expression;
            while (true) {
                System.out.print("Expressão: ");
                expression = scanner.nextLine();

                if (expression.equalsIgnoreCase("sair")) {
                    break;
                }

                out.println(expression);
                String result = in.readLine();
                System.out.println("Resultado: " + result);
            }

        } catch (IOException e) {
            System.err.println("Erro de comunicação com o servidor: " + e.getMessage());
        }
    }
}


