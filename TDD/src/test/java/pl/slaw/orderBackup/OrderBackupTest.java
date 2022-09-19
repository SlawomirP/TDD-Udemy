package pl.slaw.orderBackup;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.slaw.meal.Meal;
import pl.slaw.order.Order;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OrderBackupTest {

    private static OrderBackup orderBackup; // obiekt do stworzenia

    @BeforeAll // wymaga by metoda byla static - utworzenie i otwarcie pliku
    //uruchamiana jako pierwsza
    static void setup() throws FileNotFoundException {
        orderBackup = new OrderBackup();
        orderBackup.createFile();
    }

    @AfterAll // tu mamy zamkniecie pliku na koniec
    //uruchamiana jako piata ostatnia, zamkniecie
    static void tearDown() throws IOException {
        orderBackup.closeFile();
    }

    @BeforeEach // metoda doda napis na poczatku w pliku uruchamiana
    //jako druga
    void appendAtTheStartOfTheLine() throws IOException {
        orderBackup.getWriter().append("New order: ");
    }

    @AfterEach // metoda doda napis na koncu w pliku
    //uruchamiana jako czwarta
    void appendAtTheEndOfTheLine() throws IOException {
        orderBackup.getWriter().append(" backed up");
    }

    @Test // uruchomiony jako trzeci - wpisuje meal do pliku
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