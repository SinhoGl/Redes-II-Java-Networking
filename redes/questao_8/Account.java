package redes.questao_8;

import java.math.BigDecimal;

public class Account {

    private BigDecimal balance;

    public Account () {
        balance = BigDecimal.ZERO;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }

    public BigDecimal withdraw(BigDecimal balance) {
        BigDecimal newBalance = this.balance.subtract(balance);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0)
            throw new InsufficientBalanceException("Saldo insuficiente!");
        this.balance = newBalance;
        return balance;
    }
}
