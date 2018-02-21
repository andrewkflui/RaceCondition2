/*
 * CSSolution2Example1.java
 *
 * Written by Andrew Lui 
 * The Open University of Hong Kong 2018
 *
 * This program illustrate the deadlock situation with Solution 2 of the
 * Critical Section problem
* 
Instruction: Two threads running, TestProcessA and TestProcessB, which adds 1 to variable a and b respective
No shared data issue with variables a and b.
After running the threads, a and b should equal to TARGET, which is 100000
Uncomment the while loop in the two threads, so to activate CS Solution #2

The Monitor Thread is for checking if deadlock is possible - when a and b do not change
 *
 */
public class CSSolution2Example1 {
    private static long a = 0;
    private static long b = 0;
    private static boolean flagA = false;
    private static boolean flagB = false;

    private static final int TARGET = 100000;

    static class TestProcessA implements Runnable {

        public void run() {
            for (int i = 0; i < TARGET; i++) {
                flagA = true;
                // UNCOMMENT THE FOLLOWING while loop to activate Soluton 2
                while (flagB);
                a = a + 1;
                flagA = false;
            }
        }
    }

    static class TestProcessB implements Runnable {

        public void run() {
            for (int i = 0; i < TARGET; i++) {
                flagB = true;
                // UNCOMMENT THE FOLLOWING while loop to activate Soluton 2
                while (flagA);
                b = b + 1;
                flagB = false;
            }
        }
    }

    static class Monitor implements Runnable {

        int time = 0;
        long prevA, prevB;

        public void run() {
            while (true) {
                try {
                    Thread.currentThread().sleep(1000);
                    System.out.println(String.format("Time %d: a = %d b = %d", time, a, b));
                    if (time == 0) {
                        prevA = a;
                        prevB = b;
                    } else {
                        if (prevA == a || prevB == b) {
                            System.out.println("Seems to be in deadlock");
                        }
                    }
                    time++;
                } catch (Exception ex) {
                    break; // break on interrupted
                }
            }
        }
    }

    public static void main(String args[]) throws Exception {
        int testnum = 100;
        int countError = 0;
        Thread monitor = new Thread(new Monitor());
        monitor.start();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testnum; i++) {
            flagA = flagB = false;
            a = b = 0;
            Thread threadA = new Thread(new TestProcessA());
            Thread threadB = new Thread(new TestProcessB());
            threadA.start();
            threadB.start();
            threadA.join();
            threadB.join();
            if (a != TARGET || b != TARGET) {
                System.out.println(String.format("%d: Error Found a = %d b = %d", i, a, b));
                countError++;
            } else {
                System.out.println(String.format("%d: No Error a = %d b = %d", i, a, b));
            }
        }
        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime);
        System.out.println("Number of errors = " + countError);
        System.out.println("Time taken = " + timeTaken + " ms");
        monitor.interrupt();
        monitor.join();
    }

}
