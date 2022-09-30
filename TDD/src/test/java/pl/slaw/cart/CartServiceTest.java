package pl.slaw.cart;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import pl.slaw.order.Order;
import pl.slaw.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    //CHEMY SPRAWDZIC JAKI ARGUMENT JEST PRZESYŁANY DO METODY sendToPrepare()
    //zakladamy ze nie wiemy ze jest to obiekt cart
    //JEZELI METODA MIALABY 2 ARGUMENTY TO TRZEBA STWORZYC CAPTORA DLA KAZDEGO ARGUMENTU
    @Test
    void processCartShouldSendToPrepareWithArgumentCaptor() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService= new CartService(cartHandler);

        //deklarujemy ArgumentCaptor'a
        //           typ argumentu do przechwycenia <Cart>
        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);
            //przechwycenie argumentu nastepuje w momencie weryfikacji metody
            //czylie w -> then -> potem should()

        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart= cartService.processCart(cart);

        //then - tutad dajemy obiekt argumentCaptura (zgodnie z BDD)
        //czyli kiedy wywola sie metoda sendToPrepare nastapi przechwycenie
        then(cartHandler).should().sendToPrepare(argumentCaptor.capture());

        //drugi przyklad z metodą verify
        verify(cartHandler). sendToPrepare(argumentCaptor.capture());

        //aby sprawdzic przechwycenie korzystamy z assercji
        assertThat(argumentCaptor.getValue().getOrdersCart().size(), equalTo(1));

        //JEZELI MIALABY BYC 2X WYWOLANA
//        if (cartHandler.canHandleCart(cart)) {
//            cartHandler.sendToPrepare(cart);
//            cartHandler.sendToPrepare(cart);
//        to
//        then(cartHandler).should(times(2)).sendToPrepare(argumentCaptor.capture());
//        assertThat(argumentCaptor.getAllValues().size(), equalTo(2));
//        assertThat(argumentCaptor.getAllValues().get(0).getOrders().size(), equalTo(2));

        assertThat(resultCart.getOrdersCart(), hasSize(1));
        assertThat(resultCart.getOrdersCart().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    // METODA: doNothing() -------------------------------------------------------------------------------
    @Test
    void shouldDoNothingWhenProcessCart() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService= new CartService(cartHandler);
            //jezeli met canHandleCart zwraca true to:
//        if (cartHandler.canHandleCart(cart)) {
//            cartHandler.sendToPrepare(cart); <-- to zostanie wywolane
                // ta metoda jest typu void, i nic nie robi
        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //sprawdzimy jej dzialanie - to sie przydaje jezeli np metoda bedzie wykonywana kilka razy
        // i za pierwszym razem zeby nic nie zrobila a np za drugim zeby rzucila wyjatek
        doNothing().when(cartHandler).sendToPrepare(cart);
            //lub BDD
        willDoNothing().given(cartHandler).sendToPrepare(cart);

        // jezeli wywolana np2 razy to:
        //za I razem nic nie zrobi, za II razem wywali wyjatek-- reszta tak samo
        willDoNothing().willThrow(IllegalStateException.class).given(cartHandler).sendToPrepare(cart);

//        willDoNothing().willDoNothing().willDoNothing().willThrow() ..........


        //when
        Cart resultCart= cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart);

        assertThat(resultCart.getOrdersCart(), hasSize(1));
        assertThat(resultCart.getOrdersCart().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    // metoda doAnswer - gdy chcemy cos zrobic z argumentami metody wywolywanej na mocku-----------------
    @Test
    void shouldAnswerWhenProcessCart() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService= new CartService(cartHandler);

                //doAnswer, tu mozemy dac lambe a metoda canHandleCart zwraca booleana
        doAnswer(invocationOnMock -> {
            //instancja Cart ktora bedzie argumentem
            Cart argumentCart = invocationOnMock.getArgument(0); // argument pierwszy
            argumentCart.clearCart(); // wyczyszczenie koszyka
            return true;

            // po kropce dopiero dajemy wlasciwe wywolanie metody
        }).when(cartHandler).canHandleCart(cart); // po wywolaniu tego dpojawi sie nasza
                //odpowiedz ta z góry

        // alternatywny zapis
        when(cartHandler.canHandleCart(cart)).then(i -> {
            Cart argumentCart = i.getArgument(0); // argument pierwszy
            argumentCart.clearCart(); // wyczyszczenie koszyka
            return true;
        });

        //alternatywny zapis BDD
        willAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
            // po kropce dopiero dajemy wlasciwe wywolanie metody
        }).given(cartHandler).canHandleCart(cart);

        //alternatywny krótszy zapis
        given(cartHandler.canHandleCart(cart)).will(i -> {
            Cart argumentCart = i.getArgument(0); // argument pierwszy
            argumentCart.clearCart(); // wyczyszczenie koszyka
            return true;
        });

        //when
        Cart resultCart= cartService.processCart(cart); // pusty koszyk

        //then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrdersCart().size(), equalTo(0));

    }

    //TESTOWANIE DOMYŚLNYCH METOD W INTERFEJSACH ------------------------------------
    @Test
    void deliveryShouldBeFree(){

        //given
        Cart cart = new Cart();
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());

            // robiąc moka, trzeba zmokowac tez metody na nim uzywane
        CartHandler cartHandler = mock(CartHandler.class);
            // uzycie prawdziwej metody na mocku
        given(cartHandler.isDeliveryFree(cart)).willCallRealMethod();
            //alternatywa z BDD
        doCallRealMethod().when(cartHandler).isDeliveryFree(cart);

        //when
        boolean isDeliveryFree = cartHandler.isDeliveryFree(cart);

        //then
        assertTrue(isDeliveryFree); // cartHandler to mock i skoro mamy
                //wywolac na nim prawdziwa metode to trzeba uzyc willCallRealMethod
    }

}