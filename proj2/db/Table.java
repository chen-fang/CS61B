package db;

import edu.princeton.cs.introcs.In;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by BlackIce on 2017/7/7.
 */
public class Table implements Iterable {
    String[]       columnNames = null;
    ArrayList<Row> rows        = new ArrayList<>();

    /** Initialize an empty table with selected column names */
    public Table(String[] columnNames) {
        this.columnNames = columnNames;
    }

    /** Load a table from the specified file */
    public Table(String filename) {
        In in = new In (filename);
        String line = in.readLine();
        this.columnNames = line.split(",");
        while (!in.isEmpty()) {
            line = in.readLine();
            Row row = new Row(line.split(","));
            this.addRow(row);
        }
    }

    /** Get number of columns in this table */
    public int numColumns() {
        return this.columnNames.length;
    }

    /** Get number of rows in this table */
    public int numRows() {
        return this.rows.size();
    }

    /** Test if a column with name exists in this table.
     *  If found: return the column index
     *  If not:   return -1
     */
    int contains (String name) {
        for (int i = 0; i < numColumns(); i++) {
            String columnName = columnNames[i];
            if (columnName.equals(name))
                return i;
        }
        return -1;
    }

    /** Find a column with [name] in this table */
    Column findColumn(String name) {
        return Column.findColumn(name,this);
    }

    /** Add a row in this table */
    void addRow (Row row) {
        this.rows.add(row);
    }

    /** Get the i-th row in this table */
    Row getRow(int index) {
        return this.rows.get(index);
    }

    @Override
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }

    /** Select one or multiple columns from the specified table.
     *  TODO: Assume all columnNames CAN be found in the given table.
     */
    public static Table select (String[] columnNames, Table table) {
        Table returnTable = new Table(columnNames);
        ArrayList<Column> listColumn = new ArrayList<>();
        for (String name : columnNames) {
            Column column = table.findColumn(name);
            listColumn.add(column);
        }
        for (Object eachRow : table) {
            Row row = ((Row)eachRow).subRow(listColumn);
            returnTable.addRow(row);
        }
        return returnTable;
    }

    /** Select [columns] from this table that agree with all [conditions] */
    public Table select (String[] columnNames, Condition...conditions) {
        Table filterTable = this;
        for (Condition condition : conditions) {
            filterTable = condition.evaluateTable(filterTable);
        }
        return Table.select(columnNames, filterTable);
    }

    /** Select multiple columns from two tables.
    /*  TODO[1]: Assume all columnNames CAN be found in the specified tables.
     *  TODO[2]: Assume commonColumnNames CAN be found in the specified tables.
     *  TODO[3]: Cartesian join is NOT supported for the moment.
     */
    public static Table select (String[] columnNames, Table table1, Table table2) {
        Table returnTable = new Table(columnNames);

        /* find shared column names from two tables */
        List<Column> commonColumns1 = new ArrayList<>();
        List<Column> commonColumns2 = new ArrayList<>();
        for (String name : table1.columnNames) {
            Column[] common = Column.findCommonColumns(name,table1,table2);
            if (common != null) {
                commonColumns1.add(common[0]);
                commonColumns2.add(common[1]);
            }
        }

        /* Create a list of selected columns with the same order as input.
         * According to the assumption, a column can be found in table2 if
         * it cannot be found in table1.
         */
        ArrayList<Column> selectedColumns = new ArrayList<>();
        for (String name : columnNames) {
            Column column = Column.findColumn(name,table1,table2);
            selectedColumns.add(column);
        }

        /* Invariants:
         * row1 must come from table1.
         * row2 must come from table2.
         */
        for (Row row1 : table1.rows) {
            Row[] listRows = new Row[2];
            listRows[0] = row1;
            Row r1 = row1.subRow(commonColumns1);
            for (Row row2 : table2.rows) {
                Row r2 = row2.subRow(commonColumns2);
                if (r1.equals(r2)) { /* elements under shared column names are equal */
                    listRows[1] = row2;
                    Row r = new Row(selectedColumns, listRows);
                    returnTable.addRow(r);
                }
            }
        }
        return returnTable;
    }

    /** Test if two tables are completely equal in terms of table elements */
    public boolean equals(Table table) {
        boolean flag = Arrays.deepEquals(this.columnNames, table.columnNames);
        if (flag == false) {
            return flag;
        }
        for (int i = 0; i < numRows(); i++) {
            Row thisRow = rows.get(i);
            Row thatRow = table.rows.get(i);
            flag = Arrays.deepEquals(thisRow.content, thatRow.content);
        }
        return flag;
    }

    /** Print column names */
    private void printColumnNames() {
        int length = numColumns();
        for (int i = 0; i < length-1; i++) {
            System.out.print(this.columnNames[i] + ",  ");
        }
        System.out.println(this.columnNames[length-1]);
    }
    /** Print table to screen */
    public void print() {
        printColumnNames();
        for (int i = 0; i < numRows(); i++) {
            Row row = this.rows.get(i);
            row.print();
        }
    }
}
