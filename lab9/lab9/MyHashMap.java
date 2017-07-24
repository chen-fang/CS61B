package lab9;

import java.util.*;

/**
 * Created by BlackIce on 2017/7/21.
 */
public class MyHashMap<K extends Comparable<K>, V>
        implements Map61B<K,V> {
    private int _N;
    private int _size;
    private double _loadfactor;
    private List<Entry> _entries;
    private HashSet<K> _keyset;

    private class Pair {
        public Pair(K k, V v) {
            key = k;
            val = v;
        }
        K key;
        V val;

        public boolean equals(Pair p) {
            return key.equals(p.key) && val.equals(p.val);
        }
    }

    private class Entry extends LinkedList<Pair> {
        public Entry() {
            super();
        }

        public Entry(Pair p) {
            super();
            add(p);
        }
    }

    public MyHashMap() {
        createNewHashMap();
    }

    public MyHashMap(int initialSize) {
        createNewHashMap(initialSize, 1);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        createNewHashMap(initialSize, loadFactor);
    }
/*

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyHashMap<K,V> myHashMap = (MyHashMap<K,V>) o;

        if (_size != myHashMap._size) return false;
        if (Double.compare(myHashMap._loadFactor, _loadFactor) != 0) return false;
        return _entries != null ? _entries.equals(myHashMap._entries) : myHashMap._entries == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = _size;
        temp = Double.doubleToLongBits(_loadFactor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (_entries != null ? _entries.hashCode() : 0);
        return result;
    }
*/

    /** Reuse in constructor & clear */
    private void createNewHashMap() {
        createNewHashMap(11,1.0);
    }
    /** Reuse in constructor & createNewHashMap() */
    private void createNewHashMap(int initialSize, double loadFactor) {
        _N = 0;
        _size = initialSize;
        _loadfactor = loadFactor;
        _entries = new ArrayList<>();
        for (int i = 0; i < initialSize; i++) {
            _entries.add(new Entry());
        }
        _keyset = new HashSet<>();
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        createNewHashMap();
    }

    private int hash(K key) {
        return (0x7fffffff & key.hashCode()) % _size;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        V val = get(key);
        return val != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        int h = hash(key);
        Entry e = _entries.get(h);
        if (e == null) {
            return null;
        }
        for (Pair p : e) {
            if (p.key.equals(key))
                return p.val;
        }
        return null;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return _N;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if ((double)_N / _size > _loadfactor) {
            // System.out.println( "Resize from " + _size +" to " + _size*2);
            resize();
        }
        Pair p = new Pair(key,value);
        int h = hash(key);
        Entry e = _entries.get(h);
        if (e.isEmpty() == true) {
            e.add(p);
        }
        else {
            for (Pair pair : e) {
                if (key.equals(pair.key)) { /* if find identical key, replace value */
                    pair.val = value;
                    return;
                }
            }
            e.add(p); /* otherwise, add new pair */
        }
        _keyset.add(key);
        _N += 1;
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        return _keyset;
    }


    private Entry find(K key) {
        for (Entry e : _entries) {
            if (!e.isEmpty()) {
                for (Pair p : e) {
                    if (p.key.equals(key))
                        return e;
                }
            }
        }
        return null;
    }

    private Entry find(K key, V val) {
        for (Entry e : _entries) {
            if (!e.isEmpty()) {
                for (Pair p : e) {
                    if (p.key.equals(key) && p.val.equals(val))
                        return e;
                }
            }
        }
        return null;
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        Entry e = find(key);
        if (e != null) {
            for (Pair p : e) {
                if (p.key.equals(key)) {
                    V val = p.val;
                    e.remove(p);
                    return val;
                }
            }
        }
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        Entry e = find(key, value);
        if (e != null) {
            for (Pair p : e) {
                if (p.key.equals(key) && p.val.equals(value)) {
                    e.remove(p);
                    return value;
                }
            }
        }
        return null;
    }

    public Iterator<K> iterator() {
        return _keyset.iterator();
    }

    private void resize() {
        List<Entry> currentEntries = _entries;
        createNewHashMap(_size * 2, _loadfactor);
        for (Entry e : currentEntries) {
            if (!e.isEmpty()) {
                for (Pair p : e) {
                    put(p.key, p.val);
                }
            }
        }
    }


    private int maxChainSize() {
        int max = 0;
        for (Entry e : _entries) {
            int m = e.size();
            if (m > max)
                max = m;
        }
        return max;
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);
        }
        System.out.println("max chain size = " + b.maxChainSize());
    }
}
