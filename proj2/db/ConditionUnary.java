package db;

import java.util.regex.Pattern;

/**
 * Created by BlackIce on 2017/7/13.
 * A unary condition involve one column as left operand and one literal,
 * including int, float and string, as right operand.
 */
public class ConditionUnary extends Condition {
    ConditionUnary(String colName0, String operator, String literal) {
        super(colName0, operator, literal);
    }

    /** Return table with rows that satisfy this specific condition */
    @Override
    Table evaluate(Table table) {
        Column column = table.findColumn(super._colName0);
        String colType = table.getColumnType(column);
        String litType = typeOf(this._operand);
        boolean flag_compatible_types = isCompatible(colType, litType);
        if (!flag_compatible_types) {
            // TODO: throw exception
            return null;
        }
        Table returnTable = new Table(table.columnNames, table);
        for (Row row : table.rows) {
            String elem = row.getElement(column);
            boolean isPass = super.compare(elem, colType, super._operator, super._operand, litType);
            if (isPass == true)
                returnTable.addRow(row);
        }
        return returnTable;
    }

}
