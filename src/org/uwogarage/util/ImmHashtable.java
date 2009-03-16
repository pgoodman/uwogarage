package org.uwogarage.util;

import java.util.Hashtable;

import org.uwogarage.util.functional.D;

public class ImmHashtable {
    static public <K,V> Hashtable<K,V> getInstance(int capacity, D<Hashtable<K,V>> init) {
        Hashtable<K,V> table = new Hashtable<K,V>(capacity);
        init.call(table);
        return table;
    }
}
