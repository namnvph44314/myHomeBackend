package com.example.my_school.utils.random;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBER = "0123456789";
    private static final String ALL = UPPER + LOWER + NUMBER;

    public static String generatePassword() {
        List<Character> chars = new ArrayList<>();

        // Đảm bảo có ít nhất 1 ký tự mỗi loại
        chars.add(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
        chars.add(LOWER.charAt(RANDOM.nextInt(LOWER.length())));
        chars.add(NUMBER.charAt(RANDOM.nextInt(NUMBER.length())));

        // Thêm 3 ký tự ngẫu nhiên còn lại
        for (int i = 0; i < 3; i++) {
            chars.add(ALL.charAt(RANDOM.nextInt(ALL.length())));
        }

        // Xáo trộn vị trí
        Collections.shuffle(chars);

        StringBuilder result = new StringBuilder();
        chars.forEach(result::append);

        return result.toString();
    }
}
