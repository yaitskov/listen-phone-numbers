package com.github.listenumbers;

import com.google.common.base.Equivalence;

/**
 * Daneel Yaitskov
 */
public class SpacelessEqualizer extends Equivalence<String> {
    private String dropSpaces(String s) {
        return s.replaceAll(" ", "");
    }

    @Override
    protected boolean doEquivalent(String a, String b) {
        return dropSpaces(a).equals(dropSpaces(b));
    }

    @Override
    protected int doHash(String s) {
        return dropSpaces(s).hashCode();
    }
}
