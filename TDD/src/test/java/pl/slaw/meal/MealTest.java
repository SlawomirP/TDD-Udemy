package pl.slaw.meal;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.slaw.IAExceptionIgnoreExtension;
import pl.slaw.order.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class MealTest {

    //przykladowy test dynamiczny - najprostszy
    @TestFactory // bedzie tu metoda zwracajaca kolekcje obiektow
                //DynamicTest pochodzi z junita
    Collection<DynamicTest> dynamicTestCollection(){ //1 to nazwa a druga to jakies wyrazenie
        return Arrays.asList(
                dynamicTest("Dymic test 1", () -> assertThat(5, lessThan(6)) ),
                dynamicTest("Dynamic test 2", () -> assertEquals(4, 2*2)));
    }

    //metoda pomocnicza do wlasciwego testu dynamicznego
    private int calculatePrice(int price, int quantity){
        return price*quantity;
    }
    @Tag("fries")
    //test
    @TestFactory
    Collection<DynamicTest> calculateMealPrices(){
        Order order = new Order();
        order.addMealToOrder(new Meal(10, 2, "Hamburger"));
        order.addMealToOrder(new Meal(7, 4, "Fries"));
        order.addMealToOrder(new Meal(22, 3, "Pizza"));

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int i = 0; i < order.getMeals().size(); i++) {
            int price = order.getMeals().get(i).getPrice();
            int quantity = order.getMeals().get(i).getQuantity();

            // z junita - tu dajemy assercje
            Executable executable = () -> {
                assertThat(calculatePrice(price,quantity), lessThan(67));
            };
            //nazwa dla testu, uzyjemy nizej
            String name = "Test name: " + i;

            //instancja testu dynamicznego - z junita-- w nawiasach nazwa i obiekt interfejsu executable
            DynamicTest dynamicTest = DynamicTest.dynamicTest(name, executable);
            //dodanie tego testu do kolekcji
            dynamicTests.add(dynamicTest);
        }
        return dynamicTests;
    }









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

    @ExtendWith(IAExceptionIgnoreExtension.class) // zaloguje zignorowany test przy 8
    @ParameterizedTest
    @ValueSource(ints = {1,3,5,8})
    void mealPrizesShouldBeLowerThan10(int price) {
        if (price > 5){  //warunek ktory bedzie ignorowany przez extension
            throw new IllegalArgumentException();
        }
        assertThat(price, lessThan(20));
    }

    //METODA DO CALL REAL METHOD-----------------------------------------
    // na obiekcie mock mozna wywolac prawdziwą metodę
    @Test
    void testMealSumPrice(){

        //given
        Meal meal = mock(Meal.class);

            // dwie metody beda zmockowane a trzecia bedzie prawdziwa
        given(meal.getPrice()).willReturn(15);
        given(meal.getQuantity()).willReturn(3);
            //ustawienie zeby na mocku wykorzystac pradziwa metode
        given(meal.sumPrice()).willCallRealMethod();

        //when
        int result = meal.sumPrice();

        //then
        assertThat(result, equalTo(45)); // jezeli wywolamy to bez willCallRealMethod
                                                // to nie przejdzie

    }

    //test z uzyciem obiektu Spy
    @Test
    void testMealSumPriceWitchSpy(){

        //given - utworzenie nowego obiektu zamiast mocka, spy bedzie chcial utworzyć
                // obiekt klasy Meal korzystajac z konstruktora bezargumentowego
        Meal meal = spy(Meal.class);

                //jezeli chcemy skorzystac z konstruktora z argumentami to:
        Meal mealArg = new Meal(14,4,"Burito");
        Meal mealSpy = spy(mealArg); // i działamy na tym
//------------------------
            //można też adnotacjami pod nazwa klasy i potem uzywac mealSpy
//        @Spy
//                private Meal mealSpy;
            //mozna tez dac adnotacje nad testem
//        @ExtendWith(MockitoExtension.class)
//------------------------
        // nie mozemy wykorzystac metod given, willReturn na prawdziwym obiekcie
        // ale mozna tego uzyc na obiekcie Spy
        given(meal.getPrice()).willReturn(15); // metoda mokowana - zwraca co chcemy
        given(meal.getQuantity()).willReturn(3); // metoda mokowana - zwraca co chcemy

        //when
        int result = meal.sumPrice(); // metoda prawdziwa

        //then
        then(meal).should().getPrice(); // sprawdzenie wywolania metody
        then(meal).should().getQuantity(); // sprawdzenie wywolania metody
        assertThat(result, equalTo(45));
    }




}