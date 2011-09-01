package be.dieterdemeyer.build.maven;

import org.apache.maven.DefaultMaven;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.reactor.MavenExecutionException;

public class BProfMavenWrapper extends DefaultMaven {

    public void execute(MavenExecutionRequest request) throws MavenExecutionException {
        BProfEventMonitor eventMonitor = new BProfEventMonitor();
        request.addEventMonitor(eventMonitor);
        eventMonitor.started();
        try {
            super.execute(request);
        } finally {
            eventMonitor.finished();
        }
    }
    
}