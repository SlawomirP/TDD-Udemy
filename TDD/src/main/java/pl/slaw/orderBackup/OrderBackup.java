package pl.slaw.orderBackup;

import pl.slaw.order.Order;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class OrderBackup {

    private Writer writer;

    public Writer getWriter() {
        return writer;
    }

    //metoda tworzaca plik z zapisem
    void createFile() throws FileNotFoundException {
        File file = new File("OrderBackup.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        writer = new BufferedWriter(outputStreamWriter);
    }

    void closeFile() throws IOException {
        writer.close();
    }

    void backup(Order order) throws IOException {
        writer.append(order.toString());
    }
}
