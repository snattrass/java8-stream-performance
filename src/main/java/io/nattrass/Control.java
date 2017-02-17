package io.nattrass;

import java.util.Arrays;
import java.util.OptionalInt;
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
import org.openjdk.jmh.infra.Blackhole;

@Fork(value = 2)
@Warmup(iterations = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Control {

    private static final int NUMBER_OF_ELEMENTS = 500_000;

    private static int[] intArray;

    @Setup
    public static void init() {
      intArray = initializeArrayOfRandomInts();
    }

    @Benchmark
    public void array_for(Blackhole hole) {
      for (int i = 0; i < intArray.length; i++) {
        hole.consume(intArray[i]);
      }
    }

    @Benchmark
    public void array_forEach(Blackhole hole) {
      for (int intValue : intArray) {
        hole.consume(intValue);
      }
    }
    @Benchmark
    public void array_lambda (Blackhole hole) {
        Arrays.stream(intArray).forEach(value -> hole.consume(value));
    }

    @Benchmark
    public void array_stream (Blackhole hole) {
        Arrays.stream(intArray).forEach(hole::consume);
    }

    @Benchmark
    public void array_parallelStream (Blackhole hole) {
        Arrays.stream(intArray).parallel().forEach(hole::consume);
    }

    private static int[] initializeArrayOfRandomInts() {
        Random random = new Random();
        int[] array = new int[NUMBER_OF_ELEMENTS];

        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            array[i] = random.nextInt();
        }

        return array;
    }
}
