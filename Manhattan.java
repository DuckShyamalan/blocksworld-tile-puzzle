import java.util.ArrayList;
import java.util.List;

public class Manhattan extends Nodes implements Comparable {

	public List<Nodes> neighbours = new ArrayList<Nodes>();
	private int manhattanDist;
	int f = depth + manhattanDist; //f=g(distance from original root)+h(heuristic)
	
	public Manhattan(int n) {
		super(n);
		manhattanDist = findManhattan();
	}
	
	public Manhattan(Grid grid, int depth, String directionFromParent, Nodes parent) {
		super(grid, depth, directionFromParent, parent);
		manhattanDist = findManhattan();
	}
	
	/* Calculates the Manhattan Distance
     * Goes through the whole board and finds the letter blocks.
     * It then uses that letter block relative to the alphabet array to calculate
     * where the letter block should be in the array.
     * It uses the distance as a cost, and sums up for every letter.
     */
	public Integer findManhattan() {
		int manhattanX = 0;
		int manhattanY = 0;
		for (int y = 0; y < grid.getSize(); y++) {
            for(int x = 0; x < grid.getSize(); x++) {
                for (int a = 0; a < grid.getSize(); a++) {
                    if(grid.getBoard()[y][x] == Grid.alphabet[a]) {
                        manhattanY += Math.abs(y - (a + 1));
                        manhattanX += x;
                    }
                }
            }
        }
		return (manhattanX + manhattanY);
	}

	@Override
	//Manhattan distance + depth of current node
	public int compareTo(Object next) {
		int compareNodes = (this.manhattanDist + this.depth) - ((((Manhattan)next).manhattanDist + ((Manhattan)next).depth));
		return compareNodes;
	}

}
