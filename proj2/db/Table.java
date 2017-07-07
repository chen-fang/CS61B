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
    private String[] _columnNames = null;
    private ArrayList<Row> _rows = new ArrayList<>();

    /** Initialize an empty table with selected column names */
    public Table(String[] columnNames) {
        _columnNames = columnNames;
    }

    /** Load a table from the specified file */
    public Table(String filename) {
        In in = new In (filename);
        String line = in.readLine();
        _columnNames = line.split(",");
        while (!in.isEmpty()) {
            line = in.readLine();
            Row row = new Row(line.split(","));
            this.addRow(row);
        }
    }

    /** Print table to screen */
    public void print() {
        for (int i = 0; i < numColumns()-1; i++) {
            System.out.print(_columnNames[i] + ",  ");
        }
        System.out.println(_columnNames[numColumns()-1]);
        for (int i = 0; i < numRows(); i++) {
            Row row = _rows.get(i);
            for (int j = 0; j < numColumns()-1; j++) {
                System.out.print(row.get(j)+ ",  ");
            }
            System.out.println(row.get(numColumns()-1));
        }
    }

    public int numColumns() {
        return _columnNames.length;
    }

    public int numRows() {
        return _rows.size();
    }

    public Column find (String columnName) {
        for (int i = 0; i < numColumns(); i++) {
            if (_columnNames[i].equals(columnName))
                return new Column(i);
        }
        return null;
    }

    public void addRow (Row row) {
        _rows.add(row);
    }

    public Row getRow(int index) {
        return _rows.get(index);
    }

    @Override
    public Iterator<Row> iterator() {
        return _rows.iterator();
    }

    /** Assume, for the moment, that all column names CAN be found in the given table.
     *
     * @param columnNames
     * @param table
     * @return
     */
    public static Table select (String[] columnNames, Table table) {
        Table t = new Table(columnNames);
        ArrayList<Column> listColumn = new ArrayList<>();
        for (String name : columnNames) {
            Column column = table.find(name);
            listColumn.add(column);
        }
        for (Object eachRow : table) {
            Row row = ((Row)eachRow).getFrom(listColumn);
            t.addRow(row);
        }
        return t;
    }

    public boolean equals(Table table) {
        boolean flag = Arrays.deepEquals(this._columnNames, table._columnNames);
        for (int i = 0; i < numRows(); i++) {
            Row thisRow = _rows.get(i);
            Row thatRow = table._rows.get(i);
            flag = Arrays.deepEquals(thisRow._content, thatRow._content);
        }
        return flag;
    }
}
