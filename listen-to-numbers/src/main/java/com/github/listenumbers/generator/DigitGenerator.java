package com.github.listenumbers.generator;

import java.util.Random;

/**
 * Daneel Yaitskov
 */
public class DigitGenerator implements TextGenerator {
    private final Random random = new Random();

    @Override
    public String generate() {
        return String.valueOf(random.nextInt(10));
    }
}
