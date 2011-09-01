package be.dieterdemeyer.build.common.report;

import be.dieterdemeyer.build.common.LogService;
import be.dieterdemeyer.build.common.Entry;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class HtmlReport {

    private final File outdir;
    private final LogService service;

    public HtmlReport(File outdir, LogService service) {
        this.outdir = outdir;
        this.service = service;
    }

    public void generate() throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(HtmlReport.class, "/templates/");
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setStrictBeanModels(false);
        Template temp = cfg.getTemplate("index.ftl");
        
        Map<String, Object> context = new HashMap<String, Object>();

        // munge the data, get the max time
        long max = 0;
        for (Entry entry : service) {
            max = entry.getDuration() > max ? entry.getDuration() : max;
        }
        System.out.println("max = " + max);

        for (Entry entry : service) {
            float pc = (float)entry.getDuration()/(float)max;
            entry.setPc(pc);
            System.out.println(entry.getDuration() + " pc = " + entry.getPcStr());
        }

        context.put("events", service.iterator());
        writeToFile(context, temp, "index.html");
        
        // do json...
        JsonReport json = new JsonReport(outdir, service);
        context.put("data", json.generate());
        writeToFile(context, cfg.getTemplate("data.ftl"), "data.js");

    }

    private void writeToFile(Object root, Template html, String filename) throws IOException, TemplateException {
        final Writer fWriter = new FileWriter(new File(outdir, filename));
        html.process(root, fWriter);
        fWriter.flush();
    }

}