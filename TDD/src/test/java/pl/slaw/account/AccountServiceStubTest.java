package pl.slaw.account;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class AccountServiceStubTest {

    @Test
    void getAllActiveAccounts() {
        //given - w tym przykladzie w konstruktorze potrzbujemy metody
            //ktora zwróci nam dane - stworzymy taka metoda i ona zwróci nam
            // przykladowe dane ktorych uzyjemy w tym teście
           // stuba tworzymy w paczce z testami, potem wrzucamy tu
        AccountRepository accountRepositoryStub = new AccountRepositoryStub(); //instancja interfejsu
        AccountService accountService = new AccountService(accountRepositoryStub);

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(2));
    }
}