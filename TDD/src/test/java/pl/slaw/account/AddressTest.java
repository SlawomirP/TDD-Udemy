package pl.slaw.account;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @ParameterizedTest
    @CsvSource({"Długa,10", "Armii Krajowej, 57/11"}) // tu dajemy cale zestawy danych
    void givenAddressesShouldntBeEmptyAndHaveProperNames(String street, String number){
        assertThat(street, notNullValue());
        assertThat(street, containsString("a"));
        assertThat(number, notNullValue());
        assertThat(number.length(), lessThan(8));

    }

    //test bioracy wartosci z pliku csv
    @Disabled
    @ParameterizedTest
    @CsvFileSource(resources = "/addresses.csv")
    void addressesFromFileShouldntBeEmptyAndHaveProperNames(String street, String number){
        assertThat(street, notNullValue());
        assertThat(street, containsString("street"));
        assertThat(number, notNullValue());
        assertThat(number.length(), lessThan(8));

    }

}