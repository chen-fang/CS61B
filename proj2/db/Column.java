package db;

import java.util.List;

/**
 * Created by BlackIce on 2017/7/7.
 */
public class Column {
    private int columnIndex;
    private int whichTable;

    /** Applied when [whichTable] doesn't matter. */
    Column(int columnIndex) {
        this.columnIndex = columnIndex;
        whichTable = -1;
    }

    Column(int columnIndex, int whichTable) {
        this.columnIndex = columnIndex;
        this.whichTable = whichTable;
    }

    int getColumnIndex() {
        return this.columnIndex;
    }

    int getTableIndex() {
        return whichTable;
    }

    /** Find column with [name] in [table] */
    static Column findColumn(String name, Table table) {
        int columnIndex = table.contains(name);
        if (columnIndex < 0)
            return null;
        return new Column(columnIndex);
    }
    /** Assume:
     *  column with [name] exists in at lease one of the two tables.
     *
     *  Objective:
     *  Find column with [name] from two tables, and return as soon as it is found.
     *
     *  It does two things:
     *  1). find the column
     *  2). mark from which table the column is found.
     *
     *  Applied in:
     *  create the list of selected columns under:
     *  Table.select(String[] columnNames, Table table1, Table table2).
     */
    static Column findColumn(String name, Table table1, Table table2) {
        int columnIndex = table1.contains(name);
        if (columnIndex >= 0) {
            return new Column(columnIndex,0);
        }
        /* if column is not found in table1, search in table2 */
        columnIndex = table2.contains(name);
        return new Column(columnIndex,1);
    }

    /** Objective:
     *  Find two columns that share the specified [name] from two tables respectively.
     *  Return null if not found.
     *
     *  Applied in:
     *  Table.select(String[] columnNames, Table table1, Table table2).
     */
    public static Column[] findCommonColumns(String name, Table table1, Table table2) {
        int columnIndex1 = table1.contains(name);
        int columnIndex2 = table2.contains(name);
        if (columnIndex1 < 0 || columnIndex2 < 0) {
            return null;
        }
        Column[] common = new Column[2];
        Column c1 = new Column(columnIndex1,0);
        Column c2 = new Column(columnIndex2,1);
        common[0] = c1;
        common[1] = c2;
        return common;
    }

    /** [API]: public String getElement (Row row) {}
     *
     *  This is NOT a proper API due to possible confusions.
     *  Explanation:
     *  The element can be obtained with:
     *  return row[columnIndex]
     *  But, the passed-in "row" might not come from the table that is
     *  associated with this column by "tableReference".
     */


}
