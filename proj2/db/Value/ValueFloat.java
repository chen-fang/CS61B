package db.Value;
/**
 * Created by BlackIce on 2017/7/17.
 */
public class ValueFloat extends ValueNumeric {
    ValueFloat(String floatVal) {
        super(floatVal, "float");
    }

    @Override
    Number getNumber() {
        return Float.parseFloat(super.getValue());
    }

    @Override
    ValueFloat toValueFloat() {
        return this;
    }

    @Override
    ValueBase addSameType(ValueNumeric other) {
        System.out.println("float type addition");
        float a = Float.parseFloat(super.getValue());
        float b = Float.parseFloat(other.getValue());
        String c = Float.toString(a+b);
        return new ValueFloat(c);
    }
}
