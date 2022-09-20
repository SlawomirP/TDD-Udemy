package pl.slaw.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.slaw.order.Order;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@DisplayName("Tests for Cart class") // można umieścic nad metoda lub nad nazwa klasy
class CartTest {

//    @Disabled          // powoduje zignowrowanie testu nad ktorym sie znajduje
    // umieszczając to nad nazwą klasy to ignorowane sa testy z całej klasy
    @Test
    @DisplayName("Sample text")
    void simulateLargeOrder() {

        //given
        Cart cart = new Cart();

        //when
        //then - przedzial czasowy  w np ms i drugi argument metoda z klasy cart
        assertTimeout(Duration.ofMinutes(100), cart::simulateLargeOrder);
    }

    @Test
    void cartShouldntBeEmptyAfterAddingOrderToCart(){
        Order order = new Order();
        Cart cart = new Cart();

        //when
        cart.addOrderToCart(order);

        //then - przypadek alternatywy - anyOf oznacza ze jeden z nich wystarczy
        //zeby sie zgadzal i test przejdzie
        assertThat(cart.getOrdersCart(),anyOf( // teraz dajemy warunki
                notNullValue(),
                hasSize(1),
                is(not(empty())),
                is(not(emptyCollectionOf(Order.class)))
        ));

        // matcher allOf -> asercja bedzie spelniona jezeli wszystkie warunki
        //beda spelnione -> tylko !!!! all
        //jezeli jakies asercje nie przejda to wypisze tylko pierwsza z nich
        assertThat(cart.getOrdersCart(),allOf( // teraz dajemy warunki
                notNullValue(),
                hasSize(1),
                is(not(empty())),
                is(not(emptyCollectionOf(Order.class)))
        ));

        //JUnit5 - tylko -- mozna dawac calkiem dowolne asercje  a nie tak jak wyzej ze
        // bylo cart.getOrdersCart() a potem warunek
        //jezeli jakies asercje nie przejda to wypisze wszystkie
        assertAll(
                () -> assertThat(cart.getOrdersCart(), notNullValue()),
                () -> assertThat(cart.getOrdersCart(), hasSize(1)),
                () -> assertThat(cart.getOrdersCart(), is(not(empty()))),
                () -> assertThat(cart.getOrdersCart(), is(not(emptyCollectionOf(Order.class)))),
                () -> assertThat(cart.getOrdersCart().get(0).getMeals(), empty())
        );

    }
}