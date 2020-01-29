package games.labyrinthe.characters;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import games.labyrinthe.Cell;
import games.labyrinthe.World;

public class Player {

	private World world;
	private double x,y;
	private boolean up,down,right,left,updown,rightLeft;
	private int cellSize=64;
	private Cell cell;
	private double speedX,speedY;

	public Player(World world) {
		this.world = world;
		x = 100;
		y = 100;
		this.getCell();
	}

	public Player(World world, int i, int j) {
		this.world = world;
		x = i*cellSize + 32 - 250;
		y = j*cellSize + 32 - 25;
		this.getCell();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.blue);
		context.fillRect((float)x,(float) y, 50, 50);
		context.drawString("bonjour", 1100, 500);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		this.getCell();
		move();
		x+=speedX*delta;
		y+=speedY*delta;
	}

	public void keyPressed(int key, char c) {
		switch (key){
		case Input.KEY_UP:
			up=true;
			updown=false;
		break;

		case Input.KEY_DOWN:
			down=true;
			updown=true;
		break;

		case Input.KEY_LEFT:
			left=true;
			rightLeft=false;
		break;
		case Input.KEY_RIGHT:
			right=true;
			rightLeft=true;
		break;
		}
	}

	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			up=false;
			break;
		case Input.KEY_RIGHT:
			right=false;
			break;
		case Input.KEY_LEFT:
			left=false;
			break;
		case Input.KEY_DOWN:
			down=false;
			break;
		}
	}

	private void move() {
		speedX = 0;
		speedY = 0;
		if(((up && !down) || (up && down && !updown)) )
		{
			if(!(this.cell.isNorthWall())){
				speedY=-0.2;
			}
		}
		if(((down && !up) || (up && down && updown)) && !(this.cell.isSouthWall())){
				speedY=0.2;
		}
		if(((left && !right)|| (left && right && !rightLeft)) && !(this.cell.isWestWall()))
		{
				speedX = -0.2;
		}
		if(((!left && right)|| (left && right && rightLeft)) && !(this.cell.isEastWall()))
		{
				speedX = 0.2;
		}
	}

	public void getCell() {
		int i=(int) Math.floor(x/cellSize);
		int j=(int) Math.floor(y/cellSize);
		this.cell=world.getLabyrinth().getCell(i,j);
	}

}
