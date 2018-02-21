/*
 * RaceCondition3.java
 *
 * Written by Andrew Lui 
 * The Open University of Hong Kong 2018
 *
 * This program uses the wait and notify synchronization methods in Java
 * to implement the critical section situation
 *
 * Note that this seems a more efficient method compared to synchronized
 */
public class RaceCondition3 {

  static class Buffer {
    static int value = 0;
  }

  private static final Object obj = new Object();
  private static final int a = 1;

  static class TestProcessA implements Runnable {

    public void run() {
      try {
        for (int i = 0; i < 1000000; i++) {
          wait();
          Buffer.value = Buffer.value + a;
          notifyAll();
        }
      } catch (Exception ex) {
      }
    }
  }

  static class TestProcessB implements Runnable {

    public void run() {
      try {
        for (int i = 0; i < 1000000; i++) {
          wait();
          Buffer.value = Buffer.value - a;
          notifyAll();
        }
      } catch (Exception ex) {
      }
    }
  }

  public static void main(String args[]) throws Exception {
    int testnum = 10000;
    int countError = 0;
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < testnum; i++) {
      Buffer.value = 0;
      Thread threadA = new Thread(new TestProcessA());
      Thread threadB = new Thread(new TestProcessB());
      threadA.start();
      threadB.start();
      threadA.join();
      threadB.join();
      if (Buffer.value != 0) {
        System.out.println("Error found in run " + i + " value is " + Buffer.value);
        countError++;
      }
    }
    long endTime = System.currentTimeMillis();
    long timeTaken = (endTime - startTime);
    System.out.println("Number of errors = " + countError);
    System.out.println("Time taken = " + timeTaken + " ms");
  }

}
