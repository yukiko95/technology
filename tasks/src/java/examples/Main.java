package examples;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

public class Main {
    /**
     * Локальное поле, хранящееся в экземплярах класса.
     */
    int local = 12;

    /**
     * Это статический метод класса, который печатает свой аргумент.
     * @param x
     */
    @SuppressWarnings("unused")
    private static void m1(int x) {
        System.out.println("static m1. Аргумент x = " + x);
    }

    /**
     * Нестатический метод класса печатает локальное значение поля
     * и свой аргумент.
     * @param y
     */
    @SuppressWarnings("unused")
    private void m2(int y) {
        System.out.format("m2. Локальное значение local = %d, аргумент y = %d\n", local, y);
    }
    /**
     * Нестатический метод класса печатает локальное значение поля
     * и свой аргумент.
     * @param z
     */
    @SuppressWarnings("unused")
    private void m3(int z) {
        System.out.format("m3. Локальное значение local = %d, аргумент z = %d\n", local, z);
    }

    /**
     * Вызов метода с заданным именем из заданного класса с заданным аргументом.
     * @param methodName
     * @param arg
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private static void launchMethod(Class<Main> clazz, String methodName, int arg) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
// Находим метод и запускаем его.
        Method method = clazz.getDeclaredMethod(methodName, int.class);
        if ((method.getModifiers() & Modifier.STATIC) != 0) {
            method.invoke(null, arg);
        } else {
            method.invoke(new Main(), arg);
        }
    }

//========================================================
// Тесты
//========================================================

    @Test
    public void test01() {
        try {
            launchMethod(Main.class, "m1", 15);
        } catch (Exception e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void test02() {
        try {
            launchMethod(Main.class, "m2", 25);
        } catch (Exception e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void test03() {
        try {
            launchMethod(Main.class, "m3", 35);
        } catch (Exception e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void test04() {
        try {
            launchMethod(Main.class, "m4", 45);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            assertTrue(null, false);
        }
    }
}
