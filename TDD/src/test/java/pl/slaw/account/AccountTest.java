package pl.slaw.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {

    @Test
    void newAccountShouldntBeActiveAfterCreate() { // test zawsze jest voidem
        //obiekt klasy testowanej
        Account newAccount = new Account();
        // assercja -dajemy to co chcemy sprawdzic czyli
        // czy newAccount jest active
        assertFalse(newAccount.isActive());
    }

    @Test
    void accountShouldBeActiveAfterActivation(){
        //given
        Account newAccount = new Account();
        //when
        //sprawdzenie czy po aktywacji zmieni sie stan
        newAccount.activate();
        //then
        assertTrue(newAccount.isActive());
    }

    //chcemy zeby nowoutworzone account mialo adres null na poczatku
    //sprawdzimy czy tak bedzie
    @Test
    void newlyCreatedAccountShouldNotHaveDefaultDeliveryAddressSet(){
        //given
        Account account = new Account();

        //when - tworzymy nowy adres do ktorego przypisujemy adres z
        //nowo utworzonego obiektu account
        Address address = account.getDefaultDeliveryAddress();

        //then
        assertNull(address);

    }

    //sprawdzenie czy obiekt nie jest nullem
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

    }






}