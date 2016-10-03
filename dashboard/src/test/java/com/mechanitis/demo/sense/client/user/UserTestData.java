package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.client.RandomGenerator;
import com.mechanitis.demo.sense.client.StubService;

public class UserTestData {
    private static final String[] EXAMPLE_HANDLES = {"aaa", "bbb", "ccc", "ddd", "eee", "fff", "gee", "ggg",
            "hhh", "iii", "jjj", "kkk", "lll", "mmm", "nnn", "ooo", "ppp", "qqq", "rrr", "sss", "ttt", "uuu", "vvv",
            "www", "xxx", "yyy", "zzz"};

    public static void main(String[] args) {
        new StubService("/users/", 8083, RandomGenerator.of(EXAMPLE_HANDLES)).run();
    }
}