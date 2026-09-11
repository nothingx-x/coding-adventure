package xyz.nothing.multithreading;


import java.util.ArrayList;
import java.util.List;

public class MultipleReadWriteQ {
    // in this example we created 100 threads
    // and each thread doing 10K addition
    // the sum should be 1M (10K * 100 = 1M)
    // but it never matches

    public static class ClassTest {
        private int a = 0;

        public void add() {
            a++;
        }

        public void sub() {
            a--;
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
