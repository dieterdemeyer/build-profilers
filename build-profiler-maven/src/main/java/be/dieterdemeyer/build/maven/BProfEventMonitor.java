package be.dieterdemeyer.build.maven;

import org.apache.maven.monitor.event.EventMonitor;

public class BProfEventMonitor implements EventMonitor {

    public void started() {
        System.err.println("BPROF started");
    }

    public void finished() {
        System.getProperties().list(System.err);
        System.err.println("BPROF finished");
    }


    public void startEvent(String eventName, String target, long timestamp) {
        System.err.println(String.format("BPROF started event '%s' with target '%s' at %tT",
                eventName, target, timestamp));
    }

    public void endEvent(String eventName, String target, long timestamp) {
        System.err.println(String.format("BPROF finished event '%s' with target '%s' at %tT",
                eventName, target, timestamp));
    }

    public void errorEvent(String eventName, String target, long timestamp, Throwable cause) {
        System.err.println(String.format("BPROF got error '%s' from event '%s' with target '%s' at %tT",
                cause, eventName, target, timestamp));
        cause.printStackTrace(System.err);
    }

}