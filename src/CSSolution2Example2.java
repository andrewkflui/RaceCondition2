/*
 * CSSolution2Example2.java
 *
 * Written by Andrew Lui 
 * The Open University of Hong Kong 2018
 *
* Critical Section Solution 2 Example 2
* A More Complex Implementation
 *
 */

public class CSSolution2Example2 {

    static class Buffer {
        static boolean flag = false;
    }
    private static int countError = 0;
    private static boolean p1 = false;
    private static boolean p2 = false;
    private static Object obj = new Object();

    private static boolean isFlagTrue() {
        return Buffer.flag;
    }

    private static void setFlag(boolean value) {
        //synchronized (obj) { // make the following an atomic execution
        Buffer.flag = value;
        //}
    }

    private static void criticalSection(int id) {
        if (id == 1) {
            p1 = true;
        } else if (id == 2) {
            p2 = true;
        }
        Thread.currentThread().yield(); // force the pre-emption of the current Thread
        if (p1 && p2) {
            System.out.println("Process " + id + ": error occured: p1 and p2 are both true");
            countError++;
        }
        if (id == 1) {
            p1 = false;
        } else if (id == 2) {
            p2 = false;
        }
    }

    static class TestProcess implements Runnable {

        private int id;
        private int counter = 0;

        TestProcess(int id) {
            this.id = id;
        }

        public void run() {
            System.out.println("Process #" + id + " started");
            while (true) {
                //synchronized (obj) { // make the following an atomic execution
                while (Buffer.flag == true);
                Buffer.flag = true;
                //}

                criticalSection(id);

                Buffer.flag = false;

                counter++;
                System.out.println("Process #" + id + ": " + counter);
                if (counter > 10000) {
                    break;
                }
            }
        }
    }

    public static void main(String args[]) throws Exception {
        long startTime = System.currentTimeMillis();
        TestProcess processA = new TestProcess(1);
        TestProcess processB = new TestProcess(2);
        Thread threadA = new Thread(processA);
        Thread threadB = new Thread(processB);
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime);
        System.out.println("Number of errors = " + countError);
        System.out.println("Time taken = " + timeTaken + " ms");
    }
}
