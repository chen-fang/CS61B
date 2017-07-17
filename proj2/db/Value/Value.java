package db.Value;
/**
 * Created by BlackIce on 2017/7/15.
 * This class is implemented to hide details from users.
 * For example,
 * BaseValue v1 = new BaseValue("1","int")
 * will not compile since BaseValue is abstract. To proceed, users will have to
 * specify:
 * BaseValue v1 = new ValueInt("1")
 * and will need to determine which class to pick outside ValueBase class with
 * knowledge of class details.
 * In conclusion, ValueBase is acting as a black box in which appropriate types
 * are chosen internally, and invoke methods according to users' intentions.
 */
public class Value implements Comparable<Value> {
    ValueBase vBase;

    private Value(ValueBase v) {
        vBase = v;
    }

    /** Construct new Value instance */
    public Value(String value, String type) {
        switch (type) {
            case ("int"):
                this.vBase = new ValueInt(value);
                break;
            case ("float"):
                this.vBase = new ValueFloat(value);
                break;
            case ("string"):
                this.vBase = new ValueString(value);
                break;
        }
    }

    public String getValue() {
        return vBase.getValue();
    }

    public String getType() {
        return vBase.getType();
    }

    /** Supported operations */
    public Value add(Value other) {
        ValueBase c = this.vBase.add(other.vBase);
        return new Value(c);
    }

    /** Compare method */
    @Override
    public int compareTo(Value other) {
        return this.vBase.compareTo(other.vBase);
    }

    public static void main(String[] args) {
        Value value1 = new Value("3", "int");
        Value value2 = new Value("5", "int");

        Value value3 = new Value("2.2f", "float");
        Value value4 = new Value("1.3f", "float");

        Value r = value1.add(value3);
        System.out.println(r.getValue());
        System.out.println(r.getType());

        int c = value3.compareTo(value4);
        System.out.println(c);
    }
}
