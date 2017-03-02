package io.nattrass;

import java.util.Arrays;
import java.util.OptionalInt;

public class SlideContent {
    public static void main(String[] args) {
        int[] someArray = { 5, 6, 1, 99, 101, 3, 42, 1, 4};

        int max1 = 0;
        for (int i = 0; i < someArray.length; i++) {
            max1 = Math.max(max1, someArray[i]);
        }
        System.out.println(max1);

        int max2 = 0;
        for (int value : someArray) {
            max2 = Math.max(max2, value);
        }
        System.out.println(max2);

        OptionalInt optionalInt1 = Arrays.stream(someArray).max();
        int max3 = optionalInt1.getAsInt();
        System.out.println(max3);

        OptionalInt optainalInt2 = Arrays.stream(someArray).reduce((a, b) -> Math.max(a , b));
        int max4 = optainalInt2.getAsInt();
        System.out.println(max4);
    }
}
