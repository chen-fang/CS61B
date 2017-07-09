package db;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by BlackIce on 2017/7/9.
 */
public class testColumn {
    @Test
    public void testFindColumn01() {
        Table t1 = new Table("examples/t1.tbl");
        Column c1 = t1.findColumn("x int");
        assertEquals(0,c1.getColumnIndex());
        assertEquals(-1,c1.getTableIndex());

        Column c2 = t1.findColumn("y int");
        assertEquals(1,c2.getColumnIndex());
        assertEquals(-1,c2.getTableIndex());
    }

    @Test
    public void testFindColumn02() {
        Table t1 = new Table("examples/t1.tbl");
        Table t2 = new Table("examples/t2.tbl");
        Column c1 = Column.findColumn("x int", t1, t2);
        assertEquals(0,c1.getColumnIndex());
        assertEquals(0,c1.getTableIndex());

        Column c2 = Column.findColumn("x int", t2, t1);
        assertEquals(0,c2.getColumnIndex());
        assertEquals(0,c2.getTableIndex());
    }

    /** select two columns from "t1.tbl" and "t2.tbl" */
    @Test
    public void testFindCommonColumns() {
        Table t1 = new Table("examples/t1.tbl");
        Table t2 = new Table("examples/t2.tbl");

        Column[] cc = Column.findCommonColumns("x int", t1, t2);
        assertEquals(0,cc[0].getColumnIndex());
        assertEquals(0,cc[1].getColumnIndex());
        assertEquals(0,cc[0].getTableIndex());
        assertEquals(1,cc[1].getTableIndex());

        Column[] CC = Column.findCommonColumns("y int", t1, t2);
        assertNull(CC);
    }
}
