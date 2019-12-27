import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

public class SearchAlgos {
	/*
	 * This class is a set of 4 search algorithms to solve the puzzle.
	 * It contains Breadth First Search, Depth First Search, Iterative
	 * Deepening and A* Heuristic. To use Depth First Search, use dfs() with
	 * a negative iterations value, otherwise, using it with a +ve iterations
	 * value does Iterative Deepening.
	 */
	
	public static void main(String[] args) {
		SearchAlgos sa = new SearchAlgos();
		//sa.bfs(new Nodes(4)); //new bfs
		//sa.dfs(new Nodes(4)); //new dfs
		sa.ids(new Nodes(4), 20);  //new ids
		//sa.aStar(new Manhattan(4)); //a*
	}
	
	public void aStar(Manhattan root) {
		long startTime = System.nanoTime();
		PriorityQueue<Manhattan> open = new PriorityQueue<Manhattan>();
		ArrayList<Manhattan> closed = new ArrayList<Manhattan>();
		int nodesGen = 0;
		open.add(root);
		
		while (!open.isEmpty()) {
			Manhattan mi = open.poll();
//			for (Manhattan man : open) {  //find min f
//				if (mi == null || man.f < mi.f) {
//					mi = man;
//				}
//			}
//			open.remove(mi);
			closed.add(mi);
			nodesGen++;
			// just print details
            mi.getGrid().currentGrid();
            System.out.println("Node depth: " + mi.getDepth() + "	Node direction: " + mi.directionFromParent);
            System.out.println("-------------------------------------------------");
	    		if (mi.getGrid().checkSolved()) {
	            	long endTime = System.nanoTime();
	        		long totalTime = (endTime - startTime)/1000000;  //total time in millisec
	        		System.out.println("Puzzle has been solved in: " + totalTime + "ms");
	        		System.out.println("no. of nodes generated: " + nodesGen);
	        		System.out.println("Space Complexity of solution: " + open.size());  //time and space complexity are O(bm)
	        		//check up on this?
	        		Nodes nodePath = mi;
	            String path = nodePath.getDirectionFromParent();
	
	            while((nodePath = nodePath.getParent()) != null) {
	                path = nodePath.getDirectionFromParent() + ", " + path;
	            }
	            
	            System.out.println("Path to solution: " + path);
	            return;
	        }
			
	    		//second condition to check if we've not just visited the node
	    		if (mi.getGrid().checkMove("up") && !mi.directionFromParent.equals("DOWN")) {
				Manhattan next = new Manhattan(new Grid(mi.getGrid()), (mi.getDepth() + 1), "UP", mi);
				if (!open.contains(next) || next.f < mi.f) {
					next.getGrid().move("up");
					open.add(next);
				}
	    		}
			if (mi.getGrid().checkMove("down") && !mi.directionFromParent.equals("UP")) {
				Manhattan next = new Manhattan(new Grid(mi.getGrid()), (mi.getDepth() + 1), "DOWN", mi);
				if (!open.contains(next) || next.f < mi.f) {
					next.getGrid().move("down");
					open.add(next);
				}
			}
			if (mi.getGrid().checkMove("left") && !mi.directionFromParent.equals("RIGHT")) {
				Manhattan next = new Manhattan(new Grid(mi.getGrid()), (mi.getDepth() + 1), "LEFT", mi);
				if (!open.contains(next) || next.f < mi.f) {
					next.getGrid().move("left");
					open.add(next);
				}
			}
			if (mi.getGrid().checkMove("right") && !mi.directionFromParent.equals("LEFT")) {
				Manhattan next = new Manhattan(new Grid(mi.getGrid()), (mi.getDepth() + 1), "RIGHT", mi);
				if (!open.contains(next) || next.f < mi.f) {
					next.getGrid().move("right");
					open.add(next);
				}
			} 
		}
		
	}
	
