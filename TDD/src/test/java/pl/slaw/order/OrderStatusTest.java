package pl.slaw.order;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

class OrderStatusTest {

    //sprawdzenie pol z ENUM, czy ich dlugosc jest mniejsza od ...
    @ParameterizedTest
    @EnumSource(OrderStatus.class)
    void allOrderStatusShouldBeShorterThan15Chars(OrderStatus orderStatus){
        assertThat(orderStatus.toString().length(), lessThan(15));
    }

}