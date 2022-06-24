package fr.sg.bankaccountkata.service.impl;

import fr.sg.bankaccountkata.domain.*;
import fr.sg.bankaccountkata.exception.InvalidOperationException;
import fr.sg.bankaccountkata.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final List<Account> accounts = new ArrayList<>();

    public AccountServiceImpl() {
    }

    /**
     * @param client
     */
    @Override
    public void createAccount(Client client) {
        accounts.add(new Account(accounts.size()+1, client));
    }

    /**
     * Make a deposit in my account
     * @param client
     * @param date
     * @param amount
     * @param operationType
     * @return boolean
     */
    @Override
    public boolean deposit(Client client, LocalDate date, BigDecimal amount, String operationType) {
        Optional<Account> account = getAccount(client);
        if(account.isEmpty())
            return false;
        try {
            account.get().addOperation(operationType, date,amount);
            return true;
        }
        catch(InvalidOperationException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Make a withdrawal from my account
     * @param client
     * @param date
     * @param amount
     * @param operationType
     * @return boolean
     */
    @Override
    public boolean withdrawal(Client client, LocalDate date, BigDecimal amount, String operationType) {
        Optional<Account> account = getAccount(client);
        if(account.isEmpty())
            return false;
        try {
            account.get().addOperation(operationType, date, amount.negate());
            return true;
        }
        catch(InvalidOperationException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * @param client
     * @return BigDecimal
     */
    @Override
    public BigDecimal getBalance(Client client) {
        Optional<Account> account = getAccount(client);
        if(account.isEmpty())
            return new BigDecimal(0);
        return account.get().getBalance();
    }

    /**
     * See the history (operation, date, amount, balance) of my operations
     * @param client
     * @return List<Operation>
     */
    @Override
    public List<Operation> getOperationsHistory(Client client) {
        Optional<Account> account = getAccount(client);
        return account.map(Account::getHistory).orElse(null);
    }

    /**
     * @param client
     * @return Optional<Account>
     */
    protected Optional<Account> getAccount(Client client) {
        if(client == null)
            return Optional.empty();
        return accounts.stream().filter(account -> client.equals(account.getClient())).findAny();
    }
}
