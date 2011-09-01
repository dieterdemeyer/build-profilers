package be.dieterdemeyer.build.common;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

public class EntryTest {

    @Test
    public void newEntryHasZeroDuration() {
        assertEquals(0, new Entry("test", "entry").getDuration());
    }

    @Test
    public void durationIsCalculatedCorrectly() {
        Clock clock = MockClockBuilder.startNow().nextAfter(5, SECONDS).build();
        Entry entry = new Entry("test", "entry", clock);
        entry.commit();

        assertEquals(5000, entry.getDuration());
    }

    @Test
    public void durationIsFormattedCorrectly() {
        Clock clock = MockClockBuilder.startNow().nextAfter(10, SECONDS).build();
        Entry entry = new Entry("test", "entry", clock);
        entry.commit();

        assertEquals("10000", entry.getDurationStr());
    }

    @Test
    public void percentageIsFormattedCorrectly() {
        Entry entry = new Entry("test", "entry");
        entry.setPc(0.678912f);

        assertEquals("67,891", entry.getPcStr());
    }

    private static class MockClockBuilder {
        private LinkedList<Long> timeSeries = Lists.newLinkedList();

        private MockClockBuilder(long timestamp) {
            timeSeries.add(timestamp);
        }

        static MockClockBuilder startNow() {
            return startWith(System.currentTimeMillis());
        }

        static MockClockBuilder startWith(long timestamp) {
            return new MockClockBuilder(timestamp);
        }

        MockClockBuilder nextTime(long timestamp) {
            timeSeries.add(timestamp);
            return this;
        }

        MockClockBuilder nextAfter(long duration, TimeUnit unit) {
            return nextTime(unit.toMillis(duration) + timeSeries.getLast());
        }

        Clock build() {
            return new Clock() {
                public long currentTimeMillis() {
                    return timeSeries.pop();
                }
            };
        }
    }

}