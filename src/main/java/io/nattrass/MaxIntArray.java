package io.nattrass;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Fork(value = 2)
@Warmup(iterations = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MaxIntArray {

  private static int[] randoms;

  @Setup
  public static void init () {
    randoms = RandomNumbers.INSTANCE.getIntArray();
  }

  @Benchmark
  public int _for () {
    int max = 0;
    for (int i = 0; i < randoms.length; i++) {
      max = Math.max(max, randoms[i]);
    }

    return max;
  }

  @Benchmark
  public int _forEach () {
    int max = 0;
    for (int value : randoms) {
      max = Math.max(max, value);
    }

    return max;
  }

  @Benchmark
  public int _lambda () {
    return Arrays.stream(randoms).reduce(0, (a, b) -> Math.max(a, b));
  }

  @Benchmark
  public int _stream () {
    return Arrays.stream(randoms).reduce(0, Math::max);
  }

  @Benchmark
  public int _parallelStream () {
    return Arrays.stream(randoms).parallel().reduce(0, Math::max);
  }
}