	//Uses a linked list(preserving insertion order) to expand nodes
	public void bfs(Nodes root) {
		long startTime = System.nanoTime();
		LinkedList<Nodes> fringe = new LinkedList<Nodes>();  //FIFO queue
		ArrayList<Nodes> explored = new ArrayList<Nodes>();
		if (root == null) {
			return;
		} else {
			fringe.add(root);
		}
        int nodesGen = 0;  //nodes generated
        while (!fringe.isEmpty()) {
        		Nodes temp = fringe.poll();
        		explored.add(temp);
        		nodesGen++;
        		
        		// just print details
                temp.getGrid().currentGrid();
                System.out.println("Node depth: " + temp.getDepth() + " 	Node direction: " + temp.directionFromParent);
                System.out.println("-------------------------------------------------");
        		if (temp.getGrid().checkSolved()) {
	            	long endTime = System.nanoTime();
	        		long totalTime = (endTime - startTime)/1000000;  //total time in millisec
            		System.out.println("Puzzle has been solved in: " + totalTime + "ms");
            		System.out.println("no. of nodes generated: " + nodesGen);
            		System.out.println("Space Complexity: " + explored.size());
            		
            		Nodes nodePath = temp;
                String path = nodePath.getDirectionFromParent();

                while((nodePath = nodePath.getParent()) != null) {
                    path = nodePath.getDirectionFromParent() + ", " + path;
                }

                System.out.println("Path to solution: " + path);
                return;
            }
            
        		//second condition to check if we've not just visited the node
            if (temp.getGrid().checkMove("up") && !temp.directionFromParent.equals("DOWN")) {
            		Nodes next =  new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "UP", temp);
            		if (!fringe.contains(next) && !explored.contains(next)) {  //not already visited
					next.getGrid().move("up");
					fringe.add(next);
				}
            }
            if (temp.getGrid().checkMove("down") && !temp.directionFromParent.equals("UP")) {
            		Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "DOWN", temp);
            		if (!fringe.contains(next) && !explored.contains(next)) {
					next.getGrid().move("down");
					fringe.add(next);
				}
            }
            if (temp.getGrid().checkMove("left") && !temp.directionFromParent.equals("RIGHT")) {
	            	Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "LEFT", temp);
	        		if (!fringe.contains(next) && !explored.contains(next)) {
					next.getGrid().move("left");
					fringe.add(next);
				}
            }
            if (temp.getGrid().checkMove("right") && !temp.directionFromParent.equals("LEFT")){
	            	Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "RIGHT", temp);
	        		if (!fringe.contains(next) && !explored.contains(next)) {
					next.getGrid().move("right");
					fringe.add(next);
				}
            }
        
        }
	}
	
	//Uses a stack to expand nodes to have a LIFO queue-like structure
	public void dfs(Nodes root) {
		long startTime = System.nanoTime();
		Stack<Nodes> fringe = new Stack<Nodes>();  //LIFO queue
		if (root == null) {
			return;
		} else {
			fringe.push(root);
		}
		int nodesGen = 0;
		while (!fringe.isEmpty()) {
			Nodes temp = fringe.pop();
			nodesGen++;
				

			// just print details
			temp.getGrid().currentGrid();
			System.out.println("Node depth: " + temp.getDepth() + "	 Node direction: " + temp.directionFromParent);
			System.out.println("-------------------------------------------------");

			//check if solved
			if (temp.getGrid().checkSolved()) {
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000; //total time in millisec
				
				Nodes nodePath = temp;
				String path = nodePath.getDirectionFromParent();
				System.out.println("Puzzle has been solved in: " + totalTime + "ms");
				System.out.println("no. of nodes generated: " + nodesGen);
				System.out.println("Space Complexity of solution: " + temp.depth);
					

				while ((nodePath = nodePath.getParent()) != null) {  //create path
						path = nodePath.getDirectionFromParent() + ", " + path;
					}

					System.out.println("Path to solution: " + path);
					return;
				}

			ArrayList<Nodes> nextLvl = new ArrayList<Nodes>();
			
			if (temp.getGrid().checkMove("up")) {
				Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "UP", temp);
				next.getGrid().move("up");
				nextLvl.add(next);
			}
			if (temp.getGrid().checkMove("down")) {
				Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "DOWN", temp);
				next.getGrid().move("down");
				nextLvl.add(next);
			}
			if (temp.getGrid().checkMove("left")) {
				Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "LEFT", temp);
				next.getGrid().move("left");
				nextLvl.add(next);
			}
			if (temp.getGrid().checkMove("right")) {
				Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "RIGHT", temp);
				next.getGrid().move("right");
				nextLvl.add(next);
			}

			//add new nodes to stack
			while (!nextLvl.isEmpty()) {
				int random = new Random().nextInt(nextLvl.size());
				fringe.push(nextLvl.remove(random));
			}
		}
	}

	/* Uses a stack to expand nodes to have a LIFO queue-like structure.
	 * Repetitively calls the dls helper function for each value until the limit.
	 */
	public void ids (Nodes root, int iterations) {
		for (int i = 0; i < iterations; i++) {
			long startTime = System.nanoTime();
			Stack<Nodes> fringe = new Stack<Nodes>();  //LIFO queue
			if (root == null) {
				return;
			} else {
				fringe.push(root);
			}
			int nodesGen = 0;
			while (!fringe.isEmpty()) {
				Nodes temp = fringe.pop();
				nodesGen++;
				
				dls(temp, i, nodesGen, fringe);
				
				//print details
				temp.getGrid().currentGrid();
				System.out.println("Node depth: " + temp.getDepth() + "	 Node direction: " + temp.directionFromParent);
				System.out.println("-------------------------------------------------");
			
				//check if solved
				if (temp.getGrid().checkSolved()) {
					long endTime = System.nanoTime();
					long totalTime = (endTime - startTime) / 1000000; //total time in millisec
					
					Nodes nodePath = temp;
					String path = nodePath.getDirectionFromParent();
					System.out.println("Puzzle has been solved in: " + totalTime + "ms");
					System.out.println("no. of nodes generated: " + nodesGen);
					System.out.println("Space Complexity of solution: " + temp.getDepth());
					

					while ((nodePath = nodePath.getParent()) != null) {  //create path
						path = nodePath.getDirectionFromParent() + ", " + path;
					}

					System.out.println("Path to solution: " + path);
					return;
				}
				
				
			}
		}
		System.out.println("No solution within given limit");
	}
	
	/* Function that performs dls and serves as the helper to the ids.
	 * Uses stack, iteration, and node information from ids. Cannot work alone.
	 */
	public void dls (Nodes temp, int iterations, int nodesGen, Stack<Nodes> fringe) {
		ArrayList<Nodes> nextLvl = new ArrayList<Nodes>();

		if (temp.getDepth() != iterations) {
			nodesGen++;
			//second condition to check if we've not just visited the node
			if (temp.getGrid().checkMove("up") && !temp.directionFromParent.equals("DOWN")) {
				Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "UP", temp);
				next.getGrid().move("up");
				nextLvl.add(next);
			}
			if (temp.getGrid().checkMove("down") && !temp.directionFromParent.equals("UP")) {
				Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "DOWN", temp);
				next.getGrid().move("down");
				nextLvl.add(next);
			}
			if (temp.getGrid().checkMove("left") && !temp.directionFromParent.equals("RIGHT")) {
				Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "LEFT", temp);
				next.getGrid().move("left");
				nextLvl.add(next);
			}
			if (temp.getGrid().checkMove("right") && !temp.directionFromParent.equals("LEFT")) {
				Nodes next = new Nodes(new Grid(temp.getGrid()), temp.getDepth() + 1, "RIGHT", temp);
				next.getGrid().move("right");
				nextLvl.add(next);
			}
					
		} else {
			//System.out.println("No solution at depth " + iterations);
		}
		//add new (randomised) nodes to stack
		while (!nextLvl.isEmpty()) {
			int random = new Random().nextInt(nextLvl.size());
			fringe.push(nextLvl.remove(random));
		}
	}

	
}
