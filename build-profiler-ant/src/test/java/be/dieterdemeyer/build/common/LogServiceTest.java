package be.dieterdemeyer.build.common;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class LogServiceTest {

    private LogService logService;

    @Before
    public void createLogService() {
        logService = new LogService();
    }

    @Test
    public void emptyLogServiceHasEmptyIterator() {
        assertFalse("Expected empty iterator but was " + formatEntries(logService),
                logService.iterator().hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void poppingEmptyLogServiceThrowsNoSuchElementException() {
        logService.pop();
    }

    @Test
    public void pushingEntryReturnsSameEntry() {
        Entry entry = new Entry("pushedEntry", "entry");

        assertSame(entry, logService.push(entry));
    }

    @Test
    public void pushedEntryIsReturnedFromIterator() {
        Entry entry = new Entry("pushedEntry", "entry");
        logService.push(entry);

        assertEntriesAreEqual(ImmutableList.of(entry), logService);
    }

    @Test
    public void pushedEntryIsReturnedFromPop() {
        Entry entry = new Entry("pushedEntry", "entry");
        logService.push(entry);

        assertSame(entry, logService.pop());
    }

    @Test
    public void iterationPreservesPoppedEntries() {
        List<Entry> entries = Lists.newLinkedList();
        for (int i = 0; i < 5; i++) {
            entries.add(new Entry("Entry #" + i, "entry"));
        }
        for (Entry entry : entries) {
            logService.push(entry);
        }
        for (int i = 0; i < 5; i++) {
            logService.pop();
        }
        assertEntriesAreEqual(entries, logService);
    }

    @Test
    public void popCallsCommitOnThePoppedEntry() {
        MockEntry entry = new MockEntry("mockEntry", "entry");

        logService.push(entry);
        assertFalse(entry.isCommitted());

        logService.pop();
        assertTrue(entry.isCommitted());
    }

    @Test
    public void pushAddsChildToParent() {
        Entry parent = new Entry("parent", "entry");
        logService.push(parent);
        assertHasEmptyChildren(parent);

        Entry firstChild = new Entry("child #1", "entry");
        logService.push(firstChild);
        logService.pop();

        Entry secondChild = new Entry("child #2", "entry");
        logService.push(secondChild);
        logService.pop();

        assertHasEmptyChildren(firstChild);
        assertHasEmptyChildren(secondChild);

        assertEntriesAreEqual(ImmutableList.of(firstChild, secondChild), parent.getChildren());

    }

    private static void assertHasEmptyChildren(Entry parent) {
        assertTrue("Expected empty children for entry " + parent + ", but got " + formatEntries(parent.getChildren()),
                Iterables.isEmpty(parent.getChildren()));
    }

    private static void assertEntriesAreEqual(Iterable<Entry> expected, Iterable<Entry> actual) {
        assertTrue("Expected " + formatEntries(expected) + ", but was " + formatEntries(actual),
                Iterables.elementsEqual(expected, actual));
    }

    private static String formatEntries(Iterable<Entry> entries) {
        return Joiner.on(", ").join(entries);
    }

    private static class MockEntry extends Entry {
        private boolean committed = false;

        public MockEntry(String name, String type) {
            super(name, type);
        }

        @Override
        public void commit() {
            super.commit();
            committed = true;
        }

        public boolean isCommitted() {
            return committed;
        }
    }

}