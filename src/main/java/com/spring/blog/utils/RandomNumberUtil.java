package com.spring.blog.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumberUtil {
    private static int size = 0;
    public static String getKey(int size) {
        RandomNumberUtil.size = size;
        return generateAuthCode();
    }

    private static String generateAuthCode() {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();

        while (buffer.length() < size) {
            int num = random.nextInt(10);
            buffer.append(num);
        }

        return buffer.toString();
    }
}
