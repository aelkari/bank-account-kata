package fr.sg.bankaccountkata.service;

import fr.sg.bankaccountkata.domain.Operation;
import fr.sg.bankaccountkata.domain.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author SG Account service API : Deposit and Withdrawal, Account statement
 *         (date, amount, balance) and statement printing
 */
public interface AccountService {
    void createAccount(Client client);
    boolean deposit(Client client, LocalDate date, BigDecimal amount, String operationType);
    boolean withdrawal(Client client, LocalDate date, BigDecimal amount, String operationType);
    List<Operation> getOperationsHistory(Client client);
    BigDecimal getBalance(Client client);
}
