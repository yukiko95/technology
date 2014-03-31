import java.io.*;
import java.util.*;

/*Класс, который считывает данные из файла и записывает их в ArrayList<Data>*/

public class FileReader {
    private ArrayList<Data> data = new ArrayList<Data>();

    public FileReader(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        try {
            while (scanner.hasNext()) {
                data.add(new Data(scanner.next(), Double.parseDouble(scanner.next()), scanner.nextLine().substring(1)));
            }
        } finally {
            scanner.close();
        }
    }

    ArrayList<Data> getData() {
        return data;
    }
}
