package pl.slaw.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {

    @Test
    void newAccountShouldntBeActiveAfterCreate() { // test zawsze jest voidem
        Account newAccount = new Account();
        assertFalse(newAccount.isActive());

        //tutaj podajemy odwrotnie, tzn na poczatku actual a potem expected
        //przykład z matcherami - hamcrest

    }

    @Test
    void accountShouldBeActiveAfterActivation(){
        //given
        Account newAccount = new Account();
        //when
        newAccount.activate();
        //then
        assertTrue(newAccount.isActive());

        //wersja z matcherami - hamcrest

    }


    @Test
    void newlyCreatedAccountShouldNotHaveDefaultDeliveryAddressSet(){
        //given
        Account account = new Account();
        //when
        Address address = account.getDefaultDeliveryAddress();
        //then
        assertNull(address);

        //wersja z matcherem - hamcrest

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

        //wersja z matcherem - hamcrest

    }

}