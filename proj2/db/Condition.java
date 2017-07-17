package db;
import db.Value.Value;

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

    static String typeOf(String literal) {
        if (Pattern.matches("^-?\\d+$", literal))  //TODO: or: -?\^\\d*[1-9]\\d*$
            return "int";
        if (Pattern.matches("[']\\w*[']", literal))
            return "string";
        if (Pattern.matches("^-?\\d*[.]\\d+f$", literal))
            return "float";
        // TODO: unhandled case
        return null;
    }

    /** Check if two types can be compared:
     *          string   int   float
     *  string    OK      x      x
     *  int       x      OK      OK
     *  float     x      OK      OK
     *
     *  string can only compare to another string.
     *  int and float can be compared together.
     */
    static boolean isCompatible(String type0, String type1) {
        if (type0 == "string" || type1 == "string")
            return type0.equals(type1);
        return true;
    }

    static boolean interpretFlag(int flag, String operator ) {
        switch(operator) {
            case "==": return flag == 0;
            case "!=": return flag != 0;
            case "<=": return flag <= 0;
            case ">=": return flag >= 0;
            case "<":  return flag < 0;
            case ">":  return flag > 0;
        }
        return false;
    }

    /** Compare two literals, e0 of type0 vs. e1 of type1.
     *  TODO: boxing/unboxing issue here while converting from String to double/Double.
     */
    static boolean compare(String e0, String type0, String operator, String e1, String type1) {
        /* Any comparison operation that has a NOVALUE as one of its operands
           should evaluate to false.
         */
        if (e0 == "NOVALUE" || e1 == "NOVALUE")
            return false;
        /* Any comparison operators involving NaN should treat it as being larger
           than all other values except itself, to which it should be equal.
         */
        if (e0 == "NaN" || e1 == "NaN") {
            int flag;
            if (e0 == "NaN" && e1 == "NaN") //both are NaN
                flag = 0;
            else if (e0 == "NaN") // only e0 is NaN
                flag = 1;
            else
                flag = -1; // only e1 is NaN
            return interpretFlag(flag, operator);
        }

        /* general case below */
        if (!isCompatible(type0, type1)) {
            // TODO: incompatible
        }
        Value v1 = new Value(e0, type0);
        Value v2 = new Value(e1, type1);
        int Flag = v1.compareTo(v2);
        return interpretFlag(Flag, operator);
    }

    abstract Table evaluate(Table table);

}
