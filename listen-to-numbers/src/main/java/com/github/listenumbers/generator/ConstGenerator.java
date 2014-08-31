package com.github.listenumbers.generator;

/**
 * Daneel Yaitskov
 */
public class ConstGenerator implements TextGenerator {
    private final String output;

    public ConstGenerator(String output) {
        this.output = output;
    }

    @Override
    public String generate() {
        return output;
    }
}
