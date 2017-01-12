/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.avaloq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

@Fork(value = 1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SimpleOperation {

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

  @Benchmark
  public int intArray_max_for () {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < intArray.length; i++) {
      if (intArray[i] > max) {
        max = intArray[i];
      }
    }

    return max;
  }

  @Benchmark
  public int intArray_max_forEach () {
    int max = Integer.MIN_VALUE;
    for (int value : intArray) {
      if (value > max) {
        max = value;
      }
    }

    return max;
  }

  @Benchmark
  public int intArray_max_stream () {
    return Arrays.stream(intArray).max().getAsInt();
  }

  @Benchmark
  public int intArray_sum_for () {
    int sum = 0;
    for (int i = 0; i < intArray.length; i++) {
      sum += intArray[i];
    }

    return sum;
  }

  @Benchmark
  public int intArray_sum_forEach () {
    int sum = 0;
    for (int value : intArray) {
      sum += value;
    }

    return sum;
  }

  @Benchmark
  public int intArray_sum_stream () {
    return Arrays.stream(intArray).sum();
  }

  // Integer Array

  @Benchmark
  public int integerArray_max_for () {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < integerArray.length; i++) {
      if (integerArray[i] > max) {
        max = integerArray[i];
      }
    }

    return max;
  }

  @Benchmark
  public int integerArray_max_forEach () {
    int max = Integer.MIN_VALUE;
    for (int value : integerArray) {
      if (value > max) {
        max = value;
      }
    }

    return max;
  }

  @Benchmark
  public int integerArray_max_stream () {
    return Arrays.stream(integerArray).mapToInt(Integer::intValue).max().getAsInt();
  }

  @Benchmark
  public int integerArray_sum_for () {
    int sum = 0;
    for (int i = 0; i < integerArray.length; i++) {
      sum += intArray[i];
    }

    return sum;
  }

  @Benchmark
  public int integerArray_sum_forEach () {
    int sum = 0;
    for (int value : integerArray) {
      sum += value;
    }

    return sum;
  }

  @Benchmark
  public int integerArray_sum_stream () {
    return Arrays.stream(integerArray).mapToInt(Integer::intValue).sum();
  }

  // Integer List

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
  public int list_max_forEach () {
    int max = Integer.MIN_VALUE;
    for (Integer value : integerList) {
      if (value > max) {
        max = value;
      }
    }

    return max;
  }

  @Benchmark
  public int list_max_stream () {
    return integerList.stream().mapToInt(Integer::intValue).max().getAsInt();
  }

  // @Benchmark
  // public int list_sum_for() {
  // int sum = 0;
  //
  // for (int i = 0; i < intArray.length; i++) {
  // sum += intArray[i];
  // }
  //
  // return sum;
  // }

  @Benchmark
  public int list_sum_forEach () {
    int sum = 0;
    for (int value : integerList) {
      sum += value;
    }

    return sum;
  }

  @Benchmark
  public int list_sum_stream () {
    return integerList.stream().mapToInt(Integer::intValue).sum();
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
