package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean haveCycle = false;
    public MazeCycles(Maze m) {
        super(m);
        distTo[0] = 0;
        edgeTo[0] = 0;
    }


    @Override
    public void solve() {
        dfs(0, 0);
    }
    private void dfs(int vertex, int lastVertex) {
        marked[vertex] = true;

        for (int nxtVertex : maze.adj(vertex)) {
            if (haveCycle) {
                break;
            }
            if (nxtVertex == lastVertex) {
                continue;
            }
            if (marked[nxtVertex]) {
                haveCycle = true;
                edgeTo[nxtVertex] = vertex;
                announce();
                return;
            } else {
                marked[nxtVertex] = true;
                edgeTo[nxtVertex] = vertex;
                distTo[nxtVertex] = distTo[vertex] + 1;
                announce();
                dfs(nxtVertex, vertex);
            }
        }

    }
}

