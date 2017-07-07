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
    public void testFind() {
        Table table = new Table("examples/t1.tbl");
        Column column01 = table.find("x int");
        Column column02 = table.find("y int");
        assertEquals(0,column01._columnIndex);
        assertEquals(1,column02._columnIndex);
    }

    @Test
    public void testSelect() {
        Table table = new Table("examples/t1.tbl");
        String[] columnNames = new String[1];
        columnNames[0] = "y int";
        Table newTable = Table.select(columnNames, table);
        assertEquals(1, newTable.numColumns());
        assertEquals(3, newTable.numRows());
        newTable.print();
    }
}
