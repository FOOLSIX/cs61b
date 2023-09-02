import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
        Queue<TrieNode> q = new LinkedList<>();
        q.add(curNode);
        while (!q.isEmpty()) {
            TrieNode node = q.remove();
            if (node.hasWord()) {
                ans.addAll(node.names);
            }
            q.addAll(node.next.values());
        }
        return ans;
    }
}
