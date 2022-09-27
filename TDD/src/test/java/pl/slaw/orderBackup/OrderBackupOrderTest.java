package pl.slaw.orderBackup;

import org.junit.jupiter.api.Test;
import pl.slaw.order.Order;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OrderBackupOrderTest {

    @Test
    void callingBackupWithoutCreatingAFileFirstShouldThrowException() throws IOException {
        //given
        OrderBackup orderBackup = new OrderBackup();

        //when
        //then
        assertThrows(IOException.class, ()-> orderBackup.backup(new Order()));
    }

}