package pl.slaw.meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class MealRepositoryTest { // repozytorium Meal tworzone za pomocą TDD

    MealRepository mealRepository = new MealRepository(); // aby nie tworzyć tego obiektu

    // w kazdym nowym tescie
    //aby zachować porządek, sprzątamy po każdym teście
    @BeforeEach
    void cleanUp() {
        mealRepository.getAllMeals().clear();
    }

    //najpierw niedziałający test dla dodawania do repozytorium
    // znajduje sie tam tylko taka linijka:
    //  MealRepository mealRepository = new MealRepositoryTest();
    //test nie przechodzi - red test -- musimy naprawic test
    // tworzymy klase MealRepository -- wlaczamy test - jest zielony
    // dodajemy obiekt ktory bedzie dodany -> meal
    // w when linijka mealRepository.add(meal); --> test red
    // naprawiamy, nie dopisujemy nic poki red nie bedzie green
    // tworzymy metodę dodajaca w MealRepository , wlaczamy test --> green
    //metoda jest pusta public void add(Meal meal) {} a mimo to test przechodzi
    // pamietać: nie dodawac nic wiecej, tyle zeby test przeszedl
    //nastepnie dodajemy linijke ktora sprawdzi czy dodany zostal nasz meal
    //assertThat(mealRepository.getAllMeals().get(0), is(meal)); --> red
    //dodajemy logikę w MealRepository w metodach -- test przechodzi green


    @Test
    void shouldBeAbleToAddMealToRepository() {

        //given
//        MealRepository mealRepository = new MealRepository();
        Meal meal = new Meal(10, "Pizza");

        //when
        mealRepository.add(meal);

        //then
        assertThat(mealRepository.getAllMeals().get(0), is(meal));
    }

    //kolejny test bedzie sprawdzał usuwanie, Meal musi mieć implementacje equals() i hashcode()
    // sekcja given pozostaje taka sama plus dodanie meal, w sekcji when zamiast add dajemy delete --> red
    // tworzymy metode delete w MealRepository, nic nie dodajemy, uruchamiamy test --> green
    //assercja --> red
    //uzupelnienie logiki w metodzie --> green
    @Test
    void shouldBeAbleToRemoveMealFromRepositury() {

        //given
//        MealRepository mealRepository = new MealRepository();
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);

        //when
        mealRepository.delete(meal);

        //then - mealRepository--pobierz liste meal --> nie--zawiera--meal
        assertThat(mealRepository.getAllMeals(), not(contains(meal)));
    }

    //test dla metody wyszukiwania po cenie
    // w given dajemy to co wczesniej, -- test ciagle green
    //w when chcemy zeby wynikiem dzialania metody byla lista -- red test
    //dodajemy brakujaca metode -- green test
    //w then dajemy asercje -- red test
    // uzupelniamy metode -- green test
//    @Test
//    void shouldBeAbleToFindMealByName() {
//
//        //given
////        MealRepository mealRepository = new MealRepository();
//        Meal meal = new Meal(10, "Pizza");
//        mealRepository.add(meal);
//
//        //when -- po wywolaniu metody, wyniki powinny byc zwrocone w postaci listy
//        List<Meal> result = mealRepository.findByName("Pizza");
//
//        //then
//        assertThat(result.size(), is(1));
//    }

    //znajdowanie posiłku po cenie
//    @Test
//    void shouldBeAbleFindMealByPrice() {
////given
////        MealRepository mealRepository = new MealRepository();
//        Meal meal = new Meal(10, "Pizza");
//        mealRepository.add(meal);
//
//        //when -- po wywolaniu metody, wyniki powinny byc zwrocone w postaci listy
//        List<Meal> result = mealRepository.findByPrice(10);
//
//        //then - dodanie asercji
//        assertThat(result.size(), is(1));
//    }

    //rozszerzanie funkcjonalności - test do wyszukiwania po nazwie, dodamy dodatkowy
    //parametr do metody
    @Test
    void shouldBeAbleToFindMealByExactName() {

        //given
//        MealRepository mealRepository = new MealRepository();
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(10, "Pi"); // ten obiekt nie powienien byc przpuszczony
        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when -- po wywolaniu metody, wyniki powinny byc zwrocone w postaci listy
        List<Meal> result = mealRepository.findByName("Pizza", true);

        //then
        assertThat(result.size(), is(1));
    }

    @Test
    void shouldBeAbleFindMealByStartingLetters() {

        //given
//        MealRepository mealRepository = new MealRepository();
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(10, "Pi"); // ten obiekt nie powienien byc przpuszczony
        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when -- po wywolaniu metody, wyniki powinny byc zwrocone w postaci listy
        List<Meal> result = mealRepository.findByName("P", false);

        //then
        assertThat(result.size(), is(2));

    }

    //homework tests
    @Test
    void shouldBeAbleFindMealByPriceConditionLower() {
//given
//        MealRepository mealRepository = new MealRepository();
        Meal meal1 = new Meal(9, "Pizza");
        Meal meal2 = new Meal(10, "HotDog");
        Meal meal3 = new Meal(11, "Burger");
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);

        //when -- po wywolaniu metody, wyniki powinny byc zwrocone w postaci listy
        //dodalem drugi parametr Condition.LOWER - test red
        //stworzylem enuma Condition z trzema polami LOWER, EQULS, HIGHER - green test
        //dodalem pierwsza asercje -> red test, uzupelnilem logike metody --> green test
        List<Meal> result = mealRepository.findByPrice(10, Condition.LOWER);

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), equalTo(meal1));
    }
    @Test
    void shouldBeAbleFindMealByPriceConditionEqual() {
//given
//        MealRepository mealRepository = new MealRepository();
        Meal meal1 = new Meal(9, "Pizza");
        Meal meal2 = new Meal(10, "HotDog");
        Meal meal3 = new Meal(11, "Burger");
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);

        //when -- po wywolaniu metody, wyniki powinny byc zwrocone w postaci listy
        //dodalem drugi parametr Condition.EQUAL --> green test
        //dodalem asercje -> red test, uzupelnilem logike w metodzie -> green
        List<Meal> result = mealRepository.findByPrice(10, Condition.EQUAL);

        //then
        assertThat(result.get(0), equalTo(meal2));
        assertThat(result.size(), is(1));
    }
    @Test
    void shouldBeAbleFindMealByPriceConditionHigher() {
//given
//        MealRepository mealRepository = new MealRepository();
        Meal meal1 = new Meal(9, "Pizza");
        Meal meal2 = new Meal(10, "HotDog");
        Meal meal3 = new Meal(11, "Burger");
        Meal meal4 = new Meal(12, "Sandwich");
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);
        mealRepository.add(meal4);

        //when -- po wywolaniu metody, wyniki powinny byc zwrocone w postaci listy
        //dodalem drugi parametr Condition.HIGHER --> green test
        //dodalem asercje -> red test, uzupelnilem logike w metodzie -> green
        List<Meal> result = mealRepository.findByPrice(10, Condition.HIGHER);

        //then
        assertThat(result.get(0), equalTo(meal3));
        assertThat(result.get(1), equalTo(meal4));
        assertThat(result.size(), is(2));
    }

}
