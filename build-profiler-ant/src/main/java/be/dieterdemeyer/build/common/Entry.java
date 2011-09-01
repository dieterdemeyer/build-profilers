package be.dieterdemeyer.build.common;

import com.google.common.collect.ImmutableList;

import java.util.LinkedList;
import java.util.List;

public class Entry {

    private final String name; // the name
    private final String type;
    private final Clock clock;
    private final long start; // the start time

    private final List<Entry> children = new LinkedList<Entry>();

    private long duration; // the end time
    private float pc; // the percentage

    public Entry(String name, String type) {
        this(name, type, Clock.SYSTEM);
    }

    public Entry(String name, String type, Clock clock) {
        this.name = name;
        this.type = type;
        this.clock = clock;

        this.start = clock.currentTimeMillis();
    }

    public void commit() {
        duration = clock.currentTimeMillis() - start;
    }

    public long getDuration() {
        return duration;
    }

    public String getEntryName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPcStr() {

        return String.format("%.3f", 100f * pc);
    }

    public String getDurationStr() {

        return String.format("%d", getDuration());
    }

    public void setPc(float pc) {
        this.pc = pc;
    }

    public Entry addChildEntry(Entry entry) {
        children.add(entry);
        return entry;
    }

    public Iterable<Entry> getChildren() {
        return ImmutableList.copyOf(children);
    }
    
}