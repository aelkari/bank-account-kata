package fr.sg.bankaccountkata.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public final class Operation {
	private @Getter final String operationType;
	private @Getter final LocalDate date;
	private @Getter final BigDecimal amount;

}
