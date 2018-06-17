package com.irunninglog.api;

import com.irunninglog.api.progress.Progress;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ProgressTest {

    @Test
    public void sanity() {
        assertNotNull(Progress.BAD);
        assertNotNull(Progress.GOOD);
        assertNotNull(Progress.OK);
        assertNotNull(Progress.NONE);
    }

}
