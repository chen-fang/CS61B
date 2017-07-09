package db;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by BlackIce on 2017/7/7.
 */
public class testTable {
    @Test
    public void testLoadFromFile() {
        Table table = new Table("examples/t1.tbl");
        System.out.println();
        table.print();
    }

    @Test
    public void testNumRowCol() {
        Table table = new Table("examples/t1.tbl");
        int rows = table.numRows();
        int cols = table.numColumns();
        assertEquals(rows,3);
        assertEquals(2,cols);
    }

    @Test
    public void testContains() {
        Table table = new Table("examples/t1.tbl");
        assertEquals(0, table.contains("x int"));
        assertEquals(1, table.contains("y int"));
        assertEquals(-1, table.contains("z int"));
    }

    @Test
    public void testFindColumn() {
        // See testColumn.java
    }

    @Test
    public void testSelect01() {
        Table table = new Table("examples/t1.tbl");
        String[] columnNames = new String[1];
        columnNames[0] = "y int";
        Table newTable = Table.select(columnNames, table);
        assertEquals(1, newTable.numColumns());
        assertEquals(3, newTable.numRows());

        /* create expected table */
        String[] name = new String[]{"y int"}; // column name
        Table expect = new Table(name);
        String[] content;
        content = new String[]{"5"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"3"}; // 2nd row
        expect.addRow(new Row(content));
        content = new String[]{"7"}; // 3rd row
        expect.addRow(new Row(content));

        assertTrue(newTable.equals(expect));

    }

    @Test
    public void testSelect02() {
        Table table1 = new Table("examples/t1.tbl");
        Table table2 = new Table("examples/t2.tbl");
        String[] selectedColumnNames = new String[]{"y int","z int"};
        Table newTable = Table.select(selectedColumnNames, table1, table2);

        /* create expected table */
        Table expect = new Table(selectedColumnNames);
        String[] content;
        content = new String[]{"5","4"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"3","9"}; // 2nd row
        expect.addRow(new Row(content));

        assertTrue(newTable.equals(expect));
    }

    /** select three columns from "records.tbl" and "teams.tbl" */
    @Test
    public void testSelect03() {
        Table table1 = new Table("examples/teams.tbl");
        Table table2 = new Table("examples/records.tbl");

        String[] selectedColumnNames = new String[]{"City string","Season int", "Wins int"};
        Table newTable = Table.select(selectedColumnNames, table1, table2);
        newTable.print();
    }
}
