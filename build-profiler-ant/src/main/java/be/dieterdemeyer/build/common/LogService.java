package be.dieterdemeyer.build.common;

import java.util.Iterator;
import java.util.LinkedList;

public class LogService implements Iterable<Entry> {

    private final LinkedList<Entry> entries = new LinkedList<Entry>();
    private final LinkedList<Entry> entryStack = new LinkedList<Entry>();

    public Entry push(Entry entry) {
        if (!entryStack.isEmpty()) {
            entryStack.getLast().addChildEntry(entry);
        }
        entryStack.add(entry);
        entries.add(entry);
        return entry;
    }

    public Iterator<Entry> iterator() {
        return entries.iterator();
    }

    public Entry pop() {
        Entry entry = entryStack.removeLast();
        entry.commit();
        return entry;
    }

}