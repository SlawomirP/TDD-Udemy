package pl.slaw.meal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MealTest {

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