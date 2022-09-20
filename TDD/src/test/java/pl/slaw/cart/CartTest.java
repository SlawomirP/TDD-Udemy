package pl.slaw.cart;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void simulateLargeOrder() {

        //given
        Cart cart = new Cart();

        //when
        //then - przedzial czasowy  w np ms i drugi argument metoda z klasy cart
        assertTimeout(Duration.ofMinutes(100), cart::simulateLargeOrder);
    }
}