import java.util.Random;

public class Grid {  //grid -- whole area containing tiles and by extension, blocks(NxN)
	protected static int gridSize;
	private char[][] board;
	private int agentX;
	private int agentY;
	final static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	Random random = new Random();
	
	public Grid(int N) {
		gridSize = N;

        board = new char[gridSize][gridSize];

        // fill board with blank tiles
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                board[y][x] = '*';  //*'s represent empty tiles
            }
        }

        // set the bottom row to be the blocks
        for (int x = 0; x < gridSize - 1; x++) {
            board[gridSize - 1][x] = alphabet[x];
        }
        //uncomment below lines(28-34) for a random obstacle
//        int tempObsY = random.nextInt(gridSize-2);
//        int tempObsX = random.nextInt(gridSize);
//        if (tempObsX != 1) {
//			board[tempObsY][tempObsX] = 'X';
//		} else {  //if value chosen was at the goal state or an unreachable position, put obstacle on top left
//			board[0][0] = 'X';
//		}
		// sets agent tile
        board[gridSize - 1][gridSize - 1] = '#'; //# represents agent
        agentX = gridSize - 1;
        agentY = gridSize - 1;
	}
	
	//for there to be a separate Grid for each node of the tree
	public Grid(Grid grid) {
		board = new char[grid.getSize()][grid.getSize()];

        for (int x = 0; x < grid.getSize(); x++) {
            for (int y = 0; y < grid.getSize(); y++) {
                board[y][x] = grid.board[y][x];
            }
        }

        this.gridSize = grid.gridSize;
        this.agentX = grid.agentX;
        this.agentY = grid.agentY;
	}
	
	// getters and setters
    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public int getSize() {
        return gridSize;
    }

    public void setSize(int size) {
        this.gridSize = size;
    }
    // end of getters and setters
    
    //prints the current board
    public void currentGrid() {
    		for (int y = 0; y < gridSize; y++) {
    			for (int x = 0; x < gridSize; x++) {
    				System.out.print(board[y][x]);
    			}
    			System.out.println();
    		}
    		System.out.println();
    }
	
    /*Method that checks whether the board is solved or not.
     *Counts the amount of letters in the right spot for each column.
     *There is one less letter than the size.
    */
    public boolean checkSolved() {
    		for (int x = 1; x < 2; x++) {  //gives only if on second col
    		//for (int x = 0; x < gridSize; x++) {
    			char currentLetter = 'a';
    			int countCorrect = 0;
    			for (int y = 1; y < gridSize; y++) {  //letters start from second row
    				if (board[y][x] == currentLetter) {  //change to board[y][x](?)
    					currentLetter++;
    					countCorrect++;
    				} else {
    					break;
    				}
    			}
    			if (countCorrect == gridSize - 1) {
    				return true;
    			}
    		}
    		return false;
    }
    
    //verifies if move is valid or not
    public boolean checkMove(String direction) {
    		if (direction.equals("up") || direction.equals("Up") || direction.equals("UP")) {
    			if (agentY == 0 || board[agentY - 1][agentX] == 'X') {
    				return false;
    			} else {
    				return true;
    			}
    		} else if (direction.equals("down") || direction.equals("Down") || direction.equals("DOWN")) {
    			if (agentY == gridSize - 1  || board[agentY + 1][agentX] == 'X') {
    				return false;
    			} else {
    				return true;
    			}
    		} else if (direction.equals("left") || direction.equals("Left") || direction.equals("LEFT")) {
    			if (agentX == 0 || board[agentY][agentX - 1] == 'X') {
    				return false;
    			} else {
    				return true;
    			}
    		} else if (direction.equals("right") || direction.equals("Right") || direction.equals("RIGHT")) {
    			if (agentX == gridSize - 1 || board[agentY][agentX + 1] == 'X') {
    				return false;
    			} else {
    				return true;
    			}
    		} else {
    			System.out.println("Invalid move: please type - up, down, left or right");
    		}
    		return false;
    }
    
    //moves the agent
    public void move(String direction) {
    		char newTile;
    		if (direction.equals("up") || direction.equals("Up") || direction.equals("UP")) {
			newTile = board[agentY - 1][agentX];
            board[agentY - 1][agentX] = '#';
            board[agentY][agentX] = newTile;  //swap agent with neighbouring block
            agentY -= 1;
		} else if (direction.equals("down") || direction.equals("Down") || direction.equals("DOWN")) {
			newTile = board[agentY + 1][agentX];
            board[agentY + 1][agentX] = '#';
            board[agentY][agentX] = newTile;
            agentY += 1;
		} else if (direction.equals("left") || direction.equals("Left") || direction.equals("LEFT")) {
			newTile = board[agentY][agentX - 1];
            board[agentY][agentX - 1] = '#';
            board[agentY][agentX] = newTile;
            agentX -= 1;
		} else if (direction.equals("right") || direction.equals("Right") || direction.equals("RIGHT")) {
			newTile = board[agentY][agentX + 1];
            board[agentY][agentX + 1] = '#';
            board[agentY][agentX] = newTile;
            agentX += 1;
		}
    }
}
