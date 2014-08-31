package com.githug.listenumbers.generator;

import android.test.AndroidTestCase;

import com.github.listenumbers.generator.ConstGenerator;
import com.github.listenumbers.generator.JoinGenerator;
import com.github.listenumbers.generator.PatternParser;
import com.github.listenumbers.generator.TextGenerator;

import junit.framework.Assert;

import java.util.Arrays;

/**
 * Daneel Yaitskov
 */
public class TextGeneratorTest extends AndroidTestCase {
    public void testJoinConst() {
        TextGenerator tg = new JoinGenerator(Arrays.asList(
                new ConstGenerator("1"),
                new ConstGenerator(" "),
                new ConstGenerator("2")));

        Assert.assertEquals("1 2", tg.generate());
    }

    public void testPatternParser() {
        PatternParser parser = new PatternParser();
        TextGenerator tg = parser.parse("d dd ddd ");
        String number = tg.generate();
        Assert.assertTrue(number, number.matches("[0-9] [0-9][0-9] [0-9][0-9][0-9] "));
    }
}
