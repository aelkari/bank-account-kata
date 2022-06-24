package fr.sg.bankaccountkata;

import fr.sg.bankaccountkata.domain.Operation;
import fr.sg.bankaccountkata.domain.Client;
import fr.sg.bankaccountkata.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankAccountKataApplicationTests {

    private Client client;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    public void before() {
        client = new Client("Azziz", "EL KARI");
        accountService.createAccount(client);
    }
    // US 1
    @Test
    public void deposit() {
        assertTrue(accountService.deposit(client, LocalDate.of(2022, 05, 27),
                new BigDecimal(200), "DEPOSIT"));
        assertTrue(accountService.deposit(client, LocalDate.of(2022, 06, 21),
                new BigDecimal(55), "DEPOSIT"));
    }

    // US 2
    @Test
    public void withdraw() {
        assertTrue(accountService.deposit(client, LocalDate.of(2022, 06, 20),
                new BigDecimal(150), "DEPOSIT"));
        assertFalse( accountService.withdrawal(client, LocalDate.of(2022, 06, 21),
                new BigDecimal(200), "WITHDRAW"));
    }

    // US 3
    @Test
    public void checkOperationsHistory() {
        assertTrue(accountService.deposit(client, LocalDate.of(2022, 06, 15), new BigDecimal(200), "DEPOSIT"));
        assertTrue(accountService.withdrawal(client, LocalDate.of(2022, 06, 21), new BigDecimal(40), "WITHDRAW"));
        assertTrue(accountService.deposit(client, LocalDate.of(2022, 06, 12), new BigDecimal(100), "DEPOSIT"));
        assertEquals(new BigDecimal(260), accountService.getBalance(client));
        List<Operation> accountTransactions = accountService.getOperationsHistory(client);
        assertNotNull(accountTransactions);
        assertEquals(3, accountTransactions.size());
        assertEquals(new BigDecimal(100), accountTransactions.get(0).getAmount());
        assertEquals(new BigDecimal(200), accountTransactions.get(1).getAmount());
        assertEquals(new BigDecimal(-40), accountTransactions.get(2).getAmount());
    }
}
