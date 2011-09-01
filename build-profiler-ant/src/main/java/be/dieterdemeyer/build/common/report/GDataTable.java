package be.dieterdemeyer.build.common.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class GDataTable {

    List<Col> cols = new ArrayList<Col>();
    List<Row> rows = new ArrayList<Row>();
    private Row currentRow = null;

    public void addCol(String id, String label, String type) {
        cols.add(new Col(id, label, type));
    }

    public List<Col> getCols() {
        return cols;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void insertRow() {
        currentRow = new Row();
        rows.add(currentRow);
    }

    public void addData(int value, String format) {
        currentRow.add(value, format);
    }
    
    public void addData(String value, String format) {
        currentRow.add(value, format);
    }

    private class Col {
        String id;
        String label;
        String type;

        private Col(String id, String label, String type) {
            this.id = id;
            this.label = label;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public String getLabel() {
            return label;
        }

        public String getType() {
            return type;
        }
    }

    private class Row {
        List<Map> c = new ArrayList<Map>();
        public void add(int v, String f) {
            final Map hashMap = new HashMap();
            hashMap.put("v", v);
            hashMap.put("f", f);
            c.add(hashMap);
        }

        public void add(String v, String f) {
            final Map hashMap = new HashMap();
            hashMap.put("v", v);
            hashMap.put("f", f);
            c.add(hashMap);
        }

    }

}