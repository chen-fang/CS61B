package db.Value;
/**
 * Created by BlackIce on 2017/7/17.
 */
public abstract class ValueBase implements Comparable<ValueBase> {
    private String value;
    private String type;

    ValueBase(String value, String type) {
        this.value = value;
        this.type = type;
    }

    String getValue() {
        return value;
    }
    /** Get the type of the literal */
    String getType() {
        return type;
    }

    /** Test if the literal's type is string */
    boolean isStringType() {
        return type == "string";
    }

    /** Test if two literals are of the same type */
    boolean isSameType(ValueBase other) {
        return type == other.type;
    }

    /** Compare method.
     *  However, since this class is abstract, the line below can be ignored
     *  since it inherits from the "Comparable" interface.
     *  */
    //public abstract int compareTo(ValueBase other);

/*
    public boolean compareToNoValue(ValueNoValue noValue) {
        return false;
    }
*/

    /** Arithmetic operations */
    // TODO: consider to put these into an interface named "Arithmetic"
    abstract ValueBase add(ValueBase other);
}
