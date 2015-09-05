package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.client.StubService;

import java.util.Random;

public class UserTestData {
    private static final String[] EXAMPLE_HANDLES = {"aaa", "bbb", "ccc", "ddd", "eee", "fff", "gee", "ggg",
            "hhh", "iii", "jjj", "kkk", "lll", "mmm", "nnn", "ooo", "ppp", "qqq", "rrr", "sss", "ttt", "uuu", "vvv",
            "www", "xxx", "yyy", "zzz"};

    public static void main(String[] args) {
        Random random = new Random();
        new StubService("/users/", 8083, () -> EXAMPLE_HANDLES[random.nextInt(EXAMPLE_HANDLES.length)]).run();
    }
}
