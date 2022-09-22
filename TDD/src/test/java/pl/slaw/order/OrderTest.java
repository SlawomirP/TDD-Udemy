package pl.slaw.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.slaw.BeforeAfterExtension;
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


@ExtendWith(BeforeAfterExtension.class) // umieszczamy nad klasa lub metoda
                                        //gdzie chcemy uzyc rozszerzenia
class OrderTest {

    private Order order;
    @BeforeEach // tzn tworzony przed kazdym testem
    //metoda ktora stworzy obiekt Order order, usuwamy juz tworzenie
    //tego obiektu z testów
    void initialezeOrder(){
        System.out.println("Iside @BeforeEach method");
        order = new Order();
    }

    @AfterEach //wywolywanie po kazdym tescie, tutaj czyszczenie listy
    void cleanUp(){
        System.out.println("Iside @AfterEach method");
        order.cancel();
    }

    @Test
    void testAssertArrayEquals() {
        System.out.println("Inside test");
        //given
        int[] init1 = {1, 2, 3};
        int[] init2 = {1, 2, 3};

        //then
        assertArrayEquals(init1, init2);
    }

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {
        //given
        //then - rożne opcje
        assertThat(order.getMeals(), empty());
//        ------------------------------------
        assertThat(order.getMeals().size(), equalTo(0));
        //        ------------------------------------
        assertThat(order.getMeals(), hasSize(0));
//        -------------------------------------
        assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {

        //given
        Meal meal = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");

        //when
        order.addMealToOrder(meal);

        //then
        assertThat(order.getMeals(), hasSize(1));

        // sprawdzenie czy lista zawiera konkretny element
        assertThat(order.getMeals(), contains(meal));

        // komenda podobna do contain
        assertThat(order.getMeals(), hasItem(meal));

        //sprawdzenie konkretnego parametru elementu z listy
        assertThat(order.getMeals().get(0).getPrice(), equalTo(15));

    }

    @Test
    void removingMealFromOrderShouldDecreaseOrderSize() {

        //given
        Meal meal = new Meal(15, "Burger");

        //when
        order.addMealToOrder(meal);
        order.removeMeal(meal);

        //then
        assertThat(order.getMeals(), empty());
//        --------------------
        assertThat(order.getMeals(), not(contains(meal)));
    }

    @Test
    void mealShouldBeInCorrectOrderAfterAddingThemToOrder() {

        //given
        Meal meal1 = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        //then
        assertThat(order.getMeals(), contains(meal1, meal2));
//        assertThat(order.getMeals(), contains(meal2, meal1)); - nie przejdzie

        //sprawdza czy poprostu zawiera, bez uwzglednienia kolejnosci
        assertThat(order.getMeals(), containsInAnyOrder(meal1, meal2));
    }

    //porownanie dwoch kolekcji
    @Test
    void testIfTwoMealListAreTheSame() {

        //given
        Meal meal1 = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");
        Meal meal3 = new Meal(25, "Kebab");

        List<Meal> meals1 = Arrays.asList(meal1, meal2);
        List<Meal> meals2 = Arrays.asList(meal1, meal2);

        //then
        assertThat(meals1, is(meals2)); //przejdzie jesli bedzie ta sama kolejnosc
        // i ta sama zawartosc

    }

}