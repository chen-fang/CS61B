package db.Value;

/**
 * Created by BlackIce on 2017/7/17.
 */
public class ValueString extends ValueBase {
    ValueString(String valString) {
        super(valString,"string");
    }

    @Override
    public int compareTo(ValueBase other) {
        ValueString that = (ValueString) other;
        return this.getValue().compareTo(that.getValue());
    }

    @Override
    ValueBase add(ValueBase other) {
        ValueString that = (ValueString) other;
        return new ValueString(this.getValue() + that.getValue());
    }
}
