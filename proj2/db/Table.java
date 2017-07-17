package db;

import edu.princeton.cs.introcs.In;

import java.rmi.UnexpectedException;
import java.util.*;

/**
 * Created by BlackIce on 2017/7/7.
 * Big Assumption:
 * Columns that appear in [conditions] MUST also appear in <select> sentence.
 */
public class Table implements Iterable {
    String[]       columnNames = null;
    String[]       columnTypes = null;
    ArrayList<Row> rows        = new ArrayList<>();

    /** Create an empty table with [colSentence], which includes all column titles. */
    private void createEmptyTable(String colSentence) {
        String[] titles = colSentence.split("\\s*,\\s*");
        this.columnNames = new String[titles.length];
        this.columnTypes = new String[titles.length];
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            String[] name_type = title.split("\\s+");
            this.columnNames[i] = name_type[0];
            this.columnTypes[i] = name_type[1];
        }
    }

    public Table(String colTitles) {
        createEmptyTable(colTitles);
    }
    /** Initialize an empty table with selected column names and types */
/*
    public Table(String[] columnNames, String[] columnTypes) {
        createEmptyTable(columnNames, columnTypes);
    }
*/

    /** Create an empty table while trying to pick some columns with [columnNames]
     *  from one or more specified [tables]
     */
    Table (String[] columnNames, Table...tables) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String searchName : columnNames) {
            Table whichTable = null;
            int columnIndex = -1;
            for (Table t : tables) {
                int c = t.contains(searchName);
                if (c >= 0) {
                    whichTable = t;
                    columnIndex = c;
                    break;
                }
            }
            if (columnIndex < 0) {
                /* TODO: column cannot be found */
            }

            String searchType = whichTable.getColumnType(columnIndex);
            joiner.add(searchName + " " + searchType);
        }
        createEmptyTable(joiner.toString());
    }

    /** Load a table from the specified file */
    public static Table loadTable(String filename) {
        In in = new In (filename);
        String title = in.readLine();
        Table table = new Table(title);

        /* Load data */
        while (!in.isEmpty()) {
            String r = in.readLine();
            Row row = new Row(r.split("\\s*,\\s*"));
            table.addRow(row);
        }
        return table;
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

    /** Get column type associated with [name] */
    String getColumnType(int columnIndex) {
        return this.columnTypes[columnIndex];
    }

    /** Get column type given a specific column */
    String getColumnType(Column col) {
        return this.columnTypes[col.getColumnIndex()];
    }

    /** Find a column based on [name] in this table */
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

    /** Get the element at (i,j) in this table */
    String getElement(int rowIndex, int columnIndex) {
        Row r = rows.get(rowIndex);
        return r.get(columnIndex);
    }

    @Override
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }



    /** Select one or multiple columns from the specified table.
     *  TODO: Assume all columnNames CAN be found in the given table.
     */
    public static Table select (String[] columnNames, Table table) {
        Table returnTable = new Table(columnNames, table);
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
        for (Condition cond : conditions) {
            filterTable = cond.evaluate(filterTable);
        }
        return Table.select(columnNames, filterTable);
    }

    /** Select multiple columns from two tables.
    /*  TODO[1]: Assume all columnNames CAN be found in the specified tables.
     *  TODO[2]: Assume commonColumnNames CAN be found in the specified tables.
     *  TODO[3]: Cartesian join is NOT supported for the moment.
     */
    public static Table select (String[] columnNames, Table table1, Table table2) {
        Table returnTable = new Table(columnNames,table1,table2);

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

        /* For each row1 in table1, search EACH row2 in table2 such that there exists
         * a r2 == r1 under shared column names.
         * Invariants:
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
        if (!Arrays.deepEquals(this.columnNames, table.columnNames))
            return false;
        if (!Arrays.deepEquals(this.columnTypes, table.columnTypes))
            return false;
        if (this.numColumns() != table.numColumns())
            return false;
        if (this.numRows() != table.numRows())
            return false;

        for (int i = 0; i < numRows(); i++) {
            Row thisRow = rows.get(i);
            Row thatRow = table.rows.get(i);
            if (!Arrays.equals(thisRow.content, thatRow.content))
                return false;
        }
        return true;
    }

    /** Print column names */
    private void printColumnNames() {
        int length = numColumns();
        for (int i = 0; i < length-1; i++) {
            System.out.print(this.columnNames[i] + " " + this.columnTypes[i] + ",  ");
        }
        System.out.println(this.columnNames[length-1] + " " + this.columnTypes[length-1]);
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
