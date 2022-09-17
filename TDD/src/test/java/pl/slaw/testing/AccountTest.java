package pl.slaw.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {

    @Test
    void myTest() { // test zawsze jest voidem
        //obiekt klasy testowanej
        Account newAccount = new Account();
        // assercja -dajemy to co chcemy sprawdzic czyli
        // czy newAccount jest active
        assertFalse(newAccount.isActive());
    }

    @Test
    void myTest2(){
        Account newAccount = new Account();
        assertFalse(newAccount.isActive());
        //sprawdzenie czy po aktywacji zmieni sie stan
        newAccount.activate();
        assertTrue(newAccount.isActive());
    }
}