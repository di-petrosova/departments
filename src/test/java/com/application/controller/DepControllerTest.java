package com.application.controller;

import org.junit.Assert;
import org.junit.Test;

public class DepControllerTest {
    private DepController depController = new DepController();

    @Test
    public void getSubstringTest() {
        String s = depController.getSubstring("123456");
        Assert.assertNotNull(s);
        Assert.assertEquals("ERROR!", "6", s);
    }

    @Test(expected = NullPointerException.class)
    public void getSubstringTest2() {
        depController.getSubstring(null);
    }
}
