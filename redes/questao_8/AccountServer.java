package redes.questao_8;

import java.math.BigDecimal;

import redes.util.Server;

public class AccountServer {
    public static void main(String[] args) 
        throws Exception {

        Account account = new Account();
        Server server = new Server(12345);

        String[] clientMessage;
        String command;
        BigDecimal value;
        do {
            server.openServer();
            String messageToClient;
            clientMessage = server.readClientMessage().split("\s+");
            command = clientMessage[0];

            switch (command) {

                case "x":
                    messageToClient = "Saindo da conta";
                    break;

                case "saldo":
                    messageToClient = String.format("Saldo atual: R$%s", account.getBalance().setScale(2));
                break;

                case "deposito":
                    try {
                        value = BigDecimal.valueOf(Double.parseDouble(clientMessage[1]));
                        account.deposit(value);
                        messageToClient = String.format("Deposito de R$%s realizado", value.setScale(2));
                    }
                    catch(NumberFormatException exception) {
                        messageToClient = "Valor nao reconhecido, por favor tente novamente";
                    }
                    break;

                case "saque":
                    try {
                        value = BigDecimal.valueOf(Double.parseDouble(clientMessage[1]));
                        account.withdraw(value);
                        messageToClient = String.format("Saque de R$%s realizado", value.setScale(2));
                    }
                    catch(InsufficientBalanceException exception) {
                        messageToClient = exception.getMessage();
                    }
                    catch(NumberFormatException exception) {
                        messageToClient = "Valor nao reconhecido, por favor tente novamente";
                    }
                    break;

                default:
                    messageToClient = "Comando nao reconhecido, por favor tente novamente";
                    break;
            }
            server.writeMessageToClient(messageToClient);

        } while(!command.equalsIgnoreCase("X"));

        server.close();
    }


}
