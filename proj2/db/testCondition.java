package db;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by BlackIce on 2017/7/11.
 */
public class testCondition {
    @Test
    public void testEvaluateOperation() {
        Table table = new Table("examples/t1.tbl");
        Condition condition = new Condition("x int", ">", "5");
        Row row0 = table.getRow(0);
        Row row1 = table.getRow(1);
        Row row2 = table.getRow(2);

        Column column = table.findColumn(condition.columnName);

        int e0 = Integer.parseInt(row0.getElement(column));
        int e1 = Integer.parseInt(row1.getElement(column));
        int e2 = Integer.parseInt(row2.getElement(column));

        assertFalse(condition.evaluateOperation(e0));
        assertTrue(condition.evaluateOperation(e1));
        assertTrue(condition.evaluateOperation(e2));
    }

    @Test
    public void testEvaluateTable01() {
        Table table = new Table("examples/t1.tbl");
        Condition condition = new Condition("x int", ">", "5");
        Table actual = condition.evaluateTable(table);

        /* create expected table */
        Table expect = new Table(table.columnNames);
        String[] content;
        content = new String[]{"8","3"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"13","7"}; // 2nd row
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
    }

    @Test
    public void testEvaluateTable02() {
        Table table = new Table("examples/t2.tbl");
        Condition condition = new Condition("z int", "<", "7");
        Table actual = condition.evaluateTable(table);

        /* create expected table */
        Table expect = new Table(table.columnNames);
        String[] content;
        content = new String[]{"2","4"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"10","1"}; // 2nd row
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
    }
}
