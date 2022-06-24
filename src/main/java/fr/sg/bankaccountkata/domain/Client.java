package fr.sg.bankaccountkata.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
public class Client {
    private @Getter @Setter
    String firstName, lastName;
}
