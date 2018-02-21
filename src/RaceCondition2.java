/*
 * RaceCondition2.java
 *
 * Written by Andrew Lui 
 * The Open University of Hong Kong 2018
 *

Instruction:

Errors will occur now due to race condition on adding and subtracting the shared variable Buffer.value

To solve the problem, we can use Java synchronization.  Uncomment the synchronoized block
 */

public class RaceCondition2 {

  static class Buffer {
    static int value = 0;
  }

  private static final Object obj = new Object();
  private static final int a = 1;

  static class TestProcessA implements Runnable {

    public void run() {
      for (int i = 0; i < 1000000; i++) {
        //synchronized (obj) { // make the following critical section an atomic execution
          Buffer.value = Buffer.value + a;
        //}
      }
    }
  }

  static class TestProcessB implements Runnable {

    public void run() {
      for (int i = 0; i < 1000000; i++) {
        //synchronized (obj) { // make the following critical section an atomic execution
          Buffer.value = Buffer.value - a;
        //}
      }
    }
  }

  public static void main(String args[]) throws Exception {
    int testnum = 100;
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
