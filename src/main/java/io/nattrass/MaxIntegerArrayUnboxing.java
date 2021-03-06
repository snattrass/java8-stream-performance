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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Fork(value = 2)
@Warmup(iterations = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MaxIntegerArrayUnboxing {

  private static Integer[] randoms;

  @Setup
  public static void init () {
    randoms = RandomNumbers.INSTANCE.getIntegerArray();
  }

  @Benchmark
  public Integer _for () {
    Integer max = 0;
    for (int i = 0; i < randoms.length; i++) {
      max = integerMax(max, randoms[i]);
    }

    return max;
  }

  @Benchmark
  public Integer _forEach () {
    Integer max = 0;
    for (Integer value : randoms) {
      max = integerMax(max, value);
    }

    return max;
  }

  @Benchmark
  public Integer _lambda () {
    return Arrays.stream(randoms).mapToInt(Integer::intValue).reduce(0, (a, b) -> Math.max(a, b));
  }

  @Benchmark
  public Integer _stream () {
    return Arrays.stream(randoms).mapToInt(Integer::intValue).reduce(0, Math::max);
  }

  @Benchmark
  public Integer _parallelStream () {
    return Arrays.stream(randoms).mapToInt(Integer::intValue).parallel().reduce(0, Math::max);
  }

  // helper function to avoid auto-boxing.  We're still unboxing (which we can't avoid) but it's explicitly done
  private Integer integerMax(Integer a, Integer b) {
    return a.intValue() > b.intValue() ? a : b;
  }
}
