package db;

/**
 * Created by BlackIce on 2017/7/11.
 * TODO: literal is assumed to be of int type
 */
public class Condition {
    // Table  table;
    String columnName;
    String operator;
    int    literal;

    Condition(String columnName, String operator, String literal) {
        this.columnName = columnName;
        this.operator = operator;
        this.literal = Integer.parseInt(literal);
    }

    boolean evaluateOperation(int elem) {
        switch(operator) {
            case "==": return elem == this.literal;
            case "!=": return elem != this.literal;
            case "<=": return elem <= this.literal;
            case ">=": return elem >= this.literal;
            case "<":  return elem <  this.literal;
            case ">":  return elem >  this.literal;
        }
        // TODO: exception: none of the above tests were performed.
        return false;
    }

    /** Return table with rows that satisfy this specific condition */
    Table evaluateTable(Table table) {
        Column column = table.findColumn(this.columnName);
        Table returnTable = new Table(table.columnNames);
        for (Row row : table.rows) {
            String elemString = row.getElement(column);
            int elem = Integer.parseInt(elemString);
            boolean isPass = evaluateOperation(elem);
            if (isPass == true)
                returnTable.addRow(row);
        }
        return returnTable;
    }
}
