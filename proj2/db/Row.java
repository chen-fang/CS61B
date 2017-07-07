package db;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackIce on 2017/7/7.
 */
public class Row {
    protected String[] _content;

    /** Initialize an empty row with specified size */
    public Row(int n) {
        _content = new String[n];
    }

    public Row(String[] content) {
        _content = content;
    }

    public String get(int i) {
        return _content[i];
    }

    private String getElement(Column column) {
        return _content[column._columnIndex];
    }

    private void addElement(int index, String element) {
        _content[index] = element;
    }

    public Row getFrom(List<Column> listColumns) {
        int length = listColumns.size();
        String[] content = new String[length];
        for (int i = 0; i < length; i++) {
            Column column = listColumns.get(i);
            content[i] = this.getElement(column);
        }
        return new Row(content);
    }

    public static class testRow {
        /** One row, one column */
        @Test
        public void testGetFrom01() {
            Table table = new Table("examples/t1.tbl");

            Column column = new Column(1);
            ArrayList<Column> columnList = new ArrayList<>();
            columnList.add(column);

            Row row1 = table.getRow(1);
            Row actual = row1.getFrom(columnList);
            Row expect = new Row (new String[]{"3"});
            assertArrayEquals(actual._content, expect._content);
        }
        /** One row, multiple columns */
        @Test
        public void testGetFrom02() {
            Table table = new Table("examples/t1.tbl");

            ArrayList<Column> columnList = new ArrayList<>();
            Column column0 = new Column(0);
            columnList.add(column0);
            Column column1 = new Column(1);
            columnList.add(column1);

            Row row2 = table.getRow(2);
            Row actual = row2.getFrom(columnList);
            Row expect = new Row (new String[]{"13","7"});
            assertArrayEquals(expect._content, actual._content);
        }
    }

}
