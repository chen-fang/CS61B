package db;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by BlackIce on 2017/7/14.
 */
public class testCondition {
    /** Test typeOf(String literal) */
    @Test
    public void testTypeOf() {
        /* int */
        assertTrue(Condition.typeOf("1") == "int");
        assertTrue(Condition.typeOf("-1") == "int");
        assertFalse(Condition.typeOf("1s") == "int");

        /* float */
        assertTrue(Condition.typeOf("1.1f") == "float");
        assertTrue(Condition.typeOf("-1.1f") == "float");
        assertTrue(Condition.typeOf(".1f") == "float");
        assertTrue(Condition.typeOf("-.1f") == "float");
        assertFalse(Condition.typeOf("1.1") == "float");
        assertFalse(Condition.typeOf("s1.1f") == "float");
        /* string */
        assertTrue(Condition.typeOf("'Lee'") == "string");
        assertTrue(Condition.typeOf("''") == "string");
        assertFalse(Condition.typeOf("Lee") == "string");

    }

    /** compare 1st column elements of t1 with int 5 */
    @Test
    public void testCompareInt() {
        Table table = Table.loadTable("examples/t1.tbl");
        Condition condition = new ConditionUnary("x", ">", "5");
        Row row0 = table.getRow(0);
        Row row1 = table.getRow(1);
        Row row2 = table.getRow(2);

        Column column = table.findColumn(condition._colName0);

        String e0 = row0.getElement(column);
        String e1 = row1.getElement(column);
        String e2 = row2.getElement(column);

        String t0 = table.getColumnType(column);
        String t1 = "int";

        assertFalse(condition.compare(e0, t0, condition._operator, condition._operand, t1));
        assertTrue (condition.compare(e1, t0, condition._operator, condition._operand, t1));
        assertTrue (condition.compare(e2, t0, condition._operator, condition._operand, t1));
    }

    /** compare 1st column elements of fans with string 'Lee' */
    @Test
    public void testCompareString() {
        Table table = Table.loadTable("examples/fans.tbl");
        Condition condition = new ConditionUnary("Lastname", ">", "'Lee'");
        Row row0 = table.getRow(0);
        Row row1 = table.getRow(1);
        Row row2 = table.getRow(2);
        Row row3 = table.getRow(3);
        Row row4 = table.getRow(4);
        Row row5 = table.getRow(5);

        Column column = table.findColumn(condition._colName0);

        String e0 = row0.getElement(column);
        String e1 = row1.getElement(column);
        String e2 = row2.getElement(column);
        String e3 = row3.getElement(column);
        String e4 = row4.getElement(column);
        String e5 = row5.getElement(column);

        String t0 = table.getColumnType(column);
        String t1 = "string";

        assertFalse(condition.compare(e0, t0, condition._operator, condition._operand, t1));
        assertFalse(condition.compare(e1, t0, condition._operator, condition._operand, t1));
        assertTrue (condition.compare(e2, t0, condition._operator, condition._operand, t1));
        assertFalse(condition.compare(e3, t0, condition._operator, condition._operand, t1));
        assertTrue (condition.compare(e4, t0, condition._operator, condition._operand, t1));
        assertFalse(condition.compare(e5, t0, condition._operator, condition._operand, t1));

    }

    /** select * from t1 where x > 5 */
    @Test
    public void testConditionUnary01() {
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

        System.out.println(actual.numColumns());
        System.out.println(expect.numColumns());

        assertTrue(actual.equals(expect));
    }

    /** select * from t2 where z < 8 */
    @Test
    public void testConditionUnary02() {
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

    /** select * from fans where Lastname > 'Lee' */
/*    @Test
    public void testConditionUnary03() {
        Table table = Table.loadTable("fans/t2.tbl");
        Condition condition = new ConditionUnary("Lastname","<","z");
        Table actual = condition.evaluate(table);

        Table expect = new Table(table.columnNames, table);
        String[] content;
        content = new String[]{"2","4"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"8","9"}; // 2nd row
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
        actual.print();
    }*/


    /** select * from t1 where x > y */
    @Test
    public void testConditionBinary01() {
        Table table = Table.loadTable("examples/t1.tbl");
        Condition condition = new ConditionBinary("x",">","y");
        Table actual = condition.evaluate(table);

        Table expect = new Table(table.columnNames, table);
        String[] content;
        content = new String[]{"8","3"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"13","7"}; // 2nd row
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
    }

    /** select * from t2 where x < z */
    @Test
    public void testConditionBinary02() {
        Table table = Table.loadTable("examples/t2.tbl");
        Condition condition = new ConditionBinary("x","<","z");
        Table actual = condition.evaluate(table);

        Table expect = new Table(table.columnNames, table);
        String[] content;
        content = new String[]{"2","4"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"8","9"}; // 2nd row
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
        actual.print();
    }


}
