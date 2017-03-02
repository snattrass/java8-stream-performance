package io.nattrass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum RandomNumbers {
  INSTANCE;

  private static final int NUMBER_OF_ELEMENTS = 1_000_000;
  private final int[] randoms;
  private final Integer[] randomIntegers;
  private final List<Integer> randomIntegerList;

  RandomNumbers() {
    randomIntegers = generateArrayOfRandomPositiveIntegers();
    randomIntegerList = new ArrayList<>(Arrays.asList(randomIntegers));
    randoms = Arrays.stream(randomIntegers).mapToInt(Integer::intValue).toArray();
  }

  public int[] getIntArray() {
    return randoms;
  }

  public Integer[] getIntegerArray() {
    return randomIntegers;
  }

  public List<Integer> getRandomIntegerList() {
    return randomIntegerList;
  }

  private static Integer[] generateArrayOfRandomPositiveIntegers () {
    Random random = new Random();
    Integer[] array = new Integer[NUMBER_OF_ELEMENTS];

    for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
        array[i] = random.nextInt(Integer.MAX_VALUE);
    }

    return array;
  }
}
