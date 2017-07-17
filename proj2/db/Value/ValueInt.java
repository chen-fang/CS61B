package db.Value;
/**
 * Created by BlackIce on 2017/7/16.
 */
public class ValueInt extends ValueNumeric {
    public ValueInt(String intVal) {
        super(intVal, "int");
    }

    @Override
    Number getNumber() {
        return Integer.parseInt(super.getValue());
    }

    @Override
    ValueFloat toValueFloat() {
        float f = this.getFloat();
        String s = Float.toString(f);
        return new ValueFloat(s);
    }


    @Override
    ValueBase addSameType(ValueNumeric other) {
        System.out.println("int type addition");
        int a = Integer.parseInt(super.getValue());
        int b = Integer.parseInt(other.getValue());
        String c = Integer.toString(a+b);
        return new ValueInt(c);
    }
}
