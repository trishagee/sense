package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.client.StubService;

import java.util.Random;
import java.util.function.Supplier;

public class UserTestData {
    private static final String[] EXAMPLE_HANDLES = {"aaa", "bbb", "ccc", "ddd", "eee", "fff", "gee", "ggg",
            "hhh", "iii", "jjj", "kkk", "lll", "mmm", "nnn", "ooo", "ppp", "qqq", "rrr", "sss", "ttt", "uuu", "vvv",
            "www", "xxx", "yyy", "zzz"};

    private static final Supplier<String> RANDOM_SAMPLE_TWITTER_HANDLE = () -> EXAMPLE_HANDLES[new Random().nextInt(EXAMPLE_HANDLES.length)];

    public static void main(String[] args) {
        new StubService("/users/", 8083, RANDOM_SAMPLE_TWITTER_HANDLE).run();
    }
}
