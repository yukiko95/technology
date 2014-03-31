import java.io.*;
import java.util.*;

/**
 * Class reading data
 */
public class Reader {
    private ArrayList<Data> data = new ArrayList<Data>();

    /**
     * Constructor reading data and save to ArrayList<Data> data
     *
     * @param fileName reading data from file fileName
     * @throws FileNotFoundException
     */
    public Reader(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        try {
            while (scanner.hasNext()) {
                data.add(new Data(scanner.next(), Double.parseDouble(scanner.next()), scanner.nextLine().substring(1)));
            }
        } finally {
            scanner.close();
        }
    }

    /**
     * @return data
     */
    ArrayList<Data> getData() {
        return data;
    }
}
