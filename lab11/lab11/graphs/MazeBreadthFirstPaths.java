package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final int source;
    private final int target;
    private boolean targetFound = false;
    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        distTo[source] = 0;
        edgeTo[source] = source;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        marked[source] = true;
        while (!targetFound) {
            int vertex = q.remove();
            if (vertex == target) {
                targetFound = true;
                return;
            }
            for (int nxtVertex : maze.adj(vertex)) {
                if (!marked[nxtVertex]) {
                    marked[nxtVertex] = true;
                    distTo[nxtVertex] = distTo[vertex] + 1;
                    edgeTo[nxtVertex] = vertex;
                    q.add(nxtVertex);
                }
            }
            announce();
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

