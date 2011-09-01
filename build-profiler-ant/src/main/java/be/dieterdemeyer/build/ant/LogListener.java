package be.dieterdemeyer.build.ant;

import java.io.File;
import java.io.IOException;

import be.dieterdemeyer.build.common.Entry;
import be.dieterdemeyer.build.common.report.HtmlReport;
import be.dieterdemeyer.build.common.report.JsonReport;
import be.dieterdemeyer.build.common.LogService;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;

import freemarker.template.TemplateException;

/**
 * This class collects all events and feeds them into the model.
 *
 */
public class LogListener implements BuildListener {

    private final LogService service = new LogService();

    public void buildStarted(BuildEvent event) {
        Entry buildEntry = service.push(new Entry("Build Started", "build"));
        System.out.println("buildEntry = " + buildEntry);
    }

    public void buildFinished(BuildEvent event) {
        Entry buildEntry = service.pop();
        if (buildEntry == null)
        {
            throw new NullPointerException("Unexpected null build entry");
        }
        if (!"build".equals(buildEntry.getType()))
        {
            throw new AssertionError("Mismatched entry type: expected 'build' but was " + buildEntry.getType());
        }
        System.out.println("buildEntry = " + buildEntry);

        // generate a report, on build finished.
        // get the output dir property...

        final String bprofDirFromAnt = event.getProject().getProperty("bprof.home");
        final String bprofDir = System.getProperty("bprof.home", bprofDirFromAnt != null ? bprofDirFromAnt : ".bprof");
        final File outdir = event.getProject().resolveFile(bprofDir);
        outdir.mkdirs();
        try {
            new JsonReport(outdir, service).generate();
            new HtmlReport(outdir, service).generate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    public void targetStarted(BuildEvent event) {
        service.push(new Entry(event.getTarget().getName(), "target"));
    }

    public void targetFinished(BuildEvent event) {
        Entry targetEntry = service.pop();
        if (targetEntry == null)
        {
            throw new NullPointerException("Unexpected null target entry");
        }
        if (!"target".equals(targetEntry.getType()))
        {
            throw new AssertionError("Mismatched entry type: expected 'target' but was " + targetEntry.getType());
        }
    }

    public void taskStarted(BuildEvent event) {
        service.push(new Entry(event.getTask().getTaskName(), "task"));
    }

    public void taskFinished(BuildEvent event) {
        Entry taskEntry = service.pop();
        if (taskEntry == null)
        {
            throw new NullPointerException("Unexpected null task entry");
        }
        if (!"task".equals(taskEntry.getType()))
        {
            throw new AssertionError("Mismatched entry type: expected 'task' but was " + taskEntry.getType());
        }
    }

    public void messageLogged(BuildEvent event) {
        // do nothing
    }

}