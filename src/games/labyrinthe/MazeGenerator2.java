package games.labyrinthe;

import java.util.Stack;

public class MazeGenerator2
{

	private Labyrinth labyrinth;
	private Cell[][] unvisitedCell;
	private Stack<Cell> stack;
	private Cell currentCell;
	private Cell chosenCell;


	public MazeGenerator2(Labyrinth labyrinth)
	{
		stack = new Stack<Cell>();
		this.labyrinth = labyrinth;
		this.unvisitedCell = new Cell[this.labyrinth.getLines()][this.labyrinth.getRows()];
		for (int i = 0 ; i < labyrinth.getLines();i++)
		{
			for (int j = 0 ; j < labyrinth.getRows() ; j++)
			{
				this.unvisitedCell[i][j] = labyrinth.getCell(i, j);
			}
		}
	}

	public void destroyWall()
	{
		int random = (int)Math.random()*4;
		//Choose randomly one of the unvisited neighbours and Remove the wall between the current cell and the chosen cell
		switch(random)
		{
		case(0):
			if (this.currentCell.getI()-1 >= 0)
			{
				this.chosenCell = this.labyrinth.getCell(this.currentCell.getI()-1,this.currentCell.getJ());
				this.currentCell.setEastWall(false);
				this.chosenCell.setWestWall(false);
			}
		case (1):
			if (this.currentCell.getI()+1 < this.labyrinth.getLines() )
			{
				this.chosenCell = this.labyrinth.getCell(this.currentCell.getI()+1,this.currentCell.getJ());
				this.currentCell.setWestWall(false);
				this.chosenCell.setEastWall(false);
			}
		case (2):
			if (this.currentCell.getJ()-1 >= 0 )
			{
				this.chosenCell = this.labyrinth.getCell(this.currentCell.getI(),this.currentCell.getJ()-1);
				this.currentCell.setNorthWall(false);
				this.chosenCell.setSouthWall(false);
			}
		case(3):
			if (this.currentCell.getJ()+1 < this.labyrinth.getRows() )
			{
				this.chosenCell = this.labyrinth.getCell(this.currentCell.getI(),this.currentCell.getJ()+1);
				this.currentCell.setSouthWall(false);
				this.chosenCell.setNorthWall(false);
			}
		}
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

	public void mazeGenerate() {
		//Make the initial cell the current cell and mark it as visited
		stack = new Stack<Cell>();
		currentCell = labyrinth.getCell(0, 0);
		unvisitedCell[0][0] = null;
		boolean stillUnvisitedCell = this.stillUnvisitedCell();

		//While there are unvisited cells
		while (stillUnvisitedCell)
		{
			//If the current cell has any neighbours which have not been visited
			if(hasAnUnvisitedNeighbor(this.currentCell.getI(),this.currentCell.getJ()))
			{
				//Choose randomly one of the unvisited neighbours and Remove the wall between the current cell and the chosen cell
				if (this.currentCell.getI()-1 >= 0 && (unvisitedCell[this.currentCell.getI()-1][this.currentCell.getJ()]!=null))
				{
					this.chosenCell = this.labyrinth.getCell(this.currentCell.getI()-1,this.currentCell.getJ());
				}
				else if (this.currentCell.getI()+1 < this.labyrinth.getLines() && (unvisitedCell[this.currentCell.getI()+1][this.currentCell.getJ()]!=null))
				{
					this.chosenCell = this.labyrinth.getCell(this.currentCell.getI()+1,this.currentCell.getJ());
				}
				else if (this.currentCell.getJ()-1 >= 0 && (unvisitedCell[this.currentCell.getI()][this.currentCell.getJ()-1]!=null))
				{
					this.chosenCell = this.labyrinth.getCell(this.currentCell.getI(),this.currentCell.getJ()-1);
				}
				else if (this.currentCell.getJ()+1 < this.labyrinth.getRows() && (unvisitedCell[this.currentCell.getI()][this.currentCell.getJ()+1]!=null))
				{
					this.chosenCell = this.labyrinth.getCell(this.currentCell.getI(),this.currentCell.getJ()+1);
				}

				//Push the current cell to the stack
				stack.push(this.currentCell);

				//Make the chosen cell the current cell and mark it as visited
				this.currentCell = this.chosenCell;
				this.destroyWall();
				System.out.println("i : " +this.currentCell.getI()+" j : "+this.currentCell.getJ()+hasAnUnvisitedNeighbor(this.currentCell.getI(),this.currentCell.getJ()));
				this.unvisitedCell[this.currentCell.getI()][this.currentCell.getJ()]=null;
			}

			//Else if stack is not empty
			else if (!stack.isEmpty())
			{
				//Pop a cell from the stack and Make it the current cell
				this.currentCell = stack.pop();
				this.destroyWall();
			}
			else break;
			System.out.println(this.currentCell.getI() + "  "+this.currentCell.getJ()+"  "+hasAnUnvisitedNeighbor(this.currentCell.getI(),this.currentCell.getJ())+" "+stack.isEmpty());
		}
		this.labyrinth.autoset();
	}

}
