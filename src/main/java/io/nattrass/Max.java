package io.nattrass;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Fork(value = 1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Max {

  private static final int NUMBER_OF_ELEMENTS = 500_000;

  private static int[] intArray;
  private static Integer[] integerArray;
  private static List<Integer> integerList;

  @Setup
  public static void init () {
    integerArray = arrayOfRandomIntegers();
    integerList = new ArrayList<>(Arrays.asList(integerArray));
    intArray = Arrays.stream(integerArray).mapToInt(Integer::intValue).toArray();
  }

  // int[]

  @Benchmark
  public int intArray_for () {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < intArray.length; i++) {
      if (intArray[i] > max) {
        max = intArray[i];
      }
    }

    return max;
  }

  @Benchmark
  public int intArray_forEach () {
    int max = Integer.MIN_VALUE;
    for (int value : intArray) {
      if (value > max) {
        max = value;
      }
    }

    return max;
  }

  @Benchmark
  public int intArray_stream () {
    return Arrays.stream(intArray).max().getAsInt();
  }

  @Benchmark
  public int intArray_lambda () {
    return Arrays.stream(intArray).reduce(Integer.MIN_VALUE, Integer::max);
  }

  // Integer[]

  @Benchmark
  public int integerArray_for () {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < integerArray.length; i++) {
      if (integerArray[i] > max) {
        max = integerArray[i];
      }
    }

    return max;
  }

  @Benchmark
  public int integerArray_forEach () {
    int max = Integer.MIN_VALUE;
    for (int value : integerArray) {
      if (value > max) {
        max = value;
      }
    }

    return max;
  }

  @Benchmark
  public int integerArray_stream () {
    return Arrays.stream(integerArray).mapToInt(Integer::intValue).max().getAsInt();
  }


  // List<Integer>

  // @Benchmark
  // public int list_max_for() {
  // int max = Integer.MIN_VALUE;
  // for (int i = 0; i < listOfRandomIntegers().length; i++) {
  // if(intArray[i] > max) {
  // max = intArray[i];
  // }
  // }
  //
  // return max;
  // }

  @Benchmark
  public int list_forEach () {
    int max = Integer.MIN_VALUE;
    for (Integer value : integerList) {
      if (value > max) {
        max = value;
      }
    }

    return max;
  }

  @Benchmark
  public int list_stream () {
    return integerList.stream().mapToInt(Integer::intValue).max().getAsInt();
  }

  private static Integer[] arrayOfRandomIntegers () {
    Random random = new Random();
    Integer[] array = new Integer[NUMBER_OF_ELEMENTS];

    for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
      array[i] = random.nextInt();
    }

    return array;
  }
}
