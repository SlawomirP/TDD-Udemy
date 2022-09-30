package pl.slaw.cart;


import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import pl.slaw.order.Order;
import pl.slaw.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
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

    @Test
    void processCartShouldNotSendToPrepareWithArgumentMatchers() {

        // MATCHERY any(), anyString(), anyInt() ----------------------------------------------

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService= new CartService(cartHandler);

        //teraz przypadek gdy nie wiemy co zostanie wywolane na cartHandleCart
        // zamiast cart dajemy matcher z mockito any()
        given(cartHandler.canHandleCart(any())).willReturn(false);

        //lub -- i wtedy wywola na kazdym argumencie bedacym instancja klasy Cart
        given(cartHandler.canHandleCart(any(Cart.class))).willReturn(false);
            //jezeli w argumencie ma byc np jakis int to leipej uzyc anyInt()
            //zamiast ogolnego any()

        //jezeli metoda jest dwuargumentowa to wtedy przykladowo
//        given(cartHandler.canHandleCart(any(Cart.class), anyString())).willReturn(false);

        //when
        Cart resultCart= cartService.processCart(cart);

        //then - sprawdz czy po nie spelnienu warunku false, nie wykona
        //sendToPrepare
        //      nazwa mocka    metoda do weryfikacji
        verify(cartHandler, never()).sendToPrepare(any(Cart.class));
        //lub wersja BDD
        then(cartHandler).should(never()).sendToPrepare(any(Cart.class));

        assertThat(resultCart.getOrdersCart(), hasSize(1));
        assertThat(resultCart.getOrdersCart().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void canHandleCartShouldReturnMultipleValues() {

        //METODY ZWRACAJACE WIELE WARTOŚCI-------------------------------------------

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService= new CartService(cartHandler);

        // willReturn moze zwracac wiele wartosci
        //jezeli cartHandler zostanie wywolany wiecej razy to w willReturn
        //dajemy wiecej wartosci i one beda wywolywane po kolei
        given(cartHandler.canHandleCart(any())).willReturn(false,true,true,false);


        //then
       assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
    }

    //KORZYSTANIE Z LAMBD -----------------------------------
    @Test
    void processCartShouldSendToPrepareWithLambdas() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService= new CartService(cartHandler);

        //tutaj mozna skorzystac z lamby, to w srodku powinno zwracac booleana
        //metoda wywolana na moku zwroci true tylko jezeli przekazany tam argument cart bedzie mial liste ktora ma
        //rozmiar wiekszy niz 0
        given(cartHandler.canHandleCart(argThat(cartObject -> cartObject.getOrdersCart().size() > 0))).willReturn(true);

        //when
        Cart resultCart= cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrdersCart(), hasSize(1));
        assertThat(resultCart.getOrdersCart().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    //DANY MOCK MA RZUCIC WYJATKIEM

    @Test
    void canHandleCartShouldThrowException() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService= new CartService(cartHandler);

        given(cartHandler.canHandleCart(any(Cart.class))).willThrow(IllegalStateException.class);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> cartService.processCart(cart));
    }

}