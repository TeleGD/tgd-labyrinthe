package games.labyrinthe;

import java.util.Stack;

public class MazeGenerator
{
	private Labyrinth labyrinth;
	private Cell[][] unvisitedCell;
	private Cell currentCell;
	private Cell choosenCell;
	private Stack<Cell> stack;

	public MazeGenerator (Labyrinth labyrinth)
	{
		this.labyrinth = labyrinth;
		this.unvisitedCell = new Cell[labyrinth.getLines()][labyrinth.getRows()];
		for (int i = 0 ; i < labyrinth.getLines();i++)
		{
			for (int j = 0 ; j < labyrinth.getRows() ; j++)
			{
				this.unvisitedCell[i][j] = labyrinth.getCell(i, j);
			}
		}
	}

	public int getChosenCell(int i,int j)
	{
		int random = (int)Math.floor(Math.random()*4);
		if (random == 0 && i-1 >=0 && this.unvisitedCell[i-1][j]!=null)
			this.choosenCell = this.labyrinth.getCell(i-1, j);
		else if (random ==1 && i+1<this.labyrinth.getLines() && this.unvisitedCell[i+1][j]!=null)
			this.choosenCell = this.labyrinth.getCell(i+1, j);
		else if (random ==2 && j-1>=0 && this.unvisitedCell[i][j-1]!=null)
			this.choosenCell = this.labyrinth.getCell(i, j-1);
		else if (random == 3 && j+1 < this.labyrinth.getRows() &&  this.unvisitedCell[i][j+1]!=null)
			this.choosenCell = this.labyrinth.getCell(i, j+1);
		else return getChosenCell(i,j);
		return random;
	}


	public boolean hasAnUnvisitedNeighbor(int i , int j)
	{
		boolean result = false;
		if (i-1 >= 0 && this.unvisitedCell[i-1][j]!=null)
			result =  result||(this.unvisitedCell[i-1][j]!=null);
		if (j-1 >= 0 && this.unvisitedCell[i][j-1]!=null)
			result =  result||(this.unvisitedCell[i][j-1]!=null);
		if (i+1 < labyrinth.getLines() && this.unvisitedCell[i+1][j]!=null)
			result =  result||(this.unvisitedCell[i+1][j]!=null);
		if (j+1 < labyrinth.getRows() && this.unvisitedCell[i][j+1]!=null)
			result =  result||(this.unvisitedCell[i][j+1]!=null);
		return result;
	}

	public boolean stillUnvisitedCell()
	{
		for (int i=0 ; i<labyrinth.getLines();i++ )
		{
			for (int j = 0 ; j<labyrinth.getRows();j++)
			{
				if (this.unvisitedCell[i][j] !=null)
				{
					return true;
				}
			}
		}
		return false;
	}


	public void mazeGenrator() {
		//Make the initial cell the current cell and mark it as visited
		stack = new Stack<Cell>();
		currentCell = labyrinth.getCell(0, 0);
		unvisitedCell[0][0] = null;
		boolean stillUnvisitedCell = this.stillUnvisitedCell();
		int i,j;
		//While there are unvisited cells
		while(stillUnvisitedCell)
		{
			i = this.currentCell.getI();
			j = this.currentCell.getJ();
			unvisitedCell[i][j] = null;

			//If the current cell has any neighbours which have not been visited
			if (this.hasAnUnvisitedNeighbor(i,j))
			{
				//Choose randomly one of the unvisited neighbours
				int random = this.getChosenCell(i,j);

				//Push the current cell to the stack
				stack.push(this.currentCell);

				//Remove the wall between the current cell and the chosen cell
				if (random == 0)
				{
					//System.out.print("up - ");
					this.labyrinth.getCell(i, j).setNorthWall(false);
					this.labyrinth.getCell(i-1, j).setSouthWall(false);
				}
				else if (random == 1)
				{
					//System.out.print("down - ");
					this.labyrinth.getCell(i, j).setSouthWall(false);
					this.labyrinth.getCell(i+1, j).setNorthWall(false);
				}
				else if (random == 2)
				{
					//System.out.print("left - ");
					this.labyrinth.getCell(i, j).setWestWall(false);
					this.labyrinth.getCell(i, j-1).setEastWall(false);
				}
				else if (random == 3)
				{
					//System.out.print("right - ");
					this.labyrinth.getCell(i, j).setEastWall(false);
					this.labyrinth.getCell(i, j+1).setWestWall(false);
				}

				//Make the chosen cell the current cell and mark it as visited
				this.currentCell = this.choosenCell;
			}

			//Else if stack is not empty
			else if (!stack.isEmpty())
			{
				//Pop a cell from the stack and make it the current cell
				this.currentCell = stack.pop();
			}
			//else break;
			stillUnvisitedCell = this.stillUnvisitedCell();

		}
		setExit();
		labyrinth.autoset();
	}

	public void setExit()
	{
		if (!labyrinth.isHaveExit()){
			int i,j;
			do
			{
				i = (int)Math.floor(Math.random()*this.labyrinth.getLines());
				j = (int)Math.floor(Math.random()*this.labyrinth.getRows());
				//System.out.println("i : "+i+" j :"+j);
			}while (i==0 && j==0);
			this.labyrinth.getCell(i, j).setFinalCell(true);
			this.labyrinth.getCell(i, j).setGiveScore(false);
			this.labyrinth.getCell(i, j).setItsATrap(false);
			this.labyrinth.setHaveExit(true);
		}
	}

}
