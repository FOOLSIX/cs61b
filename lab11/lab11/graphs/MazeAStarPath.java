package lab11.graphs;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private int tX;
    private int tY;
    private boolean targetFound = false;
    private Maze maze;
    private class Node{
        private int v;
        private int priority;
        Node(int v,int h) {
            this.v = v;
            priority = h;
        }
    }

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        tX = targetX;
        tY = targetY;
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - tX) + Math.abs(maze.toY(v) - tY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        Queue<Node> pq = new PriorityQueue<>(Comparator.comparingInt((Node n) -> n.priority));
        pq.add(new Node(s, h(s)));
        while (!targetFound) {
            int vertex = pq.remove().v;
            marked[vertex] = true;
            if (vertex == t) {
                targetFound = true;
                return;
            }
            for (int nxtVertex : maze.adj(vertex)) {
                if (!marked[nxtVertex]) {
                    marked[nxtVertex] = true;
                    distTo[nxtVertex] = distTo[vertex] + 1;
                    edgeTo[nxtVertex] = vertex;
                    pq.add(new Node(nxtVertex, h(nxtVertex)));
                }
            }
            announce();

        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

