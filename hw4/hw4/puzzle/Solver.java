package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class Solver {
    private class SearchNode {
        private WorldState theWs;
        private SearchNode last;
        private final int step;
        private final int estimatedStep;
        SearchNode(WorldState ws, SearchNode lst, int s) {
            theWs = ws;
            estimatedStep = ws.estimatedDistanceToGoal();
            last = lst;
            step = s;
        }
        public void makeTrack(List<WorldState> l, SearchNode node) {
            if (node == null) {
                return;
            }
            makeTrack(l, node.last);
            l.add(node.theWs);
        }

        private boolean equalLast(WorldState ws) {
            return (last != null && last.theWs.equals(ws));
        }

    }
    private MinPQ<SearchNode> solverSequenece;
    //private int debugnum = 0;
    private List<WorldState> ret;
    public Solver(WorldState initial) {
        solverSequenece = new MinPQ<>(Comparator.comparingInt((SearchNode n) ->
                n.step + n.estimatedStep));
        solverSequenece.insert(new SearchNode(initial, null, 0));
        ret = new LinkedList<>();
        solve();
    }

    private void solve() {
        while (true) {
            SearchNode bms = solverSequenece.delMin();
            WorldState thisWs = bms.theWs;
            if (thisWs.isGoal()) {
                bms.makeTrack(ret, bms);
                return;
            }
            for (WorldState nxtWs : thisWs.neighbors()) {
                if (!bms.equalLast(nxtWs)) {
                    solverSequenece.insert(new SearchNode(nxtWs, bms, bms.step + 1));
                    //System.out.println(++debugnum);
                }
            }
        }
    }
    public int moves() {
        return ret.size() - 1;
    }
    public Iterable<WorldState> solution() {
        return ret;
    }
}
