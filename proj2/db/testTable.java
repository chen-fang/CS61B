package db;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by BlackIce on 2017/7/7.
 */
public class testTable {
    @Test
    public void testLoadFromFile() {
        Table table = Table.loadTable("examples/fans.tbl");
        System.out.println();
        table.print();
    }

    /** Table(String colSentence */
    @Test
    public void testConstructor01() {
        Table t0 = new Table("x int, y float,    z      string");
        Table t1 = new Table("x   int,y    float,    z  string");
        assertTrue(t0.equals(t1));
        t0.print();
        t1.print();
    }

    /** Table(String[] colNames, Table...tables) */
    @Test
    public void testConstructor02() {
        Table t0 = new Table("x int, y float, z string");
        Table t1 = new Table("b string, x int, c float");
        String[] colNames = new String[]{"z", "c", "b", "y"};
        Table actual = new Table(colNames, t0, t1);
        Table expect = new Table("z string, c float, b string, y float");
        assertTrue(actual.equals(expect));
        actual.print();
    }

    @Test
    public void testNumRowCol() {
        Table table = Table.loadTable("examples/t1.tbl");
        int rows = table.numRows();
        int cols = table.numColumns();
        assertEquals(rows,3);
        assertEquals(2,cols);
    }

    @Test
    public void testContains() {
        Table table = Table.loadTable("examples/t1.tbl");
        assertEquals(0, table.contains("x"));
        assertEquals(1, table.contains("y"));
        assertEquals(-1, table.contains("z"));
    }

    @Test
    public void testFindColumn() {
        // See testColumn.java
    }

    /** select y from t1
     *  Details: select (1) column from (1) table, without conditions.
     */
    @Test
    public void testSelect01() {
        Table table = Table.loadTable("examples/t1.tbl");
        String[] columnNames = new String[1];
        columnNames[0] = "y";
        Table newTable = Table.select(columnNames, table);
        assertEquals(1, newTable.numColumns());
        assertEquals(3, newTable.numRows());

        /* create expected table */
        String[] name = new String[]{"y"};
        String[] type = new String[]{"int"};
        Table expect = new Table("y int");
        String[] content;
        content = new String[]{"5"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"3"}; // 2nd row
        expect.addRow(new Row(content));
        content = new String[]{"7"}; // 3rd row
        expect.addRow(new Row(content));

        assertTrue(newTable.equals(expect));

    }

    /** select y, z from t1, t2
     *  Details: select (1) column from (2) tables, respectively, without conditions.
     *  select (1) column [y] from t1, and
     *  select (1) column [z] from t2
     */
    @Test
    public void testSelect02() {
        Table table1 = Table.loadTable("examples/t1.tbl");
        Table table2 = Table.loadTable("examples/t2.tbl");
        String[] selectedColumnNames = new String[]{"y","z"};
        Table newTable = Table.select(selectedColumnNames, table1, table2);

        /* create expected table */
        Table expect = new Table(selectedColumnNames, table1, table2);
        String[] content;
        content = new String[]{"5","4"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"3","9"}; // 2nd row
        expect.addRow(new Row(content));

        assertTrue(newTable.equals(expect));
    }

    /** select City, Season, Wins from teams, records
     *  Details: select (1) column and then (2) columns from (2) tables, respectively,
     *           without conditions.
     *  select City from teams
     *  select Season, Wins from records
     *  join
     */
    @Test
    public void testSelect03() {
        Table table1 = Table.loadTable("examples/teams.tbl");
        Table table2 = Table.loadTable("examples/records.tbl");

        String[] selectedColumnNames = new String[]{"City","Season", "Wins"};
        Table newTable = Table.select(selectedColumnNames, table1, table2);
        newTable.print();
    }

    /** Cartesian join */
    @Test
    public void testCartesian() {
        // TODO
    }


    /** select z from t2 where x > 3, z < 7 */
    @Test
    public void testSelectConditionUnary01() {
        Table table = Table.loadTable("examples/t2.tbl");
        Condition condition0 = new ConditionUnary("x", ">", "3");
        Condition condition1 = new ConditionUnary("z", "<", "7");
        String[] selectedColumnNames = new String[]{"z"};
        Table actual = table.select(selectedColumnNames, condition0, condition1);
        /* create expected table */
        Table expect = new Table(selectedColumnNames, table);
        String[] content;
        content = new String[]{"1"}; // 1st row
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
    }

    /** select x, y from t1 where x > 1, y < 6 */
    @Test
    public void testSelectConditionUnary02() {
        Table table = Table.loadTable("examples/t1.tbl");
        Condition condition0 = new ConditionUnary("x", ">", "1");
        Condition condition1 = new ConditionUnary("y", "<", "6");
        String[] selectedColumnNames = new String[]{"x","y"};
        Table actual = table.select(selectedColumnNames, condition0, condition1);
        /* create expected table */
        Table expect = new Table(selectedColumnNames, table);
        String[] content;
        content = new String[]{"2","5"}; // 1st row
        expect.addRow(new Row(content));
        content = new String[]{"8","3"}; // 2nd row
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
    }

    /** select Firstname,Lastname,TeamName from fans where Lastname >= 'Lee' */
    @Test
    public void testSelectConditionUnary03() {
        Table table = Table.loadTable("examples/fans.tbl");
        Condition condition = new ConditionUnary("Lastname", ">=", "'Lee'");
        String[] selectedColumnNames = new String[]{"Firstname", "Lastname", "TeamName"};
        Table actual = table.select(selectedColumnNames, condition);
        actual.print();

        Table expect = new Table(selectedColumnNames, table);
        String[] content;
        content = new String[]{"'Maurice'","'Lee'","'Mets'"};
        expect.addRow(new Row(content));
        content = new String[]{"'Maurice'","'Lee'","'Steelers'"};
        expect.addRow(new Row(content));
        content = new String[]{"'Mitas'","'Ray'","'Patriots'"};
        expect.addRow(new Row(content));
        content = new String[]{"'Jared'","'Rulison'","'EnVyUs'"};
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
        expect.print();
    }

    /** select Mascot, YearEstablished from teams where YearEstablished > 1942 */
    @Test
    public void testSelectConditionUnary04 () {
        Table table = Table.loadTable("examples/teams.tbl");
        Condition condition = new ConditionUnary("YearEstablished", ">", "1942");
        String[] selectedColumnNames = new String[]{"Mascot", "YearEstablished"};
        Table actual = table.select(selectedColumnNames, condition);
        actual.print();

        Table expect = new Table(selectedColumnNames, table);
        String[] content;
        content = new String[]{"'Mr. Met'","1962"};
        expect.addRow(new Row(content));
        content = new String[]{"'Pat Patriot'","1960"};
        expect.addRow(new Row(content));
        content = new String[]{"NOVALUE","2012"};
        expect.addRow(new Row(content));
        content = new String[]{"NOVALUE","2007"};
        expect.addRow(new Row(content));

        assertTrue(actual.equals(expect));
        expect.print();
    }
}
