package games.labyrinthe.characters;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.labyrinthe.Cell;
import games.labyrinthe.Labyrinth;

public class Enemy {

	private Labyrinth labyrinth;
	private double x,y,nextX,nextY;
	private boolean moving;
	private int cellSize=64;
	private Cell cell;
	private int lastCell;
	private double speedX,speedY;

	public Enemy(Labyrinth labyrinth, int i, int j) {
		this.labyrinth = labyrinth;
		x = i*cellSize + 32 - 25;
		y = j*cellSize + 32 - 25;
		nextX=x;
		nextY=y;
		this.lastCell=1;
		this.getCell();
		speedX=0;
		speedY=0;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.cyan);
		context.fillRect((float)x,(float) y, 50, 50);
		context.drawString("nextX="+nextX, 1100, 20);
		context.drawString("nextY="+nextY, 1100, 40);
		context.drawString("lastCell="+lastCell, 1100, 60);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		this.getCell();
		if(this.nextX==x && this.nextY==y){
			moving=false;
			speedX=0;
			speedY=0;
		}
		if(!moving)
			System.out.println("oui");
			move();

		if(this.x>this.nextX-5 && this.x<this.nextX+5)
			x=this.nextX;
		if(this.y>this.nextY-5 && this.y<this.nextY+5)
			y=this.nextY;

		x+=speedX*delta;
		y+=speedY*delta;
	}

	private void move() { // 1: bas, 2:gauche, 3:haut, 4:droite
		switch (lastCell){
		case 1:
			if(!cell.isEastWall() && !cell.isNorthWall() && !cell.isWestWall()){
				double c=Math.random();
				if(c>0.7){moveUp();return;}
				else if(c>0.4){ moveRight();return;}
				else{ moveLeft();return;}
			}else if(!cell.isNorthWall() && !cell.isWestWall()){
				double c=Math.random();
				if(c>=0.5){moveUp();return;}
				else {moveLeft();return;}
			}else if(!cell.isNorthWall() && !cell.isEastWall()){
				double c=Math.random();
				if(c>=0.5){moveUp();return;}
				else {moveRight();return;}
			}else if(!cell.isWestWall() && !cell.isEastWall()){
				double c=Math.random();
				if(c>=0.5){moveLeft();return;}
				else {moveRight();return;}
			}else if(!cell.isEastWall()){moveRight();return;}
			else if(!cell.isNorthWall()){moveUp();return;}
			else if(!cell.isWestWall()){moveLeft();return;}
			else{moveDown();return;}
		case 2:
			if(!cell.isEastWall() && !cell.isNorthWall() && !cell.isSouthWall()){
				double c=Math.random();
				if(c>0.7){moveUp();return;}
				else if(c>0.4){ moveRight();return;}
				else{ moveDown();return;}
			}else if(!cell.isNorthWall() && !cell.isSouthWall()){
				double c=Math.random();
				if(c>=0.5){moveUp();return;}
				else {moveDown();return;}
			}else if(!cell.isNorthWall() && !cell.isEastWall()){
				double c=Math.random();
				if(c>=0.5){moveUp();return;}
				else {moveRight();return;}
			}else if(!cell.isSouthWall() && !cell.isEastWall()){
				double c=Math.random();
				if(c>=0.5){moveDown();return;}
				else {moveRight();return;}
			}else if(!cell.isEastWall()){moveRight();return;}
			else if(!cell.isNorthWall()){moveUp();return;}
			else if(!cell.isSouthWall()){moveDown();return;}
			else{moveLeft();return;}
		case 3:
			if(!cell.isEastWall() && !cell.isSouthWall() && !cell.isWestWall()){
				double c=Math.random();
				if(c>0.7){moveDown();return;}
				else if(c>0.4){ moveRight();return;}
				else{ moveLeft();return;}
			}else if(!cell.isSouthWall() && !cell.isWestWall()){
				double c=Math.random();
				if(c>=0.5){moveDown();return;}
				else {moveLeft();return;}
			}else if(!cell.isSouthWall() && !cell.isEastWall()){
				double c=Math.random();
				if(c>=0.5){moveDown();return;}
				else {moveRight();return;}
			}else if(!cell.isWestWall() && !cell.isEastWall()){
				double c=Math.random();
				if(c>=0.5){moveLeft();return;}
				else {moveRight();return;}
			}else if(!cell.isEastWall()){moveRight();return;}
			else if(!cell.isSouthWall()){moveDown();return;}
			else if(!cell.isWestWall()){moveLeft();return;}
			else{moveUp();return;}
		case 4:
			if(!cell.isSouthWall() && !cell.isNorthWall() && !cell.isWestWall()){
				double c=Math.random();
				if(c>0.7){moveUp();return;}
				else if(c>0.4){ moveDown();return;}
				else{ moveLeft();return;}
			}else if(!cell.isNorthWall() && !cell.isWestWall()){
				double c=Math.random();
				if(c>=0.5){moveUp();return;}
				else {moveLeft();return;}
			}else if(!cell.isNorthWall() && !cell.isSouthWall()){
				double c=Math.random();
				if(c>=0.5){moveUp();return;}
				else {moveDown();return;}
			}else if(!cell.isWestWall() && !cell.isSouthWall()){
				double c=Math.random();
				if(c>=0.5){moveLeft();return;}
				else {moveDown();return;}
			}else if(!cell.isSouthWall()){moveDown();return;}
			else if(!cell.isNorthWall()){moveUp();return;}
			else if(!cell.isWestWall()){moveLeft();return;}
			else{moveRight();return;}
		}
	}

	private void moveUp() {
		moving=true;
		this.nextY=this.y-64;
		this.nextX=this.x;
		speedY=-0.1;
		speedX=0;
		lastCell=1;
	}

	private void moveRight() {
		moving=true;
		this.nextY=this.y;
		this.nextX=this.x+64;
		speedY=0;
		speedX=0.1;
		lastCell=2;
	}

	private void moveLeft() {
		moving=true;
		this.nextY=this.y;
		this.nextX=this.x-64;
		speedY=0;
		speedX=-0.1;
		lastCell=4;
	}

	private void moveDown() {
		moving=true;
		this.nextY=this.y+64;
		this.nextX=this.x;
		speedY=0.1;
		speedX=0;
		lastCell=3;
	}

	public void getCell() {
		int i=(int) Math.floor(x/cellSize);
		int j=(int) Math.floor(y/cellSize);
		this.cell=labyrinth.getCell(i,j);
	}

}
