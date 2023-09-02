import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTrie {
    private class TrieNode {
        Map<Character, TrieNode> next;
        List<String> names;
        TrieNode() {
            next = new HashMap<>();
            names = new ArrayList<>();
        }
        boolean hasWord() {
            return  !names.isEmpty();
        }
    }
    TrieNode root;
    MyTrie() {
        root = new TrieNode();
    }
    public void add(String s,String name) {
        TrieNode curNode = root;
        for (char c : s.toCharArray()) {
            curNode.next.putIfAbsent(c, new TrieNode());
            curNode = curNode.next.get(c);
        }
        if (!curNode.names.contains(name)) {
            curNode.names.add(name);
        }
    }

    public List<String> getWordWithPrefix(String prefix) {
        TrieNode curNode = root;
        for (char c : prefix.toCharArray()) {
            if(!curNode.next.containsKey(c)) {
                return new LinkedList<>();
            }
            curNode = curNode.next.get(c);
        }
        List<String> ans = new LinkedList<>();
        getAllWordWithPrefix(root, ans);
        return ans;
    }
    private void getAllWordWithPrefix(TrieNode node, List<String> ans) {
        if (node.hasWord()) {
            ans.addAll(node.names);
        }

        for (TrieNode next : node.next.values()) {
            getAllWordWithPrefix(next, ans);
        }
    }
}
