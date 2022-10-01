package pl.slaw.account;

import java.util.Arrays;
import java.util.List;

public class AccountRepositoryStub implements AccountRepository {

    //tutaj bedzie metoda zwracajaca przykladowe dane
    @Override
    public List<Account> getAllAccounts() {
        Address address1 = new Address("Długa", "2E/2");
        Account account1 = new Account(address1);

        Account account2 = new Account();

        Address address2 = new Address("Krótka", "3a");
        Account account3 = new Account(address2);

        return Arrays.asList(account1, account2, account3);
    }

    @Override
    public List<String> getByName(String name) {
        return null;
    }
}
