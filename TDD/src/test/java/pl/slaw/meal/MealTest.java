package pl.slaw.meal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MealTest {

    //TESTY SPARAMETRYZOWANE ------możemy uzywac parametrów-------

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 19})
        // tu podajemy z ktorych bedziemy korzystac
        // test zostanie wykonany dla 5,10,15,19
    void mealPrizesShouldBeLowerThan20(int price) {
        assertThat(price, lessThan(20));
    }

    //metoda pomocnicza dajaca stream
    private static Stream<Arguments> createMealsWithNameAndPrice() {
        return Stream.of(
                Arguments.of("Hamburger", 10),
                Arguments.of("CheeseHamburger", 12)
        );
    }

    //test dla wyniku dzialanie metody
    @ParameterizedTest
    @MethodSource("createMealsWithNameAndPrice")
        // nazwa metody ktora zworci strumien argumentow
    void burgerShouldHaveCorrectNameAndPrice(String name, int price) {
        assertThat(name, containsString("burger"));
        assertThat(price, greaterThanOrEqualTo(10));
    }

    //prostszy test dla wyniku dzialania metody
    private static Stream<String> createCakeNames(){
        List<String> cakeNames = Arrays.asList("Cheesecake", "Fruitcake", "Cupcake");
        return cakeNames.stream();
    }
    @ParameterizedTest
    @MethodSource("createCakeNames")
    void cakeNamesShouldEndWithCakes(String name){
        assertThat(name, notNullValue());
        assertThat(name, endsWith("cake"));
    }


    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(35);

        //when
        int discountedPrice = meal.getDiscountedPrice(7);

        //then
        assertEquals(28, discountedPrice);

        //wersja z matcherem - assertJ
//        assertThat(discountedPrice).isEqualTo(28);
    }

    //porównanie obiektów, najpierw referencji
    @Test
    void referencesToTheSameObjectShouldBeEqual() {
        //given
        Meal meal1 = new Meal(10);
        Meal meal2 = meal1;

        //then
        assertSame(meal1, meal2);

        //z matcherem - assertJ
//        assertThat(meal1).isSameAs(meal2);
    }

    @Test
    void referenceToTheDifferentObjectShouldNotBeEqual() {
        //given
        Meal meal1 = new Meal(10);
        Meal meal2 = new Meal(20);

        //then
        assertNotSame(meal1, meal2);

        //z matcherem -- not to wrapper - assertJ
//        assertThat(meal1).isNotSameAs(meal2);
    }

    @Test
    void twoMealsShouldBeEqualWhenPriceAndNameAreTheSame() {
        //given
        Meal meal1 = new Meal(10, "Pizza");
        Meal meal2 = new Meal(10, "Pizza");

        //then
        assertEquals(meal1, meal2);
        //matcher assertJ
//        assertThat(meal1).isEqualTo(meal2);
    }

    //testowanie wyjątków - sprawdzenie czy wyskoczy, warunkiem pojawienia sie
    //wyjatku w tym przypadku jest cena upustu wieksza niz cena meal
    @Test
    void exceptionShouldBeThrownIfDiscountIsHigherThanThePrice() {

        //given
        Meal meal = new Meal(8, "Soup");

        //when
        //then - tutaj najpierw podajemy klase wyjątku a nastepnie metode ktora testujemy
        assertThrows(IllegalArgumentException.class, () -> meal.getDiscountedPrice(40));

    }


}