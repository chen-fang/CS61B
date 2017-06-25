/**
 * Created by BlackIce on 2017/6/25.
 */
public class OffByN implements CharacterComparator {
    private int n;
    public OffByN( int N ) {
        n = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int off = x - y;
        if( off == n || off == -n )
            return true;
        return false;
    }
}
