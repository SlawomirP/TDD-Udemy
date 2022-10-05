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

    public List<Meal> findByName(String name, boolean exactMatch) {
        if (exactMatch) {
            return meals.stream()
                    .filter(meal -> meal.getName().equals(name))
                    .collect(Collectors.toList());
        } else {
            return meals.stream()
                    .filter(meal -> meal.getName().startsWith(name))
                    .collect(Collectors.toList());
        }
    }

    public List<Meal> findByPrice(int mealPrice, Condition priceCondition) {
        List<Meal> mealsList = new ArrayList<>();
        switch (priceCondition) {
            case LOWER -> {
                mealsList = meals.stream()
                        .filter(meal -> meal.getPrice() < mealPrice)
                        .collect(Collectors.toList());
            }
            case EQUAL -> {
                mealsList = meals.stream()
                        .filter(meal -> meal.getPrice() == mealPrice)
                        .collect(Collectors.toList());
            }
            case HIGHER -> {
                mealsList = meals.stream()
                        .filter(meal -> meal.getPrice() > mealPrice)
                        .collect(Collectors.toList());
            }
        }
        return mealsList;
    }

    public List<Meal> find(String mealName, boolean byExact, int price, Condition priceCondition) {
        List<Meal> list = new ArrayList<>();
        if (byExact) {
            switch (priceCondition) {
                case LOWER -> {
                    list = meals.stream()
                            .filter(meal -> meal.getName().equals(mealName))
                            .filter(meal -> meal.getPrice() < price)
                            .collect(Collectors.toList());
                }
                case EQUAL -> {
                    list = meals.stream()
                            .filter(meal -> meal.getName().equals(mealName))
                            .filter(meal -> meal.getPrice() == price)
                            .collect(Collectors.toList());
                }
                case HIGHER -> {
                    list = meals.stream()
                            .filter(meal -> meal.getName().equals(mealName))
                            .filter(meal -> meal.getPrice() > price)
                            .collect(Collectors.toList());
                }
            }
        } else {
            switch (priceCondition) {
                case LOWER -> {
                    list = meals.stream()
                            .filter(meal -> meal.getName().toLowerCase().startsWith(mealName))
                            .filter(meal -> meal.getPrice() < price)
                            .collect(Collectors.toList());
                }
                case EQUAL -> {
                    list = meals.stream()
                            .filter(meal -> meal.getName().toLowerCase().startsWith(mealName))
                            .filter(meal -> meal.getPrice() == price)
                            .collect(Collectors.toList());
                }
                case HIGHER -> {
                    list = meals.stream()
                            .filter(meal -> meal.getName().toLowerCase().startsWith(mealName))
                            .filter(meal -> meal.getPrice() > price)
                            .collect(Collectors.toList());
                }
            }
        }
        return list;
    }
}
