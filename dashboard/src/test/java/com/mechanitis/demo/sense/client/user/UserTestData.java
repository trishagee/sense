package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.client.StubService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Deprecated(since = "forever", forRemoval = true)
public class UserTestData {
    private static final List<String> EXAMPLE_HANDLES = List.of("aaa", "bbb",
            "ccc", "ddd", "eee", "fff", "gee", "ggg", "hhh", "iii", "jjj",
            "kkk", "lll", "mmm", "nnn", "ooo", "ppp", "qqq", "rrr", "sss",
            "ttt", "uuu", "vvv", "www", "xxx", "yyy", "zzz");

    public static void main(String[] args) {
        Random random = new Random();
        new StubService("/users/", 8083,
                        () -> EXAMPLE_HANDLES.get(random.nextInt(27))).run();
    }

    @Test
    public void shouldDoSOmething() {
        ArrayList<String> strings = ((Map) new ArrayList<>())
    }
}
