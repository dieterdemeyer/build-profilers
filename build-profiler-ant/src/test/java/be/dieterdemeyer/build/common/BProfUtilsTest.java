package be.dieterdemeyer.build.common;

import junit.framework.TestCase;

/**
 */
public class BProfUtilsTest extends TestCase {

    public void testGetJarPath() throws Exception {
        assertNotNull(BProfUtils.getJarPath());
    }
    
}