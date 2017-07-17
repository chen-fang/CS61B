package db;

/**
 * Created by BlackIce on 2017/7/15.
 * Binary condition involves two columns as both operands.
 */
public class ConditionBinary extends Condition {
    ConditionBinary(String c0, String operator, String c1) {
        super(c0,operator,c1);
    }

    /** Compare two columns, and return a table with rows that satisfies this condition */
    @Override
    Table evaluate(Table table) {
        Table returnTable = new Table(table.columnNames, table);
        Column c0 = table.findColumn(super._colName0);
        Column c1 = table.findColumn(super._operand);
        String t0 = table.getColumnType(c0);
        String t1 = table.getColumnType(c1);
        for (Row r : table.rows) {
            String e0 = r.getElement(c0);
            String e1 = r.getElement(c1);
            if (Condition.compare(e0, t0, super._operator, e1, t1)) {
                returnTable.addRow(r);
            }
        }

        return returnTable;
    }
}
