package com.company;

import java.util.List;
import java.util.Random;

public class Dictionary {
    private static final Random random = new Random();
    private static final List<String> WORDS = List.of("адреса", "болезни", "возвращения", "варвары", "городки", "залежи");

    public String getRandomWord() {
        return WORDS.get(random.nextInt(WORDS.size()));
    }
}
