package be.dieterdemeyer.build.common.report;

import be.dieterdemeyer.build.common.Entry;
import be.dieterdemeyer.build.common.LogService;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public final class ByTypeAggregator {

    private final LogService data;
    private final String type;

    public ByTypeAggregator(LogService service, String type) {
        this.data = checkNotNull(service, "service");
        this.type = checkNotNull(type, "type");
    }

    public Map<String, Long> aggregate() {
        final Map<String, Long> totals = new HashMap<String, Long>();
        Iterable<Entry> entries = filter();

        // sum all the durations...
        for (Entry entry : entries) {
            Long total = totals.get(entry.getEntryName());
            total = total == null ? 0 : total;
            totals.put(entry.getEntryName(), total + entry.getDuration());
        }
        return totals;
    }

    private Iterable<Entry> filter() {
        return Iterables.filter(data, new Predicate<Entry>() {
            public boolean apply(Entry entry) {
                return !type.equals(entry.getType());
            }
        });
    }

}