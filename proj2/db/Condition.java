package db;

import java.util.regex.Pattern;

/**
 * Created by BlackIce on 2017/7/11.
 */
public abstract class Condition {
    String _colName0;
    String _operator;
    String _operand;

    Condition(String c0, String operator, String operand) {
        _colName0 = c0;
        _operator = operator;
        _operand  = operand;
    }


    static boolean compare(String elem, String operator, String literal) {
        int v = elem.compareTo(literal);
        switch(operator) {
            case "==": return v == 0;
            case "!=": return v != 0;
            case "<=": return v <= 0;
            case ">=": return v >= 0;
            case "<":  return v < 0;
            case ">":  return v > 0;
        }
        // TODO: exception: none of the above tests were performed.
        return false;
    }

    abstract Table evaluate(Table table);

}
