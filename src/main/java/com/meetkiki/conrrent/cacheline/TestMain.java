package com.meetkiki.conrrent.cacheline;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class TestMain {

    private LongAdder longAdder = new LongAdder();
    private AtomicLong atomicLong = new AtomicLong();

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(TestMain.class.getName()).build();

        new Runner(options).run();
    }

    @Benchmark
    @Threads(8)
    public void runLongAdder() {
        for (int i = 0; i < 1000; i++) {
            longAdder.add(i);
        }
    }

    @Benchmark
    @Threads(8)
    public void runAtomicLong() {
        for (int i = 0; i < 1000; i++) {
            atomicLong.addAndGet(i);
        }
    }
}
