package xyz.nothing.multithreading;


import java.util.ArrayList;
import java.util.List;

public class MultipleReadWriteA {
    // the solution is to run each operation inside synchronize block
    public static class ClassTest {
        private int a = 0;
        private final Object lock = new Object();

        public void add() {
            synchronized (lock) {
                a++;
            }
        }

        public void sub() {
            synchronized (lock) {
                a--;
            }
        }

        public int a() {
            return a;
        }
    }

    static void main() throws InterruptedException {
        ClassTest classTest = new ClassTest();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10_000; j++) {
                    classTest.add();
                }
            });
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(classTest.a());
    }
}
