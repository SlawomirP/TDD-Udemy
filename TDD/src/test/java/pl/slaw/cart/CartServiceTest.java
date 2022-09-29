package pl.slaw.cart;


import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import pl.slaw.order.Order;
import pl.slaw.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CartServiceTest {

    @Test
    void processCartShouldSendToPrepare() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService= new CartService(cartHandler);

        //           ta metoda                  zwroci        true
        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart= cartService.processCart(cart);

        //then - sprawdz czy po spelnienu warunku true wykona
        // metode sendToPrepare
        //      nazwa mocka    metoda do weryfikacji
        verify(cartHandler).sendToPrepare(cart);
        //lub wersja BDD
        then(cartHandler).should().sendToPrepare(cart);

        verify(cartHandler, times(1)).sendToPrepare(cart);

        verify(cartHandler, atLeastOnce()).sendToPrepare(cart);

        //sprawdzenie kolejności wywolania metod na moku
        InOrder inOrder = Mockito.inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCart(cart);
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(resultCart.getOrdersCart(), hasSize(1));
        assertThat(resultCart.getOrdersCart().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void processCartShouldNotSendToPrepare() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService= new CartService(cartHandler);

        //           ta metoda                  zwroci        false
        given(cartHandler.canHandleCart(cart)).willReturn(false);

        //when
        Cart resultCart= cartService.processCart(cart);

        //then - sprawdz czy po nie spelnienu warunku false, nie wykona
        //sendToPrepare
        //      nazwa mocka    metoda do weryfikacji
        verify(cartHandler, never()).sendToPrepare(cart);
        //lub wersja BDD
        then(cartHandler).should(never()).sendToPrepare(cart);

        assertThat(resultCart.getOrdersCart(), hasSize(1));
        assertThat(resultCart.getOrdersCart().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }
}