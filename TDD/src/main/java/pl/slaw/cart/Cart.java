package pl.slaw.cart;

import pl.slaw.meal.Meal;
import pl.slaw.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Order> ordersCart = new ArrayList<>();

    void addOrderToCart(Order order){
        this.ordersCart.add(order);
    }

    void clearCart(){
        this.ordersCart.clear();
    }

    //utworzenie symulatora zamówień
    void simulateLargeOrder(){
        for (int i = 0; i < 1000; i++) {
            Meal meal = new Meal(i%10, "Hamburger no " + i);
            Order order = new Order();
            order.addMealToOrder(meal);
            addOrderToCart(order);
        }
        System.out.println("Cart size: " + ordersCart.size());
        clearCart();
    }

}
