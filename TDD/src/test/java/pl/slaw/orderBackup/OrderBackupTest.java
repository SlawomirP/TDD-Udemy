package pl.slaw.orderBackup;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.slaw.meal.Meal;
import pl.slaw.order.Order;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OrderBackupTest {

    private static OrderBackup orderBackup; // obiekt do stworzenia

    @BeforeAll // wymaga by metoda byla static
    static void setup() throws FileNotFoundException {
        orderBackup = new OrderBackup();
        orderBackup.createFile();
    }

    @AfterAll
    static void tearDown() throws IOException {
        orderBackup.closeFile();
    }

    @Test
    void backupOrderWithOneMeal() throws IOException {

        //given
        Meal meal = new Meal(7, "Fries");
        Order order = new Order();
        order.addMealToOrder(meal);

        //when - robimy backup
        orderBackup.backup(order);

        //then
        System.out.println("Order: " + order.toString() + " backed up.");
    }

}