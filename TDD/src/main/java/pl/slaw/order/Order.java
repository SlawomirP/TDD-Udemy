package pl.slaw.order;

import pl.slaw.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<Meal> meals= new ArrayList<>();

    //metoda dodajaca meal do listy
    public void addMealToOrder(Meal meal){
        this.meals.add(meal);
    }

    //metoda usuwająca meal z listy
    public void removeMeal(Meal meal){
        this.meals.remove(meal);
    }

    public List<Meal> getMeals() {
        return meals;
    }
}
