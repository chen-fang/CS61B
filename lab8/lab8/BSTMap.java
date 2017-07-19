package lab8;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * Created by BlackIce on 2017/7/18.
 */
public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {
    private BST root;
    private int size;

    private class BST {
        K key;
        V val;
        BST left, right;

        public BST(K k, V v) {
            key = k;
            val = v;
            left = null;
            right = null;
        }

        public BST get(BST t, K key) {
            if (t == null || key == null)
                return null;
            int cmp = key.compareTo(t.key);
            if (cmp < 0)
                return get(t.left, key);
            else if (cmp > 0)
                return get(t.right, key);
            else
                return this;
        }
    }


    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key) {
        if (root == null)
            return false;
        BST t = root.get(root, key);
        return t != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (root == null)
            return null;
        BST t = root.get(root, key);
        if (t == null)
            return null;
        return t.val;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /** Insert (key,value) into tree rooted at t. Return modified t */
    private BST put(BST t, K key, V value) {
        if (t == null)
            return new BST(key, value);
        int cmp = key.compareTo(t.key);
        if (cmp < 0) {
            t.left = put(t.left, key, value);
        }
        else if (cmp > 0) {
            t.right = put(t.right, key, value);
        }
        t.val = value;
        return t;
    }

    /** Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null)
            return;
        root = put(root, key, value);
        size += 1;
    }


    /** Get minimum node */
    private BST getMin(BST t) {
        if (t == null)
            return null;
        if (t.left == null)
            return t;
        return getMin(t.left);
    }

    public V getMin() {
        BST min = getMin(root);
        return min.val;
    }

    /** Remove the minimum node from  tree rooted in t, and return the modified t */
    private BST removeMin(BST t) {
        if (t.left == null)
            return t.right;
        t.left = removeMin(t.left);
        return t;
    }

    /** Remove node with key from tree rooted in t, and return modified t */
    private BST remove(BST t, K key) {
        if (t == null || key == null)
            return null;
        int cmp = key.compareTo(t.key);
        if (cmp < 0) {
            t.left = remove(t.left, key);
            return t;
        }
        if (cmp > 0) {
            t.right = remove(t.right, key);
            return t;
        }

        if (t.right == null) return t.left;
        if (t.left == null)  return t.right;
        BST x = getMin(t.right);
        t.right = removeMin(t.right);
        x.left = t.left;
        x.right = t.right;
        size -= 1;
        return x;
    }

    /** Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V returnVal = get(key);
        root = remove(root, key);
        return returnVal;
    }

    /** Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        V val = get(key);
        if (val.equals(value))
            return remove(key);
        return null;
    }



    public static class TestPrivate {
        /** getMin() */
        @Test
        public void testGetMin() {
            BSTMap<String,String> q = new BSTMap<String,String>();
            q.put("c","a");
            q.put("b","a");
            q.put("a","a");
            q.put("d","a");
            q.put("e","a"); // a b c d e
            assertEquals(q.get("a"), q.getMin());
        }

        @Test
        public void testRemove() {
            BSTMap<String,String> q = new BSTMap<String,String>();
            q.put("c","a");
            q.put("b","a");
            q.put("a","a");
            q.put("d","a");
            q.put("e","a"); // a b c d e
            Object r = q.remove("e");
            assertFalse(q.containsKey("e"));
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        Iterator iter = iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            set.add((K)key);
        }
        return set;
    }


    /** Iterator */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator(root);
    }

    private class BSTMapIterator implements Iterator<K> {
        Stack<BST> stack = new Stack<>();

        BSTMapIterator(BST root) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            BST t = stack.pop();
            K retKey = t.key;
            if (t.right != null) {
                t = t.right;
                while (t != null) {
                    stack.push(t);
                    t = t.left;
                }
            }

            return retKey;
        }
    }

    /** Print tree rooted in t in order of increasing key. */
    private void printInOrder(BST t) {

    }
    /** Print in order of increasing key. */
    public void printInOrder() {

    }

    public static void main(String[] args) {
    }
}
