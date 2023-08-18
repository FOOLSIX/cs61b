package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.equals(p.key)) {
            return p.value;
        }

        if (key.compareTo(p.key) > 0) {
            return getHelper(key, p.right);
        }

        return  getHelper(key, p.left);
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }
        if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.left = putHelper(key, value, p.left);
        }

        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        ++size;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    private void keySetHelper(Set<K> keysey, Node p) {
        if (p == null) {
            return;
        }
        keysey.add(p.key);
        keySetHelper(keysey, p.left);
        keySetHelper(keysey, p.right);
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<>();
        keySetHelper(keyset, root);
        return keyset;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    private Node findnode(K key, Node p) {
        if (p == null || key.equals(p.key)) {
            return p;
        }
        if (key.compareTo(p.key) > 0) {
            return findnode(key, p.right);
        }
        return findnode(key, p.left);
    }
    @Override
    public V remove(K key) {
        Node node = findnode(key, root);
        if (node == null) {
            return null;
        }
        V ret = node.value;
        if (node.left != null) {
            Node r = node.right;
            node = node.left;
            Node lr = node;
            while (lr.right != null) {
                lr = lr.right;
            }
            lr.right = r;
        } else if (node.right != null) {
            node = node.right;
        } else {
            node = null;
        }
        --size;
        return ret;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        Node node = findnode(key, root);
        if (node == null || node.value != value) {
            return null;
        }
        V ret = node.value;
        if (node.left != null) {
            Node r = node.right;
            node = node.left;
            Node lr = node;
            while (lr.right != null) {
                lr = lr.right;
            }
            lr.right = r;
        } else if (node.right != null) {
            node = node.right;
        } else {
            node = null;
        }
        --size;
        return ret;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
