/*
 * CheckSequence.java
 *
 * Written by Andrew Lui 
 * The Open University of Hong Kong 2018
 *
 */

public class CheckSequence {
    
    public static void main(String[] args) {
        int a[] = new int[6];
        int j, k;
        int counter = 0;
        
        for (a[0] = 1; a[0] <= 6; a[0]++)
            for (a[1] = 1; a[1] <= 6; a[1]++)
                for (a[2] = 1; a[2] <= 6; a[2]++)
                    for (a[3] = 1; a[3] <= 6; a[3]++)
                        for (a[4] = 1; a[4] <= 6; a[4]++)
                            for (a[5] = 1; a[5] <= 6; a[5]++)   {
            boolean test = false;
            for (j=0; j<6; j++)
                for (k=0; k<j; k++) {
                  if (k >= j) continue;
                  if (a[j] == a[k])
                      test = true;
                  else if (a[j] <= 3 && a[k] <= 3 && a[k] > a[j])
                      test = true;
                  else if (a[j] >= 4 && a[k] >= 4 && a[k] > a[j])
                      test = true;
                }
            if (test == true) continue;
            System.out.println(a[0] + " " + a[1] + " " + a[2] + " " + a[3] + " " + a[4] + " " + a[5]);
            counter++;
       }
        
        System.out.println("Number of possible sequence = " + counter);
    }
    
}
