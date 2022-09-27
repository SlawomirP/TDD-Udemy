package pl.slaw.account;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class AccountTest {

    @Test
    void newAccountShouldntBeActiveAfterCreate() {
        //given
        Account newAccount = new Account();
        //then
        assertFalse(newAccount.isActive());

        //przykład z matcherami - assertJ
//        assertThat(newAccount.isActive()).isFalse();

    }

    @Test
    void accountShouldBeActiveAfterActivation(){
        //given
        Account newAccount = new Account();
        //when
        newAccount.activate();
        //then
        assertTrue(newAccount.isActive());

        //wersja z matcherami - assertJ
//        assertThat(newAccount.isActive()).isTrue();
    }


    @Test
    void newlyCreatedAccountShouldNotHaveDefaultDeliveryAddressSet(){
        //given
        Account account = new Account();
        //when
        Address address = account.getDefaultDeliveryAddress();
        //then
        assertNull(address);

        //wersja z matcherem - assertJ
//        assertThat(address).isNull();
    }

    @Test
    void defaultDeliveryAddressShouldNotBeNullAfterBeingSet(){
        //given
        Address address = new Address("Długa", "67c");
        Account account = new Account();
        account.setDefaultDeliveryAddress(address);
        //when
        Address defaultAddress = account.getDefaultDeliveryAddress();
        //then
        assertNotNull(defaultAddress);

        //wersja z matcherem - assertJ
//        assertThat(defaultAddress).isNotNull();
    }

    //test dla wyrazenia z warunkiem
    @RepeatedTest(15) // spowoduje uruchomienie testu 5x
    @Test
    void newAccountWithNotNullAddressShouldBeActive(){

        //given
        Address address = new Address("Długa", "46/6");

        //when
        Account account = new Account(address);

        //then - warunek: jezeli adres rozny od 0 to:
                        // assertTrue i sprawdzenie isActive()
        //w przypadku gdy pierwszy warunek nie bedzie spelniony
        // to asercje sie nie uruchomią
        assumingThat(address != null, () -> {
            assertTrue(account.isActive());
        });
    }

    @Test
    void invalidEmailShouldThrowException(){
        //given
        Account account = new Account();

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> account.setEmail("wrong email"));
    }

}