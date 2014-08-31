package com.github.listenumbers.generator;

import java.util.List;

/**
 * Daneel Yaitskov
 */
public class JoinGenerator implements TextGenerator {
    private final List<? extends TextGenerator> forward;

    public JoinGenerator(List<? extends TextGenerator> forward) {
        this.forward = forward;
    }

    @Override
    public String generate() {
        StringBuilder result = new StringBuilder();
        for (TextGenerator generator : forward) {
            result.append(generator.generate());
        }
        return result.toString();
    }
}
