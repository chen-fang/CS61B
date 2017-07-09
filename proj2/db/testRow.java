package db;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Created by BlackIce on 2017/7/9.
 */
public class testRow {
    /** One row, one column */
    @Test
    public void testSubRow01() {
        Table table = new Table("examples/t1.tbl");

        Column column = new Column(1,0);
        ArrayList<Column> columnList = new ArrayList<>();
        columnList.add(column);

        Row row1 = table.getRow(1);
        Row actual = row1.subRow(columnList);
        Row expect = new Row (new String[]{"3"});
        assertArrayEquals(actual.content, expect.content);
    }
    /** One row, multiple columns */
    @Test
    public void testSubRow02() {
        Table table = new Table("examples/t1.tbl");

        ArrayList<Column> columnList = new ArrayList<>();
        Column column0 = new Column(0,0);
        columnList.add(column0);
        Column column1 = new Column(1,0);
        columnList.add(column1);

        Row row2 = table.getRow(2);
        Row actual = row2.subRow(columnList);
        Row expect = new Row (new String[]{"13","7"});
        assertArrayEquals(expect.content, actual.content);
    }
}

