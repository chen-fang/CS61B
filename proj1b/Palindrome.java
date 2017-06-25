/**
 * Created by BlackIce on 2017/6/25.
 */
public class Palindrome {
    public static Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new ArrayDequeSolution<>();
        for(int i=0; i<word.length(); i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    private static boolean isPalindromeRecursive(Deque<Character> wordDeque) {
        if( wordDeque.size()==0 || wordDeque.size()==1 ) {
            return true;
        }
        Character first = wordDeque.removeFirst();
        Character last  = wordDeque.removeLast();
        if( first != last )
            return false;
        return isPalindromeRecursive(wordDeque);
    }

    public static boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeRecursive(wordDeque);
    }

    private static boolean isPalindromeRecursive( Deque<Character> wordDeque, CharacterComparator cc ) {
        if( wordDeque.size()==0 || wordDeque.size()==1 ) {
            return true;
        }
        Character first = wordDeque.removeFirst();
        Character last  = wordDeque.removeLast();
        if( cc.equalChars( first, last ) == false )
            return false;
        return isPalindromeRecursive( wordDeque, cc );
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeRecursive( wordDeque, cc );
    }
}
