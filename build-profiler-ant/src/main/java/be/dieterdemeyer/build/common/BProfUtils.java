package be.dieterdemeyer.build.common;


import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

/**
 */
public class BProfUtils {

    public static String getJarPath() {
        final URL url = BProfUtils.class.getProtectionDomain().getCodeSource().getLocation();
        try {

            return new File(url.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            Logger.getAnonymousLogger().warning("Could not locate bprof jar path. " + e.getMessage());
            return new File(url.getPath()).getAbsolutePath();
        }
    }
    
}