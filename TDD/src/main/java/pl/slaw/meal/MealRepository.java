package pl.slaw.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealRepository {

    private List<Meal> meals = new ArrayList<>();

    public void add(Meal meal) {
        meals.add(meal);
    }

    public List<Meal> getAllMeals() {
        return meals;
    }

    public void delete(Meal meal) {
        meals.remove(meal);
    }

    public List<Meal> findByName(String name) {
        return meals.stream()
                .filter(meal -> meal.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<Meal> findByPrice(int mealPrice) {
        return meals.stream()
                .filter(meal -> meal.getPrice() == mealPrice)
                .toList();
    }
}
