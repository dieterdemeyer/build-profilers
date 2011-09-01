package be.dieterdemeyer.build.common.report;

import be.dieterdemeyer.build.common.LogService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 */
public class JsonReport {

    File outdir;
    LogService service;

    public JsonReport(File outdir, LogService service) {
        this.outdir = outdir;
        this.service = service;
    }

    public String generate() {
        Gson json = new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.PRIVATE).create();

        Map<String, GDataTable> data = new HashMap<String, GDataTable>();
        data.put("tasks", asTable(new ByTypeAggregator(service, "tasks").aggregate()));
        data.put("targets", asTable(new ByTypeAggregator(service, "targets").aggregate()));
        return json.toJson(data);
    }

    public GDataTable asTable(Map<String, Long> totals) {


//    "cols":[
//        {"id":"Task","label":"Task","type":"string"}, {"id":"Time","label":"Time","type":"number"}
//    ],
//    "rows":[
//        {"c":[{"v":"Property","f":"Prop"}, {"v":1192,"f":"1192"}]},
//        {"c":[{"v":"Javac","f":"fmt-JAVAC"}, {"v":1192,"f":"1192"}]},
//        {"c":[{"v":"Java","f":"fmt-JAVA"}, {"v":1192,"f":"1192"}]}
//
//    ]

        GDataTable table = new GDataTable();
        table.addCol("Task", "Task", "string");
        table.addCol("Time", "Time", "number");

        for (Iterator<String> iterator = totals.keySet().iterator(); iterator.hasNext();) {
            String s = iterator.next();
            table.insertRow();
            table.addData(s, s);
            table.addData(totals.get(s).intValue(), s);
        }
        return table;
    }

}