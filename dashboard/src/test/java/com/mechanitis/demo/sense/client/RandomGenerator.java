package com.mechanitis.demo.sense.client;

import java.util.Random;
import java.util.function.Supplier;

public class RandomGenerator
{
    private static final Random RANDOM = new Random();

    public static <T> Supplier<T> of(T[] array)
    {
        return () -> array[ someRandomIndexIn(array) ];
    }

    private static <T> int someRandomIndexIn(T[] array)
    {
        int upperBound = array.length;
        return RANDOM.nextInt(upperBound);
    }
}
