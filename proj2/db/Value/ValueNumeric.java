package db.Value;
/**
 * Created by BlackIce on 2017/7/16.
 */

public abstract class ValueNumeric extends ValueBase {
    /** The string representation of float numbers will be specified to
     *  exactly three(3) decimal places, hence the tolerance is set to
     *  four(4) decimal places.
     */
    private static final double EPSILON = 0.0001;

    public ValueNumeric(String number, String type) {
        super(number, type);
    }

    /** [Abstract method]: Get the "Number" representation of "value" */
    abstract Number getNumber();

    /** Get the "float" representation of "value" */
    float getFloat() {
        return getNumber().floatValue();
    }
    /** Get the "double" representation of "value" */
    double getDouble() {
        return getNumber().doubleValue();
    }

    /** Convert from current type to ValueFloat for mixed type operations */
    abstract ValueFloat toValueFloat();

    /** Convert from int/float to double and compare */
    @Override
    public int compareTo(ValueBase other) {
        if (isStringType()) {
            // TODO: throw( numerical type cannot work with string type.
        }
        double a = this.getDouble();
        double b = ((ValueNumeric)other).getDouble();
        double c = a - b;
        if (Math.abs(c) < EPSILON)
            return 0;
        else if (c < 0)
            return -1;
        else
            return 1;
    }

    /** [Abstract arithmetic methods]
     *  Call specific arithmetic methods in subclasses if types of two literals
     *  are identical.
     */
    abstract ValueBase addSameType(ValueNumeric other);

    /** General arithmetic operations involving two ValueBase objects
     *  with choosing appropriate methods according to the types of two literals
     */
    @Override
    ValueBase add(ValueBase other) {
        ValueNumeric that = (ValueNumeric)other;
        if (isSameType(other))
            return addSameType(that);
        /* mixed type addition */
        System.out.println("Mixed type operation");
        ValueFloat v1 = this.toValueFloat();
        ValueFloat v2 = that.toValueFloat();
        return v1.addSameType(v2);
    }
}
