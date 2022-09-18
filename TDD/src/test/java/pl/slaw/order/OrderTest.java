package pl.slaw.order;

import org.junit.jupiter.api.Test;
import pl.slaw.meal.Meal;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class OrderTest {

    //test demonstrujący pewną assercje
    @Test
    void testAssertArrayEquals(){

        //given
        int [] init1 = {1,2,3};
        int [] init2 = {1,2,3};

        //then
        assertArrayEquals(init1, init2);
    }

    //przetestowanie listy z klasy order
    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder(){

        //given
        Order order = new Order();

        //then - rożne opcje
        assertThat(order.getMeals(), empty());
//        ------------------------------------
        assertThat(order.getMeals().size(), equalTo(0));
        //        ------------------------------------
        assertThat(order.getMeals(), hasSize(0));
//        -------------------------------------
        assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    //test listy z dodanymi meal, sprawdzenie czy zwikszy sie rozmiar
    @Test
    void addingMealToOrderShouldIncreaseOrderSize(){

        //given
        Meal meal = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");
        Order order = new Order();

        //when
        order.addMealToOrder(meal);

        //then
        assertThat(order.getMeals(), hasSize(1));

        // sprawdzenie czy lista zawiera konkretny element
        assertThat(order.getMeals(), contains(meal));

        // komenda podobna do contain
        assertThat(order.getMeals(),hasItem(meal));

        //sprawdzenie konkretnego parametru elementu z listy
        assertThat(order.getMeals().get(0).getPrice(), equalTo(15));

//        assertThat(order.getMeals(), contains(meal2));

    }

    //testowanie usuwania elementow z kolekcji
    @Test
    void removingMealFromOrderShouldDecreaseOrderSize(){

        //given
        Meal meal = new Meal(15, "Burger");
        Order order = new Order();

        //when
        order.addMealToOrder(meal);
        order.removeMeal(meal);

        //then
        assertThat(order.getMeals(), empty());
//        --------------------
        assertThat(order.getMeals(), not(contains(meal)));
    }

    //testowanie wiekszej ilosci elementow w kolekcji np
    //czy sa w dobrej kolejnosci
    @Test
    void mealShouldBeInCorrectOrderAfterAddingThemToOrder(){

        //given
        Meal meal1 = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");
        Order order = new Order();

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        //then
        assertThat(order.getMeals(), contains(meal1, meal2));
//        assertThat(order.getMeals(), contains(meal2, meal1)); - nie przejdzie

        //sprawdza czy poprostu zawiera, bez uwzglednienia kolejnosci
        assertThat(order.getMeals(),containsInAnyOrder(meal1, meal2));
    }

    //porownanie dwoch kolekcji
    @Test
    void testIfTwoMealListAreTheSame(){

        //given
        Meal meal1 = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");
        Meal meal3 = new Meal(25, "Kebab");

        List<Meal> meals1 = Arrays.asList(meal1,meal2);
        List<Meal> meals2 = Arrays.asList(meal1,meal2);

        //then
        assertThat(meals1,is(meals2)); //przejdzie jesli bedzie ta sama kolejnosc
                                        // i ta sama zawartosc


    }

}