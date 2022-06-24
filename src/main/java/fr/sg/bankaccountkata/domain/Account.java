package fr.sg.bankaccountkata.domain;

import fr.sg.bankaccountkata.enums.OperationType;
import fr.sg.bankaccountkata.exception.InvalidOperationException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Account {
	private @Getter final Client client;
	private @Getter final int number;
	private final List<Operation> operations = new ArrayList<>();

	public Account(int number, Client client) {
		this.number = number;
		this.client = client;
	}
	
	public void addOperation(String operationType, LocalDate date, BigDecimal amount) throws InvalidOperationException {
		verifyOperation(operationType, amount);
		Operation operation = new Operation(operationType, date, amount);
		operations.add(operation);
	}

	private void verifyOperation(String operationType, BigDecimal amount) throws InvalidOperationException {
		if(operationType == null)
			throw new InvalidOperationException("Unspecified debitor");
		if(OperationType.DEPOSIT.toString().equals(operationType) && amount.compareTo(new BigDecimal(0)) < 0)
			throw new InvalidOperationException("Incorrect amount for a credit transaction");
		if(OperationType.WITHDRAWAL.toString().equals(operationType) && amount.compareTo(new BigDecimal(0)) > 0)
			throw new InvalidOperationException("Incorrect amount for a debit transaction");
		if(amount.compareTo(new BigDecimal(0)) < 0
				&& amount.abs().compareTo(getBalance()) > 0
				)
			throw new InvalidOperationException("Account not sufficiently provisionned : " +  getBalance() + " < " + amount);
		if(amount.compareTo(new BigDecimal(0)) == 0)
			throw new InvalidOperationException("I have better things to do with my time");

	}

	public BigDecimal getBalance() {
		BigDecimal balance = new BigDecimal(0);
		for(Operation t : operations)
			balance = balance.add(t.getAmount());
		return balance;
	}

	public List<Operation> getHistory() {
		if(!operations.isEmpty()) {
			List<Operation> sortedHistory = (List<Operation>) ((ArrayList<Operation>) operations).clone();
			sortedHistory.sort(Comparator.comparing(Operation::getDate));
			return sortedHistory;
		}
		return operations;
	}
}
