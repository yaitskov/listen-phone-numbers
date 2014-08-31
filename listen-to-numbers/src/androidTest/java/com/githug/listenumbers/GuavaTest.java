package com.githug.listenumbers;

import android.test.AndroidTestCase;

import com.google.common.base.Optional;

import org.junit.Assert;

/**
 * Daneel Yaitskov
 */
public class GuavaTest extends AndroidTestCase {
    public void testOptional() {
        Assert.assertEquals(123, (int)(Integer) Optional.fromNullable(null).or(123));
        Assert.assertEquals(321, (int) Optional.fromNullable(321).or(123));
    }
}
