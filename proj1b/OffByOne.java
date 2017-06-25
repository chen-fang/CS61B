/**
 * Created by BlackIce on 2017/6/25.
 */
public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int off = x - y;
        if(off==1 || off==-1)
            return true;
        return false;
    }

    public static void main(String[] args) {
        OffByOne obo = new OffByOne();
        if( obo.equalChars('z','a') == true )
            System.out.println("true");
        else
            System.out.println("false");
    }
}
