package db;

/**
 * Created by BlackIce on 2017/7/13.
 * A unary condition involve one column as left operand and one literal,
 * including int, float and string, as right operand.
 */
public class ConditionUnary extends Condition {
    ConditionUnary(String c0, String operator, String literal) {
        super(c0, operator, literal);
    }

    /** Return table with rows that satisfy this specific condition */
    @Override
    Table evaluate(Table table) {
        Column column = table.findColumn(super._colName0);
        Table returnTable = new Table(table.columnNames, table);
        for (Row row : table.rows) {
            String elem = row.getElement(column);
            boolean isPass = super.compare(elem, super._operand, super._operand);
            if (isPass == true)
                returnTable.addRow(row);
        }
        return returnTable;
    }

}
