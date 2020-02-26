/*
 * RaceCondition.java
 *
 * Written by Andrew Lui 
 * The Open University of Hong Kong 2020
 *

Instruction:

Errors will occur now due to race condition on adding and subtracting the shared variable Buffer.value

To solve the problem, we can use Java synchronization.  Uncomment the synchronoized block
 */

public class RaceCondition {
  static final int COUNT = 1000000;
  static int value = 0;  // a shared variable between threads
  
  static class TestProcessA implements Runnable {
    public void run() {
      for (int i = 0; i < COUNT; i++) {
          value = value + 1;
      }
    }
  }

  static class TestProcessB implements Runnable {
    public void run() {
      for (int i = 0; i < COUNT; i++) {
          value = value - 1;
      }
    }
  }
  public static void main(String args[]) throws Exception {
    int testnum = 100;
    int countError = 0;
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < testnum; i++) {
      value = 0;
      Thread threadA = new Thread(new TestProcessA());
      Thread threadB = new Thread(new TestProcessB());
      threadA.start();
      threadB.start();
      threadA.join(); // blocked until threadA has finished
      threadB.join(); // blocked until threadB has finished
      if (value != 0) {
        System.out.println("ERROR FOUND: run " + i + " the value is " + value + 
                " instead of the expected 0");
        countError++;
      }
    }
    long endTime = System.currentTimeMillis();
    long timeTaken = (endTime - startTime);
    System.out.println("Number of errors: " + countError + " out of " + testnum + " epoches");     
    System.out.println("Time taken = " + timeTaken + " ms");
  }

}
