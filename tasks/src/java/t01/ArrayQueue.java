package t01;

//автор: Кобыленко Дарья, группа 2743
//задание 1, вариант 2

/*Надо написать две реализации этого интерфейса - в виде массива элементов (ArrayQueue) и в виде кольцевого
  списка элементов (CircleQueue). Программа запускается с параметрами - имя класса реализации и (в случае реализации
  в виде массива) максимальный размер очереди (массива). Программа должна динамически загрузить класс с нужной
  реализацией и замерить время выполнения достаточно большой последовательности из операций добавления и удаления
  элементов (скажем, для оценки производительности реализации).*/

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayQueue<E> implements Queue<E> {
    private E[] queue;  //сам массив
    private int head;  //"голова" очереди
    private int tail;  //"хвост" очереди

    @SuppressWarnings("unchecked")
    public ArrayQueue(int size) {
        this.queue = (E[]) new Object[size];
        this.head = -1;
        this.tail = 0;
    }

    /*добавление элемента происходит следующим образом:
    * пока есть свободная ячейка (null), то добавляем, иначе - ошибка.*/

    @Override
    public void add(E elem) {
        if (tail - head <= queue.length) {
            queue[tail % queue.length] = elem;
            tail++;
        } else {
            throw new IllegalStateException("Out of size");
        }
    }

    /*"посмотреть на "голову".
    * если список пустой (аналогично методу isEmpty();) - ошибка*/

    @Override
    public E first() {
        if (tail == 0) {
            throw new IllegalStateException("No element");
        } else {
            return queue[(head + 1) % queue.length];
        }
    }

/*для того, чтобы проверить содержится ли элемент в очереди, достаточно пробежаться
* по всему массиву.
* (пыталась рассмотреть разные случаи размещения head, tail, но как-то неудачно).*/

    @Override
    public boolean contains(E elem) {
        for (int i = 0; i < queue.length; i++) {
            if (elem.equals(queue[i])) {
                return true;
            }
        }
        return false;
    }

    /*возвращаем и удаляем "головной" элемент. как только дошли до конца очереди - ошибка.*/

    @Override
    public E poll() {
        if (tail == head) {
            throw new IllegalStateException("No element");
        } else {
            E headElem = queue[(++head) % queue.length];
            queue[head % queue.length] = null;
            return headElem;
        }
    }
}