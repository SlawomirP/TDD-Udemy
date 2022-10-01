package pl.slaw.account;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
                // spowoduje wykonanie szczegolowego opisu błędu
public class AccountServiceTest {

    @Test
    void getAllActiveAccounts() {
        //given - w tym przykladzie zamiast stuba bedzie mock
        //w metodzie mock w argumencie dajemy nazwe klasy klasy ktorej zachowanie symulujemy
        List<Account> accounts = prepareAccountData();// do tej zmiennej przypisujemy
        //wynik dzialania metody priv

        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);

        // metoda z mockito-> when zostanie na obiekcie accountRepository wywołana
        //metoda getAllAccounts to thenReturn liste o nazwie accounts
        //       mock              nazwa metody              nasza wartosc
        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        //lub
        given(accountRepository.getAllAccounts()).willReturn(accounts);

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(2));
    }

    @Test
    void getNoActiveAccounts() {
        //given -
        List<Account> accounts = List.of();// do tej zmiennej przypisujemy

        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);

        // metoda z mockito-> when zostanie na obiekcie accountRepository wywołana
        //metoda getAllAccounts to thenReturn liste o nazwie accounts
        //       mock              nazwa metody              nasza wartosc
        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        //lub
        given(accountRepository.getAllAccounts()).willReturn(accounts);

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(0));
    }

    //tutaj sa dane ktore podstawimy do metody wywolywanej z zmockowanej klasy
    private List<Account> prepareAccountData() {
        Address address1 = new Address("Długa", "2E/2");
        Account account1 = new Account(address1);

        Account account2 = new Account();

        Address address2 = new Address("Krótka", "3a");
        Account account3 = new Account(address2);

        return Arrays.asList(account1, account2, account3);
    }

    @Test
    void getAccountsByName() {
        //given -
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);

        given(accountRepository.getByName("dawi")).willReturn(Collections.singletonList("nowak"));

        //when
        List<String> accountNames = accountService.findByName("dawid");

        //then
        assertThat(accountNames, contains("nowak"));
    }
}
