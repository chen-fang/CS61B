package db;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by BlackIce on 2017/7/14.
 */
public class testCondition {
    @Test
    public void testCompare() {
        Table table = Table.loadTable("examples/t1.tbl");
        Condition condition = new ConditionUnary("x int", ">", "5");
        Row row0 = table.getRow(0);
        Row row1 = table.getRow(1);
        Row row2 = table.getRow(2);

        Column column = table.findColumn(condition._colName0);

        String e0 = row0.getElement(column);
        String e1 = row1.getElement(column);
        String e2 = row2.getElement(column);

        assertFalse(condition.compare(e0, condition._operator, condition._operand));
        assertTrue(condition.compare(e1, condition._operator, condition._operand));
        assertTrue(condition.compare(e2, condition._operator, condition._operand));
    }

    /** select * from t1 where x > 5 */
    @Test
    public void testConditionUnaryEvaluate01() {
        Table table = Table.loadTable("examples/t1.tbl");
        Condition condition = new ConditionUnary("x", ">", "5");
        Table actual = condition.evaluate(table);
        /* create expected table */
        Table expect = new Table(table.columnNames, table);
        String[] content;
        content = new String[]{"8","3"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"13","7"}; // 2nd row
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
    }

    /** select * from t2 where z < 8 */
    @Test
    public void testConditionUnaryEvaluate02() {
        Table table = Table.loadTable("examples/t2.tbl");
        Condition condition = new ConditionUnary("z", "<", "7");
        Table actual = condition.evaluate(table);
        /* create expected table */
        Table expect = new Table(table.columnNames, table);
        String[] content;
        content = new String[]{"2","4"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"10","1"}; // 2nd row
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
    }
}
