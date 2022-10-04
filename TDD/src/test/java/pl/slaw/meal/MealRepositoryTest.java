package pl.slaw.meal;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class MealRepositoryTest { // repozytorium Meal tworzone za pomocą TDD

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
        MealRepository mealRepository = new MealRepository();
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
        MealRepository mealRepository = new MealRepository();
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);

        //when
        mealRepository.delete(meal);

        //then - mealRepository--pobierz liste meal --> nie--zawiera--meal
        assertThat(mealRepository.getAllMeals(), not(contains(meal)));


    }


}
