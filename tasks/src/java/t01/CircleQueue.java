package t01;

public class CircleQueue<E> implements Queue<E> {
    private Node<E> head = null;
    private Node<E> tail = null;

    /* Добавляем элемент в конец очереди */
    @Override
    public void add(E elem) {
        Node<E> newTail = new Node<E>(elem, null);
        if (tail == null) {
            head = newTail;
        } else {
            tail.next = newTail;
        }
        tail = newTail;
    }

    /* Пытаем получить первый элемент очереди */
    @Override
    public E first() {
        if (head == null) {
            throw new IllegalStateException("No element");
        } else {
            return head.element;
        }
    }

    /* Проверяем находится ли элемент в очереди */
    @Override
    public boolean contains(E elem) {
        for (Node<E> current = head; current != null; current = current.next) {
            if (current.element.equals(elem)) {
                return true;
            }
        }
        return false;
    }

    /* Пытаемся удалить элемент из очереди */
    @Override
    public E poll() {
        E element = first();
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
        }
        return element;
    }

    /* единичный элемент очереди с ссылкой на следующий */
    private class Node<E> {
        public E element;
        public Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }
}
