package pl.slaw.meal;

import org.junit.jupiter.api.Test;

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
    }

    //porównanie obiektów, najpierw referencji
    @Test
    void referencesToTheSameObjectShouldBeEqual(){
        //given
        Meal meal1 = new Meal(10);
        Meal meal2 = meal1;

        //then
        assertSame(meal1, meal2);
    }

    @Test
    void referenceToTheDifferentObjectShouldNotBeEqual(){
        //given
        Meal meal1 = new Meal(10);
        Meal meal2 = new Meal(20);

        //then
        assertNotSame(meal1, meal2);
    }

    //porownanie samych obiektów, tutaj nalezy nadpisac metode equals poniewaz
    //zostanie wykorzystana metoda standardowa z klasy object ktora porownuje
    //referencje. Nadpisujemy met equals a zgodnie z konwencja rowniez hashcode
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