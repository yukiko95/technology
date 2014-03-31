package t01;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Created Darya Kobylenko, group 2743.
 * ---
 * Задание 1 (2 вариант):
 * Надо написать две реализации этого интерфейса - в виде массива элементов (ArrayQueue) и в виде кольцевого
 * списка элементов (CircleQueue). Программа запускается с параметрами - имя класса реализации и (в случае реализации
 * в виде массива) максимальный размер очереди (массива). Программа должна динамически загрузить класс с нужной
 * реализацией и замерить время выполнения достаточно большой последовательности из операций добавления и удаления
 * элементов (скажем, для оценки производительности реализации).
 */

public class Main {
    /* Обрабатываем данные в args, исходя из которых вызываем нужный нам класс */
    public static void main(String[] args) throws Exception {
        if (args.length < 1 || args.length > 2) {
            throw new IllegalArgumentException("Incorrect number of arguments");
        }
        if (args[0].equals("CircleQueue")) {
            Class<? extends Queue> clazz = CircleQueue.class;
            Queue<Integer> object = (Queue<Integer>) clazz.newInstance();
            System.out.println("CircleQueue: " + checkTime(object));
        } else if (args[0].equals("ArrayQueue")) {
            int size = Integer.parseInt(args[1]);
            Class<? extends Queue> clazz = ArrayQueue.class;
            Queue<Integer> object = (Queue<Integer>) clazz.getConstructor(int.class).newInstance(size);
            System.out.println("ArrayQueue: " + checkTime(object));
        } else {
            throw new ClassNotFoundException();
        }
    }

    /* имея объект класса, замеряем время работы на его методах */
    private static long checkTime(Queue<Integer> object) throws Exception {
        long start = System.currentTimeMillis();
        Class<? extends Queue> clazz = object.getClass();
        for (int i = 0; i < 1000000; i++) {
            clazz.getMethod("add", Object.class).invoke(object, 21);
            clazz.getMethod("contains", Object.class).invoke(object, 21);
            clazz.getMethod("contains", Object.class).invoke(object, 23);
            clazz.getMethod("first").invoke(object);
            clazz.getMethod("poll").invoke(object);
        }
        return System.currentTimeMillis() - start;
    }
}
