package me.demo.springcloud.utils;

import java.util.function.Consumer;

public class MulitAssert {


    public static <T extends Object> void assertMulitpleTimes(int times, Consumer<T> consumer) {

        for (int i = 0; i < times; i++) {
            consumer.accept(null);
        }
    }
}
