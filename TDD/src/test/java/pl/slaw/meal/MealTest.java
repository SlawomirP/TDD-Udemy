package pl.slaw.meal;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    //porównanie typów prymitywnych
    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(35);

        //when
        int discountedPrice = meal.getDiscountedPrice(7);

        //then
        assertEquals(28, discountedPrice);

        //wersja z matcherem - hamcrest
        assertThat(discountedPrice,equalTo(28));
    }

    //porównanie obiektów, najpierw referencji
    @Test
    void referencesToTheSameObjectShouldBeEqual(){
        //given
        Meal meal1 = new Meal(10);
        Meal meal2 = meal1;

        //then
        assertSame(meal1, meal2);

        //z matcherem - hamcrest
        assertThat(meal1,sameInstance(meal2));
    }

    @Test
    void referenceToTheDifferentObjectShouldNotBeEqual(){
        //given
        Meal meal1 = new Meal(10);
        Meal meal2 = new Meal(20);

        //then
        assertNotSame(meal1, meal2);

        //z matcherem -- not to wrapper - hamcrest
        assertThat(meal1, not(sameInstance(meal2)));
    }


    @Test
    void twoMealsShouldBeEqualWhenPriceAndNameAreTheSame(){
        //given
        Meal meal1 = new Meal(10, "Pizza");
        Meal meal2 = new Meal(10, "Pizza");

        //then
        assertEquals(meal1, meal2);
//        assertEquals(meal1, meal2, "Check if arent equal");
    }
}