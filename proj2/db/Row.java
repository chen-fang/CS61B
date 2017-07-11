package db;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by BlackIce on 2017/7/7.
 */
public class Row {
    protected String[] content;

    /** Initialize an empty row with specified size */
    Row(int n) {
        this.content = new String[n];
    }

    /** Initialize a row with given content */
    Row(String[] content) {
        this.content = content;
    }

    /** Objective:
     *  Assemble a row from multiple columns and two rows.
     *
     *  Applied in:
     *  Table.select(String[] columnNames, Table table1, Table table2).
     *
     * @param selectedColumns: list of selected columns
     * @param listRows: list of rows where specific elements should be retrieved.
     *                  Invariants:
     *                  Row[0] comes from table1
     *                  Row[1] comes from table2.
     *
     */
    Row(List<Column> selectedColumns, Row[] listRows) {
        int length = selectedColumns.size();
        this.content = new String[length];
        for (int i = 0; i < length; i++) {
            Column column = selectedColumns.get(i);
            Row row = listRows[column.getTableIndex()];
            this.content[i] = row.getElement(column);
        }
    }

    /** Retrieve the i-th element in this row */
    private String get(int i) {
        return this.content[i];
    }

    /** Retrieve the element under specified [column] in this row */
    String getElement(Column column) {
        return get(column.getColumnIndex());
    }

    /** Get a subRow from thisRow based on specified columns */
    public Row subRow(List<Column> listColumns) {
        int length = listColumns.size();
        String[] content = new String[length];
        for (int i = 0; i < length; i++) {
            Column column = listColumns.get(i);
            content[i] = this.getElement(column);
        }
        return new Row(content);
    }

    /** Test if two rows are equal */
    public boolean equals(Row other) {
        return Arrays.equals (this.content, other.content);
    }

    void print() {
        int length = this.content.length;
        for (int j = 0; j < length-1; j++) {
            System.out.print(this.get(j)+ ",  ");
        }
        System.out.println(this.get(length-1));
    }

    /** Unit tests */

}
