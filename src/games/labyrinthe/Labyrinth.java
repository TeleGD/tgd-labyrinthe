package games.labyrinthe;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import games.labyrinthe.Cell;

public class Labyrinth {

	private int lines,rows;
	private Cell[][] cells;
	private boolean haveExit;


	public Labyrinth (int lines,int rows)
	{
		int rand;
		this.lines = lines;
		this.rows = rows;
		cells = new Cell[lines][rows];
		Cell cell;
		for(int i=0 ; i<lines;i++ )
		{
			for (int j=0;j<rows;j++)
			{
				cell = new Cell(i,j);
				cells[i][j]=cell;
				rand =(int) (Math.random()*10);
				if(rand < 3) cells[i][j].setGiveScore(true);
			}
		}

	}

	public int getLines()
	{
		return this.lines;
	}

	public int getRows()
	{
		return this.rows;
	}

	public Cell getCell(int i,int j)
	{
		return cells[i][j];
	}


	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException
	{
		for (Cell[] cells2 : cells)
		{
			for(Cell cell : cells2)
			{
				cell.update(arg0, arg1, arg2);
			}
		}
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		for (Cell[] cells2 : cells)
		{
			for(Cell cell : cells2)
			{
				cell.render(arg0, arg1, arg2);
			}
		}
	}

	public void autoset() throws SlickException
	{
		for (int i = 0 ; i<this.getLines() ; i++)
		{
			for (int j = 0 ; j<this.getRows() ; j++)
				{
					this.getCell(i, j).autoSetSprite();
				}
		}
	}

	public boolean isHaveExit() {
		return haveExit;
	}

	public void setHaveExit(boolean haveExit) {
		this.haveExit = haveExit;
	}

}
