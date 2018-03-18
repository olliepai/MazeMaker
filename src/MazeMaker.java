import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//select a random cell to start
		Cell currentCell = maze.getCell(randGen.nextInt(width), randGen.nextInt(height));
		
		//call selectNextPath method with the randomly selected cell
		selectNextPath(currentCell);
		
		return maze;
	}

	private static void selectNextPath(Cell currentCell) {
		// mark current cell as visited
		currentCell.setBeenVisited(true);
		
		// check for unvisited neighbors
		boolean hasUnvisitedNeighbors = false;
		
		if (getUnvisitedNeighbors(currentCell).size() > 0) {
			hasUnvisitedNeighbors = true;
		}
		
		// if has unvisited neighbors,
		if (hasUnvisitedNeighbors) {
			// select one at random.
			Cell unvisitedNeighbor = getUnvisitedNeighbors(currentCell).get(randGen.nextInt(getUnvisitedNeighbors(currentCell).size()));
			
			// push it to the stack
			uncheckedCells.push(unvisitedNeighbor);
			
			// remove the wall between the two cells
			removeWalls(currentCell, unvisitedNeighbor);
			
			// make the new cell the current cell and mark it as visited
			unvisitedNeighbor.hasBeenVisited();
		
			//call the selectNextPath method with the current cell
			selectNextPath(unvisitedNeighbor);
		}	
		
		// if all neighbors are visited
		else {
			//if the stack is not empty
			if (uncheckedCells.size() > 0) {
				// pop a cell from the stack
				// make that the current cell
				//call the selectNextPath method with the current cell
				selectNextPath(uncheckedCells.pop());
			}
		}
	}

	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getY() == c2.getY()) {
			if(c1.getX() < c2.getX()) {
				c1.setEastWall(false);
				c2.setWestWall(false);
			} else {
				c1.setWestWall(false);
				c2.setEastWall(false);
			}
		} else {
			if (c1.getY() < c2.getY()) {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			} else {
				c1.setNorthWall(false);
				c2.setSouthWall(false);
			}
		}
	}

	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> unvisitedNeighbors = new ArrayList<Cell>();
		
		if (c.getY() != 0 && !maze.getCell(c.getX(), c.getY() - 1).hasBeenVisited()) {
			unvisitedNeighbors.add(maze.getCell(c.getX(), c.getY() - 1));
		}
		if (c.getY() != maze.getHeight() - 1 && !maze.getCell(c.getX(), c.getY() + 1).hasBeenVisited()) {
			unvisitedNeighbors.add(maze.getCell(c.getX(), c.getY() + 1));
		}
		if (c.getX() != 0 && !maze.getCell(c.getX() - 1, c.getY()).hasBeenVisited()) {
			unvisitedNeighbors.add(maze.getCell(c.getX() - 1, c.getY()));
		}
		if (c.getX() != maze.getWidth() - 1 && !maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited()) {
			unvisitedNeighbors.add(maze.getCell(c.getX() + 1, c.getY()));
		}
		
		return unvisitedNeighbors;
	}
}