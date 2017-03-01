package io.nattrass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@Fork(value = 2)
@Warmup(iterations = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Max {

  private static final int NUMBER_OF_ELEMENTS = 1_000_000;

  private static int[] randoms;
  private static Integer[] randomIntegers;
  private static List<Integer> randomIntegerList;

  @Setup
  public static void init () {
    randomIntegers = arrayOfRandomIntegers();
    randomIntegerList = new ArrayList<>(Arrays.asList(randomIntegers));
    randoms = Arrays.stream(randomIntegers).mapToInt(Integer::intValue).toArray();
  }

  // int[]

  @Benchmark
  public int intArray_for () {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < randoms.length; i++) {
      max = Math.max(max, randoms[i]);
    }

    return max;
  }

  @Benchmark
  public int intArray_forEach () {
    int max = Integer.MIN_VALUE;
    for (int value : randoms) {
      max = Math.max(max, value);
    }

    return max;
  }

  @Benchmark
  public int intArray_lambda () {
    return Arrays.stream(randoms).reduce(Integer.MIN_VALUE, (a, b) -> Math.max(a, b));
  }

  @Benchmark
  public int intArray_stream () {
    return Arrays.stream(randoms).max().getAsInt();
  }

  @Benchmark
  public int intArray_parallelStream () {
    return Arrays.stream(randoms).parallel().max().getAsInt();
  }

  // Integer[]

  @Benchmark
  public Integer integerArray_for () {
    Integer max = Integer.MIN_VALUE;
    for (int i = 0; i < randomIntegers.length; i++) {
      max = this.integerMax(max, randomIntegers[i]);
    }

    return max;
  }

  @Benchmark
  public Integer integerArray_forEach () {
    Integer max = Integer.MIN_VALUE;
    for (Integer value : randomIntegers) {
      max = this.integerMax(max, value);
    }

    return max;
  }

  @Benchmark
  public Integer integerArray_lambda () {
    return Arrays.stream(randomIntegers).reduce(Integer.MIN_VALUE, (a, b) -> integerMax(a, b));
  }

  @Benchmark
  public Integer integerArray_stream () {
    Optional<Integer> integerOptional = Arrays.stream(randomIntegers).reduce(this::integerMax);
    return integerOptional.get();
  }

  @Benchmark
  public Integer integerArray_parallelStream () {
    Optional<Integer> integerOptional =  Arrays.stream(randomIntegers).parallel().reduce(this::integerMax);
    return integerOptional.get();
  }

  // List<Integer>

  @Benchmark
  public Integer list_forEach () {
    Integer max = Integer.MIN_VALUE;
    for (Integer value : randomIntegerList) {
      max = integerMax(max, value);
    }

    return max;
  }

  @Benchmark
  public Integer list_lambda () {
    return randomIntegerList.stream().reduce(Integer.MIN_VALUE, (a, b) -> integerMax(a, b)); // avoid unboxing
  }

  @Benchmark
  public Integer list_stream () {
    Optional<Integer> integerOptional = randomIntegerList.stream().reduce(this::integerMax);
    return integerOptional.get();
  }

  @Benchmark
  public Integer list_parallelStream () {
    Optional<Integer> integerOptional =  randomIntegerList.stream().parallel().reduce(this::integerMax);
    return integerOptional.get();
  }

  private static Integer[] arrayOfRandomIntegers () {
    Random random = new Random();
    Integer[] array = new Integer[NUMBER_OF_ELEMENTS];

    for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
      array[i] = random.nextInt();
    }

    return array;
  }

  // helper function to avoid the unboxing
  private Integer integerMax(Integer a, Integer b) {
      return a.intValue() > b.intValue() ? a : b;
  }
}
