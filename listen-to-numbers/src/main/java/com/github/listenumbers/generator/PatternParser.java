package com.github.listenumbers.generator;

import com.github.listenumbers.generator.ConstGenerator;
import com.github.listenumbers.generator.DigitGenerator;
import com.github.listenumbers.generator.JoinGenerator;
import com.github.listenumbers.generator.TextGenerator;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Daneel Yaitskov
 */
public class PatternParser {
    public TextGenerator parse(String pattern) {
        ConstGenerator space = new ConstGenerator(" ");
        DigitGenerator digit = new DigitGenerator();
        List<TextGenerator> result = Lists.newArrayList();
        for (char c : pattern.toCharArray()) {
            switch (c) {
                case 'd':
                case 'D':
                    result.add(digit);
                    break;
                case ' ':
                    result.add(space);
                    break;
            }
        }
        return new JoinGenerator(result);
    }
}
