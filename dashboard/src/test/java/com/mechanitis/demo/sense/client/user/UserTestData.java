package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.client.StubService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UserTestData {
    private static final Set<String> EXAMPLE_HANDLES = new HashSet<>(Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee",
                                                                                   "fff", "gee", "ggg", "hhh", "iii",
                                                                                   "jjj", "kkk", "lll", "mmm", "nnn",
                                                                                   "ooo", "ppp", "qqq", "rrr", "sss",
                                                                                   "ttt", "uuu", "vvv", "www", "xxx",
                                                                                   "yyy", "zzz"));

    public static void main(String[] args) {
        new StubService("/users/", 8083,
                        UserTestData::getRandomValue).run();
    }

    private static String getRandomValue() {
        Random random = new Random();

        int requiredIndex = random.nextInt(27);
        int i = 0;
        for (String handle : EXAMPLE_HANDLES) {
            if (i == requiredIndex)
                return handle;
            i = i + 1;
        }
        return null;
    }
}
